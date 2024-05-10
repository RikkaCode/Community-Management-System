<template>
  <el-row :gutter="10">
    <el-col :span="20">
      <div class="grid-content ep-bg-purple-light" />
      <el-card class="box-card">
        <div class="text item">
          <el-row>
            <el-col :xs="12" :sm="8" :md="7" v-for="item in tabledata" :key="item['id']" :offset="1">
              <div style="margin-top: 15px">
                <el-card :body-style="{ padding: '0px' }" shadow="hover">
                  <img :src="item['image']" class="image" />
                  <div>
                    <strong style="font-size: 12px">{{ item["title"] }}</strong><br />
                    <div class="bottom clearfix">
                      <time class="time"><span>加入时间:</span>{{ item["joinTime"] }}</time>
                      <el-button @click="toDetail(item)" class="button">查看</el-button>
                    </div>
                  </div>
                </el-card>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup lang="ts">
  import { ref, onMounted } from "vue";
  import { getMyActivityApi } from "@/api/news/index";
  import { userSotre } from "@/store/user";
  import { useRouter } from "vue-router";

  const router = useRouter();
  const store = userSotre();
  const tabledata = ref([]);
  const getMyActivity = async () => {
    let res = await getMyActivityApi(store.userInfo.sutId);
    if (res && res.code == 200) {
      console.log(res);
      tabledata.value = res.data;
    }
  };

  // 跳转详情
  const toDetail = (item: any) => {
    console.log(item);
    router.push({ path: `/activityDetails/${item.id}` });
  };

  onMounted(() => {
    getMyActivity();
  });

</script>

<style scoped>
  .time {
    font-size: 10px;
    color: #999;
  }

  .cateitem {
    height: 50px;
    line-height: 50px;
    border-bottom: 1px solid #e4e7ed;
    text-align: center;
    cursor: pointer;
  }

  .bottom {
    margin-top: 5px;
    line-height: 12px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .image {
    width: 100%;
    display: block;
  }
</style>
