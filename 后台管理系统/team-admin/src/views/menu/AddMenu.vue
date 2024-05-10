<template>
    <SysDialog :title="dialog.title" :visible="dialog.visible" :width="dialog.width" :height="dialog.height"
        @on-close="onClose" @on-confirm="commit">
        <template v-slot:content>
            <el-form :model="addModel" ref="addFormRef" :rules="rules" label-width="80px" :inline="false"
                size="default">
                <el-form-item prop="type" label="菜单类型">
                    <el-radio-group v-model="addModel.type">
                        <el-radio :label="'1'">菜单</el-radio>
                        <el-radio :label="'2'">按钮</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="parentId" label="上级菜单">
                            <el-tree-select v-model="addModel.parentId" :data="treeData" check-strictly
                                :render-after-expand="false" show-checkbox />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="title" label="菜单名称">
                            <el-input v-model="addModel.title" placeholder="请填写菜单名称"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="code" label="权限字段">
                            <el-input v-model="addModel.code" placeholder="请填写权限字段"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="orderNum" label="菜单序号">
                            <el-input v-model="addModel.orderNum" placeholder="请填写菜单序号"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row v-if="addModel.type != '2'">
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="path" label="路由">
                            <el-input v-model="addModel.path" placeholder="请填写路由path"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="icon" label="菜单图标">
                            <el-input v-model="addModel.icon" placeholder="请填写菜单图标"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <!-- <el-row>
                    <el-col v-if="addModel.type != '2'" :span="12" :offset="0">
                        <el-form-item prop="icon" label="菜单图标">
                            <el-input v-model="addModel.icon" placeholder="请填写菜单图标"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row> -->
            </el-form>
        </template>
    </SysDialog>
</template>

<script setup lang="ts">
    import { nextTick, reactive, ref } from "vue";
    import SysDialog from "@/components/SysDialog.vue";
    import useDialog from "@/hooks/useDialog";
    import { ElMessage, FormInstance } from "element-plus";
    import { getParentListApi, addMenuApi, MenuType, editMenuApi } from "@/api/menu";
    import { EditType, Title } from "@/type/BaseType";
    const addFormRef = ref<FormInstance>();

    // 弹框属性
    const { dialog, onClose, onShow } = useDialog();
    // 显示弹框
    const tags = ref('')

    const show = (type: string, row: MenuType) => {
        tags.value = type;
        type == EditType.ADD ? dialog.title = Title.ADD : dialog.title = Title.EDIT;
        dialog.height = 220;
        // 如果是编辑，回显数据
        if (type == EditType.EDIT && row) {
            nextTick(() => {
                Object.assign(addModel, row)
            })
        }
        // 获取上级菜单数据
        getParentList();
        onShow();
        // 清空表单
        addFormRef.value?.resetFields();
    };

    // 暴露出去，外部才能使用
    defineExpose({
        show,
    });
    
    // 表单绑定的对象
    const addModel = reactive({
        menuId: "",
        parentId: "",
        title: "",
        code: "",
        type: "", // 菜单类型
        path: "",
        icon: "",
        parentName: "",
        orderNum: "",
    });

    // 表单验证规则
    const rules = reactive({
        parentId: [
            {
                required: true,
                trigger: "change",
                message: "请选择上级菜单",
            },
        ],
        title: [
            {
                required: true,
                trigger: "change",
                message: "请填写菜单名称",
            },
        ],
        code: [
            {
                required: true,
                trigger: "change",
                message: "请填写权限字段",
            },
        ],
        type: [
            {
                required: true,
                trigger: "change",
                message: "请选择菜单类型",
            },
        ],
    });

    // 获取上级菜单
    const treeData = ref([]);
    const getParentList = async () => {
        let res = await getParentListApi();
        if (res && res.code == 200) {
            treeData.value = res.data;
        }
    };
    
    // 注册事件
    const emits = defineEmits(["onFresh"]);

    // 表单提交
    const commit = () => {
        addFormRef.value?.validate(async (valid) => {
            if (valid) {
                let res = null;
                if (tags.value == EditType.ADD) {
                    res = await addMenuApi(addModel);
                } else {
                    res = await editMenuApi(addModel);
                }
                if (res && res.code == 200) {
                    ElMessage.success(res.msg);
                    // 刷新表格
                    emits("onFresh");
                    onClose();
                }
            }
        });
    };
</script>

<style scoped></style>