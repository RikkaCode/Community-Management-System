import {defineStore} from 'pinia'

// 定义store
// 第一个参数是应用中 Store 的唯一 ID
export const userSotre = defineStore('userSotre',{
    state:()=>{
        return{
            userInfo:{
                sutId:'',
                username:'',
                phone:'',
                name:'',
                major:"",
                grade:'',
                classes:'',
                sex: ''
            }
        }
    },
    actions:{
        setUserinfo(user:any){
            this.userInfo = user;
        }
    },
    persist: {
        enabled: true,
        strategies: [
            { storage: sessionStorage, paths: ['userInfo'] },
        ],
    }
})