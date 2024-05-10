import { defineStore } from 'pinia'

// 定义store
export const userStore = defineStore('userStore', {
    // 存储count
    state: () => {
        return {
            userId: '',
            menuList: [],
            codeList: [],
        }
    },

    // 获取state数据
    getters: {
        getUserId(state) {
            return state.userId
        }
    },
    
    // 操作state数据
    actions: {
        setuserId(userId: string) {
            this.userId = userId;
        },
        setMenu(menu: any) {
            this.menuList = menu;
        },
        setCode(code: any) {
            this.codeList = code;
        },
    },
    persist: {
        enabled: true,
        strategies: [
            { storage: sessionStorage, paths: ['userId', 'menuList', 'codeList'] },
        ],
    }
})