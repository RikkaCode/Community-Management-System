<template>
    <el-main>
        <!-- 搜索栏 -->
        <el-form :model="searchParm" :inline="true" size="default">
            <el-form-item>
                <el-input placeholder="请输入姓名" v-model="searchParm.name"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input placeholder="请输入电话" v-model="searchParm.phone"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button icon="Search" @click="searchBtn">搜索</el-button>
                <el-button icon="Refresh" type="danger" plain @click="resetBtn">重置</el-button>
                <!-- <el-button icon="Plus" type="primary" @click="addBtn">新增</el-button> -->
            </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :height="tableHeight" :data="tableList" border stripe>
            <el-table-column prop="name" label="姓名"></el-table-column>
            <el-table-column prop="sex" label="性别">
                <template #default="scope">
                    <el-tag v-if="scope.row.sex == '0'" size="default">男</el-tag>
                    <el-tag v-if="scope.row.sex == '1'" type="danger" size="default">女</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="phone" label="电话"></el-table-column>
            <el-table-column prop="grade" label="年级"></el-table-column>
            <el-table-column prop="major" label="专业"></el-table-column>
            <el-table-column prop="classes" label="班级"></el-table-column>
            <el-table-column prop="username" label="账号"></el-table-column>
            <el-table-column prop="status" label="用户状态">
                <template #default="scope">
                    <el-tag v-if="scope.row.status == '0'" type="success" size="default">启用</el-tag>
                    <el-tag v-if="scope.row.status == '1'" type="danger" size="default">停用</el-tag>
                </template>
            </el-table-column>
            <el-table-column align="center" width="420" label="操作">
                <template #default="scope">
                    <el-button icon="Edit" type="info" size="default" @click="stopBtn(scope.row)">停用</el-button>
                    <el-button icon="Edit" type="primary" size="default" @click="startBtn(scope.row)">启用</el-button>
                    <el-button icon="Edit" type="warning" size="default" @click="resetPassword(scope.row)">重置密码</el-button>
                    <el-button icon="Delete" type="danger" size="default" @click="deleteBtn(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination
            style="display: flex; justify-content: center;"
            @size-change="sizeChange" @current-change="currentChange"
            :current-page.sync="searchParm.currentPage" :page-sizes="[10, 20, 40, 80, 100]"
            :page-size="searchParm.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="searchParm.total"
            background>
        </el-pagination>
    </el-main>
</template>

<script setup lang="ts">
    import useMemberTable from "@/composable/member/useMemberTable";
    import useMember from "@/composable/member/useMember";

    // 表格业务
    const { searchParm, getList, searchBtn, resetBtn, tableList, sizeChange, currentChange, tableHeight } =
        useMemberTable();
    // 增删改业务
    const { stopBtn, startBtn, resetPassword, deleteBtn } = useMember(getList);
</script>

<style scoped></style>