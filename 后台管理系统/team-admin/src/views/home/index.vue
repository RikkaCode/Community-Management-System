<template>
  <el-main :style="{ height: mianHeight + 'px' }">
    <!-- 数据统计 -->
    <el-row :gutter="20" type="flex" class="row-bg" justify="center" style="margin-bottom: 20px">
      <el-col :span="6">
        <div class="show-header" style="background: rgb(45, 183, 245)">
          <div class="show-num">{{ reisterCount }}</div>
          <div class="bottom-text">注册总数</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="show-header" style="background: rgb(237, 64, 20)">
          <div class="show-num">{{ memberCount }}</div>
          <div class="bottom-text">社员总数</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="show-header">
          <div class="show-num">{{ categoryCount }}</div>
          <div class="bottom-text">分类总数</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="show-header" style="background: rgb(255, 153, 0)">
          <div class="show-num">{{ teamCount }}</div>
          <div class="bottom-text">社团总数</div>
        </div>
      </el-col>
    </el-row>

    <el-card class="box-card" style="margin-top: 30px">
      <div slot="header" class="clearfix">
        <span style="color: #FF7679; font-weight: 600; margin-bottom: 10px">社团人数</span>
        <el-divider></el-divider>
      </div>
      <!-- 在页面定义一个带ref属性的div，并且指定宽度和高度 -->
      <div ref="myChart2" :style="{ width: '100%', height: '250px' }"></div>
    </el-card>

    <div style="display: flex">
      <el-card style="flex: 1">
        <template #header>
          <div class="card-header">
            <span style="color: #00c0ef; font-weight: 600;">热门社团</span>
          </div>
        </template>
        <div :style="{ width: '400px', height: '250px' }">
          <div v-for="item in hotList" class="item text">
            <span style="font-weight: 600; font-size: 14px">{{ item['name'] }}</span>
            <span style="margin-left: 10px">
              <span style="color: #ff7670">{{ item['value'] }}</span>人参与
            </span>
            <el-divider class="drive"></el-divider>
          </div>
        </div>
      </el-card>
      <el-card style="margin-left: 20px; flex: 1">
        <template #header>
          <div class="card-header">
            <span style="color: #00c0ef; font-weight: 600;">热门活动</span>
          </div>
        </template>
        <div :style="{ width: '400px', height: '250px' }">
          <div>
            <div v-for="item in hotActivity" class="item text">
              <span style="font-weight: 600; font-size: 14px">{{ item['name'] }}</span>
              <span style="margin-left: 10px">
                <span style="color: #ff7670">{{ item['value'] }}</span>人报名
              </span>
              <el-divider class="drive"></el-divider>
            </div>
          </div>
        </div>
      </el-card>
      <el-card style="margin-left: 20px; flex: 1">
        <template #header>
          <div class="card-header">
            <span style="color: #00c0ef; font-weight: 600;">男女比例</span>
          </div>
        </template>
        <div ref="myChart1" :style="{ width: '400px', height: '250px' }"></div>
      </el-card>
    </div>
  </el-main>
</template>

