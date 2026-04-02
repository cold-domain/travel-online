<template>
    <div>
        <div v-if="vendors.length !== 0">
            <h1
                style="padding-left: 10px;border-left: 4px solid rgb(0, 122, 204);background-color: rgb(250,250,250);padding-block: 10px;">
                供应商基本信息</h1>
            <div class="info">
                <el-descriptions class="margin-top" title="" :column="3" :size="size">
                    <el-descriptions-item>
                        <template slot="label">
                            供应商名称
                        </template>
                        {{ vendors[0].name }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            工作邮箱
                        </template>
                        {{ vendors[0].email }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            工作地点
                        </template>
                        {{ vendors[0].workAddress }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            提供产品类型
                        </template>
                        <el-tag size="small">{{ vendors[0].productType }}</el-tag>
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-office-building"></i>
                            联系人
                        </template>
                        {{ vendors[0].concatPerson }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-office-building"></i>
                            联系电话
                        </template>
                        {{ vendors[0].concatPhone }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            审核状态
                        </template>
                        <el-tag size="small">{{ vendors[0].isAudit ? '已审核' : '未审核' }}</el-tag>
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            供应商状态
                        </template>
                        <el-tag size="small">{{ vendors[0].status ? '正常' : '异常' }}</el-tag>
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            操作
                        </template>
                        <el-button class="customer" style="border-radius: 20px;margin-bottom: 20px;background-color: rgb(51,51,51);"
                            type="warning" size="mini" @click="updateInfo">修改信息</el-button>
                    </el-descriptions-item>
                </el-descriptions>
            </div>

            <div style="margin: 20px 0;" v-if="vendors[0].isAudit && vendors[0].status">
                <h1
                    style="padding-left: 10px;border-left: 4px solid rgb(0, 122, 204);background-color: rgb(250,250,250);padding-block: 10px;">
                    供应商功能导航入口</h1>
                <div class="content-publish">
                    <div @click="route('/scenicVendor')">
                        景点信息
                    </div>
                    <div @click="route('/hotelVendor')">
                        酒店信息
                    </div>
                    <div @click="route('/ticket')">
                        景点门票管理
                    </div>
                    <div @click="route('/hotelRoom')">
                        酒店房间管理
                    </div>
                </div>
                <div class="content-publish">
                    <div @click="route('/hotelOrder')">
                        酒店订单
                    </div>
                    <div @click="route('/ticketOrder')">
                        门票预约
                    </div>
                </div>
            </div>
        </div>
        <div v-else style="width: 500px;margin: 0 auto;">
            <div>
                <h2 style="text-align: center;">供应商信息申请</h2>
            </div>
            <div>
                <p>*供应商名称</p>
                <input style="line-height: 45px;" class="dialog-input" v-model="data.name" placeholder="请输入" />
            </div>
            <div>
                <p>*联系人</p>
                <input style="line-height: 45px;" class="dialog-input" v-model="data.concatPerson" placeholder="请输入" />
            </div>
            <div>
                <p>*联系电话</p>
                <input style="line-height: 45px;" class="dialog-input" v-model="data.concatPhone" placeholder="请输入" />
            </div>
            <div>
                <p>*工作邮箱</p>
                <input style="line-height: 45px;" class="dialog-input" v-model="data.email" placeholder="请输入" />
            </div>
            <div>
                <p>*产品类型</p>
                <input style="line-height: 45px;" class="dialog-input" v-model="data.productType" placeholder="请输入" />
            </div>
            <div>
                <p>*办公地址</p>
                <input style="line-height: 45px;" class="dialog-input" v-model="data.workAddress" placeholder="请输入" />
            </div>
            <div style="margin-top: 20px;text-align: center;">
                <el-button type="primary" class="customer" size="mini" @click="postInfo" round>
                    提交申请
                </el-button>
            </div>
        </div>
        <el-dialog :show-close="false" :visible.sync="dialogStatusOperation" width="25%">
            <div slot="title">
                <p class="dialog-title">修改信息</p>
            </div>
            <div style="padding:0 20px;">
                <div>
                    <p>*供应商名称</p>
                    <input class="dialog-input" v-model="data.name" placeholder="请输入" />
                </div>
                <div>
                    <p>*联系人</p>
                    <input class="dialog-input" v-model="data.concatPerson" placeholder="请输入" />
                </div>
                <div>
                    <p>*联系电话</p>
                    <input class="dialog-input" v-model="data.concatPhone" placeholder="请输入" />
                </div>
                <div>
                    <p>*工作邮箱</p>
                    <input class="dialog-input" v-model="data.email" placeholder="请输入" />
                </div>
                <div>
                    <p>*产品类型</p>
                    <input class="dialog-input" v-model="data.productType" placeholder="请输入" />
                </div>
                <div>
                    <p>*办公地址</p>
                    <input class="dialog-input" v-model="data.workAddress" placeholder="请输入" />
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" style="background-color: rgb(96, 98, 102);color: rgb(247,248,249);border: none;"
                    class="customer" type="info" @click="comfirmStatus">确认</el-button>
                <el-button class="customer" size="small" style="background-color: rgb(241, 241, 241);border: none;"
                    @click="dialogStatusOperation = false">取消</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
export default {
    name: "Service",
    data() {
        return {
            data: {},
            vendors: [],
            dialogStatusOperation: false
        }
    },
    created() {
        this.auth();
    },
    methods: {
        // 路由跳转
        route(path) {
            this.$router.push(path);
        },
        // 供应商信息修改
        async comfirmStatus() {
            const { data } = await this.$axios.put('/vendor/update', this.data);
            this.$notify({
                duration: 1000,
                title: '供应商信息修改',
                message: data.code === 200 ? '修改' : data.msg,
                type: data.code === 200 ? 'success' : 'error'
            });
            this.dialogStatusOperation = false;
        },
        updateInfo() {
            this.data = this.vendors[0];
            this.dialogStatusOperation = true;
        },
        async postInfo() {
            const { data } = await this.$axios.post('/vendor/save', this.data);
            this.$notify({
                duration: 1000,
                title: '申请操作',
                message: data.code === 200 ? '申请成功，请耐心等待' : data.msg,
                type: data.code === 200 ? 'success' : 'error'
            });
        },
        async auth() {
            const { data } = await this.$axios.post('/vendor/queryUser');
            if (data.code === 200) {
                this.vendors = data.data;
            }
        },
    }
};
</script>
<style scoped lang="scss">
.info {
    box-shadow: 0 0 20px rgb(240, 240, 240);
    padding: 20px;
    border-radius: 5px;
}

.content-publish {
    border-radius: 5px;
    line-height: 40px;
    display: flex;
    justify-content: left;
    gap: 10px;
    padding: 20px;
    margin-block: 10px;
    box-shadow: 0 0 20px rgb(240, 240, 240);

    div {
        padding: 6px 20px;
        cursor: pointer;
        background-color: rgb(248,248,248);
        font-weight: bold;
        border-radius: 5px;
    
    }
    div:hover{
        background-color: rgb(240,240,240);
    }
}
</style>
