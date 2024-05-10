<template>
    <el-row :gutter="10" style="margin-top: 10px;">
        <el-col :span="4">
            <el-card class="box-card" :body-style="{ padding: '50px' }">
                <div :class="{ 'cateitem-active': item['id'] == activeName }" @click="cateBtn(item['id'])"
                    v-for="item in category" :key="item['id']" class="text item cateitem">
                    {{ item['name'] }}
                </div>
            </el-card>
        </el-col>
        <el-col :span="20">
            <el-card class="box-card">
                <div class="text item">
                    <el-row>
                        <el-col :xs="12" :sm="8" :md="7" v-for="item in tabledate" :key="item['id']" :offset="1">
                            <div style="margin-top: 15px;">
                                <el-card :body-style="{ padding: '5px' }" shadow="hover">
                                    <img :src="item['image']" class="image" />
                                    <div>
                                        <strong style="font-size: 12px;">{{ item['name'] }}</strong><br />
                                        <div class="bottom clearfix">
                                            <time class="time">
                                                <span>创建时间：</span>
                                                {{ item['createTime'] }}
                                            </time>
                                            <el-button @click="toDetail(item)" class="button">查看</el-button>
                                            <!-- <router-link :to="{path:'/team/detail/', query: { id: 1, name: 'vue' }}" target="_blank">查看</router-link> -->
                                        </div>
                                    </div>
                                </el-card>
                            </div>
                        </el-col>
                    </el-row>
                </div>
            </el-card>
        </el-col>
    </el-row>
</template>

<script setup lang="ts">
    import { onMounted, ref } from 'vue';
    import { getWebListApi, getTeamByCategoryIdApi } from '@/api/team';
    import { useRouter } from "vue-router";

    const router = useRouter()

    //跳转详情
    const toDetail = (item:any)=>{
        console.log(item)
        router.push({path:`/teamDetails/${item.id}`})
    }
    // 分类数据
    const category = ref([])
    const tabledate = ref([])
    const activeName = ref('')

    // 查询分类
    const getWebList = async () => {
        let res = await getWebListApi();
        if (res && res.code == 200) {
            category.value = res.data;
            activeName.value = category.value[0]['id']
            // 获取社团列表
            getTeamByCategoryId(category.value[0]['id'])
        }
    }

    // 分类点击事件
    const cateBtn = (id:string) => {
        activeName.value = id
        getTeamByCategoryId(id)
    }

    // 查询社团
    const getTeamByCategoryId = async (id:string) => {
        let res = await getTeamByCategoryIdApi(id);
        if (res && res.code == 200) {
            tabledate.value = res.data;
        }
    }

    onMounted(() => {
        getWebList()
    })
</script>

<style scoped lang="scss">
    .time {
        font-size: 10px;
        color: #999;
    }
    .cateitem {
        height: 50px;
        line-height: 50px;
        border-bottom: 1px solid #e4e7ed;
        text-align: center;
        cursor: pointer;
    }
    .cateitem-active {
        height: 50px;
        line-height: 50px;
        border-bottom: 1px solid #e4e7ed;
        cursor: pointer;
        text-align: center;
        color: #f3af28;
    }
    .bottom {
        margin-top: 5px;
        line-height: 12px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .image {
        width: 100%;
        display: block;
    }
</style>