<template>
  <div>
    <el-button type="primary" icon="Plus" size="default" @click="addBtn">新增</el-button>
    <el-icon :size="20">
      <Edit />
    </el-icon>
    <div>{{ store.count }}</div>
  </div>
</template>

<script setup lang="ts">
  import { testStore } from '@/store/test';
  
  // 获取store
  const store = testStore()
  // 新增点击
  const addBtn = () => {
    store.$patch((state) => {
      state.count = ++state.count
    })
  }
</script>

<style scoped></style>