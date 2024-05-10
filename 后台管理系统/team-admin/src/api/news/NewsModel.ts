// 活动的参数类型
export type NewsType = {
    id: string,
    type: string,
    createId: string,
    image: string,
    title: string,
    details: string,
    hasTop: string,
    hasJoin: string,
    status: string
}

// 列表查询的参数类型
export type PageList = {
    currentPage: number,
    pageSize: number,
    title: string,
    type: string,
    total?: number
}

// 活动的参数类型
export type NewsApply = {
    id: string,
    status: string
}