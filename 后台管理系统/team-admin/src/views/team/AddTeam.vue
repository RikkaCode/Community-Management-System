<template>
    <SysDialog :title="dialog.title" :height="dialog.height" :width="dialog.width" :visible="dialog.visible"
        @on-close="onClose" @on-confirm="commit">
        <template v-slot:content>
            <el-form :model="addModel" ref="addFormRef" :rules="rules" label-width="80px" :inline="false"
                size="default">
                <el-form-item prop="name" label="社团名称">
                    <el-input v-model="addModel.name"></el-input>
                </el-form-item>
                <el-form-item prop="categoryId" label="社团分类">
                    <el-select v-model="addModel.categoryId" placeholder="请选择分类" style="width: 100%">
                        <el-option v-for="item in selectData" :key="item['value']" :label="item['label']"
                            :value="item['value']" />
                    </el-select>
                </el-form-item>
                <el-form-item prop="image" label="社团图片">
                    <UploadImage @getImg="getImg" :oldUrl="oldUrl" :fileList="fileList" ref="upImgRef"></UploadImage>
                </el-form-item>
                <el-form-item prop="details" label="社团详情">
                    <div v-if="dialog.visible" style="border: 1px solid #ccc">
                        <Toolbar style="border-bottom: 1px solid #ccc" :editor="editorRef"
                            :defaultConfig="toolbarConfig" :mode="mode" />
                        <Editor style="height: 300px; overflow-y: hidden" v-model="valueHtml"
                            :defaultConfig="editorConfig" :mode="mode" @onCreated="handleCreated" />
                    </div>
                </el-form-item>
                <el-form-item prop="hasTop" label="推荐首页">
                    <el-radio-group v-model="addModel.hasTop">
                        <el-radio :value="'0'">否</el-radio>
                        <el-radio :value="'1'">是</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
        </template>
    </SysDialog>
</template>

<script setup lang="ts">
    import { reactive, ref, watch, nextTick } from "vue";
    import SysDialog from "@/components/SysDialog.vue";
    import useDialog from "@/hooks/useDialog";
    import useSelectCategory from "@/composable/team/useSelectCategory";
    import UploadImage from "@/components/UploadImage.vue";
    import { ElMessage, FormInstance, UploadUserFile } from "element-plus";
    import { EditType, NewType } from "@/type/BaseType";
    import "@wangeditor/editor/dist/css/style.css";
    import { Editor, Toolbar } from "@wangeditor/editor-for-vue";
    import useEditor from "@/composable/team/useEditor";
    import { addTeamApi, editTeamApi } from '@/api/team/index'
    import { TeamType } from '@/api/team/TeamModel'

    // 图片上传的ref属性
    const upImgRef = ref();

    // 表单的ref属性
    const addFormRef = ref<FormInstance>()

    // 文本编辑器
    const {
        editorRef,
        handleCreated,
        editorConfig,
        valueHtml,
        toolbarConfig,
        mode,
    } = useEditor();
    const { selectData, getSelect } = useSelectCategory();
    // 图片初始值
    const fileList = ref<Array<UploadUserFile>>([]);
    // 回显的图片
    const oldUrl = ref<Array<{ url: string }>>([]);
    // 图片地址
    const imgUrl = ref<Array<{ url: string }>>([]);
    // 弹框属性
    const { dialog, onClose, onShow } = useDialog();

    // 定义、新增编辑
    const tags = ref("");

    // 显示弹框
    const show = (type:string, row:TeamType) => {
        // 清空社团详情数据
        valueHtml.value = "";
        // 清空图片数据
        fileList.value = [];
        oldUrl.value = [];
        tags.value = type;
        dialog.height = 500;
        dialog.width = 1000;
        //获取下拉数据
        getSelect();
        onShow();
        if (type == EditType.EDIT && row){
            nextTick(() => {
                Object.assign(addModel, row);
                // 图片回显
                if (addModel.image) {
                    // 逗号分隔字符串转为数组
                    let imgs = addModel.image.split(",");
                    for (let i = 0; i < imgs.length; i++) {
                        let img = { name: '', url: '' };
                        img.name = imgs[i]
                        img.url = imgs[i]
                        fileList.value.push(img);
                        oldUrl.value.push({url: imgs[i]});
                    }
                }
                // 文本编辑器回显
                valueHtml.value = addModel.details;
            })
        }
        // 清空表单
        addFormRef.value?.resetFields();
    };

    // 暴露出去：外部组件才能使用
    defineExpose({
        show,
    });

    // 表单绑定的对象
    const addModel = reactive<TeamType>({
        id: "",
        categoryId: "",
        name: "",
        image: "",
        details: "",
        hasTop: ""
    });

    // 表单验证规则
    const rules = ref({
        name: [{ required: true, message: '请输入社团名称', trigger: 'blur' }],
        categoryId: [{ required: true, message: '请选择分类', trigger: 'blur' }],
        image: [{ required: true, message: '请上传图片', trigger: 'blur' }],
        details: [{ required: true, message: '请输入详情', trigger: 'blur' }],
        hasTop: [{ required: true, message: '请选择是否推荐首页', trigger: 'blur' }]
    });

    // 子组件调用父组件的方法
    const getImg = (img: NewType) => {
        console.log("111111");
        console.log(img);
        // 处理图片路径
        imgUrl.value = oldUrl.value.concat(img.newImgUrl)
        if (img.deleteUrl.length > 0) {
            // 过滤掉删除的图片路径
            let newArr = imgUrl.value.filter(
                (x) => !img.deleteUrl.some((item) => x.url === item.url)
            )
            imgUrl.value = newArr;
        }

        // 组装为逗号分隔的字符串
        let url = "";
        for (let k = 0; k < imgUrl.value.length; k++) {
            url = url + imgUrl.value[k].url + ","
        }
        // 去掉末尾逗号
        addModel.image = url.substring(0, url.lastIndexOf(","));
    };
    watch(
        () => valueHtml.value,
        (value) => {
            addModel.details = value;
        }
    );

    // 注册事件
    const emits = defineEmits(["onFresh"]);

    // 表单提交
    const commit = () => {
        console.log(upImgRef.value);
        console.log(addModel);
        addFormRef.value?.validate(async (valid) => {
            if (valid) {
                let res = null;
                if (tags.value == EditType.ADD) {
                    res = await addTeamApi(addModel)
                } else {
                    res = await editTeamApi(addModel)
                }
                if (res && res.code == 200) {
                    ElMessage.success(res.msg)
                    // 刷新表格
                    emits('onFresh')
                    // 清空图片
                    upImgRef.value?.clearimg()
                    onClose()
                }
            }
        })
    };
</script>

<style scoped></style>