import { NewsType } from "@/api/news/NewsModel"
import { EditType, FuncList } from "@/type/BaseType"
import { ref } from "vue"
import { deleteNewsApi, applyNewsApi } from "@/api/news"
import useInstance from "@/hooks/useInstance"
import { ElMessage } from "element-plus"

export default function useActivity(getList:FuncList){
    const { global } = useInstance()
    // 新增弹框属性
    const addActivityRef = ref<{show:(type:string, row?:NewsType)=>void}>();

    // 新增
    const addBtn = () => {
        addActivityRef.value?.show(EditType.ADD);
    }

    // 编辑
    const editBtn = (row:NewsType) => {
        addActivityRef.value?.show(EditType.EDIT, row);
    }

    // 删除
    const deleteBtn = async(row:NewsType) => {
        let confirm = await global.$myconfirm('确定删除该数据吗？');
        if(confirm){
            let res = await deleteNewsApi(row);
            if(res && res.code == 200){
                ElMessage.success(res.msg);
                // 刷新表格
                getList();
            }
        }
    }

    // 审核
    const applyBtn = async(row:NewsType)=>{
        if(row.status == '1'){
            ElMessage.warning('已审核，不要重复审核!')
            return;
        }
        if(row.status == '2'){
            ElMessage.warning('已拒绝，不要重复审核!')
            return;
        }
        let confirm = await global.$myconfirm('确定审核通过吗?')
        if(confirm){
            let res = await applyNewsApi({
                id:row.id,
                status:'1'
            })
            if(res && res.code == 200){
                ElMessage.success('审核成功！')
                // 刷新表格
                getList()
            }
        }
    }

    // 拒绝
    const returnBtn = async(row:NewsType)=>{
        if(row.status == '1'){
            ElMessage.warning('已审核，不能拒绝!')
            return;
        }
        if(row.status == '2'){
            ElMessage.warning('已拒绝，不要重复拒绝!')
            return;
        }
        let confirm = await global.$myconfirm('确定拒绝吗?')
        if(confirm){
            let res = await applyNewsApi({
                id:row.id,
                status:'2'
            })
            if(res && res.code == 200){
                ElMessage.success('拒绝成功！')
                //刷新表格
                getList()
            }
        }
    }

    return{
        addBtn,
        editBtn,
        deleteBtn,
        addActivityRef,
        applyBtn,
        returnBtn
    }
}