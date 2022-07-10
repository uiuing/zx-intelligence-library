<script setup>
import { computed, ref } from "vue";

import store from "../module/index.js";
import BookContent from "./BookContent.vue";
import SearchBody from "./SearchBody.vue";
import SignIn from "./UserSign/SignIn.vue";

const isStartSignIn = ref(false);
const isSearch = computed(() => store.state.isSearch);
const isBookContent = computed(() => store.state.isBookContent);

window.addEventListener(
  "hashchange",
  () => {
    const hash = window.location.hash.replace("#", "");
    if (hash === "sign-in") {
      isStartSignIn.value = true;
    } else {
      isStartSignIn.value = false;
    }
  },
  false
);

document.addEventListener("keydown", (e) => {
  if (e.key === "End") {
    window.location.reload();
  }
});
</script>
<template>
  <div v-if="isStartSignIn">
    <sign-in />
  </div>
  <div v-show="!isStartSignIn" v-if="isSearch">
    <search-body />
  </div>
  <div v-show="!isStartSignIn" v-if="isBookContent">
    <book-content />
  </div>
</template>
<style scoped>
div {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  z-index: 99999;
  width: 100%;
  height: 100%;
  background-color: #fff;
}
</style>
