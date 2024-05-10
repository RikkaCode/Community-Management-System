import http from "@/http";

// 菜单数据类型
export type MenuType = {
    menuId: string,
    parentId: string,
    title: string,
    code: string,
    type: string, // 菜单类型
    path: string,
    icon: string,
    orderNum: string,
}

// 列表查询
export const getListApi = ()=>{
    return http.get("/api/menu/list")
}

// 查询上级菜单
export const getParentListApi = ()=>{
    return http.get("/api/menu/getParent")
}

// 新增
export const addMenuApi = (parm:MenuType)=>{
    return http.post("/api/menu",parm)
}

// 编辑
export const editMenuApi = (parm:MenuType)=>{
    return http.put("/api/menu",parm)
}

// 删除
export const deleteMenuApi = (menuId:string)=>{
    return http.delete(`/api/menu/${menuId}`)
}