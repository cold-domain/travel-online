<template>
    <el-row style="background-color: #FFFFFF;padding: 5px 0;border-radius: 5px;">
        <el-row style="padding: 10px 5px;margin: 0 5px;">
            <el-row>
                <el-select @change="fetchFreshData" size="small" v-model="scenicQueryDto.categoryId"
                    style="margin-left: 5px;" placeholder="所属类别">
                    <el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.id">
                    </el-option>
                </el-select>
                <el-select @change="fetchFreshData" size="small" v-model="scenicQueryDto.status"
                    style="margin-left: 5px;" placeholder="景点状态">
                    <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
                <el-date-picker size="small" style="width: 220px;margin-left: 5px;" v-model="searchTime"
                    type="daterange" range-separator="至" start-placeholder="创建开始" end-placeholder="创建结束">
                </el-date-picker>
                <el-input size="small" style="width: 188px;margin-left: 5px;margin-right: 6px;"
                    v-model="scenicQueryDto.name" placeholder="景点名" clearable @clear="handleFilterClear">
                    <el-button slot="append" @click="handleFilter" icon="el-icon-search"></el-button>
                </el-input>
                <span style="float: right;">
                    <el-button size="small"
                        style="background-color: rgb(96, 98, 102);color: rgb(247,248,249);border: none;"
                        class="customer" type="info" @click="add()"><i class="el-icon-plus"></i>新增景点</el-button>
                </span>
            </el-row>
        </el-row>
        <el-row style="margin: 0 15px;border-top: 1px solid rgb(245,245,245);">
            <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="cover" label="景区封面" width="120px">
                    <template slot-scope="scope">
                        <img :src="scope.row.cover" style="width: 88px;height: 55px;border-radius: 5px;" />
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="景点名"></el-table-column>
                <el-table-column prop="address" label="所在地" width="160px"></el-table-column>
                <el-table-column prop="categoryName" label="所属分类" width="120px"></el-table-column>
                <el-table-column prop="vendorName" label="供应商" width="120px"></el-table-column>
                <el-table-column prop="ratingScore" label="评分" width="60px">
                    <template slot-scope="scope">
                        <span>{{ scope.row.ratingScore === null ? 0 : scope.row.ratingScore }}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" sortable width="168" label="创建时间"></el-table-column>
                <el-table-column prop="isWord" width="108" label="状态">
                    <template slot-scope="scope">
                        <i v-if="!scope.row.status" style="margin-right: 5px;" class="el-icon-warning"></i>
                        <i v-else style="margin-right: 5px;color: rgb(253, 199, 50);" class="el-icon-success"></i>
                        <el-tooltip v-if="scope.row.status" class="item" effect="dark" content="如果是不可用状态，暂停景点服务"
                            placement="bottom-end">
                            <span style="text-decoration: underline;text-decoration-style: dashed;">可用</span>
                        </el-tooltip>
                        <span v-else>不可用</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <span class="text-button" @click="scenicLineHandle(scope.row)">景点路线</span>
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
        <el-dialog :show-close="false" :visible.sync="dialogOperation" width="45%">
            <div slot="title">
                <p class="dialog-title">{{ !isOperation ? '新增景点' : '修改景点信息' }}</p>
            </div>
            <div style="padding:0 20px;">
                <el-row>
                    <span class="dialog-hover">景点封面</span>
                    <el-upload class="avatar-uploader" action="/api/online-travel-sys/v1.0/file/upload"
                        :show-file-list="false" :on-success="handleCoverSuccess">
                        <img v-if="cover" :src="cover" style="width: 250px;height: 150px;">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-row>
                <el-row style="margin-bottom: 20px;">
                    <span class="dialog-hover">景点名称</span>
                    <input class="dialog-input" v-model="data.name" placeholder="请输入" />
                    <span class="dialog-hover">景点所在地址</span>
                    <input class="dialog-input" v-model="data.address" placeholder="请输入" />
                    <div>
                        <div>
                            <span class="dialog-hover">景点详情</span>
                        </div>
                        <!-- 这里原来的，单词写错..... v变c即可....-->
                        <!-- <Editor height="300px" :reveiveContent="data.detail" @on-receive="onReceive" /> -->
                        <Editor height="300px" :receiveContent="data.detail" @on-receive="onReceive" />
                    </div>
                    <div style="margin: 10px 0;">
                        <span class="dialog-hover">景点分类</span>
                        <el-select v-model="data.categoryId" placeholder="请选择分类">
                            <el-option v-for="category in categories" :key="category.id" :label="category.name"
                                :value="category.id">
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
        <el-dialog :show-close="false" :visible.sync="dialogLineOperation" width="28%">
            <div slot="title">
                <p class="dialog-title">{{ !isLineOperation ? '新增景点路线' : '修改景点路线信息' }}</p>
            </div>
            <div style="padding:0 20px;">
                <el-row>
                    <span class="dialog-hover">路线封面</span>
                    <el-upload class="avatar-uploader" action="/api/online-travel-sys/v1.0/file/upload"
                        :show-file-list="false" :on-success="handleCoverSuccess">
                        <img v-if="cover" :src="cover" style="width: 250px;height: 150px;">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-row>
                <el-row style="margin-bottom: 20px;">
                    <div>
                        <span class="dialog-hover">景点简介</span>
                    </div>
                    <el-input style="width: 100%;" type="textarea" :rows="2" placeholder="请输入内容"
                        v-model="scenicLine.detail">
                    </el-input>
                    <div>
                        <span class="dialog-hover">优先级</span>
                    </div>
                    <el-slider v-model="scenicLine.level"></el-slider>
                </el-row>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" v-if="!isLineOperation"
                    style="background-color: rgb(96, 98, 102);color: rgb(247,248,249);border: none;" class="customer"
                    type="info" @click="addLineOperation()">新增路线</el-button>
                <el-button size="small" v-else
                    style="background-color: rgb(96, 98, 102);color: rgb(247,248,249);border: none;" class="customer"
                    type="info" @click="updateLineOperation()">修改路线</el-button>
                <el-button class="customer" size="small" style="background-color: rgb(211, 241, 241);border: none;"
                    @click="cannelLine()">取消</el-button>
            </span>
        </el-dialog>
        <el-drawer title="景点路线" width="35%" :visible.sync="drawer" :direction="direction">
            <div class="add-line">
                <span @click="addLine" style="cursor: pointer;font-size: 14px">
                    <i class="el-icon-plus"></i>
                    添加路线
                </span>
            </div>
            <div v-if="scenicLines.length === 0">
                <el-empty description="暂无路线信息"></el-empty>
            </div>
            <div v-else>
                <div>
                    <el-timeline>
                        <el-timeline-item v-for="(scenicLine, index) in scenicLines" :key="index"
                            :timestamp="scenicLine.createTime" placement="top">
                            <el-card>
                                <div>
                                    <img style="width: 100%;height: 200px;border-radius: 5px;" :src="scenicLine.cover"
                                        alt="路线封面">
                                </div>
                                <p style="margin-bottom: 8px;font-size: 14px;color: rgb(97, 97, 97);">{{
                                    scenicLine.detail }}
                                </p>
                                <div>
                                    <el-tooltip class="item" effect="dark" content="修改景点路线" placement="bottom">
                                        <el-button @click="updateLine(scenicLine)" type="primary" icon="el-icon-edit"
                                            circle></el-button>
                                    </el-tooltip>
                                    <el-tooltip class="item" effect="dark" content="删除景点路线" placement="bottom">
                                        <el-button @click="deleteLine(scenicLine)" type="danger" icon="el-icon-delete"
                                            circle></el-button>
                                    </el-tooltip>
                                </div>
                            </el-card>
                        </el-timeline-item>
                    </el-timeline>
                </div>
            </div>
        </el-drawer>
    </el-row>
