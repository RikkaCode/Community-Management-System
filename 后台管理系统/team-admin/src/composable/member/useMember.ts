import { stopStuApi, startStuApi, deleteStuApi, resetPasswordApi } from "@/api/member";
import { ElMessage } from "element-plus";
import useInstance from "@/hooks/useInstance";
import { FuncList } from "@/type/BaseType";

export default function useMember(getList:FuncList){
    const { global } = useInstance();

    // 停用
    const stopBtn = async(row:any)=>{
        let confirm = await global.$myconfirm('确定停用该用户吗？')
        if (confirm){
            let res = await stopStuApi({
                sutId: row.sutId,
                status: '1'
            })
            if (res && res.code == 200){
                ElMessage.success(res.msg)
                getList()
            }
        }
    }

    // 启用
    const startBtn = async(row:any)=>{
        let confirm = await global.$myconfirm('确定启用该用户吗？')
        if (confirm){
            let res = await startStuApi({
                sutId: row.sutId,
                status: '0'
            })
            if (res && res.code == 200){
                ElMessage.success(res.msg)
                getList()
            }
        }
    }

    // 重置密码
    const resetPassword = async(row:any)=>{
        let confirm = await global.$myconfirm('确定重置密码吗？默认密码为"666666"')
        if (confirm){
            let res = await resetPasswordApi({
                sutId: row.sutId,
                password: '666666'
            })
            if (res && res.code == 200){
                ElMessage.success(res.msg)
                getList()
            }
        }
     }

    // 删除
    const deleteBtn = async(row:any)=>{
        let confirm = await global.$myconfirm('确定删除吗？')
        if(confirm){
            let res = await deleteStuApi(row.sutId)
            if (res && res.code == 200){
                ElMessage.success(res.msg)
                getList()
            }
        }
    }
    return{
        stopBtn,
        resetPassword,
        startBtn,
        deleteBtn
    }
}