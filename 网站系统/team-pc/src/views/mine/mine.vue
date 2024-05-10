<template>
    <el-row style="margin-top: 10px" :gutter="10">
        <el-col :span="4">
            <el-card :body-style="{ padding: '5px' }" class="box-card">
                <div v-for="(item, index) in category" @click = "$event => btnClick(item.id)"
                :class="{ 'active-item': item.id == activeName }"
                class="text item cateitem"
                >
                <router-link :to="item.id">{{ item.name }}</router-link>
                </div>
            </el-card>
        </el-col>
        <el-col :span="20">
        <router-view />
        </el-col>
    </el-row>
  </template>

<script setup lang="ts">
    import { computed, ref } from 'vue';
    import { tabsStore } from '@/store/tabs/index'

    const store = tabsStore()
    const activeName = computed(() => {
        return store.mineTabs
    })

    // 分类数据
    const category = ref([
        {
            name: "个人中心",
            id: "/mine/mycenter"
        },
        {
            name: "我的社团",
            id: "/mine/myteam"
        },
        {
            name: "我的活动",
            id: "/mine/myactivity"
        }
    ])

    const btnClick = (id:string)=>{
        store.mineTabs = id
    }
</script>

<style scoped lang="scss">
    .time {
        font-size: 10px;
        color: #999;
    }
    .cateitem {
        height: 50px;
        line-height: 50px;
        text-align: center;
        border-bottom: 1px solid #e4e7ed;
    a {
        height: 50px;
        line-height: 50px;
        color: #999;
        text-align: center;
        cursor: pointer;
        text-decoration: none;
    }
    }
    .active-item {
    a {
    color: #f3af28;
    }
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