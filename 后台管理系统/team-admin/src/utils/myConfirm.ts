import { ElMessageBox } from 'element-plus'
export default function myConfirm(text: string) {
    return new Promise((resolve, reject) => {
        ElMessageBox.confirm(
            text,
            '系统提示',
            {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }
        ).then(() => {
            resolve(true)
        }).catch(() => {
            reject(false)
        })
    }).catch(()=>{
        return false
    })
}