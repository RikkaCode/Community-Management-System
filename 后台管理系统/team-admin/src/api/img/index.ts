import http from "@/http";

// 图片上传
export const uploadImageApi = (parm:object)=>{
    return http.upload("/api/upload/uploadImage", parm)
}