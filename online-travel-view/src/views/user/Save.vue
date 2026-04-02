<template>
    <div>
        <el-row v-if="scenicList.length === 0">
            <el-empty description="暂无景点信息"></el-empty>
        </el-row>
        <el-row v-else class="scenic-item">
            <el-col :span="4" @click.native="scenicClick(scenic)" v-for="(scenic, index) in scenicList" :key="index">
                <div class="item">
                    <img :src="scenic.cover" alt="" srcset="">
                    <div class="name">{{ scenic.name }}</div>
                    <div>
                        <span class="address">
                            <i class="el-icon-location"></i>
                            {{ scenic.address }}
                        </span>
                    </div>
                    <div class="info">
                        <span class="time">{{ timeAgo(scenic) }}</span>
                        <span class="save">收藏{{ strDeal(scenic.saveIds) }}</span>
                        <span class="view">浏览{{ strDeal(scenic.viewIds) }}</span>
                    </div>
                    <div class="extra-info">
                        <span><i class="el-icon-school"></i>{{ scenic.vendorName }}</span>
                        <span>{{ scenic.categoryName }}</span>
                    </div>
                </div>
            </el-col>
        </el-row>
    </div>
</template>
<script>

import { timeAgo } from "@/utils/data"
export default {
    name: "Save",
    data() {
        return {
            scenicList: []
        }
    },
    created() {
        this.fetchSaveScenic();
    },
    methods: {
        // 收藏与浏览的处理方法
        strDeal(str) {
            if (str === null) {
                return '(0)'
            }
            const saveIdsAry = str.split(',');
            return '(' + saveIdsAry.length + ')';
        },
        timeAgo(scenic) {
            return timeAgo(scenic.createTime);
        },
        // 景点的点击事件
        scenicClick(scenic) {
            sessionStorage.setItem('scenicInfo', JSON.stringify(scenic));
            // 跳转至景点详情页
            this.$router.push('/scenicDetail');
        },
        // 查询用户收藏的景点信息
        fetchSaveScenic() {
            this.$axios.get('/scenic/querySave').then(res => {
                if (res.data.code === 200) {
                    this.scenicList = res.data.data;
                }
            });
        },
    }
};
</script>
<style scoped lang="scss">
.scenic-item {
    .item:hover {
        background-color: rgb(248, 248, 248);
    }

    .item {
        margin-block: 20px;
        padding: 30px 10px;
        box-sizing: border-box;
        cursor: pointer;

        .extra-info {
            display: flex;
            justify-content: left;
            align-items: center;
            gap: 5px;
            font-size: 12px;

            span:last-child {
                display: inline-block;
                padding: 3px 6px;
                border-radius: 3px;
                background-color: aliceblue;
                color: rgb(84, 49, 223);
            }
        }

        img {
            width: 100%;
            min-height: 120px;
            max-height: 140px;
            border-radius: 5px;
        }

        .address {
            margin-top: 10px;
            display: inline-block;
            width: 100px;
            font-size: 12px;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }

        .name {
            font-size: 20px;
            font-weight: 800;
        }

        .info {
            display: flex;
            justify-content: left;
            gap: 5px;
            margin-block: 6px;

            span {
                font-size: 12px;
            }
        }
    }

}
</style>
