import { nextTick, onMounted, reactive, ref } from "vue";
import { getListApi } from "@/api/news";

export default function useActivityTable() {
    const options = [
        {
          value: '0',
          label: '活动',
        },
        {
          value: '1',
          label: '新闻',
        },
        {
          value: '2',
          label: '公告',
        }
      ]

    // 搜索参数
    const searchParm = reactive({
        currentPage: 1,
        pageSize: 10,
        title: '',
        total: 0,
        type: ''
    })

    // 表格数据
    const tableList = ref([])

    // 列表
    const getList = async() => {
        let res = await getListApi(searchParm)
        if (res && res.code == 200) {
            // 把后端返回的数据设置到表格数据
            console.log(res)
            tableList.value = res.data.records;
            searchParm.total = res.data.total;
        }
    }

    // 搜索
    const searchBtn = () => {
        getList();
    }

    // 重置
    const resetBtn = () => {
        searchParm.title = ''
        searchParm.type = ''
        getList()
    }

    // 页数改变时触发
    const sizeChange = (size:number)=>{
        searchParm.pageSize = size;
        getList()
    }
    const currentChange = (page:number)=>{
        searchParm.currentPage = page;
        getList()
    }
    // 表格高度
    const tableHeight = ref(0)
    onMounted(()=>{
        getList()
        nextTick(()=>{
            tableHeight.value = window.innerHeight - 200
        })
    })

    onMounted(() => {
        getList()
    })

    return {
        searchParm,
        getList,
        searchBtn,
        resetBtn,
        options,
        tableList,
        sizeChange,
        currentChange,
        tableHeight
    }
}