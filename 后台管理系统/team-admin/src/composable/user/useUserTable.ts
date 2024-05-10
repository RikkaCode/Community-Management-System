import { nextTick, onMounted, reactive, ref } from "vue"
import { getListApi } from "@/api/user"
import { ListParm } from "@/api/user/UserModel"

export default function useUserTable() {
    // 表格数据
    const tableList = ref([])
    // 表格高度
    const tableHeight = ref(0)
    // 搜索表单绑定的对象
    const searchParm = reactive<ListParm>({
        nickName: '',
        pageSize:10,
        currentPage:1,
        total:0
    })

    // 获取列表
    const getList = async()=>{
        let res = await getListApi(searchParm)
        if(res && res.code == 200){
            // 把后端返回的数据设置到表格数据
            // console.log(res)
            tableList.value = res.data.records
            // 设置总条数
            searchParm.total = res.data.total;
        }
    }
     
    // 搜索按钮
    const searchBtn = () => {
        getList()
    }

    // 重置按钮
    const resetBtn = () => {
        searchParm.nickName = ''
        searchParm.currentPage = 1;
        getList()
    }

    // 页容量改变时触发
    const sizeChange = (size:number)=>{
        searchParm.pageSize = size;
        getList()
    }
    // 页数改变时触发
    const currentChange = (page:number)=>{
        searchParm.currentPage = page;
        getList()
    }
    onMounted(()=>{
        getList()
        // 表格高度计算
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