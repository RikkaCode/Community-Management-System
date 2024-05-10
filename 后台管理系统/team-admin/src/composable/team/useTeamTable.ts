import { nextTick, onMounted, reactive, ref } from "vue";
import { getListApi } from "@/api/team";
import { get } from "node_modules/axios/index.d.cts";

export default function useTeamTable(){
    // 表格高度
    const tableHeight = ref(0)

    // 表格数据
    const tableList = ref([])

    // 列表参数
    const searchParm = reactive({
        currentPage:1,
        pageSize:10,
        name:'',
        total:0
    })

    // 列表查询
    const getList = async()=>{
        let res = await getListApi(searchParm)
        if(res && res.code == 200){
            console.log(res)
            tableList.value = res.data.records
            // 设置总条数
            searchParm.total = res.data.total
        }
    }

    // 搜索
    const searchBtn = ()=>{
        getList()
    }

    // 重置
    const resetBtn = ()=>{
        searchParm.name = ''
        getList()
    }

    // 新增
    const addBtn = ()=>{

    }

    // 页容量改变触发
    const sizeChange = (size:number)=>{
        searchParm.pageSize = size
        getList()
    }

    const currentChange = (page:number)=>{
        searchParm.currentPage = page
        getList()
    }

    onMounted(()=>{
        getList();
        nextTick(()=>{
            tableHeight.value = window.innerHeight - 200
        })
    })

    return{
        tableList,
        searchParm,
        getList,
        searchBtn,
        resetBtn,
        sizeChange,
        currentChange,
        tableHeight
    }
}