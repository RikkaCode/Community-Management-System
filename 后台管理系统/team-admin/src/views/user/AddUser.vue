<template>
  <SysDialog :title="dialog.title" :visible="dialog.visible" :height="dialog.height" @on-close="onClose"
    @on-confirm="commit">
    <template v-slot:content>
      <el-form :model="addModel" ref="addFormRef" :rules="rules" :inline="false" label-width="80px" size="default">
        <el-form-item prop="nickName" label="姓名">
          <el-input v-model="addModel.nickName"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="性别">
          <el-radio-group v-model="addModel.sex">
            <el-radio :value="'0'">男</el-radio>
            <el-radio :value="'1'">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="phone" label="电话">
          <el-input v-model="addModel.phone"></el-input>
        </el-form-item>
        <el-form-item prop="username" label="登录账户">
          <el-input v-model="addModel.username"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input v-model="addModel.password"></el-input>
        </el-form-item>
        <el-form-item prop="status" label="状态">
          <el-radio-group v-model="addModel.status">
            <el-radio :value="'0'">启用</el-radio>
            <el-radio :value="'1'">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </template>
  </SysDialog>
</template>

<script setup lang="ts">
  import SysDialog from "@/components/SysDialog.vue";
  import useDialog from "@/hooks/useDialog";
  import { User } from "@/api/user/UserModel";
  import { nextTick, reactive, ref } from "vue";
  import { ElMessage, FormInstance } from "element-plus";
  import { addUserApi, editUserApi } from '@/api/user/index'
  import { EditType, Title } from "@/type/BaseType";
  
  // 表单ref属性
  const addFormRef = ref<FormInstance>();
  // 弹框属性
  const { dialog, onClose, onShow } = useDialog();
  // 表单绑定的数据对象
  const addModel = reactive<User>({
    userId: "",
    username: "",
    password: "",
    nickName: "",
    phone: "",
    sex: "",
    status: ""
  });
  // 表单验证规则
  // 1.el-form-item加prop属性
  // 2.编写表单验证规则 rules
  // 3.表单要绑定 :rules="rules"
  const rules = reactive({
    username: [{ required: true, message: '请输入登录账号', trigger: 'blur' }],
    password: [{ required: true, message: '请输入登录密码', trigger: 'blur' }],
    nickName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
    phone: [{ required: true, message: '请输入电话', trigger: 'blur' }],
    sex: [{ required: true, message: '请输入性别', trigger: 'blur' }],
    status: [{ required: true, message: '请选择状态', trigger: 'blur' }]
  });
  // 新增和编辑的标识
  const tags = ref('')
  // 显示弹框
  const show = (type: string, row: User) => {
    tags.value = type;
    // 设置弹框标题
    type == EditType.ADD ? dialog.title = Title.ADD : dialog.title = Title.EDIT;
    dialog.height = 300
    onShow();
    // 编辑回显
    if (EditType.EDIT == type && row) {
      nextTick(() => {
        // 设置编辑回显的数据
        Object.assign(addModel, row)
      })
    }
    // 清空表单
    addFormRef.value?.resetFields()
  };
  // 暴露组件里面的方法，给外部组件使用
  defineExpose({
    show,
  });
  // 注册事件
  const emits = defineEmits(['onFresh'])
  // 表单提交
  const commit = () => {
    // 验证表单
    addFormRef.value?.validate(async (valid) => {
      console.log(valid)
      // valid返回true的时候才是验证通过
      if (valid) {
        // 提交表单
        // await:等待数据返回，才执行下面的代码
        let res = null
        if (tags.value == EditType.ADD) {
          res = await addUserApi(addModel)
        } else {
          res = await editUserApi(addModel)
        }
        if (res && res.code == 200) {
          // 信息提示
          ElMessage.success(res.msg)
          // 刷新表格
          emits('onFresh')
          onClose()
        }
      }
    })
  }
</script>

<style scoped></style>
