<template>
    <div class="logincontainer">
        <el-form class="loginform" :model="loginModel" ref="loginRef" :rules="rules" size="large">
            <el-form-item>
                <div class="logintitle">欢迎注册</div>
            </el-form-item>
            <el-form-item prop="name">
                <el-input v-model="loginModel.name" size="large" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <el-form-item prop="phone">
                <el-input v-model="loginModel.phone" size="large" placeholder="请输入电话"></el-input>
            </el-form-item>
            <el-form-item prop="grade">
                <el-input v-model="loginModel.grade" size="large" placeholder="请输入年级"></el-input>
            </el-form-item>
            <el-form-item prop="major">
                <el-input v-model="loginModel.major" size="large" placeholder="请输入专业"></el-input>
            </el-form-item>
            <el-form-item prop="classes">
                <el-input v-model="loginModel.classes" size="large" placeholder="请输入班级"></el-input>
            </el-form-item>
            <el-form-item prop="username">
                <el-input v-model="loginModel.username" placeholder="请输入账号"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input v-model="loginModel.password" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item prop="sex">
                <el-radio-group v-model="loginModel.sex">
                    <el-radio :value="'0'">男</el-radio>
                    <el-radio :value="'1'">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item>
                <el-row style="width: 100%">
                    <el-col :span="12" :offset="0" style="padding-right: 10px; padding-left: 0px">
                        <el-button class="btn" type="primary" size="large" @click="registerBtn">注册</el-button>
                    </el-col>
                    <el-col :span="12" :offset="0" style="padding-right: 0px; padding-left: 10px">
                        <el-button class="btn" type="danger" plain size="large" @click="">重置</el-button>
                    </el-col>
                </el-row>
            </el-form-item>
            <div style="font-size: 14px; color: #b1b3b8">
                <span>已有账号,</span><span @click="toLogin"
                    style="color: #409eff; font-size: 15px; cursor: pointer">去登录</span>
            </div>
        </el-form>
    </div>
</template>

<script setup lang="ts">
    import { ElMessage, FormInstance } from "element-plus";
    import { reactive, ref } from "vue";
    import { useRouter } from "vue-router";
    import { registerApi } from '@/api/login/index'

    //表单的ref属性
    const loginRef = ref<FormInstance>()
    const router = useRouter();
    const loginModel = reactive({
        name: '',
        phone: '',
        grade: '',
        major: '',
        classes: '',
        username: '',
        password: '',
        sex: ''
    });
    //表单验证规则
    const rules = reactive({
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        phone: [{ required: true, message: '请输入电话', trigger: 'blur' }],
        grade: [{ required: true, message: '请输入年级', trigger: 'blur' }],
        major: [{ required: true, message: '请输入专业', trigger: 'blur' }],
        classes: [{ required: true, message: '请输入班级', trigger: 'blur' }],
        username: [{ required: true, message: '请输入登录账号', trigger: 'blur' }],
        password: [{ required: true, message: '请输入登录密码', trigger: 'blur' }],
        sex: [{ required: true, message: '请选择性别', trigger: 'blur' }]
    });

    // 去登录页面
    const toLogin = () => {
        router.push({ name: "login" });
    };
    
    // 提交注册
    const registerBtn = () => {
        loginRef.value?.validate(async (valid) => {
            if (valid) {
                let res = await registerApi(loginModel)
                if (res && res.code == 200) {
                    ElMessage.success(res.msg)
                    // 注册成功，跳转登录
                    router.push({ name: "login" });
                }
            }
        })
    }
</script>

<style scoped lang="scss">
    .logincontainer {
        background-color: #fff;
        height: 100%;
        background-image: url("../../assets/bg.png");
        background-size: 100% 100%;
        display: flex;
        justify-content: center;
        align-items: center;

        .loginform {
            border-radius: 10px;
            background-color: #fff;
            height: 650px;
            width: 420px;
            padding: 20px 35px;

            .logintitle {
                color: #606266;
                width: 100%;
                font-weight: 600;
                font-size: 24px;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            .image {
                height: 40px;
                width: 96%;
                display: flex;
                justify-content: center;
                align-items: center;
                cursor: pointer;
                margin-left: 10px;
            }

            .btn {
                width: 100%;
            }
        }
    }
</style>