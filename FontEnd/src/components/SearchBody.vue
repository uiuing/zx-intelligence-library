<script setup>
import { onMounted, ref } from "vue";

import store from "../module/index.js";
import { getSearchBook } from "../utils/api/book.js";

const currentPage = ref(1);

const pages = ref(1);

const data = ref([]);
const isOk = ref(false);
onMounted(() => {
  getSearchBook(store.state.searchBook, currentPage.value).then((res) => {
    pages.value = res.pages;
    const { records } = res;
    data.value = records;
    isOk.value = true;
  });
});
const changePage = (index) => {
  getSearchBook(store.state.searchBook, index).then((res) => {
    pages.value = res.pages;
    const { records } = res;
    data.value = records;
  });
};
const callBack = () => {
  store.commit("setSearchBook", "");
  store.commit("setIsSearch", false);
};

const openContent = (item) => {
  store.commit("setIsBookContent", true);
  store.commit("setBookContent", item);
};
</script>

<template>
  <div v-if="isOk">
    <div class="fillet-button" @click="callBack">
      <van-icon name="arrow-left" />
    </div>
    <div v-if="data.length > 0" class="wrapper">
      <div class="b-wrapper">
        <div
          v-for="item in data"
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
        </div>
      </div>

      <van-pagination
        v-model="currentPage"
        :page-count="pages"
        :show-page-size="7"
        @change="changePage"
      >
        <template #prev-text>
          <van-icon name="arrow-left" />
        </template>
        <template #next-text>
          <van-icon name="arrow" />
        </template>
        <template #page="{ text }">{{ text }}</template>
      </van-pagination>
    </div>
    <div v-else>
      <van-empty
        description="没有数据"
        image="https://fastly.jsdelivr.net/npm/@vant/assets/custom-empty-image.png"
        image-size="80"
      />
    </div>
  </div>
</template>

<style scoped>
.b-wrapper {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
  padding: 60pt 15pt;
}

.b-content {
  width: 110pt !important;
  margin-bottom: 10pt;
}

.b-main {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 70pt !important;
  height: 90pt;
  padding: 11pt;
  background-color: rgb(0 0 0 / 9%);
  border-radius: 5%;
}

.b-main > img {
  width: 57pt;
  height: 80pt;
  border-radius: 5%;
  box-shadow: 0 0 0 5px rgb(255 255 255);
}

.b-title {
  margin: 10pt 0 5pt;
  overflow: hidden;
  font-size: 13px;
  font-weight: bold;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.b-write {
  overflow: hidden;
  font-size: 12px;
  color: #858585;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.fillet-button {
  position: fixed;
  margin: 10pt 0 0 20pt;
  background-color: rgb(217 217 217 / 73%);
  box-shadow: 0 0 5pt rgb(0 0 0 / 12%);
}
</style>
