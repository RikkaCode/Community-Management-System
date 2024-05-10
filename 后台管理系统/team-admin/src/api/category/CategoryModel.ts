//定义分类的数据类型
export type CategoryModel = {
    id: string,
    name: string,
    orderNum: string
}

//分页查询数据类型
export type CategoryPage = {
    currentPage: number,
    pageSize: number,
    name: string,
    total: number
}