</template>

<script>
import Editor from "@/components/Editor"
export default {
    components: { Editor },
    data() {
        return {
            scenicLine: {},
            data: {},
            filterText: '',
            cover: '',
            currentPage: 1,
            drawer: false, // 控制景点路线的抽屉开关
            direction: 'rtl', // right to left
            pageSize: 10,
            totalItems: 0,
            dialogLineOperation: false, // 景点路线弹窗确认
            isLineOperation: false, // 景点路线操作时显示文本开关
            dialogOperation: false, // 开关
            isOperation: false, // 开关-标识新增或修改
            tableData: [],
            selectedRows: [],
            scenicQueryDto: {}, // 搜索条件
            categories: [],
            searchTime: [],
            scenicLines: [],
            users: [],
            isAuditList: [{ value: null, label: '全部' }, { value: 0, label: '未审核' }, { value: 1, label: '已审核' }],
            statusList: [{ value: null, label: '全部' }, { value: 0, label: '不可用' }, { value: 1, label: '可用' }],
        };
    },
    created() {
        this.fetchFreshData();
        this.fetchUsers();
        this.fetchCategories();
    },
    methods: {
        onReceive(content) {
            console.log("编辑景点信息回调：" + JSON.stringify(content));
            this.data.detail = content;
        },
        // 修改景点信息
        updateLine(scenicLine) {
            this.dialogLineOperation = true;
            this.isLineOperation = true;
            this.cover = scenicLine.cover;
            this.scenicLine = scenicLine;
        },
        // 删除景点信息
        deleteLine(scenicLine) {
            this.$confirm('此操作将删除该景区路线, 是否继续?', '操作提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let ids = [scenicLine.id];
                this.$axios.post(`/scenicLine/batchDelete`, ids).then(res => {
                    if (res.data.code === 200) {
                        this.$message.success('景点路线删除成功');
                        this.fetchScenicLines(this.data.id);
                    }
                })
            }).catch(() => {
                console.log("操作取消");
            });
        },
        cannelLine() {
            this.scenicLine = {};
            this.dialogLineOperation = false;
            this.isLineOperation = false;
            this.cover = '';
        },
        addLine() {
            this.dialogLineOperation = true;
        },
        scenicLineHandle(scenic) {
            this.data = scenic;
            this.scenicLine.scenicId = scenic.id;
            this.fetchScenicLines(scenic.id);
        },
        // 请求景点路线信息
        fetchScenicLines(scenicId) {
            const scenicLineQueryDto = { scenicId: scenicId }
            this.$axios.post('/scenicLine/query', scenicLineQueryDto).then(res => {
                if (res.data.code === 200) {
                    this.scenicLines = res.data.data;
                    this.drawer = true;
                }
            }).catch(error => {
                this.$message.error('查询景点路线信息异常');
                console.error("查询景点路线信息异常：", error);
            });
        },
        // 封面上传
        handleCoverSuccess(res, file) {
            this.$notify({
                duration: 1500,
                title: '封面上传',
                message: res.code === 200 ? '上传成功' : '上传失败',
                type: res.code === 200 ? 'success' : 'error'
            });
            // 上传成功则更新封面
            if (res.code === 200) {
                this.cover = res.data;
            }
        },
        // 获取景点分类
        fetchCategories() {
            this.$axios.post('/scenicCategory/query', {}).then(res => {
                if (res.data.code === 200) {
                    this.categories = res.data.data;
                }
            }).catch(error => {
                console.error("查询景点分类信息异常：", error);
            });
        },
        // 获取供应商信息
        fetchUsers() {
            this.$axios.post('/vendor/query', {}).then(res => {
                if (res.data.code === 200) {
                    this.users = res.data.data;
                }
            }).catch(error => {
                console.error("查询供应商信息异常：", error);
            });
        },
        // 置位
        cannel() {
            this.data = {};
            this.cover = '';
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
                title: '删除景点数据',
                text: `删除后不可恢复，是否继续？`,
                icon: 'warning',
            });
            if (confirmed) {
                try {
                    let ids = this.selectedRows.map(entity => entity.id);
                    const response = await this.$axios.post(`/scenic/batchDelete`, ids);
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
                    console.error(`景点信息删除异常：`, e);
                }
            }
        },
        // 修改路线信息
        async updateLineOperation() {
            try {
                this.scenicLine.cover = this.cover;
                const response = await this.$axios.put('/scenicLine/update', this.scenicLine);
                this.$swal.fire({
                    title: '景点路线信息修改',
                    text: response.data.msg,
                    icon: response.data.code === 200 ? 'success' : 'error',
                    showConfirmButton: false,
                    timer: 1000,
                });
                if (response.data.code === 200) {
                    this.fetchScenicLines(this.data.id);
                    this.cannelLine();
                }
            } catch (error) {
                console.error('提交表单时出错:', error);
                this.$message.error('提交失败，请稍后再试！');
            }
        },
        // 修改信息
        async updateOperation() {
            try {
                this.data.cover = this.cover;
                const response = await this.$axios.put('/scenic/update', this.data);
                this.clearFormData();
                this.$swal.fire({
                    title: '景点信息修改',
                    text: response.data.msg,
                    icon: response.data.code === 200 ? 'success' : 'error',
                    showConfirmButton: false,
                    timer: 1000,
                });
                if (response.data.code === 200) {
                    this.cannel();
                    this.fetchFreshData(this.data);
                }
            } catch (error) {
                console.error('提交表单时出错:', error);
                this.$message.error('提交失败，请稍后再试！');
            }
        },
        // 信息新增
        async addLineOperation() {
            try {
                this.scenicLine.scenicId = this.data.id;
                this.scenicLine.cover = this.cover;
                const response = await this.$axios.post('/scenicLine/save', this.scenicLine);
                this.$message[response.data.code === 200 ? 'success' : 'error'](response.data.msg);
                if (response.data.code === 200) {
                    this.cannelLine();
                    this.fetchScenicLines(this.data.id);
                }
            } catch (error) {
                console.error('提交表单时出错:', error);
                this.$message.error('提交失败，请稍后再试！');
            }
        },
        // 信息新增
        async addOperation() {
            try {
                this.data.cover = this.cover;
                const response = await this.$axios.post('/scenic/save', this.data);
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
                    ...this.scenicQueryDto
                };
                const response = await this.$axios.post('/scenic/queryVendorScenic', params);
                const { data } = response;
                this.tableData = data.data;
                this.totalItems = data.total;
            } catch (error) {
                console.error('查询景点信息异常:', error);
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
            console.log("编辑的景点信息：" + JSON.stringify(row));
            this.cover = row.cover;
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
<style scoped lang="scss">
.add-line {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgb(64, 158, 255);
    padding: 10px 20px;
    margin: 10px 20px;
    color: rgb(245, 245, 245);
    border-radius: 5px;
}
</style>