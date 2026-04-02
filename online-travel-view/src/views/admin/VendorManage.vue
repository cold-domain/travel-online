<template>
    <el-row style="background-color: #FFFFFF;padding: 5px 0;border-radius: 5px;">
        <el-row style="padding: 10px;margin: 0 5px;">
            <el-row>
                <el-select @change="fetchFreshData" size="small" v-model="vendorQueryDto.isAudit" placeholder="审核状态">
                    <el-option v-for="item in isAuditList" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
                <el-select @change="fetchFreshData" size="small" v-model="vendorQueryDto.status"
                    style="margin-left: 5px;" placeholder="可用状态">
                    <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
                <el-date-picker size="small" style="width: 220px;margin-left: 5px;" v-model="searchTime"
                    type="daterange" range-separator="至" start-placeholder="申请开始" end-placeholder="申请结束">
                </el-date-picker>
                <el-input size="small" style="width: 188px;margin-left: 5px;margin-right: 6px;"
                    v-model="vendorQueryDto.name" placeholder="供应商名" clearable @clear="handleFilterClear">
                    <el-button slot="append" @click="handleFilter" icon="el-icon-search"></el-button>
                </el-input>
                <span style="float: right;">
                    <el-button size="small"
                        style="background-color: rgb(96, 98, 102);color: rgb(247,248,249);border: none;"
                        class="customer" type="info" @click="add()"><i class="el-icon-plus"></i>新增供应商</el-button>
                </span>
            </el-row>
        </el-row>
        <el-row style="margin: 0 15px;border-top: 1px solid rgb(245,245,245);">
            <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="name" label="供应商名"></el-table-column>
                <el-table-column prop="userId" sortable width="120" label="负责人ID"></el-table-column>
                <el-table-column prop="userName" width="70" label="负责人"></el-table-column>
                <el-table-column prop="concatPerson" width="100" label="联系人"></el-table-column>
                <el-table-column prop="concatPhone" width="130" label="联系电话"></el-table-column>
                <el-table-column prop="workAddress" width="130" label="工作地点"></el-table-column>
                <el-table-column prop="createTime" sortable width="168" label="创建时间"></el-table-column>
                <el-table-column prop="isWord" width="98" label="是否审核">
                    <template slot-scope="scope">
                        <i v-if="!scope.row.isAudit" style="margin-right: 5px;" class="el-icon-warning"></i>
                        <i v-else style="margin-right: 5px;color: rgb(253, 199, 50);" class="el-icon-success"></i>
                        <el-tooltip v-if="scope.row.isAudit" class="item" effect="dark" content="审核通过后，供应商才可以发布服务或者商品"
                            placement="bottom-end">
                            <span style="text-decoration: underline;text-decoration-style: dashed;">已审核</span>
                        </el-tooltip>
                        <span v-else>未审核</span>
                    </template>
                </el-table-column>
                <el-table-column prop="isWord" width="108" label="状态">
                    <template slot-scope="scope">
                        <i v-if="!scope.row.status" style="margin-right: 5px;" class="el-icon-warning"></i>
                        <i v-else style="margin-right: 5px;color: rgb(253, 199, 50);" class="el-icon-success"></i>
                        <el-tooltip v-if="scope.row.status" class="item" effect="dark" content="不可用状态，暂停供应商服务"
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
            <el-pagination style="margin:10px 0;" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage" :page-sizes="[10, 20]" :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="totalItems"></el-pagination>
        </el-row>
        <!-- 操作面板 -->
        <el-dialog :show-close="false" :visible.sync="dialogOperation" width="32%">
            <div slot="title">
                <p class="dialog-title">{{ !isOperation ? '新增供应商' : '修改供应商信息' }}</p>
            </div>
            <div style="padding:0 20px;">
                <el-row style="margin-bottom: 20px;">
                    <span class="dialog-hover">供应商名称</span>
                    <input class="dialog-input" v-model="data.name" placeholder="请输入" />
                    <span class="dialog-hover">联系人</span>
                    <input class="dialog-input" v-model="data.concatPerson" placeholder="请输入" />
                    <span class="dialog-hover">联系电话</span>
                    <input class="dialog-input" v-model="data.concatPhone" placeholder="请输入" />
                    <span class="dialog-hover">电子邮箱</span>
                    <input class="dialog-input" v-model="data.email" placeholder="电子邮箱" />
                    <span class="dialog-hover">产品类型</span>
                    <input class="dialog-input" v-model="data.productType" placeholder="请输入" />
                    <span class="dialog-hover">工作地点</span>
                    <input class="dialog-input" v-model="data.workAddress" placeholder="请输入" />
                    <div style="margin-block: 10px;">
                        <span class="dialog-hover">是否审核</span>
                        <el-switch v-model="data.isAudit" active-text="已审核" inactive-text="未审核">
                        </el-switch>
                    </div>
                    <div>
                        <span class="dialog-hover">可用状态</span>
                        <el-switch v-model="data.status" active-text="可用" inactive-text="不可用">
                        </el-switch>
                    </div>
                    <div style="margin: 10px 0;">
                        <span class="dialog-hover">法人用户</span>
                        <el-select v-model="data.userId" placeholder="请选择法人用户">
                            <el-option v-for="user in users" :key="user.id" :label="user.userName" :value="user.id">
                            </el-option>
                        </el-select>
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
            currentPage: 1,
            pageSize: 10,
            totalItems: 0,
            dialogOperation: false, // 开关
            isOperation: false, // 开关-标识新增或修改
            tableData: [],
            selectedRows: [],
            vendorQueryDto: {}, // 搜索条件
            users: [],
            searchTime: [],
            isAuditList: [{ value: null, label: '全部' }, { value: 0, label: '未审核' }, { value: 1, label: '已审核' }],
            statusList: [{ value: null, label: '全部' }, { value: 0, label: '不可用' }, { value: 1, label: '可用' }],
        };
    },
    created() {
        this.fetchFreshData();
        this.fetchUsers();
    },
    methods: {
        // 获取法人用户信息
        fetchUsers() {
            this.$axios.post('/user/query', {}).then(res => {
                if (res.data.code === 200) {
                    this.users = res.data.data;
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
                title: '删除供应商数据',
                text: `删除后不可恢复，是否继续？`,
                icon: 'warning',
            });
            if (confirmed) {
                try {
                    let ids = this.selectedRows.map(entity => entity.id);
                    const response = await this.$axios.post(`/vendor/batchDelete`, ids);
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
                    console.error(`供应商信息删除异常：`, e);
                }
            }
        },
        // 修改信息
        async updateOperation() {
            try {
                const response = await this.$axios.put('/vendor/update', this.data);
                this.clearFormData();
                this.$swal.fire({
                    title: '供应商信息修改',
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
                const response = await this.$axios.post('/vendor/save', this.data);
                if (response.data.code === 200) {
                    this.cannel();
                    this.fetchFreshData();
                    this.$notify({
                        dyration: 1000,
                        title: '供应商新增',
                        message: '新增成功',
                        type: 'success'
                    });
                } else {
                    this.$notify({
                        dyration: 1000,
                        title: '供应商新增',
                        message: response.data.msg,
                        type: 'info'
                    });
                }
            } catch (error) {
                console.error('提交表单时出错:', error);
            }
        },
        clearFormData() {
            this.data = {};
        },
        async fetchFreshData() {
            try {
                this.tableData = [];
                let startTime = null;
                let endTime = null;

                if (this.searchTime != null && this.searchTime.length === 2) {
                    const [startDate, endDate] = this.searchTime;

                    // 设置开始时间为当天的 00:00:00
                    startTime = new Date(startDate);
                    startTime.setHours(0, 0, 0, 0);

                    // 调整时区偏移
                    const timeZoneOffset = startTime.getTimezoneOffset();
                    startTime = new Date(startTime.getTime() - timeZoneOffset * 60 * 1000);

                    // 设置结束时间为当天的 23:59:59
                    endTime = new Date(endDate);
                    endTime.setHours(23, 59, 59, 999);

                    // 调整时区偏移
                    endTime = new Date(endTime.getTime() - timeZoneOffset * 60 * 1000);

                    // 将时间转换为 ISO 格式（如果需要）
                    startTime = startTime.toISOString();
                    endTime = endTime.toISOString();

                }
                // 请求参数
                const params = {
                    current: this.currentPage,
                    size: this.pageSize,
                    key: this.filterText,
                    startTime: startTime,
                    endTime: endTime,
                    ...this.vendorQueryDto
                };
                const response = await this.$axios.post('/vendor/query', params);
                const { data } = response;
                this.tableData = data.data;
                this.totalItems = data.total;
            } catch (error) {
                console.error('查询供应商信息异常:', error);
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