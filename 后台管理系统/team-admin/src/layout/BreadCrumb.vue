<template>
    <el-breadcrumb class="bred" separator="/">
        <el-breadcrumb-item v-for="item in tabs">{{ item.meta.title }}</el-breadcrumb-item>
    </el-breadcrumb>
</template>

<script setup lang="ts">
    import { Ref, ref, watch, onMounted } from "vue";
    import { useRoute } from "vue-router";
    import { RouteLocationMatched } from "vue-router";

    // 获取路由
    const route = useRoute();
    // 定义面包屑导航数据类型 
    const tabs:Ref<RouteLocationMatched[]> = ref([])
    watch(
        () => route.path,
        () => getBredcrumb()
    )

    // 获取面包屑数据
    const getBredcrumb = () => {
        let mached = route.matched.filter((item) => item.meta && item.meta.title)
        const first = mached[0]
        // 构造面包屑导航第一个数据
        if(first.path !== '/home') {
            mached = [{ path: '/home', meta: { title: '首页' } } as any].concat(mached)
        }
        tabs.value = mached
    }

    onMounted(()=>{
        getBredcrumb()
    })
</script>

<style scoped lang="scss">
    :deep(.el-breadcrumb__inner) {
        color: #fff !important;
    }
    .bred {
        margin-left: 10px;
    }
    :deep(.el-breadcrumb__item) {
        font-size: 15px !important;
    }
</style>