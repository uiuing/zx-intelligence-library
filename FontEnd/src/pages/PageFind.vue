<script setup>
import "swiper/css";
import "swiper/css/effect-coverflow";
import "swiper/css/pagination";

import { FreeMode, Pagination } from "swiper";
import { Swiper, SwiperSlide } from "swiper/vue";
import { ref } from "vue";

import store from "../module/index.js";
import { getBook } from "../utils/api/book.js";
import { bookCategory } from "../utils/data/common.js";

const getRandomCategory = () => {
  const index = Math.floor(Math.random() * bookCategory.length);
  return bookCategory[index];
};
const place = ref(getRandomCategory());

const searchBook = ref("");

setInterval(() => {
  place.value = getRandomCategory();
}, 7000);
const modules = ref([FreeMode, Pagination]);

const focusSearch = () => {
  document.getElementsByClassName("van-search__content")[0].style.boxShadow =
    "rgb(0 0 0 / 18%) 0px 0px 17px 9px";
};
const blurSearch = () => {
  document.getElementsByClassName("van-search__content")[0].style.boxShadow =
    "0 0 20px 0px rgb(143 143 143 / 27%)";
};

const openSearch = () => {
  store.commit("setSearchBook", searchBook.value);
  store.commit("setIsSearch", true);
  searchBook.value = "";
};

const hot = ref([]);

const getRandom = (max) => {
  return Math.floor(Math.random() * max);
};

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
  <div class="p-wrapper">
    <van-search
      v-model="searchBook"
      :placeholder="place"
      shape="round"
      @blur="blurSearch"
      @focus="focusSearch"
      @search="openSearch"
    />
    <h2 class="recommend">🔥 热搜书籍</h2>
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
  </div>
</template>
<style scoped>
.recommend {
  margin-top: 50pt;
}

.p-wrapper {
  margin-top: 50pt;
}

.b-wrapper {
  margin: 20pt 0;
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
</style>

<style>
.van-cell {
  align-items: center;
  height: 38pt;
}

.van-search__content {
  box-shadow: 0 0 20px 0 rgb(143 143 143 / 27%);
}
</style>
