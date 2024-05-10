import { defineStore } from 'pinia'


// 定义一个store
export const testStore = defineStore('testStore', {
    state:()=>{
        return {
            count: 0
        }
    },
    getters: {
        // 获取count值
        getCount(state) {
            return state.count
        }
    },
    actions: {
        // 设置值
        setCount(count: number) {
            this.count = count
        }
    }

})