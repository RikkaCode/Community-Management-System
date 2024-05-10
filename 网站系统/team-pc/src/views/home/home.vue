<template>
    <!-- 推荐社团 -->
    <el-row style="margin-top: 20px">
        <el-col :span="24">
            <el-card class="box-card" :body-style="{ height: '250px', padding: '10px' }">
                <template #header>
                    <div class="card-header">
                        <span class="moretitle">推荐社团</span>
                        <el-button @click="tabClick('team')" class="button" text>更多
                            <el-icon>
                                <ArrowRight></ArrowRight>
                            </el-icon>
                        </el-button>
                    </div>
                </template>
                <div>
                    <swiper1></swiper1>
                </div>
            </el-card>
        </el-col>
    </el-row>
    <!-- 活动推荐 -->
    <el-row :gutter="20" style="margin-top: 30px">
        <el-col :span="8" :offset="0">
            <el-card class="box-card" :body-style="{ height: '250px', padding: '10px' }">
                <template #header>
                    <div class="card-header">
                        <span class="moretitle">推荐活动</span>
                        <el-button @click="tabClick('activity')" class="button" text>更多
                            <el-icon>
                                <ArrowRight></ArrowRight>
                            </el-icon>
                        </el-button>
                    </div>
                </template>
                <div>
                    <swipper></swipper>
                </div>
            </el-card>
        </el-col>
        <el-col :span="16" :offset="0">
            <el-card class="box-card" :body-style="{ height: '250px', padding: '10px' }">
                <template #header>
                    <div class="card-header">
                        <span class="moretitle">推荐公告</span>
                        <el-button @click="tabClick('notice')" class="button" text>更多
                            <el-icon>
                                <ArrowRight></ArrowRight>
                            </el-icon>
                        </el-button>
                    </div>
                </template>
                <div>
                    <div @click="toActivieyDetail(item)" class="notice" v-for="(item, index) in noticeList">
                        <div class="title">{{ index + 1 + "、" + item['title'] }}</div>
                        <div class="date">{{ item['createTime'] }}</div>
                    </div>
                </div>
            </el-card>
        </el-col>
    </el-row>
    <el-row style="margin-top: 30px">
        <el-col :span="24" :offset="0">
            <el-card class="box-card" :body-style="{ height: '250px', padding: '10px' }">
                <template #header>
                    <div class="card-header">
                        <span class="moretitle">最新活动</span>
                        <el-button @click="tabClick('activity')" class="button" text>更多
                            <el-icon>
                                <ArrowRight></ArrowRight>
                            </el-icon>
                        </el-button>
                    </div>
                </template>
                <div>
                    <div @click="toNewsDetail(item)" class="notice" v-for="(item, index) in newActivity">
                        <div class="title">{{ index + 1 + "、" + item['title'] }}</div>
                        <div class="date">{{ item['createTime'] }}</div>
                    </div>
                </div>
            </el-card>
        </el-col>
    </el-row>
    <el-row style="margin-top: 30px">
        <el-col :span="24" :offset="0">
            <el-card class="box-card" :body-style="{ height: '250px', padding: '10px' }">
                <template #header>
                    <div class="card-header">
                        <span class="moretitle">最新新闻</span>
                        <el-button @click="tabClick('news')" class="button" text>更多
                            <el-icon>
                                <ArrowRight></ArrowRight>
                            </el-icon>
                        </el-button>
                    </div>
                </template>
                <div>
                    <div @click="toNoticeDetail(item)" class="notice" v-for="(item, index) in newNews">
                        <div class="title">{{ index + 1 + "、" + item['title'] }}</div>
                        <div class="date">{{ item['createTime'] }}</div>
                    </div>
                </div>
            </el-card>
        </el-col>
    </el-row>
</template>

<script setup lang="ts">
    import { ref, onMounted } from 'vue';
    import { getTopNoticeApi, getNewActivityApi, getNewNoticeApi } from '@/api/home';
    import swiper1 from './swiper1.vue';
    import swipper from './swipper.vue';
    import { useRouter } from "vue-router";
    import { tabsStore } from '@/store/tabs/index'
    import { newsStore } from '@/store/news';

    const nstore = newsStore()
    const store = tabsStore()

    const tabClick = (activeName:any)=>{
        // 清空列表数据
        const item = ref('')
        item.value = activeName;
        nstore.newsList = [];
        nstore.parm.currentPage = 1;
        nstore.parm.pageSize = 10;
        if(item.value == 'activity'){
            nstore.parm.type = '0'
            // 获取数据
            nstore.getNewsList()
        }
        if(item.value == 'news'){
            nstore.parm.type = '1'
            // 获取数据
            nstore.getNewsList()
        }
        if(item.value == 'notice'){
            nstore.parm.type = '2'
            // 获取数据
            nstore.getNewsList()
        }
        store.setFatabs(item.value)
        if (item.value === 'mine'){
            router.push({path:store.mineTabs})
        }else{
            router.push({name:item.value})
        }
    }

    const router = useRouter();
    const noticeList = ref([]);

    // 推荐公告
    const getTopNotice = async () => {
        let res = await getTopNoticeApi()
        console.log(res.data)
        if (res && res.code == 200) {
            noticeList.value = res.data
        }
    }

    // 最新活动
    const newActivity = ref([])
    const getNewActivity = async () => {
        let res = await getNewActivityApi()
        if (res && res.code == 200) {
            newActivity.value = res.data;
        }
    }

    // 最新新闻
    const newNews = ref([])
    const getNewNotice = async () => {
        let res = await getNewNoticeApi()
        if (res && res.code == 200) {
            newNews.value = res.data
        }
    }

    // 跳转活动详情
    const toActivieyDetail = (item: any) => {
        console.log(item);
        router.push({ path: `/activityDetails/${item.id}` });
    };

    // 跳转新闻详情
    const toNewsDetail = (item: any) => {
        console.log(item);
        router.push({ path: `/newsDetails/${item.id}` });
    };

    // 跳转公告详情
    const toNoticeDetail = (item: any) => {
        console.log(item);
        router.push({ path: `/noticeDetails/${item.id}` });
    };

    onMounted(() => {
        getTopNotice()
        getNewActivity()
        getNewNotice()
    })

</script>

<style scoped lang="scss">
    .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .moreactivity {
        display: flex;
        align-items: center;
        cursor: pointer;
    }

    .text {
        font-size: 14px;
    }

    .moretitle {
        color: #F3AF28;
        font-weight: 600;
    }

    .notice {
        cursor: pointer;
        display: flex;
        height: 30px;
        line-height: 30px;
    }

    .title {
        font-size: 14px;
        font-weight: 600;
        color: #666666;
    }

    .date {
        font-size: 14px;
        margin-left: 20px;
    }
</style>