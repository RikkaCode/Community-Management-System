<template>
  <MenuLogo></MenuLogo>
  <el-menu :default-active="defaultActive" class="el-menu-vertical-demo" :collapse="isCollapse" @open="handleOpen"
    @close="handleClose" background-color="#0e1f37" router>
    <el-menu-item v-for="item in menuList" :index="item['path']">
      <el-icon>
        <component :is="item['icon']"></component>
      </el-icon>
      <template #title>{{ item['title'] }}</template>
    </el-menu-item>
    <!-- <el-menu-item index="/user">
    <el-icon><Avatar /></el-icon>
    <template #title>用户管理</template>
    </el-menu-item>
    <el-menu-item index="/menu">
    <el-icon><Menu /></el-icon>
    <template #title>菜单管理</template>
    </el-menu-item>
    <el-menu-item index="/category">
    <el-icon><Operation /></el-icon>
    <template #title>社团分类</template>
    </el-menu-item>
    <el-menu-item index="/team">
    <el-icon><Histogram /></el-icon>
    <template #title>社团管理</template>
    </el-menu-item>
    <el-menu-item index="/member">
    <el-icon><UserFilled /></el-icon>
    <template #title>团员管理</template>
    </el-menu-item>
    <el-menu-item index="/activity">
    <el-icon><Postcard /></el-icon>
    <template #title>新闻活动</template>
    </el-menu-item> -->
        <!-- <el-menu-item index="/news">
    <el-icon><setting /></el-icon>
    <template #title>新闻管理</template>
    </el-menu-item> -->
  </el-menu>
</template>

<script setup lang="ts">
    import { computed } from "vue";
    import MenuLogo from "./MenuLogo.vue";
    import { menuStore } from "@/store/menu";
    import { useRoute } from "vue-router";
    import { userStore } from '@/store/user/index'
    const route = useRoute()
    const store = menuStore();
    const ustore = userStore()
    //获取菜单数据
    const menuList = computed(() => {
      return ustore.menuList
    })
    const isCollapse = computed(() => {
      return store.getCollapse;
    });
    //获取当前激活的菜单：当前路由
    const defaultActive = computed(() => {
      return route.path
    })
    const handleOpen = (key: string, keyPath: string[]) => {
      console.log(key, keyPath);
    };
    const handleClose = (key: string, keyPath: string[]) => {
      console.log(key, keyPath);
    };
</script>

<style scoped>
    .el-menu-vertical-demo:not(.el-menu--collapse) {
      width: 230px;
      min-height: 400px;
    }

    .el-menu {
      border-right: none;
    }

    .el-menu .el-menu-item {
      color: #bfcbd9;
      font-size: 15px;
      font-weight: bold;
    }

    .el-menu-item.is-active {
      color: #f07810 !important;
      background-color: #1f2d3d !important;
    }

    /* 鼠标移动菜单的颜色 */

    :deep(.el-menu-item:hover) {
      background-color: #001528 !important;
    }
</style>
