import http from "@/http";

//推荐社团
export const getTopTeamApi = ()=>{
    return http.get("/api/team/getTopTeam")
}

//推荐活动
export const getTopActivityApi = ()=>{
    return http.get("/api/news/getTopActivity")
}

// 推荐公告
export const getTopNoticeApi = ()=>{
    return http.get("/api/news/getTopNotice")
}

// 最新活动
export const getNewActivityApi = ()=>{
    return http.get("/api/news/getNewActivity")
}

// 最新新闻
export const getNewNoticeApi = ()=>{
    return http.get("/api/news/getNewNotice")
}