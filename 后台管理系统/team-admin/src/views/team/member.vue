<template>
    <el-main height="">
        <!--搜索栏 -->
        <el-form :model="parm" label-width="80px" :inline="true" size="default">
            <el-form-item label="姓名">
                <el-input placeholder="请输入姓名" v-model="parm.name"></el-input>
            </el-form-item>
            <el-form-item prop="teamId" label="社团名称">
                <el-select v-model="parm.teamId" placeholder="请选择社团" style="width: 150px">
                    <el-option v-for="item in selectData" :key="item['value']" :label="item['label']"
                        :value="item['value']" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button icon="Search" @click="searchBtn">搜索</el-button>
                <el-button @click="resetBtn" icon="Close" type="danger" plain>重置</el-button>
                <el-button @click="exportBtn" icon="Position" type="primary" plain>导出</el-button>
            </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :height="tableHeight" :data="tableList" border stripe>
            <el-table-column prop="teamName" label="社团名称"></el-table-column>
            <el-table-column prop="name" label="姓名"></el-table-column>
            <el-table-column prop="major" label="专业"></el-table-column>
            <el-table-column prop="classes" label="班级"></el-table-column>
            <el-table-column prop="grade" label="年级"></el-table-column>
            <el-table-column prop="phone" label="电话"></el-table-column>
            <el-table-column label="性别" prop="sex">
                <!-- 作用域插槽 -->
                <template #default="scope">
                    <el-tag v-if="scope.row.sex == '1'" type="danger" size="default">女</el-tag>
                    <el-tag v-if="scope.row.sex == '0'" size="default">男</el-tag>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination style="display: flex; justify-content: center;" @size-change="sizeChange" @current-change="currentChange" :current-page.sync="parm.currentPage"
            :page-sizes="[10, 20, 40, 80, 100]" :page-size="parm.pageSize"
            layout="total, sizes, prev, pager, next, jumper" :total="parm.total" background>
        </el-pagination>

    </el-main>
</template>

<script setup lang="ts">
    import { getStuPageApi, getSelectListApi } from "@/api/team/index";
    import { nextTick, onMounted, reactive, ref } from "vue";

    const tableHeight = ref(0)
    // 定义查询参数
    const parm = reactive({
        currentPage: 1,
        pageSize: 10,
        teamId: '',
        total: 0,
        name: ''
    })
    // 表格数据
    const tableList = ref([])
    // 查询数据
    const getStuPage = async () => {
        let res = await getStuPageApi(parm)
        if (res && res.code == 200) {
            console.log(res)
            tableList.value = res.data.records;
            parm.total = res.data.total;
        }
    }
    const sizeChange = (size: number) => {
        parm.pageSize = size
        getStuPage()
    }
    const currentChange = (page: number) => {
        parm.currentPage = page;
        getStuPage()
    }
    // 接收下拉数据
    const selectData = ref([])
    // 获取社团
    const getSelect = async () => {
        let res = await getSelectListApi()
        if (res && res.code == 200) {
            selectData.value = res.data
        }
    }

    const searchBtn = () => {
        getStuPage()
    }
    
    const resetBtn = () => {
        parm.currentPage = 1
        parm.teamId = ''
        parm.name = ''
        getStuPage()
    }

    //导出学生
    const exportBtn = () => {
        const abtn = document.createElement("a");
            abtn.href = process.env.BASE_API + "/api/student/exportStuInfo?teamId=" + parm.teamId;
            abtn.click();
    };

    onMounted(() => {
        getSelect()
        getStuPage()
        nextTick(() => {
            tableHeight.value = window.innerHeight - 200
        })
    })
</script>

<style scoped></style>