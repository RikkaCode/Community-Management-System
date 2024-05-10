<template>
  <div style="color: #FFF;font-size: 14px;cursor: pointer;" v-if="name">欢迎您，{{ name }}</div>
  <div v-else @click="loginBtn" style="color: #FFF;font-size: 14px;cursor: pointer;">登录/注册</div>
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
  <!-- 修改密码 -->
  <SysDialog :title="dialog.title" :visible="dialog.visible" :height="dialog.height" :width="dialog.width"
    @on-close="onClose" @on-confirm="commitPass">
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
  import { computed, reactive, ref } from 'vue';
  import SysDialog from '@/components/SysDialog.vue';
  import { useRouter } from 'vue-router';
  import { userSotre } from '@/store/user';
  import useInstance from '@/hooks/useInstance';
  import useDialog from '@/hooks/useDialog';
  import { ElMessage, FormInstance } from 'element-plus';
  import { updatePasswordApi } from '@/api/login/index'

  const formRef = ref<FormInstance>()
  // 弹框属性
  const { dialog, onClose, onConfirm, onShow } = useDialog()
  // 获取全局属性
  const { global } = useInstance()
  // 获取store
  const store = userSotre()
  // 获取用户的信息
  const name = computed(() => {
    return store.userInfo.name
  })
  // 获取router
  const router = useRouter()
  // 登录注册点击事件
  const loginBtn = () => {
    router.push({ name: 'login' })
  }
  
  // 退出登录
  const loginOut = async () => {
    let confirm = await global.$myconfirm('确定退出吗？')
    if (confirm) {
      sessionStorage.clear()
      router.push({ name: 'login' })
    }
  }

  const updatePassword = () => {
    // 设置弹框属性
    dialog.height = 150;
    dialog.title = '修改密码'
    // 显示弹框
    onShow()
  }

  // 修改密码的对象
  const parm = reactive({
    sutId: store.userInfo.sutId,
    oldPassword: '',
    newPassword: ''
  })

  // 表单验证规则
  const rules = reactive({
    oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
    newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
  })

  // 修改密码提交
  const commitPass = () => {
    console.log(store.userInfo)
    formRef.value?.validate(async (valid) => {
      if (valid) {
        let res = await updatePasswordApi(parm)
        if (res && res.code == 200) {
          ElMessage.success(res.msg)
          router.push({ name: 'login' })
        }
      }
    })
  }
</script>

<style scoped lang="scss">
  .userimg {
    height: 38px;
    width: 38px;
    border-radius: 50%;
    border: none;
  }
</style>
