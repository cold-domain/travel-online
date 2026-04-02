<template>
    <el-row style="background-color: #FFFFFF;padding: 5px 0;border-radius: 5px;">
        <el-row style="padding: 10px 5px;margin: 0 5px;">
            <el-row>
                <el-select @change="fetchFreshData" size="small" v-model="scenicStrategyQueryDto.isAudit"
                    style="margin-left: 5px;" placeholder="审核状态">
                    <el-option v-for="item in isAuditList" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
                <el-date-picker size="small" style="width: 220px;margin-left: 5px;" v-model="searchTime"
                    type="daterange" range-separator="至" start-placeholder="创建开始" end-placeholder="创建结束">
                </el-date-picker>
                <el-input size="small" style="width: 188px;margin-left: 5px;margin-right: 6px;"
                    v-model="scenicStrategyQueryDto.title" placeholder="攻略标题" clearable @clear="handleFilterClear">
                    <el-button slot="append" @click="handleFilter" icon="el-icon-search"></el-button>
                </el-input>
            </el-row>
        </el-row>
        <el-row style="margin: 0 15px;border-top: 1px solid rgb(245,245,245);">
            <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="cover" label="攻略图" width="120px">
                    <template slot-scope="scope">
                        <img :src="scope.row.cover" style="width: 88px;height: 55px;border-radius: 5px;" />
                    </template>
                </el-table-column>
                <el-table-column prop="title" label="攻略标题"></el-table-column>
                <el-table-column prop="userId" sortable label="发表者ID" width="110px"></el-table-column>
                <el-table-column prop="userName" label="发表者" width="110px"></el-table-column>
                <el-table-column prop="scenicId" sortable label="景点ID" width="90px"></el-table-column>
                <el-table-column prop="scenicName" label="关联景点" width="120px"></el-table-column>
                <el-table-column prop="createTime" sortable width="168" label="创建时间"></el-table-column>
                <el-table-column prop="isWord" width="108" label="审核状态">
                    <template slot-scope="scope">
                        <i v-if="!scope.row.isAudit" style="margin-right: 5px;" class="el-icon-warning"></i>
                        <i v-else style="margin-right: 5px;color: rgb(253, 199, 50);" class="el-icon-success"></i>
                        <el-tooltip v-if="!scope.row.isAudit" class="item" effect="dark" content="如果未审核，不显示于用户端"
                            placement="bottom-end">
                            <span style="text-decoration: underline;text-decoration-style: dashed;">未审核</span>
                        </el-tooltip>
                        <span v-else>已审核</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <span v-if="!scope.row.isAudit" class="text-button" @click="auditStrategy(scope.row)">审核</span>
                        <span class="text-button" @click="viewStrategyContent(scope.row)">查看攻略</span>
                        <span class="text-button" @click="handleDelete(scope.row)">删除</span>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination style="margin:10px 0;" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage" :page-sizes="[10, 20]" :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="totalItems"></el-pagination>
        </el-row>
        <el-drawer title="查看内容" width="35%" :visible.sync="drawer" :direction="direction">
            <div style="margin: 20px;" v-html="data.content"></div>
        </el-drawer>
    </el-row>
</template>

<script>
export default {
    data() {
        return {
            data: {},
            filterText: '',
            cover: '',
            currentPage: 1,
            drawer: false, // 控制攻略路线的抽屉开关
            direction: 'rtl', // right to left
            pageSize: 10,
            totalItems: 0,
            tableData: [],
            selectedRows: [],
            scenicStrategyQueryDto: {}, // 搜索条件
            searchTime: [],
            isAuditList: [{ value: null, label: '全部' }, { value: 0, label: '未审核' }, { value: 1, label: '已审核' }],
        };
    },
    created() {
        this.fetchFreshData();
    },
    methods: {
        viewStrategyContent(scenicStrategy) {
            this.data = scenicStrategy;
            this.drawer = true;
        },
        // 审核策略
        async auditStrategy(scenicStrategy) {
            const confirmed = await this.$swalConfirm({
                title: '攻略审核',
                text: `是否审核通过【${scenicStrategy.title}】攻略`,
                icon: 'warning',
            });
            if (confirmed) {
                try {
                    const response = await this.$axios.post(`/scenicStrategy/audit/${scenicStrategy.id}`, {});
                    if (response.data.code === 200) {
                        this.$swal.fire({
                            title: '审核结果',
                            text: response.data.msg,
                            icon: 'success',
                            showConfirmButton: false,
                            timer: 1000,
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
                    console.error(`攻略审核异常：`, e);
                }
            }
        },
        // 删除攻略信息
        deleteLine(scenicLine) {
            this.$confirm('此操作将删除该景区路线, 是否继续?', '操作提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let ids = [scenicLine.id];
                this.$axios.post(`/scenicStrategyStrategy/batchDelete`, ids).then(res => {
                    if (res.data.code === 200) {
                        this.$message.success('攻略路线删除成功');
                        this.fetchScenicLines(this.data.id);
                    }
                })
            }).catch(() => {
                console.log("操作取消");
            });
        },
        // 批量删除数据
        async batchDelete() {
            if (!this.selectedRows.length) {
                this.$message(`未选中任何数据`);
                return;
            }
            const confirmed = await this.$swalConfirm({
                title: '删除攻略数据',
                text: `删除后不可恢复，是否继续？`,
                icon: 'warning',
            });
            if (confirmed) {
                try {
                    let ids = this.selectedRows.map(entity => entity.id);
                    const response = await this.$axios.post(`/scenicStrategy/batchDelete`, ids);
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
                    console.error(`攻略信息删除异常：`, e);
                }
            }
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
                    ...this.scenicStrategyQueryDto
                };
                const response = await this.$axios.post('/scenicStrategy/query', params);
                const { data } = response;
                this.tableData = data.data;
                this.totalItems = data.total;
            } catch (error) {
                console.error('查询攻略信息异常:', error);
            }
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
<style scoped lang="scss"></style>