import http from '@/http/index'

// 统计总数
export const getTtotalApi = ()=>{
    return http.get("/api/home/getTtotal")
} 

// 直方图数据
export const getEchartsApi = ()=>{
    return http.get("/api/home/getEcharts")
} 

// 热门社团
export const hotTeamListApi = ()=>{
    return http.get("/api/home/hotTeamList")
} 

// 热门活动
export const hotActivityListApi = ()=>{
    return http.get("/api/home/hotActivityList")
} 

// 男女比例
export const stuTotalApi = ()=>{
    return http.get("/api/home/stuTotal")
} 