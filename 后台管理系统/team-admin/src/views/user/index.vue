<template>
  <el-main>
    <!-- 搜索栏 -->
    <el-form :model="searchParm" :inline="true" size="default">
      <el-form-item>
        <el-input placeholder="请输入姓名" v-model="searchParm.nickName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="Search" @click="searchBtn">搜索</el-button>
        <el-button type="danger" plain icon="Refresh" @click="resetBtn">重置</el-button>
        <el-button icon="Plus" type="primary" @click="addBtn">新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :height="tableHeight" :data="tableList" border stripe>
      <el-table-column label="姓名" prop="nickName"></el-table-column>
      <el-table-column label="性别" prop="sex">
        <!-- 作用域插槽 -->
        <template #default="scope">
          <el-tag v-if="scope.row.sex == '1'" type="danger" size="default">女</el-tag>
          <el-tag v-if="scope.row.sex == '0'" size="default">男</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="电话" prop="phone"></el-table-column>
      <el-table-column label="操作" width="320" align="center">
        <template #default="scope">
          <el-button icon="Edit" type="primary" size="default" @click="editBtn(scope.row)">编辑</el-button>
          <el-button icon="Delete" type="danger" size="default" @click="deleteBtn(scope.row)">删除</el-button>
          <el-button icon="Operation" type="warning" size="default" @click="assignBtn(scope.row)">分配菜单</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination style="display: flex; justify-content: center;" @size-change="sizeChange"
      @current-change="currentChange" :current-page.sync="searchParm.currentPage" :page-sizes="[10, 20, 40, 80, 100]"
      :page-size="searchParm.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="searchParm.total"
      background>
    </el-pagination>

    <!-- 新增弹框 -->
    <AddUser ref="addUserRef" @onFresh="getList"></AddUser>
    <!-- 分配菜单 -->
    <AssignTree ref="assignRef" @onFresh="getList"></AssignTree>
  </el-main>
</template>

<script setup lang="ts">
  import useUser from "@/composable/user/useUser";
  import useUserTable from "@/composable/user/useUserTable";
  import AddUser from "@/views/user/AddUser.vue";
  import AssignTree from "./AssignTree.vue";

  // 表格相关的操作
  const { searchBtn, searchParm, resetBtn, tableList, currentChange, sizeChange, tableHeight, getList } = useUserTable();

  // 新增、编辑、删除
  const { addBtn, editBtn, deleteBtn, addUserRef, assignBtn, assignRef } = useUser(getList);
</script>

<style scoped></style>
