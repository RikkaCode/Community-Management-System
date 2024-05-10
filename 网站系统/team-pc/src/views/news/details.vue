<template>
    <div class="container">
        <!-- <el-image class="images" :src="image[0]" /> -->
        <div style="margin-left: 30px;">
            <strong>{{ title }}</strong>
            <div v-html="details"></div>
            <div>{{ createTime }}</div>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { useRoute } from "vue-router";
    import { getNewsByIdApi } from "@/api/news";
    import { ref } from "vue";

    const route = useRoute();
    console.log(route.params.id);
    //标题
    const title = ref("");
    const details = ref("");
    const image = ref([]);
    const createTime = ref("");
    
    //根据id查询数据
    const getById = async () => {
        let res = await getNewsByIdApi(route.params.id as string);
        if (res && res.code == 200) {
            console.log(res);
            title.value = res.data.title;
            details.value = res.data.details;
            image.value = res.data.image.split(",");
            createTime.value = res.data.createTime;
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