<script setup lang="ts">
  import { onMounted, reactive, nextTick, ref } from "vue";
  import useInstance from "@/hooks/useInstance";
  import {getTtotalApi,getEchartsApi,hotTeamListApi,hotActivityListApi,stuTotalApi} from '@/api/home/index'

  const { global } = useInstance();

  const myChart1 = ref<HTMLElement>();
  // echarts的ref属性
  const myChart2 = ref<HTMLElement>();
  const mianHeight = ref(0);

  // 男女比例图
  const myChartJs = async () => {
    const myChartInstance = global.$echarts.init(myChart1.value);
    let option = reactive({
      title: {
        left: "center",
      },
      tooltip: {
        trigger: "item",
      },
      legend: {
        orient: "vertical",
        left: "left",
      },
      series: [
        {
          name: "Access From",
          type: "pie",
          radius: "50%",
          data: [],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: "rgba(0, 0, 0, 0.5)",
            },
          },
          color: ["#37A2DA", "#ff7670"],
          label: {
            normal: {
              show: true,
              formatter: "{b}: {c}({d}%)",
            },
          },
        },
      ],
    });

    // 动态获取数据
    const res = await stuTotalApi()
    if(res && res.code == 200){
      // 设置数据
      option.series[0].data = res.data;
    }
    myChartInstance.setOption(option);
  };

  // 社团人数直方图
  const charts = async () => {
    // 初始化echarts
    const echartInstance1 = global.$echarts.init(myChart2.value);
    // 图标的属性配置
    let option1 = reactive({
      title: {
        text: "直方图",
      },
      xAxis: {
        type: "category",
        data: [],
        axisLabel: {
          // x轴文字的配置
          show: true,
          interval: 0, // 使x轴文字显示全
        },
      },
      yAxis: {
        type: "value",
      },
      series: [
        {
          data: [],
          type: "bar",
          itemStyle: {
            normal: {
              // 这里是颜色
              color: function (params:any) {
                // 注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
                var colorList = [
                  "#00A3E0",
                  "#FFA100",
                  "#ffc0cb",
                  "#CCCCCC",
                  "#BBFFAA",
                  "#749f83",
                  "#ca8622",
                  "#00A3E0",
                  "#FFA100",
                  "#ffc0cb",
                  "#CCCCCC",
                  "#BBFFAA",
                  "#749f83",
                  "#ca8622",
                  "#00A3E0",
                  "#FFA100",
                  "#ffc0cb",
                  "#CCCCCC",
                  "#BBFFAA",
                  "#749f83",
                  "#ca8622",
                  "#00A3E0",
                  "#FFA100",
                  "#ffc0cb",
                  "#CCCCCC",
                  "#BBFFAA",
                  "#749f83",
                  "#ca8622",
                  "#00A3E0",
                  "#FFA100",
                  "#ffc0cb",
                  "#CCCCCC",
                  "#BBFFAA",
                  "#749f83",
                  "#ca8622",
                  "#00A3E0",
                  "#FFA100",
                  "#ffc0cb",
                  "#CCCCCC",
                  "#BBFFAA",
                  "#749f83",
                  "#ca8622",
                ];
                return colorList[params.dataIndex];
              },
            },
          },
        },
      ],
      tooltip: {
        // 鼠标悬浮提示框显示 X和Y 轴数据
        trigger: "axis",
        backgroundColor: "rgba(32, 33, 36,.7)",
        borderColor: "rgba(32, 33, 36,0.20)",
        borderWidth: 1,
        textStyle: {
          // 文字提示样式
          color: "#fff",
          fontSize: "12",
        },
      },
    });

    // 动态获取数据
    let res = await getEchartsApi()
    if(res && res.code == 200){
      option1.xAxis.data = res.data.names;
      option1.series[0].data = res.data.values

    }
    echartInstance1.setOption(option1);
  };

  // 统计总数
  const reisterCount = ref(0)
  const memberCount = ref(0)
  const categoryCount = ref(0)
  const teamCount = ref(0)
  const getTtotal = async()=>{
    let res = await getTtotalApi()
    if(res && res.code == 200){
      reisterCount.value = res.data.reisterCount
      memberCount.value = res.data.memberCount
      categoryCount.value = res.data.categoryCount
      teamCount.value = res.data.teamCount
    }
  }

  // 热门社团
  const hotList = ref([])
  const hotTeamList = async()=>{
    let res = await hotTeamListApi()
    if(res && res.code == 200){
      hotList.value = res.data;
    }
  }

  // 热门活动
  const hotActivity = ref([])
  const hotActivityList = async()=>{
    let res = await hotActivityListApi()
    if(res && res.code == 200){
      hotActivity.value = res.data;
    }
  }

  onMounted(() => {
    getTtotal();
    hotTeamList();
    hotActivityList();
    myChartJs();
    charts()
    nextTick(() => {
      mianHeight.value = window.innerHeight - 90;
    });
  });
  </script>

<style lang="scss" scoped>
  .bottom-text {
    bottom: 0;
    width: 100%;
    background: rgba(0, 0, 0, 0.1);
    height: 25px;
    line-height: 25px;
    text-align: center;
    position: absolute;
    font-weight: 600;
  }
  .show-header {
    background: #00c0ef;
    color: #fff;
    height: 80px;
    border-radius: 5px;
    position: relative;
  }
  .show-num {
    font-size: 38px;
    font-weight: 600;
    padding: 5px;
  }
  .drive {
    margin: 15px 0px;
  }
</style>
