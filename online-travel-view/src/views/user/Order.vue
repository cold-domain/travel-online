<template>
    <el-row style="background-color: #FFFFFF;padding: 5px 30px;border-radius: 5px;">
        <el-row>
            <h1 style="margin-inline: 20px;">门票订单详情</h1>
            <el-row style="padding: 10px;margin: 0 5px;">
                <el-row>
                    <el-select @change="fetchFreshData" size="small" v-model="scenicTicketOrderQueryDto.payStatus"
                        style="margin-left: 5px;" placeholder="支付状态">
                        <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                    <el-date-picker size="small" style="width: 220px;margin-left: 5px;" v-model="searchTime"
                        type="daterange" range-separator="至" start-placeholder="创建开始" end-placeholder="创建结束">
                    </el-date-picker>
                </el-row>
            </el-row>
            <el-row style="margin: 0 15px;border-top: 1px solid rgb(245,245,245);">
                <el-table :data="tableData" style="width: 100%">
                    <el-table-column prop="scenicId" sortable width="120" label="景点ID"></el-table-column>
                    <el-table-column prop="scenicName" label="景点名称"></el-table-column>
                    <el-table-column prop="concatPerson" width="120" label="联系人"></el-table-column>
                    <el-table-column prop="concatPhone" width="120" label="联系电话"></el-table-column>
                    <el-table-column prop="buyNumber" sortable width="120" label="门票数"></el-table-column>
                    <el-table-column prop="amount" sortable width="120" label="金额"></el-table-column>
                    <el-table-column prop="payTime" sortable width="168" label="支付时间"></el-table-column>
                    <el-table-column prop="createTime" sortable width="168" label="创建时间"></el-table-column>
                    <el-table-column prop="useStatus" width="98" label="支付状态">
                        <template slot-scope="scope">
                            <i v-if="!scope.row.payStatus" style="margin-right: 5px;" class="el-icon-warning"></i>
                            <i v-else style="margin-right: 5px;color: rgb(253, 199, 50);" class="el-icon-success"></i>
                            <el-tooltip v-if="!scope.row.payStatus" class="item" effect="dark" content="未支付，不能重新下单"
                                placement="bottom-end">
                                <span style="text-decoration: underline;text-decoration-style: dashed;">未支付</span>
                            </el-tooltip>
                            <span v-else>已支付</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="110">
                        <template slot-scope="scope">
                            <span class="text-button" v-if="!scope.row.payStatus"
                                @click="handlePay(scope.row)">支付</span>
                            <span class="text-button" @click="handleDelete(scope.row)">删除</span>
                        </template>
                    </el-table-column>
                </el-table>
                <el-pagination style="margin:10px 0;" @size-change="handleSizeChange"
                    @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[10, 20]"
                    :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                    :total="totalItems"></el-pagination>
            </el-row>
            <el-row>
                <LineChart height="420px" tag="消费记录（元）" @on-selected="selected" :values="values" :date="dates" />
            </el-row>
            <el-row>
                <HotelOrderInfo />
            </el-row>
        </el-row>
    </el-row>
</template>

<script>
import LineChart from "@/components/LineChart"
import HotelOrderInfo from "@/views/user/HotelOrderInfo"
export default {
    components: { LineChart, HotelOrderInfo },
    data() {
        return {
            filterText: '',
            currentPage: 1,
            pageSize: 10,
            totalItems: 0,
            queryDays: 365,
            tableData: [],
            selectedRows: [],
            scenicTicketOrderQueryDto: {}, // 搜索条件
            searchTime: [],
            values: [],
            dates: [],
            statusList: [{ value: null, label: '全部' }, { value: 0, label: '未支付' }, { value: 1, label: '已支付' }],
        };
    },
    created() {
        this.fetchFreshData();
        // 数据太少，默认查365天
        this.selected(this.queryDays);
    },
    methods: {
        // 请求后端的金额成交数据
        selected(time) {
            this.$axios.get(`/scenicTicketOrder/daysQueryUser/${time}`).then(response => {
                const { data } = response;
                if (data.code === 200) {
                    this.values = data.data.map(entity => entity.count);
                    this.dates = data.data.map(entity => entity.name);
                }
            })
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
                title: '删除门票订单数据',
                text: `删除后不可恢复，是否继续？`,
                icon: 'warning',
            });
            if (confirmed) {
                try {
                    let ids = this.selectedRows.map(entity => entity.id);
                    const response = await this.$axios.post(`/scenicTicketOrder/batchDelete`, ids);
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
                    console.error(`门票订单信息删除异常：`, e);
                }
            }
        },
        // 修改信息
        async updateOperation() {
            try {
                const response = await this.$axios.put('/scenicTicketOrder/update', this.data);
                this.clearFormData();
                this.$swal.fire({
                    title: '门票订单信息修改',
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
                const response = await this.$axios.post('/scenicTicketOrder/save', this.data);
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
                    ...this.scenicTicketOrderQueryDto
                };
                const response = await this.$axios.post('/scenicTicketOrder/queryUser', params);
                const { data } = response;
                this.tableData = data.data;
                this.totalItems = data.total;
            } catch (error) {
                console.error('查询门票订单信息异常:', error);
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
        // 处理支付的方法
        handlePay(row) {
            const sceniTicketOrder = { id: row.id }
            this.$axios.post('/scenicTicketOrder/pay', sceniTicketOrder).then(res => {
                if (res.data.code === 200) {
                    this.$message.success('支付成功');
                    this.fetchFreshData();
                    this.selected(this.queryDays);
                }
            }).catch(error => {
                console.error("支付错误：", error);
            })
        },
        handleDelete(row) {
            this.selectedRows.push(row);
            this.batchDelete();
        }
    },
};
</script>
<style scoped lang="scss"></style>