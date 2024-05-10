import { TeamType } from '@/api/team/TeamModel';
import { EditType, FuncList } from '@/type/BaseType';
import { ref } from 'vue';
import useInstance from '@/hooks/useInstance';
import { deleteTeamApi } from '@/api/team';
import { ElMessage } from 'element-plus';

export default function useTeam(getList:FuncList) {
    const { global } = useInstance();

    // 新增ref属性
    const addTeamRef = ref<{ show: (type:string, row?:TeamType) => void }>();

    // 新增
    const addBtn = () => {
        addTeamRef.value?.show(EditType.ADD);
    }

    // 编辑
    const editBtn = (row:TeamType) => {
        addTeamRef.value?.show(EditType.EDIT, row);
    }

    // 删除
    const deleteBtn = async(row:TeamType) => {
        const confirm = await global.$myconfirm('确定删除该数据吗?');
        if (confirm) {
            let res = await deleteTeamApi(row);
            if (res && res.code == 200){
                ElMessage.success(res.msg);
                // 刷新表格
                getList();
            }
        }
    }

    return {
        addBtn,
        editBtn,
        deleteBtn,
        addTeamRef
    }
}