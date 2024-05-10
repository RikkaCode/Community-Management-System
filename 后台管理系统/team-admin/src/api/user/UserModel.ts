// 用户的数据类型
export type User = {
    userId:string,
    username:string,
    password:string,
    nickName:string,
    phone:string,
    sex:string,
    status:string
}

// 表格查询参数
export type ListParm = {
    currentPage:number,
    pageSize:number,
    nickName:string,
    total?:number
}

// 登录参数类型
export type LoginType = {
    username: string,
    password: string,
    code:string
}

export type AssignParm = {
    assId:string,
    userId:string
}

// 保存权限的数据类型
export type SaveAssignParm = {
    assId:string,
    list:Array<string>
}

// 修改密码参数类型
export type UpdatePassParm = {
    userId:string,
    oldPassword:string,
    newPassword:string
}