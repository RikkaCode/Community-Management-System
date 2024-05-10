<template>
    <view @click="toDetails(item)" class="news-item" v-for="(item, index) in newList" :key="index">
        <view v-if="item['image']">
            <el-image class="images" :src="item['image']" />
        </view>
        <view class="news-intr">
            <view class="news-title">
                <text>{{ item['title'] }}</text>
            </view>
            <view class="news-info">
                <view class="news-time">
                    <text>{{ item['createTime'] }}</text>
                </view>
                <!-- <view class="news-type">
                    <text>{{ item['real_num'] }}浏览</text>
                </view> -->
            </view>
        </view>
    </view>

    <el-pagination
        style="margin-top: 15px;"
        @size-change="sizeChange"
        @current-change="currentChange"
        :current-page.sync="parm.currentPage"
        :page-sizes="[10, 20, 40, 80, 100]"
        :page-size="parm.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="parm.total"
        background>
        </el-pagination>
</template>

<script setup lang="ts">
    import { computed } from "vue";
    import { newsStore } from "@/store/news";
    import { useRouter } from "vue-router";

    const router = useRouter();

    const nstore = newsStore();
    // 活动列表
    const newList = computed(() => {
        return nstore.newsList;
    });
    
    // 分页参数
    const parm = computed(() => {
        return nstore.parm;
    });

    // 页容量改变时触发
    const sizeChange = (size:number)=>{
        //设置分页容量
        nstore.parm.pageSize = size;
        nstore.getNewsList();
    }

    // 改变页时触发
    const currentChange = (page:number)=>{
        //设置分页容量
        nstore.parm.currentPage = page;
        nstore.getNewsList();
    }

    // 跳转详情
    const toDetails = (item:any)=>{
        console.log(item);
        router.push({path: `/activityDetails/${item.id}`})
    }
</script>

<style scoped lang="scss">
    .news-item {
        display: flex;
        height: 110px;
        padding: 30px 0px;
        border-bottom: 1px solid #e4e7ed;
        cursor: pointer;
        .images{
            height: 100px;
            width: 200px;
            border-radius: 10px;
        }
        .news-intr{
            margin-left: 15px;
            .news-info{
                display: flex;
                font-size: 28rpx;
                color: #999999;
                margin-left: 16rpx;
                .news-time{
                    text-align: left;
                }
                .news-type{
                    text-align: right;
                    flex: 2;
                }
            }
        }
    }
</style>