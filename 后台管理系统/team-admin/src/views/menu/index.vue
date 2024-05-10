<template>
    <el-main>
        <!-- 新增按钮 -->
        <el-button @click="addBtn" icon="Plus" type="primary" size="default">新增</el-button>
        <!-- 表格 -->
        <el-table style="margin-top: 20px" :data="tableList" row-key="menuId" default-expand-all
            :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" border stripe>
            <el-table-column prop="title" label="菜单名称"></el-table-column>
            <el-table-column prop="type" label="类型">
                <template #default="scope">
                    <el-tag v-if="scope.row.type == '1'" type="success" size="default">菜单</el-tag>
                    <el-tag v-if="scope.row.type == '2'" type="danger" size="default">按钮</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="icon" label="图标">
                <template #default="scope">
                    <el-icon>
                        <!-- 动态组件的方式显示图标 -->
                        <component :is="scope.row.icon"></component>
                    </el-icon>
                </template>
            </el-table-column>
            <el-table-column prop="path" label="路由path"></el-table-column>
            <el-table-column prop="code" label="权限字段"></el-table-column>
            <el-table-column prop="orderNum" label="序号"></el-table-column>
            <el-table-column label="操作" width="220" align="center">
                <template #default="scope">
                    <el-button type="success" icon="Edit" size="default" @click="editBtn(scope.row)">编辑</el-button>
                    <el-button type="danger" icon="Delete" size="default" @click="deleteBtn(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
    </el-main>
    <!-- 新增弹框 -->
    <AddMenu ref="addRef" @onFresh="getList"></AddMenu>
</template>

<script setup lang="ts">
    import useMenuTable from "@/composable/menu/useMenuTable";
    import AddMenu from "./AddMenu.vue";
    import useMenu from '@/composable/menu/useMenu'

    // 表格业务
    const { tableList, getList } = useMenuTable();
    
    // 新增、编辑、删除
    const { addBtn, editBtn, deleteBtn, addRef } = useMenu(getList)
</script>

<style scoped></style>