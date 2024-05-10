<template>
    <div class="logincontainer">
        <el-form class="loginform" :model="loginModel" ref="loginRef" :rules="rules" size="large">
            <el-form-item>
                <div class="logintitle">系统登录</div>
            </el-form-item>
            <el-form-item prop="username">
                <el-input v-model="loginModel.username" placeholder="请输入账号"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="password" v-model="loginModel.password" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item prop="code">
                <el-row style="width: 100%">
                    <el-col :span="16" :offset="0">
                        <el-input v-model="loginModel.code" placeholder="请输入验证码"></el-input>
                    </el-col>
                    <el-col :span="8" :offset="0">
                        <img @click="getImg" class="image" :src="img" />
                    </el-col>
                </el-row>
            </el-form-item>
            <el-form-item>
                <el-row style="width: 100%">
                    <el-col @click="commit" :span="12" :offset="0" style="padding-right: 10px; padding-left: 0px">
                        <el-button class="btn" type="primary" size="large" @click="">登录</el-button>
                    </el-col>
                    <el-col :span="12" :offset="0" style="padding-right: 0px; padding-left: 10px">
                        <el-button class="btn" type="danger" plain size="large" @click="">重置</el-button>
                    </el-col>
                </el-row>
            </el-form-item>
        </el-form>
    </div>
</template>

<script setup lang="ts">
    import { onMounted, reactive, ref } from "vue";
    import { getImgApi, loginApi } from "@/api/user/index";
    import { ElMessage, FormInstance } from "element-plus";
    import { useRouter } from "vue-router";
    import { userStore } from "@/store/user";

    const store = userStore();
    const router = useRouter()
    const loginRef = ref<FormInstance>()
    const loginModel = reactive({
        username: "",
        password: "",
        code: "",
    });

    // 表单验证规则
    const rules = reactive({
        username: [{ required: true, message: "请输入登录账号", trigger: "blur" }],
        password: [{ required: true, message: "请输入登录密码", trigger: "blur" }],
        code: [{ required: true, message: "请输入验证码", trigger: "blur" }],
    });

    // 获取验证码
    const img = ref("");
    const getImg = async () => {
        let res = await getImgApi();
        if (res && res.code == 200) {
            img.value = res.data;
        }
    };

    onMounted(() => {
        getImg();
    });

    // 登录提交
    const commit = () => {
        loginRef.value?.validate(async (valid) => {
            if (valid) {
                let res = await loginApi(loginModel)
                if (res && res.code == 200) {
                    ElMessage.success(res.msg)
                    store.setuserId(res.data.userId)
                    store.setMenu(res.data.menuList)
                    store.setCode(res.data.codeList)
                    // 跳转首页
                    router.push({ name: 'home' })
                }
            }
        })
    }
</script>

<style lang="scss" scoped>
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
            height: 320px;
            width: 420px;
            padding: 20px 35px;

            .logintitle {
                color: #606266;
                width: 100%;
                font-size: 24px;
                justify-content: center;
                align-items: center;
                font-weight: 600;
                display: flex;
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