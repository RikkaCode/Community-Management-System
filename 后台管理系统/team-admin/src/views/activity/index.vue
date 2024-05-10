<template>
    <el-main>
        <!-- 搜索 -->
        <el-form :model="searchParm" label-width="50px" :inline="true" size="default">
            <el-form-item>
                <el-input v-model="searchParm.title" placeholder="请输入标题"></el-input>
            </el-form-item>

            <el-form-item label="类型">
                <el-select v-model="searchParm.type" clearable placeholder="请选择类型" style="width: 100px;">
                    <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
            </el-form-item>

            <el-form-item>
                <el-button icon="Search" @click="searchBtn">搜索</el-button>
                <el-button icon="Refresh" plain type="danger" @click="resetBtn">重置</el-button>
                <el-button type="primary" icon="Plus" @click="addBtn">新增</el-button>
            </el-form-item>
        </el-form>

        <!-- 表格 -->
        <el-table :height="tableHeight" :data="tableList" border stripe>
            <el-table-column prop="image" label="图片">
                <template #default="scope">
                    <el-image style="width: 100px; height: 60px" :src="scope.row.image.split(',')[0]"></el-image>
                </template>
            </el-table-column>

            <el-table-column prop="title" label="标题"></el-table-column>
            <el-table-column prop="type" label="类型">
                <template #default="scope">
                    <el-tag v-if="scope.row.type == '0'">活动</el-tag>
                    <el-tag type="success" v-if="scope.row.type == '1'">新闻</el-tag>
                    <el-tag type="warning" v-if="scope.row.type == '2'">公告</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="hasTop" label="推荐首页">
                <template #default="scope">
                    <el-tag type="danger" v-if="scope.row.hasTop == '0'">否</el-tag>
                    <el-tag v-if="scope.row.hasTop == '1'">是</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="hasJoin" label="可以报名">
                <template #default="scope">
                    <div v-if="scope.row.type == '0'">
                        <el-tag type="danger" v-if="scope.row.hasJoin == '0'">否</el-tag>
                        <el-tag type="success" v-else>是</el-tag>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="status" label="审核状态">
                <template #default="scope">
                    <div v-if="scope.row.type == '0'">
                        <el-tag type="danger" v-if="scope.row.status == '0'">未审核</el-tag>
                        <el-tag type="success" v-if="scope.row.status == '1'">已审核</el-tag>
                        <el-tag type="warning" v-if="scope.row.status == '2'">已拒绝</el-tag>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="时间"></el-table-column>
            <el-table-column align="center" width="500" label="操作">
                <template #default="scope">
                    <el-button icon="Edit" type="primary" size="default" @click="editBtn(scope.row)">编辑</el-button>
                    <el-button icon="Delete" type="danger" size="default" @click="deleteBtn(scope.row)">删除</el-button>
                    <el-button v-if="scope.row.type == '0' && scope.row.status == '0'" icon="Edit" type="warning" size="default" @click="applyBtn(scope.row)">审核</el-button>
                    <el-button v-if="scope.row.type == '0' && scope.row.status == '0'" icon="Close" type="danger" size="default" @click="returnBtn(scope.row)">拒绝</el-button>
                    <el-button v-if="scope.row.type == '0'" icon="Position" type="primary" size="default" @click="exportBtn(scope.row)">导出</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页 -->
        <el-pagination @size-change="sizeChange" @current-change="currentChange"
            style="display: flex; justify-content: center;" :current-page.sync="searchParm.currentPage"
            :page-sizes="[10, 20, 40, 80, 100]" :page-size="searchParm.pageSize"
            layout="total, sizes, prev, pager, next, jumper" :total="searchParm.total" background>
        </el-pagination>

        <!-- 新增弹窗 -->
        <AddActivity ref="addActivityRef" @onFresh="getList"></AddActivity>

    </el-main>

</template>

<script setup lang="ts">
    import AddActivity from "./AddActivity.vue";
    import useActivityTable from "@/composable/activity/useActivityTable";
    import useActivity from "@/composable/activity/useActivity";
    import { NewsType } from "@/api/news/NewsModel";
    import { ElMessage } from "element-plus";

    const { searchParm, searchBtn, resetBtn, options, tableList, sizeChange, currentChange, tableHeight, getList } = useActivityTable();
    const { addBtn, editBtn, deleteBtn, addActivityRef, applyBtn, returnBtn } = useActivity(getList);

    // 导出学生信息
    const exportBtn = (row: NewsType) => {
        if (!row.id) {
            ElMessage.warning("请选择活动");
            return;
        }
        const abtn = document.createElement("a");
        abtn.href =
            process.env.BASE_API + "/api/student/exportActivity?activityId=" + row.id;
        abtn.click();
    };

</script>

<style scoped>

</style>