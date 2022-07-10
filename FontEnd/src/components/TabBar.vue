<script setup>
import { computed, ref, watch } from "vue";

import store from "../module/index.js";
import { getUserInfo } from "../utils/api/student.js";

const active = ref(store.state.activeTabBarIndex);

const onChange = (index) => {
  store.commit("setActiveTabBarIndex", index);
};

const userInfo = ref();

const activeWatch = computed(() => store.state.activeTabBarIndex);
watch(activeWatch, (newVal) => {
  active.value = newVal;
});

watch(active, (newVal) => {
  if (newVal === 2) {
    getUserInfo().then((res) => {
      userInfo.value = res;
    });
  }
});
</script>
<template>
  <van-tabbar v-model="active" @change="onChange">
    <van-tabbar-item icon="home-o"></van-tabbar-item>
    <van-tabbar-item icon="search"></van-tabbar-item>
    <van-tabbar-item icon="user-o"></van-tabbar-item>
  </van-tabbar>
</template>
