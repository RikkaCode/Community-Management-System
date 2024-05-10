<template>
    <div class="mylogo">
        <img :src="logo" class="logo" />
        <img v-if="show" :src="title" class="title" />
    </div>
</template>

<script setup lang="ts">
    import logo from '@/assets/logo.png';
    import title from '@/assets/title.png';
    import { menuStore } from "@/store/menu";
    import { ref, watch } from "vue";

    const store = menuStore();
    const show = ref(true)
    watch(
        () => store.getCollapse,
        (collapsed:boolean) => {
        if(!collapsed){
            setTimeout(() => {
                show.value = !collapsed
            }, 300)
        }else{
            show.value = !collapsed
        }
    })
    
</script>

<style scoped lang="scss">
    .mylogo {
        height: 60px;
        background-color: #0f2542;
        color: #FFF;
        display: flex;
        align-items: center;
        .logo{
            height: 45px;
            width: 45px;
            margin-left: 10px;
            margin-right: 5px;
        }
        .title{
            width: 180px;
            height: 50px;
        }
    }
</style>