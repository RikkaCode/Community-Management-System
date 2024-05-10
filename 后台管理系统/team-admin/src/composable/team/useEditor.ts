import { onBeforeUnmount, ref, shallowRef } from "vue";
import { IEditorConfig } from '@wangeditor/editor';

export default function useEditor() {
    type InsertFnType = (url: string, alt?: string, href?: string) => void

    // 编辑器实例，必须用 shallowRef
    const editorRef = shallowRef();
    const mode = ref('default') // 或 'simple'

    // 内容 HTML
    const valueHtml = ref("");

    // 组件销毁时，也及时销毁编辑器
    onBeforeUnmount(() => {
        const editor = editorRef.value;
        if (editor == null) return;
        editor.destroy();
    });

    const toolbarConfig = {};
    // const editorConfig = { placeholder: "请输入内容..." };
    const editorConfig: Partial<IEditorConfig> = { MENU_CONF: {}, placeholder: '请输入内容...' }
    const handleCreated = (editor: any) => {
        editorRef.value = editor; // 记录 editor 实例，重要！
    };

    // 上传图片的配置
    editorConfig.MENU_CONF!['uploadImage'] = {
        // form-data fieldName ，默认值 'wangeditor-uploaded-image'
        fieldName: 'file',
        //上传图片后端地址
        server: process.env.BASE_API + '/api/upload/uploadImage',
        // 自定义插入图片
        customInsert(res: any, insertFn: InsertFnType) {
        // res 即服务端的返回结果
        console.log(res)
        // 从 res 中找到 url alt href ，然后插图图片
        insertFn(process.env.BASE_API + res.data)
        },
    }

    return{
        editorRef,
        valueHtml,
        toolbarConfig,
        editorConfig,
        handleCreated,
        mode
    }
}