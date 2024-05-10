import { getCurrentInstance, ComponentInternalInstance } from 'vue'

export default function useInstance(){
    const { appContext, proxy } = getCurrentInstance() as ComponentInternalInstance
    
    // 获取全局属性
    const  global = appContext.config.globalProperties
    return{
        global,
        proxy
    }
}