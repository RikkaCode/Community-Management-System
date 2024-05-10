<template>
    <el-container class="container">
        <el-header class="header">
            <logo></logo>
            <tabs></tabs>
            <loginout></loginout>
        </el-header>
            
        <el-main class="main">
            <router-view/>
        </el-main>

        <!-- <el-footer class="footer">Footer</el-footer> -->
    </el-container>
</template>

<script setup lang="ts">
    import logo from './logo.vue';
    import tabs from './tabs.vue';
    import loginout from './loginout.vue';
</script>

<style scoped lang="scss">
    .el-container{
        height: 100%;
        .header{
            background-color: #1077CC;
            display: flex;
            align-items: center;
            padding: 0px;
        }
        .main{
            padding: 0px 150px 0px 150px;
            // background-color: blueviolet;
        }
        .footer{
            // background-color: fuchsia;
        }
    }
</style>