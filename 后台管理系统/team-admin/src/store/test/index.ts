import { defineStore } from 'pinia'

// 定义store
export const testStore = defineStore('testStore', {
    // 存储count
    state: () => {
        return {
            count: 0
        }
    },

    // 获取state数据
    getters: {
        getCount(state) {
            return state.count
        }
    },
    
    // 操作state数据
    actions: {
        setCount(count: number) {
            this.count = count;
        }
    }
})