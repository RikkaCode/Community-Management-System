import { ref } from "vue";
import { getSelectApi } from "@/api/team";

export default function useSelectCategory(){
     //下拉数据
     const selectData = ref([])
     //获取下拉数据
     const getSelect = async()=>{
         let res = await getSelectApi()
         if(res && res.code == 200 && res.data){
             selectData.value = res.data;
         }
     }
     return{
         selectData,
         getSelect
     }
}