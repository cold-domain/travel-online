<template>
    <el-row style="background-color: #FFFFFF;padding: 5px 0;border-radius: 5px;">
        <el-row style="padding: 10px;margin: 0 5px;">
            <el-row>
                <span>
                    <el-button size="small"
                        style="background-color: rgb(96, 98, 102);color: rgb(247,248,249);border: none;"
                        class="customer" type="info" @click="add()"><i class="el-icon-plus"></i>新增景区门票</el-button>
                </span>
            </el-row>
        </el-row>
        <el-row style="margin: 0 15px;border-top: 1px solid rgb(245,245,245);">
            <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="scenicId" sortable width="120" label="景区ID"></el-table-column>
                <el-table-column prop="scenicName" label="关联景区"></el-table-column>
                <el-table-column prop="detail" width="200" label="门票介绍"></el-table-column>
                <el-table-column prop="number" width="100" label="数量"></el-table-column>
                <el-table-column prop="price" width="100" label="价格"></el-table-column>
                <el-table-column prop="discount" width="100" label="折扣">
                    <template slot-scope="scope">
                        <span>{{ scope.row.discount === null ? '无折扣' : scope.row.discount + '折' }}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" sortable width="168" label="创建时间"></el-table-column>
                <el-table-column prop="useStatus" width="98" label="是否可用">
                    <template slot-scope="scope">
                        <i v-if="!scope.row.useStatus" style="margin-right: 5px;" class="el-icon-warning"></i>
                        <i v-else style="margin-right: 5px;color: rgb(253, 199, 50);" class="el-icon-success"></i>
                        <el-tooltip v-if="scope.row.useStatus" class="item" effect="dark" content="门票可用时，用户方可购买"
                            placement="bottom-end">
                            <span style="text-decoration: underline;text-decoration-style: dashed;">可用</span>
                        </el-tooltip>
                        <span v-else>不可用</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="110">
                    <template slot-scope="scope">
                        <span class="text-button" @click="handleEdit(scope.row)">编辑</span>
                        <span class="text-button" @click="handleDelete(scope.row)">删除</span>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
        <!-- 操作面板 -->
        <el-dialog :show-close="false" :visible.sync="dialogOperation" width="32%">
            <div slot="title">
                <p class="dialog-title">{{ !isOperation ? '新增景区门票' : '修改景区门票信息' }}</p>
            </div>
            <div style="padding:0 20px;">
                <el-row style="margin-bottom: 20px;">
                    <span class="dialog-hover">门票数量</span>
                    <input class="dialog-input" v-model="data.number" placeholder="请输入" />
                    <span class="dialog-hover">价格</span>
                    <input class="dialog-input" v-model="data.price" placeholder="请输入" />
                    <span class="dialog-hover">折扣</span>
                    <input class="dialog-input" v-model="data.discount" placeholder="请输入" />
                    <div>
                        <div>
                            <span class="dialog-hover">介绍</span>
                        </div>
                        <el-input style="width: 100%;" type="textarea" :rows="2" placeholder="请输入内容"
                            v-model="data.detail">
                        </el-input>
                    </div>
                    <div style="margin: 10px 0;">
                        <span class="dialog-hover">景点</span>
                        <el-select v-model="data.scenicId" placeholder="关联景点">
                            <el-option v-for="scenic in scenics" :key="scenic.id" :label="scenic.name"
                                :value="scenic.id">
                            </el-option>
                        </el-select>
                    </div>
                    <div>
                        <span class="dialog-hover">可用状态</span>
                        <el-switch v-model="data.useStatus" active-text="可用" inactive-text="不可用">
                        </el-switch>
                    </div>
                </el-row>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" v-if="!isOperation"
                    style="background-color: rgb(96, 98, 102);color: rgb(247,248,249);border: none;" class="customer"
                    type="info" @click="addOperation()">新增</el-button>
                <el-button size="small" v-else
                    style="background-color: rgb(96, 98, 102);color: rgb(247,248,249);border: none;" class="customer"
                    type="info" @click="updateOperation()">修改</el-button>
                <el-button class="customer" size="small" style="background-color: rgb(211, 241, 241);border: none;"
                    @click="cannel()">取消</el-button>
            </span>
        </el-dialog>
    </el-row>
