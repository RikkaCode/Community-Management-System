<template>
    <div class="container">
        <!-- <el-image class="images" :src="image[0]" /> -->
        <div style="margin-left: 30px">
            <strong>{{ title }}</strong>
            <div v-html="details"></div>
            <div>{{ createTime }}</div>
        </div>
    </div>
    <div style="
        display: flex;
        justify-content: center;
        align-items: center;
        margin: 60px 0px 100px 0px;
      ">
        <el-button @click="addTeam" icon="Edit" type="primary" size="default">申请加入</el-button>
    </div>
</template>

<script setup lang="ts">
    import { ref } from "vue";
    import { useRoute } from "vue-router";
    import { useRouter } from "vue-router";
    import { getTeamByIdApi } from "@/api/team";
    import { addTeamApi } from "@/api/news";
    import { userSotre } from "@/store/user";
    import useInstance from "@/hooks/useInstance";
    import { ElMessage } from "element-plus";

    const { global } = useInstance();
    const store = userSotre();
    const router = useRouter();
    const route = useRoute();
    console.log(route.params.id);
    // 标题
    const title = ref("");
    const id = ref("");
    const details = ref("");
    const image = ref([]);
    const createTime = ref("");
    // 根据id查询数据
    const getById = async () => {
        let res = await getTeamByIdApi(route.params.id as string);
        if (res && res.code == 200) {
            console.log(res);
            title.value = res.data.name;
            id.value = res.data.id;
            details.value = res.data.details;
            image.value = res.data.image.split(",");
            createTime.value = res.data.createTime;
        }
    };

    const addTeam = async () => {
        let cons = await global.$myconfirm("您确定加入该社团吗?");
        if (cons) {
            // 判断是否登录
            if (!store.userInfo.sutId) {
                let confirm = await global.$myconfirm("您未登录，请去登录");
                if (confirm) {
                    // 跳转登录
                    router.push({ name: "login" });
                }
            } else {
                let res = await addTeamApi({
                    teamId: id.value,
                    stuId: store.userInfo.sutId,
                });
                if (res && res.code == 200) {
                    ElMessage.success(res.msg);
                }
            }
        }
    };

    getById();
</script>

<style scoped>
    .container {
        display: flex;
        margin-top: 30px;
    }

    .images {
        height: 100px;
        width: 500px;
        border-radius: 10px;
    }
</style>