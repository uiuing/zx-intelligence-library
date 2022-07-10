<script setup>
import { ref } from "vue";

import { getNotices } from "../utils/api/admin.js";
import { getBorrowList, getBorrowTimeOutList } from "../utils/api/borrowed.js";
import { getUserInfo } from "../utils/api/student.js";

const info = ref({});

getUserInfo().then((res) => {
  info.value = res;
});
const announcement = ref("");
getNotices().then((res) => {
  const { content } = res[0];
  announcement.value = content;
});

const borrowList = ref([]);
const timeOutNames = ref([]);
getBorrowTimeOutList().then((res) => {
  // eslint-disable-next-line no-restricted-syntax
  for (const item of res) {
    timeOutNames.value.push(item.bookName);
  }
});
getBorrowList().then((res) => {
  // eslint-disable-next-line no-restricted-syntax
  for (const item of res) {
    const startDate = new Date(item.startDate);
    const endDate = new Date(item.endDate);
    const desc = `借阅时间：${startDate.getFullYear()}-${
      startDate.getMonth() + 1
    }-${startDate.getDate()} ~ ${endDate.getFullYear()}-${
      endDate.getMonth() + 1
    }-${endDate.getDate()}`;
    item.desc = desc;
  }
  borrowList.value = res;
});
</script>
<template>
  <div class="main">
    <div class="pm-content">
      <div
        style="
          display: flex;
          margin-top: 30pt;
          align-items: flex-end;
          gap: 14pt;
        "
      >
        <van-image
          class="tx"
          round
          width="5rem"
          height="5rem"
          :src="info.pic"
        />
        <div>
          <h3>
            {{ info.studentName }}
          </h3>
          <div class="i-st">{{ info.college }} ></div>
        </div>
      </div>
    </div>
    <div class="m-wrapper">
      <van-notice-bar
        style="border-radius: 10pt; background-color: white"
        left-icon="volume-o"
        :text="announcement"
        scrollable
      />
      <div>
        <h2>📖 借阅记录</h2>
        <van-card
          v-for="item in borrowList"
          :key="item"
          :desc="item.desc"
          :title="item.bookName"
        >
          <template v-if="timeOutNames.indexOf(item.bookName) !== -1" #footer>
            <van-tag color="#ffe1e1" text-color="#ad0000"
              >已超时，请记得归还至图书馆哦～</van-tag
            >
          </template>
        </van-card>
      </div>
    </div>
  </div>
</template>
<style scoped>
.main {
  width: 100vw;
  margin: -15pt;
}
.pm-content {
  padding: 20pt;
  height: 160pt;
  background-image: url("./src/static/bj.jpg");
  background-size: contain;
  background-repeat: no-repeat;
  color: white;
}
h3 {
  text-shadow: 0 0 4pt #000000;
}
h2 {
  padding: 15pt;
}
.tx {
  border: white 3pt solid;
}
.i-st {
  background-color: rgba(51, 43, 43, 0.37);
  width: 100%;
  padding: 3pt 0 3pt 10pt;
  border-radius: 10pt;
  font-size: 6pt;
}
.m-wrapper {
  background-color: white;
  margin-top: -50pt;
  border-radius: 10pt 10pt 0 0;
  height: 300pt;
}
</style>
<style>
.van-notice-bar {
  background-color: white !important;
}
.van-card {
  border-radius: 10pt;
  margin: 10pt;
}
.van-card__title {
  margin-top: 5pt;
  margin-bottom: 20pt;
  font-size: 13pt;
  height: 20pt;
}
</style>
