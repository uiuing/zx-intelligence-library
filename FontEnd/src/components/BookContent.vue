<script setup>
import { ref } from "vue";

import store from "../module/index.js";
import { borrowBook } from "../utils/api/borrowed.js";

const bookContent = ref(store.state.bookContent);

const callBack = () => {
  store.commit("setIsBookContent", false);
};

// 获取当前日期格式yyyy-MM-dd
const getDate = () => {
  const date = new Date();
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  return `${year}-${month}-${day}`;
};
// 计算出30天之后
const getDateAfter = (today, addDayCount) => {
  let dd;
  if (today) {
    dd = new Date(today);
  } else {
    dd = new Date();
  }
  dd.setDate(dd.getDate() + addDayCount);
  const y = dd.getFullYear();
  const m =
    dd.getMonth() + 1 < 10 ? `0${dd.getMonth() + 1}` : dd.getMonth() + 1;
  const d = dd.getDate() < 10 ? `0${dd.getDate()}` : dd.getDate();
  return `${y}-${m}-${d}`;
};

const bow = () => {
  borrowBook(
    bookContent.value.bno,
    bookContent.value.bookName,
    getDate(),
    getDateAfter(getDate(), 30)
  ).then((res) => {
    if (res) {
      // eslint-disable-next-line no-alert
      alert("借阅申请成功！\n 请前往图书馆领取确认！");
      callBack();
    } else {
    }
  });
};
</script>

<template>
  <div class="fillet-button" @click="callBack">
    <van-icon name="arrow-left" />
  </div>
  <van-button
    class="book-bow"
    round
    type="primary"
    :disabled="bookContent.nums === 0"
    @click="bow"
    >借阅</van-button
  >
  <div class="wrapper">
    <div>
      <img :src="bookContent.pic" alt="" />
    </div>
    <div>
      <div class="b-c-title">
        <h3>{{ bookContent.bookName }}</h3>
      </div>
      <div>
        <div class="b-c-write">作者：{{ bookContent.authorPenname }}</div>
      </div>
    </div>
    <div class="bc-content">
      <div style="display: flex; flex-direction: column">
        <div class="b-c-t"></div>
        <div
          style="
            display: flex;
            flex-direction: column;
            gap: 10pt;
            padding: 20pt;
          "
        >
          <div
            style="
              display: flex;
              gap: 10pt;
              align-items: flex-start;
              width: 100%;
            "
          >
            <div>
              <van-tag size="large" text-color="rgb(0 0 0 / 72%)" plain>{{
                bookContent.bookAddress
              }}</van-tag>
            </div>
            <div>
              <van-tag
                size="medium"
                style="padding: 4pt 10pt; opacity: 0.8"
                text-color="rgb(0 0 0 / 72%)"
                plain
                >{{ bookContent.categorys }}</van-tag
              >
            </div>
            <div>
              <van-tag
                size="medium"
                style="padding: 4pt 10pt; opacity: 0.8"
                text-color="rgb(0 0 0 / 72%)"
                plain
              >
                剩余 {{ bookContent.nums }} 本
              </van-tag>
            </div>
          </div>
          <div class="descs">
            {{ bookContent.descs }}
          </div>
        </div>
      </div>
    </div>
    <div></div>
  </div>
</template>
<style scoped>
.wrapper > div:first-child {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 350pt;
}

.wrapper > div:first-child > img {
  width: 150pt;
  height: 210pt;
  border-radius: 10pt;
  box-shadow: 0 0 10pt 5pt rgb(140 133 133 / 15%);
}

.b-c-title,
.b-c-write {
  display: inline-block;
  padding: 20pt;
  margin-top: -35pt;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.wrapper > div:nth-child(2) > div {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.b-c-write {
  overflow: hidden;
  font-size: 15px;
  color: #858585;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.bc-content {
  background-color: #fff;
  border-radius: 35pt 35pt 0 0;
  box-shadow: 0 0 12pt 0 rgb(0 0 0 / 11%);
}

.bc-content > div:first-child {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
}

.b-c-t {
  width: 50pt;
  height: 4pt;
  margin-top: 10pt;
  background-color: #656565;
  border-radius: 15pt;
  box-shadow: #85858578 0 0 10pt 1px;
}

.fillet-button {
  position: fixed;
  margin: 10pt 0 0 20pt;
  background-color: rgb(217 217 217 / 73%);
  box-shadow: 0 0 5pt rgb(0 0 0 / 12%);
}

.descs {
  margin: 20pt 0;
  font-size: 14pt;
  line-height: 19pt;
  text-align: left;
  text-indent: 20pt;
  word-break: break-all;
  word-break: normal;
  word-wrap: break-word;
}
.book-bow {
  display: flex;
  position: fixed;
  margin: 10pt 15pt 0 20pt;
  right: 0;
  top: 0;
  align-items: center;
  justify-content: center;
}
</style>

<style>
.van-tag--default.van-tag--plain {
  padding: 8pt 15pt;
  border-radius: 15pt;
  box-shadow: 0 0 3pt rgb(41 70 55 / 28%);
}
</style>
