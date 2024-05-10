<template>
  <swiper :modules="modules" :initial-slide="1" :slidesPerView="3" :centeredSlides="true" :spaceBetween="20"
    :autoplay="{ disableOnInteraction: false }" loop class="mySwiper" @swiper="setSwiperRef">
    <swiper-slide @click="toDetail(item)" class="teacher_li" v-for="(item, index) in list" :key="index">
      <el-image style="height: 250px; width: 100%; object-fit: cover;" :src="item['image']" />
    </swiper-slide>
  </swiper>
</template>

<script setup lang="ts">
  import { onMounted, ref } from 'vue';
  import { getTopTeamApi } from '@/api/home';
  // 引入swiper组件
  import { Swiper, SwiperSlide } from 'swiper/vue';
  // 引入swiper样式
  import "swiper/css";
  import "swiper/css/pagination"; // 轮播图地面圆点
  import "swiper/css/navigation"; // 轮播图两边左右箭头
  import "swiper/css/scrollbar";  // 轮播图滚动条
  import { useRouter } from "vue-router";

  // 引入swiper核心和所需模块
  import { Navigation, Pagination, Autoplay, Virtual } from 'swiper/modules';

  const router = useRouter()

  // 在modules加入所使用模块
  const modules = [Navigation, Pagination, Autoplay, Virtual];
  let swiperRef = ref(null);
  const setSwiperRef = (swiper: any) => {
    swiperRef = swiper;
  }

  const list = ref()

  // 获取推荐社团列表
  const getTopTeam = async () => {
    let res = await getTopTeamApi();
    console.log(res)
    if (res && res.code == 200) {
      list.value = res.data;
    }
  }

  // 跳转详情
  const toDetail = (item: any) => {
    console.log(item)
    router.push({ path: `/teamDetails/${item.id}` })
  }

  onMounted(() => {
    getTopTeam()
  })
</script>

<style scoped></style>