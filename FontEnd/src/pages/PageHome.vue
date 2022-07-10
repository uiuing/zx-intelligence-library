<script setup>
import "swiper/css";
import "swiper/css/effect-coverflow";
import "swiper/css/pagination";

import { FreeMode, Pagination } from "swiper";
import { Swiper, SwiperSlide } from "swiper/vue";
import { ref } from "vue";

import store from "../module/index.js";
import { getBook } from "../utils/api/book.js";

const modules = ref([FreeMode, Pagination]);

const getRandom = (max) => {
  return Math.floor(Math.random() * max);
};

// 获取推荐
const recommend = ref([]);

const hot = ref([]);
const isReRecommend = ref(false);
const reRecommend = () => {
  isReRecommend.value = true;
  getBook(getRandom(100)).then((res) => {
    const { records } = res;
    recommend.value = records;
    setTimeout(() => {
      isReRecommend.value = false;
    }, 1200);
  });
};
reRecommend();
getBook(getRandom(100)).then((res) => {
  const { records } = res;
  hot.value = records;
});

const openContent = (item) => {
  store.commit("setIsBookContent", true);
  store.commit("setBookContent", item);
};
</script>

<template>
  <div class="b-a-main">
    <h2 class="recommend">热门</h2>
    <div>
      <swiper
        :free-mode="true"
        :modules="modules"
        :slides-per-view="2"
        class="b-wrapper"
      >
        <swiper-slide
          v-for="item in hot"
          :key="item.bookName"
          class="b-content"
          @click="openContent(item)"
        >
          <div class="b-main">
            <img :src="item.pic" />
          </div>
          <div class="b-title">
            {{ item.bookName }}
          </div>
          <div class="b-write">
            {{ item.authorPenname }}
          </div>
        </swiper-slide>
      </swiper>
    </div>
    <div class="c-c-title">
      <h2>推荐</h2>
      <div style="margin-right: 4pt" @click="reRecommend">
        <van-loading v-if="isReRecommend" />
        <van-icon v-else name="replay" size="20" />
      </div>
    </div>
    <div v-if="!isReRecommend">
      <swiper
        :free-mode="true"
        :modules="modules"
        :slides-per-view="2"
        class="c-wrapper"
      >
        <swiper-slide
          v-for="item in recommend.slice(0, 7)"
          :key="item.bookName"
          class="c-content"
          @click="openContent(item)"
        >
          <div class="c-main">
            <img :src="item.pic" />
          </div>
          <div class="c-title">
            {{ item.bookName }}
          </div>
        </swiper-slide>
      </swiper>
      <swiper
        :free-mode="true"
        :modules="modules"
        :slides-per-view="2"
        class="c-wrapper"
      >
        <swiper-slide
          v-for="item in recommend.slice(7, 16)"
          :key="item.bookName"
          class="c-content"
          @click="openContent(item)"
        >
          <div class="c-main">
            <img :src="item.pic" />
          </div>
          <div class="c-title">
            {{ item.bookName }}
          </div>
        </swiper-slide>
      </swiper>
    </div>
  </div>
  <div></div>
  <div></div>
</template>

<style scoped>
.recommend {
  margin-top: 0;
}

.b-wrapper {
  margin: 20pt 0;
}

.b-content {
  width: 140pt !important;
}

.b-main {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100pt !important;
  height: 140pt;
  padding: 11pt;
  margin-right: 0 !important;
  background-color: rgb(0 0 0 / 9%);
  border-radius: 5%;
}

.b-main > img {
  width: 77pt;
  height: 110pt;
  border-radius: 5%;
  box-shadow: 0 0 0 5px rgb(255 255 255);
}

.b-title {
  margin: 10pt 0 5pt;
  overflow: hidden;
  font-size: 17px;
  font-weight: bold;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.b-write {
  overflow: hidden;
  font-size: 13px;
  color: #858585;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.c-wrapper {
  margin: 20pt 0;
}

.c-c-title {
  display: flex;
  flex-direction: row;
  align-content: center;
  align-items: center;
  justify-content: space-between;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.c-content {
  width: 70pt !important;
}

.c-main > img {
  width: 60pt;
  height: 80pt;
  border-radius: 5%;
  box-shadow: 0 0 0 5px rgb(255 255 255);
}

.c-title {
  margin: 5pt 0;
  overflow: hidden;
  font-size: 14px;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
