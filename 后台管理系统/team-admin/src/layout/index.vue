<template>
    <el-container class="mycontainer">
      <el-aside class="myaside" width="auto">
        <Menu></Menu>
      </el-aside>

      <el-container>
        <el-header class="myheader">
            <div style="display: flex; align-items: center;">
                <collapse></collapse>
                <BreadCrumb></BreadCrumb>
            </div>
            <loginout></loginout>
        </el-header>

        <el-main class="mymain">
            <router-view />
        </el-main>
      </el-container>
    </el-container>
  </template>
  
<script setup lang="ts">
    import Menu from './Menu.vue';
    import collapse from './collapse.vue';
    import BreadCrumb from './BreadCrumb.vue';
    import loginout from './loginout.vue';
    
</script>
    

<style scoped lang="scss">
    .mycontainer{
        height: 100%;
        .myaside{
            background-color: #0e1f37;
        }
        .myheader{
            display: flex;
            align-items: center;
            justify-content: space-between;
            background-color: #0e1f37;
            color: #fff;
            padding: 0px 10px;
        }
        .mymain{
            padding: 0px;
        }
    }
</style>