<template>
  <div style="color: red;">社团管理系统</div>
  <el-button @click="addBtn" type="primary" size="default">新增</el-button>
  <el-icon :size="20">
    <Edit />
  </el-icon> 
  <div>{{ count }}</div>

</template>

<script setup lang="ts">
  import { computed } from "vue";
  import { testStore } from "@/store/test";

  // 获取store
  const store = testStore();
  // 获取count数据
  const count = computed(()=>{
    return store.getCount
  })

  const addBtn = () => {
    // store.setCount(++store.count)
    ++store.count
    // store.$patch({
    //   count:++store.count
    // })
    // store.$patch((state)=>{
    //   state.count++
    // })
  }
</script>


<style scoped>
.read-the-docs {
  color: #888;
}
</style>
