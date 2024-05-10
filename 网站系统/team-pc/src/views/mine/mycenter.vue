<template>
    <el-card class="box-card" style="position: relative; padding-right: 10px; padding-bottom: 10px; width: 800px; height: 450px">
        <template #header>
            <div class="card-header">
                <span>我的信息</span>
            </div>
        </template>
        <el-form :model="store.userInfo" ref="upRef" :rules="rules" label-width="80px" :inline="false" size="default">
            <el-form-item prop="name" label="姓名">
                <el-input v-model="store.userInfo.name"></el-input>
            </el-form-item>
            <el-form-item prop="sex" label="性别">
                <el-radio-group v-model="store.userInfo.sex">
                    <el-radio :value="'0'">男</el-radio>
                    <el-radio :value="'1'">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item prop="phone" label="电话">
                <el-input v-model="store.userInfo.phone"></el-input>
            </el-form-item>
            <el-form-item prop="grade" label="年级">
                <el-input v-model="store.userInfo.grade"></el-input>
            </el-form-item>
            <el-form-item prop="major" label="专业">
                <el-input v-model="store.userInfo.major"></el-input>
            </el-form-item>
            <el-form-item prop="classes" label="班级">
                <el-input v-model="store.userInfo.classes"></el-input>
            </el-form-item>
            <el-form-item prop="username" label="账号">
                <el-input readonly v-model="store.userInfo.username"></el-input>
            </el-form-item>
        </el-form>
        <!-- <el-button @click="commit" style="margin-left: 40px;" icon="Edit" type="primary">修改</el-button> -->
        <el-button @click="commit" style="position: absolute; right: 10px; bottom: 10px;" icon="Edit" type="primary">修改</el-button>
    </el-card>
</template>

<script setup lang="ts">
    import { ref, reactive } from 'vue';
    import { ElMessage, FormInstance } from "element-plus"
    import { userSotre } from '@/store/user';
    import { updateApi } from '@/api/login/index';

    const upRef = ref<FormInstance>()
    
    const store = userSotre()
    const rules = reactive({
        name: [{required: true,message: '请输入姓名', trigger: 'change',}],
        sex: [{required: true,message: '请选择性别', trigger: 'change',}],
        phone: [{required: true,message: '请输入电话', trigger: 'change',}],
        grade: [{required: true,message: '请输入年级', trigger: 'change',}],
        major: [{required: true,message: '请输入专业', trigger: 'change',}],
        classes: [{required: true,message: '请输入班级', trigger: 'change',}],
        username: [{required: true,message: '账号不可更改 ', trigger: 'change',}]
    })

    const commit = ()=>{
        upRef.value?.validate(async(valid)=>{
            if(valid){
                console.log(store.userInfo)
                    let res = await updateApi(store.userInfo)
                    if(res && res.code == 200){
                    ElMessage.success(res.msg)
                }
            }
        })
    }
</script>

<style scoped>
    .text {
        font-size: 14px;
    }

    .item {
        margin-bottom: 18px;
    }
</style>