import { defineStore } from 'pinia'
import { getNewsListApi } from '@/api/news'

// 定义store
// 第一个参数是你的应用中 Store 的唯一 ID
export const newsStore = defineStore('newsSotre', {
    state: () => {
        return {
            // 活动列表数据
            newsList: [],
            // 列表查询的参数
            parm: {
                currentPage: 1,
                pageSize: 10,
                type: '',
                total: 0,
                status: ''
            }
        }
    },
    
    actions: {
        // 获取列表
        async getNewsList() {
            let res = await getNewsListApi(this.parm)
            if(res && res.code == 200){
                this.newsList = res.data.records;
                this.parm.total = res.data.total;
            }
        }
    },
    persist: {
        enabled: true,
        strategies: [
            { storage: sessionStorage, paths: ['newsList','parm'] },
        ],
    }
})