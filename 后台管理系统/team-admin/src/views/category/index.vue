<template>
    <el-main>
        <!-- 搜索栏 -->
        <el-form :model="searchParm" :inline="true" size="default">
            <el-form-item label="">
                <el-input v-model="searchParm.name" placeholder="请输入分类名称"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button icon="Search" @click="searchBtn">搜索</el-button>
                <el-button icon="Refresh" plain type="danger" @click="resetBtn">重置</el-button>
                <el-button icon="Plus" type="primary" @click="addBtn">新增</el-button>
            </el-form-item>
        </el-form>

        <!-- 表格 -->
        <el-table :data="tableList" border stripe :height="tableHeight">
            <el-table-column prop="name" label="分类名称"> </el-table-column>
            <el-table-column prop="orderNum" label="序号"> </el-table-column>
            <el-table-column label="操作" width="220" align="center">
            <template #default="scope">
            <el-button
                icon="Edit"
                type="primary"
                size="default"
                @click="editBtn(scope.row)"
                >编辑</el-button
            >
            <el-button
                icon="Delete"
                type="danger"
                size="default"
                @click="deleteBtn(scope.row)"
                >删除</el-button
            >
            </template>
        </el-table-column>
        </el-table>

        <!-- 分页 -->
        <el-pagination
            style="display: flex; justify-content: center;"
            @size-change="sizeChange"
            @current-change="currentChange"
            :current-page.sync="searchParm.currentPage"
            :page-sizes="[10,20, 40, 80, 100]"
            :page-size="searchParm.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="searchParm.total" background>
        </el-pagination>
        
        <!-- 新增弹框 -->
        <AddCategory ref="addFormRef" @onFresh="getList"></AddCategory>
    </el-main>
</template>

<script setup lang="ts">
    import AddCategory from './AddCategory.vue';
    import useTableCategory from '@/composable/category/useTableCategory';
    import useCategory from '@/composable/category/useCategory';

    const { searchParm, tableList, currentChange, sizeChange, tableHeight, resetBtn, searchBtn, getList } = useTableCategory();
    const { addBtn, editBtn, deleteBtn, addFormRef } = useCategory(getList);
</script>

<style scoped>

</style>