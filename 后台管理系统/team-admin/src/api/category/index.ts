import http from "@/http";
import { CategoryModel } from "./CategoryModel";

// 新增
export const addCategoryApi = (parm:CategoryModel) => {
  return http.post("/api/category", parm);
};

// 列表
export const getCategoryApi = (parm:CategoryPage) => {
  return http.get("/api/category/list", parm);
}

// 编辑
export const editCategoryApi = (parm:CategoryPage) => {
  return http.put("/api/category", parm);
}

// 删除
export const deleteCategoryApi = (parm:CategoryModel) => {
  return http.delete(`/api/category/${parm.id}`)
}