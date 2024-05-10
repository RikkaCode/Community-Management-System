<template>
    <el-icon :size="30" style="cursor: pointer;" @click="iconClick">
        <!-- 动态组件: 两个组件间来回切换 -->
        <component :is="status ? Expand : Fold"></component>
    </el-icon>
</template>

<script setup lang="ts">
    import { menuStore } from '@/store/menu';
    import { computed } from 'vue';
    import { Expand, Fold } from "@element-plus/icons-vue";

    const store = menuStore();
    const status = computed(() => {
        return store.getCollapse;
    })

    const iconClick = () => {
        store.setCollapse();
    }
</script>

<style scoped>

</style>