import { reactive } from "vue";

export default function useDialog() {
    // 弹框属性
    const dialog = reactive({
        title: "新增",
        visible: false,
        width: 630,
        height: 280
    });

    // 弹框关闭
    const onClose = () => {
        dialog.visible = false;
    };

    // 弹框确定
    const onConfirm = () => {
        dialog.visible = false;
    };

    // 弹窗显示
    const onShow = () => {
        dialog.visible = true;
    }

    return {
        dialog,
        onClose,
        onConfirm,
        onShow
    }
}