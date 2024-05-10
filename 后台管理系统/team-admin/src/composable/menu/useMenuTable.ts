import { onMounted, ref } from "vue";
import { getListApi } from "@/api/menu";

export default function useMenuTable(){
    // 表格数据
    const tableList = ref([])
    // 获取表格数据
    const getList = async()=>{
        let res = await getListApi()
        if(res && res.code == 200){
            // 设置表格数据
            tableList.value = res.data;
        }
    }
    onMounted(()=>{
        getList()
    })
    return{
        tableList,
        getList
    }
}