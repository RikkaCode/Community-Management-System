<template>
    <SysDialog :title="dialog.title" :width="dialog.width" :height="dialog.height" :visible="dialog.visible"
        @onClose="onClose" @onConfirm="commit">
        <template v-slot:content>
            <el-tree ref="assignTree" :data="assignTreeData.list" node-key="menuId" :props="defaultProps"
                empty-text="暂无数据" :show-checkbox="true" default-expand-all :highlight-current="true"
                :default-checked-keys="assignTreeData.assignTreeChecked"></el-tree>
        </template>
    </SysDialog>
</template>

<script setup lang="ts">
    import SysDialog from "@/components/SysDialog.vue";
    import useDialog from "@/hooks/useDialog";
    import useAssign from "@/composable/user/useAssign";
    import { userStore } from "@/store/user";
    import { ref, reactive } from "vue";
    import { ElMessage, ElTree } from "element-plus";
    import { SaveAssignParm } from '@/api/user/UserModel'
    import { assignSaveApi } from '@/api/user/index'

    // 树的ref属性
    const assignTree = ref<InstanceType<typeof ElTree>>();
    const store = userStore();
    const { defaultProps, getAssignTree, assignTreeData } = useAssign();
    // 弹框属性
    const { dialog, onClose, onShow } = useDialog();
    // 弹框显示
    // 提交的数据
    const saveParm = reactive<SaveAssignParm>({
        list: [],
        assId: '',
    });
    const show = (assId: string, nick_name: string) => {
        console.log(assId)
        saveParm.assId = assId;
        // 清空数据
        assignTreeData.assignTreeChecked = [];
        assignTreeData.list = [];
        // 获取菜单树数据和需要回显的数据
        getAssignTree({
            assId: assId,
            userId: store.userId,
        });
        // 设置弹框属性
        dialog.title = "为【" + nick_name + "】分配权限";
        dialog.width = 300;
        dialog.height = 450;
        onShow();
    };

    // 暴露出去
    defineExpose({
        show,
    });

    // 提交
    const commit = async () => {
        let checkedIds = assignTree.value?.getCheckedKeys(false) as string[];
        let hlfIds = assignTree.value?.getHalfCheckedKeys() as string[];
        let ids = checkedIds?.concat(hlfIds);
        saveParm.list = ids;
        // 判断是否已经勾选权限
        if (saveParm.list.length == 0) {
            ElMessage.warning("请勾选权限");
            return;
        }
        console.log(saveParm);
        let res = await assignSaveApi(saveParm)
        if (res && res.code == 200) {
            ElMessage.success(res.msg)
            onClose()
        }
    };
</script>

<style scoped></style>