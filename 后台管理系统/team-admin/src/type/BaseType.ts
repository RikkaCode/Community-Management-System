// 通用函数类型
export type FuncList = () => any;

// 弹框标题
export enum Title{
    ADD = '新增',
    EDIT = '编辑'
}

// 新增和编辑的状态
export enum EditType {
    ADD = '0', // 新增
    EDIT = '1' // 编辑
}

// 图片上传数据类型
export type NewType = {
    newImgUrl:Array<{url:string}>;
    deleteUrl:Array<{url:string}>;
}
