<template>
    <el-main>
        <!-- 搜索栏 -->
        <el-form :model="searchParm" label-width="80px" :inline="true" size="default">
            <el-form-item label="">
                <el-input v-model="searchParm.name" placeholder="请输入社团名称"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button icon="Search" @click="searchBtn">搜索</el-button>
                <el-button icon="Refresh" plain type="danger" @click="resetBtn">重置</el-button>
                <el-button icon="Plus" type="primary" @click="addBtn">新增</el-button>
            </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :height="tableHeight" :data="tableList" border stripe>
            <el-table-column prop="image" label="社团图片">
                <template #default="scope">
                    <el-image :src="scope.row.image.split(',')[0]" style="width: 60px; height: 60px; border-radius: 50%;"></el-image>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="社团名称"></el-table-column>
            <el-table-column prop="hasTop" label="推荐首页">
                <template #default="scope">
                    <el-tag type="danger" v-if="scope.row.hasTop == '0'">否</el-tag>
                    <el-tag v-if="scope.row.hasTop == '1'">是</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
            <el-table-column align="center" width="200" label="操作">
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
            :page-sizes="[10, 20, 40, 80, 100]"
            :page-size="searchParm.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="searchParm.total" background>
            :pager-count="7">
        </el-pagination>
        
        <!-- 弹框组件 -->
        <AddTeam ref="addTeamRef" @onFresh="getList"></AddTeam>
    </el-main>
    
</template>

<script setup lang="ts">
    import AddTeam from './AddTeam.vue';
    import useTeamTable from '@/composable/team/useTeamTable';
    import useTeam from '@/composable/team/useTeam';

    // 表格
    const { searchParm, searchBtn, resetBtn, tableList, sizeChange, currentChange, tableHeight, getList } = useTeamTable();

    // 增删改查
    const { addBtn, editBtn, deleteBtn, addTeamRef} = useTeam(getList);


</script>

<style scoped>

</style>