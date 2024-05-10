import { nextTick, onMounted, reactive, ref } from "vue";
import { getMemberListApi } from "@/api/member";

export default function useMemberTable(){
    // 搜索参数
    const searchParm = reactive({
        currentPage:1,
        pageSize:10,
        name:'',
        phone:'',
        total:0
    })
    // 表格数据
    const tableList = ref([])
    // 列表
    const getList = async()=>{
        let res = await getMemberListApi(searchParm)
        if(res && res.code == 200){
            tableList.value = res.data.records;
            searchParm.total = res.data.total;
        }
    }
    // 搜索
    const searchBtn = ()=>{
        getList()
    }
    // 重置
    const resetBtn = ()=>{
        searchParm.name = ''
        searchParm.phone = ''
        searchParm.currentPage = 1
        getList()
    }
    // 页容量改变触发
    const sizeChange = (size:number)=>{
        searchParm.pageSize = size
        getList()
    }
    // 页数改变触发
    const currentChange = (page:number)=>{
        searchParm.currentPage = page
        getList()
    }
    // 计算表格高度
    const tableHeight = ref()
    onMounted(()=>{
        getList()
        nextTick(()=>{
            tableHeight.value = window.innerHeight - 220
        })
    })
    return{
        searchParm,
        getList,
        searchBtn,
        resetBtn,
        tableList,
        sizeChange,
        currentChange,
        tableHeight
    }
}