</template>

<script>
export default {
    data() {
        return {
            data: {},
            filterText: '',
            totalItems: 0,
            dialogOperation: false, // 开关
            isOperation: false, // 开关-标识新增或修改
            tableData: [],
            selectedRows: [],
            scenicTicketQueryDto: {}, // 搜索条件
            scenics: [],
            searchTime: [],
            statusList: [{ value: null, label: '全部' }, { value: 0, label: '不可用' }, { value: 1, label: '可用' }],
        };
    },
    created() {
        this.fetchFreshData();
        this.fetchScenics();
    },
    methods: {
        // 获取名下的景点信息
        fetchScenics() {
            this.$axios.post('/scenic/queryVendorScenic', {}).then(res => {
                if (res.data.code === 200) {
                    this.scenics = res.data.data;
                }
            }).catch(error => {
                console.error("查询法人信息异常：", error);
            });
        },
        // 置位
        cannel() {
            this.data = {};
            this.dialogOperation = false;
            this.isOperation = false;
        },
        // 批量删除数据
        async batchDelete() {
            if (!this.selectedRows.length) {
                this.$message(`未选中任何数据`);
                return;
            }
            const confirmed = await this.$swalConfirm({
                title: '删除景区门票数据',
                text: `删除后不可恢复，是否继续？`,
                icon: 'warning',
            });
            if (confirmed) {
                try {
                    let ids = this.selectedRows.map(entity => entity.id);
                    const response = await this.$axios.post(`/scenicTicket/batchDelete`, ids);
                    if (response.data.code === 200) {
                        this.$swal.fire({
                            title: '删除提示',
                            text: response.data.msg,
                            icon: 'success',
                            showConfirmButton: false,
                            timer: 2000,
                        });
                        this.fetchFreshData();
                        return;
                    }
                } catch (e) {
                    this.$swal.fire({
                        title: '错误提示',
                        text: e,
                        icon: 'error',
                        showConfirmButton: false,
                        timer: 2000,
                    });
                    console.error(`景区门票信息删除异常：`, e);
                }
            }
        },
        // 修改信息
        async updateOperation() {
            try {
                const response = await this.$axios.put('/scenicTicket/update', this.data);
                this.clearFormData();
                this.$swal.fire({
                    title: '景区门票信息修改',
                    text: response.data.msg,
                    icon: response.data.code === 200 ? 'success' : 'error',
                    showConfirmButton: false,
                    timer: 1000,
                });
                if (response.data.code === 200) {
                    this.cannel();
                    this.fetchFreshData();
                }
            } catch (error) {
                console.error('提交表单时出错:', error);
                this.$message.error('提交失败，请稍后再试！');
            }
        },
        // 信息新增
        async addOperation() {
            try {
                const response = await this.$axios.post('/scenicTicket/save', this.data);
                this.$message[response.data.code === 200 ? 'success' : 'error'](response.data.msg);
                if (response.data.code === 200) {
                    this.cannel();
                    this.fetchFreshData();
                }
            } catch (error) {
                console.error('提交表单时出错:', error);
                this.$message.error('提交失败，请稍后再试！');
            }
        },
        clearFormData() {
            this.data = {};
        },
        async fetchFreshData() {
            try {
                const response = await this.$axios.post('/scenicTicket/queryScenicTicket');
                const { data } = response;
                this.tableData = data.data;
            } catch (error) {
                console.error('查询景区门票信息异常:', error);
            }
        },
        add() {
            this.dialogOperation = true;
        },
        handleFilter() {
            this.currentPage = 1;
            this.fetchFreshData();
        },
        handleFilterClear() {
            this.filterText = '';
            this.handleFilter();
        },
        handleSizeChange(val) {
            this.pageSize = val;
            this.currentPage = 1;
            this.fetchFreshData();
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.fetchFreshData();
        },
        handleEdit(row) {
            this.dialogOperation = true;
            this.isOperation = true;
            this.data = { ...row }
        },
        handleDelete(row) {
            this.selectedRows.push(row);
            this.batchDelete();
        }
    },
};
</script>
<style scoped lang="scss"></style>