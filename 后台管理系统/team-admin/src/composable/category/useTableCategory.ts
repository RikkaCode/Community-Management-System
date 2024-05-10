import { nextTick, onMounted, reactive, ref } from "vue";
import { getCategoryApi } from "@/api/category";

export default function useTableCategory(){
    // 表格数据
    const tableList = ref([])
    // 表格高度
    const tableHeight = ref(0)
    // 搜索栏绑定数据
    const searchParm = reactive({
        currentPage:1,
        pageSize:10,
        name:'',
        total:0
    })
    
    // 列表
    const getList = async()=>{
        let res = await getCategoryApi(searchParm)
        if(res && res.code == 200){
            // 把返回值设置到表格数据
            tableList.value = res.data.records;
            searchParm.total = res.data.total;
        }
    }
    
    // 页容量改变时触发
    const sizeChange = (size:number)=>{
        searchParm.pageSize = size
        getList()
    }

    // 页数改变时触发
    const currentChange = (page:number)=>{
        searchParm.currentPage = page
        getList()
    }

    // 搜索
    const searchBtn = ()=>{
        getList()
    }

    // 重置
    const resetBtn = ()=>{
        searchParm.currentPage = 1
        searchParm.name = ''
        getList() 
    }
    onMounted(()=>{
        getList()
        nextTick(()=>{
            tableHeight.value = window.innerHeight - 220
        })
    })

    return{
        searchParm,
        getList,
        tableList,
        sizeChange,
        currentChange,
        tableHeight,
        searchBtn,
        resetBtn
    }
}