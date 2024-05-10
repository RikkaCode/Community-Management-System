import { MenuType } from "@/api/menu"
import { EditType, FuncList } from "@/type/BaseType"
import { ref } from "vue"
import { deleteMenuApi } from "@/api/menu"
import { ElMessage } from "element-plus"
import useInstance from "@/hooks/useInstance"

export default function useMenu(getList: FuncList) {
    const { global } = useInstance()
    // 新增弹框的ref属性
    const addRef = ref<{ show: (type: string, row?: MenuType) => void }>()
    // 新增
    const addBtn = () => {
        addRef.value?.show(EditType.ADD)
    }
    // 编辑
    const editBtn = (row: MenuType) => {
        addRef.value?.show(EditType.EDIT, row)
    }
    // 删除
    const deleteBtn = async (row: MenuType) => {
        // 确认信息提示
        let confirm = await global.$myconfirm('确定删除该数据吗?')
        if (confirm) {
            let res = await deleteMenuApi(row.menuId)
            if (res && res.code == 200) {
                // 信息提示
                ElMessage.success(res.msg)
                // 刷新表格
                getList()
            }
        }

    }
    return {
        addBtn,
        editBtn,
        deleteBtn,
        addRef
    }
}