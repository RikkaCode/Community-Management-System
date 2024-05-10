import { defineStore } from 'pinia'

//定义store
export const menuStore = defineStore('menuStore', {
    // 存储count
    state: () => {
        return {
            isCollapse: false
        }
    },

    // 获取state数据
    getters: {
        getCollapse(state) {
            return state.isCollapse
        }
    },

    // 操作state数据
    actions: {
        setCollapse() {
            this.isCollapse = !this.isCollapse;
        }
    }
})