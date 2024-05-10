<template>
    <SysDialog :title="dialog.title" :height="dialog.height" :width="dialog.width" :visible="dialog.visible"
        @on-close="onClose" @on-confirm="commit">
        <template v-slot:content>
            <el-form :model="addModel" ref="addFormRef" :rules="rules" label-width="80px" :inline="false"
                size="default">
                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="title" label="标题">
                            <el-input v-model="addModel.title"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="type" label="类型">
                            <el-radio-group v-model="addModel.type">
                                <el-radio :value="'0'">活动</el-radio>
                                <el-radio :value="'1'">新闻</el-radio>
                                <el-radio :value="'2'">公告</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item prop="hasTop" label="推荐首页">
                    <el-radio-group v-model="addModel.hasTop">
                        <el-radio :value="'0'">否</el-radio>
                        <el-radio :value="'1'">是</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="image" label="封面图">
                    <UploadImage ref="upImgRef" @getImg="getImg" :oldUrl="oldUrl" :fileList="fileList"></UploadImage>
                </el-form-item>
                <el-form-item v-if="dialog.visible" prop="details" label="社团详情">
                    <div style="border: 1px solid #ccc">
                        <Toolbar style="border-bottom: 1px solid #ccc" :editor="editorRef"
                            :defaultConfig="toolbarConfig" :mode="mode" />
                        <Editor style="height: 300px; overflow-y: hidden" v-model="valueHtml"
                            :defaultConfig="editorConfig" :mode="mode" @onCreated="handleCreated" />
                    </div>
                </el-form-item>
                <el-form-item v-if="addModel.type == '0'" prop="hasJoin" label="可报名">
                    <el-radio-group v-model="addModel.hasJoin">
                        <el-radio :value="'1'">是</el-radio>
                        <el-radio :value="'0'">否</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
        </template>
    </SysDialog>
</template>

<script setup lang="ts">
    import { ref, reactive, watch, nextTick } from 'vue';
    import SysDialog from "@/components/SysDialog.vue";
    import useDialog from "@/hooks/useDialog";
    import UploadImage from "@/components/UploadImage.vue";
    import { EditType, NewType, Title } from "@/type/BaseType";
    import { ElMessage, FormInstance, UploadUserFile } from "element-plus";
    import useEditor from "@/composable/team/useEditor";
    import "@wangeditor/editor/dist/css/style.css";
    import { Editor, Toolbar } from "@wangeditor/editor-for-vue";
    import { addNewsApi, editNewsApi } from '@/api/news/index'

    const upImgRef = ref();

    // 表单ref属性
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

    // 图片初始值
    const fileList = ref<Array<UploadUserFile>>([]);
    // 回显的图片
    const oldUrl = ref<Array<{ url: string }>>([]);
    // 图片地址
    const imgUrl = ref<Array<{ url: string }>>([]);

    // 获取弹框属性
    const { dialog, onClose, onShow } = useDialog();

    // 新增、编辑类型
    const tags = ref('')

    // 弹框显示
    const show = (type:string, row:NewType) => {
        valueHtml.value = ''
        // 清空图片数据
        fileList.value = [];
        oldUrl.value = [];
        tags.value = type;
        // 弹框属性设置
        dialog.width = 800;
        dialog.height = 500;
        type == EditType.ADD ? dialog.title = Title.ADD : dialog.title = Title.EDIT
        onShow();
        // 编辑数据回显
        if (type == EditType.EDIT && row) {
            nextTick(() => {
            Object.assign(addModel, row);
            // 图片回显
            if (addModel.image) {
                //逗号分隔的字符串转为数组
                let imgs = addModel.image.split(",");
                for (let i = 0; i < imgs.length; i++) {
                    let img = { name: "", url: "" };
                    img.name = imgs[i];
                    img.url = imgs[i];
                    fileList.value.push(img);
                    oldUrl.value.push({ url: imgs[i] });
                }
            }
            // 文本编辑器回显
            valueHtml.value = addModel.details;
            });
        }
    };

    // 暴露给外部
    defineExpose({
        show
    })

    // 表单绑定的对象
    const addModel = reactive({
        id: "",
        type: "",
        createId: "",
        image: "",
        title: "",
        details: "",
        hasTop: "",
        hasJoin: "",
        status: "",
    });

    // 表单验证规则
    const rules = reactive({
        title: [{ required: true, message: "请输入标题", trigger: "blur" }],
        type: [{ required: true, message: "请选择类型", trigger: "blur" }],
        hasTop: [{ required: true, message: "请选择是否推荐首页", trigger: "blur" }],
        image: [{ required: true, message: "请上传图片", trigger: "blur" }],
        details: [{ required: true, message: "请输入详情", trigger: "blur" }],
        hasJoin: [{ required: true, message: "请选择是否报名", trigger: "blur" }],
    });

    // 子组件调用父组件的方法
    const getImg = (img: NewType) => {
    console.log(img);
    // 处理图片路径
    imgUrl.value = oldUrl.value.concat(img.newImgUrl);
    if (img.deleteUrl.length > 0) {
        // 过滤掉删除的图片路径
        let newArr = imgUrl.value.filter(
        (x) => !img.deleteUrl.some((item) => x.url === item.url)
        );
        imgUrl.value = newArr;
    }
    // 组装为逗号分隔的字符串
    let url = "";
    for (let k = 0; k < imgUrl.value.length; k++) {
        url = url + imgUrl.value[k].url + ",";
    }
    // 去掉末尾逗号
        addModel.image = url.substring(0, url.lastIndexOf(","));
    };
    watch(
        () => valueHtml.value,
        (value) => {
            addModel.details = value;
    });
    
    // 注册事件
    const emits = defineEmits(['onFresh'])

    // 表单提交
    const commit = ()=>{
        console.log(addModel)
        addFormRef.value?.validate(async(valid)=>{
            if(valid){
                let res = null;
                if(tags.value == EditType.ADD){
                    res = await addNewsApi(addModel)
                }else{
                    res = await editNewsApi(addModel)
                }
                if(res && res.code == 200){
                    ElMessage.success(res.msg);
                    // 清空图片
                    upImgRef.value?.clearimg();
                    // 刷新表格
                    emits('onFresh')
                    // 关闭弹框
                    onClose()
                }
            }
        })
    }

</script>

<style scoped>

</style>