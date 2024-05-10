import { defineStore } from 'pinia'


// 定义一个store
export const tabsStore = defineStore('tabsStore', {
    state:()=>{
        return {
            fatabs: 'home',
            mineTabs: '/mine/mycenter'
        }
    },
    actions: {
        setFatabs(fatabs: string){
            this.fatabs = fatabs
        },
        setMineTabs(mineTabs: string){
            this.mineTabs = mineTabs
        }
    },
    persist: {
        enabled: true,
        strategies: [
            { storage: localStorage, paths: ['fatabs','mineTabs'] },
        ]
    }
})