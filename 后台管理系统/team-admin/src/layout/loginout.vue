<template>
    <el-dropdown style="padding: 0px 20px; cursor: pointer; border: none">
        <span class="el-dropdown-link">
            <img class="userimg" src="@/assets/user.png" />
        </span>
        <template #dropdown>
            <el-dropdown-menu>
                <el-dropdown-item @click="updatePassword">密码修改</el-dropdown-item>
                <el-dropdown-item @click="loginOut">退出登录</el-dropdown-item>
            </el-dropdown-menu>
        </template>
    </el-dropdown>

    <!-- 修改密码弹框 -->
    <SysDialog :title="dialog.title" :visible="dialog.visible" :height="dialog.height" :width="dialog.width"
        @on-close="onClose" @on-confirm="commit">
        <template v-slot:content>
            <el-form :model="parm" ref="formRef" :rules="rules" label-width="80px" :inline="false" size="default">
                <el-form-item prop="oldPassword" label="原密码">
                    <el-input v-model="parm.oldPassword"></el-input>
                </el-form-item>
                <el-form-item prop="newPassword" label="新密码">
                    <el-input v-model="parm.newPassword"></el-input>
                </el-form-item>
            </el-form>
        </template>
    </SysDialog>
</template>

<script setup lang="ts">
    import { ref, reactive } from 'vue';
    import { FormInstance } from 'element-plus';
    import { useRouter } from 'vue-router';
    import useInstance from '@/hooks/useInstance';
    import SysDialog from '@/components/SysDialog.vue';
    import useDialog from '@/hooks/useDialog';
    import { userStore } from '@/store/user';
    import { updatePasswordApi } from '@/api/user/index'

    // 获取store
    const store = userStore();
    const formRef = ref<FormInstance>()

    // 弹框组件属性
    const { dialog, onClose, onShow } = useDialog();

    //获取router
    const router = useRouter()
    //获取全局属性
    const { global } = useInstance()
    const loginOut = async () => {
        let confirm = await global.$myconfirm('确定退出登录吗?')
        if (confirm) {
            //清空缓存数据
            sessionStorage.clear()
            //跳转到登录页面
            router.push({ name: 'login' })
        }
    };

    const updatePassword = () => {
        // 设置弹框属性
        dialog.height = 150;
        dialog.title = '修改密码';
        // 显示弹框
        onShow();
    }

    // 修改密码的对象
    const parm = reactive({
        userId: store.userId,
        oldPassword: '',
        newPassword: ''
    })

    // 表单验证规则
    const rules = reactive({
        oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
        newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }]
    })

    // 提交修改
    const commit = () => {
        formRef.value?.validate(async (valid) => {
            if (valid) {
                // 提交修改
                console.log(parm)
                let res = await updatePasswordApi(parm)
                if (res && res.code == 200) {
                    // 清空缓存数据
                    sessionStorage.clear()
                    // 跳转到登录页面
                    router.push({ name: 'login' })
                }
            }
        })
    }
</script>

<style scoped>
    .userimg {
        height: 38px;
        width: 38px;
        border-radius: 50%;
        border: none;
    }
</style>