<script setup>
import { computed, onMounted } from "vue";

import GlobalPlugin from "./components/GlobalPlugin.vue";
import TabBar from "./components/TabBar.vue";
import store from "./module/index.js";
import PageFind from "./pages/PageFind.vue";
import PageHome from "./pages/PageHome.vue";
import PageMine from "./pages/PageMine.vue";
import { initContentHeight } from "./utils/c/common.js";

const activeTabBarIndex = computed(() => store.state.activeTabBarIndex);

window.location.hash = "";

const activePage = computed(
  () => [PageHome, PageFind, PageMine][store.state.activeTabBarIndex]
);

onMounted(() => {
  initContentHeight();
});
</script>

<template>
  <div :class="[activeTabBarIndex === 1 ? '' : 'wrapper']">
    <div class="content">
      <keep-alive>
        <component :is="activePage"></component>
      </keep-alive>
    </div>
  </div>
  <tab-bar />
  <global-plugin />
</template>

<style>
html,
body {
  overflow: hidden;
}

:root {
  --sat: env(safe-area-inset-top);
  --sar: env(safe-area-inset-right);
  --sab: env(safe-area-inset-bottom);
  --sal: env(safe-area-inset-left);
}

.fillet-button {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 11pt;
  height: 11pt;
  padding: 11pt;
  background-color: rgb(0 0 0 / 5%);
  border-radius: 50%;
}

header {
  display: flex;
  justify-content: center;
  padding: 0 15pt 3pt;
  background-color: white;
}

/* noinspection CssUnresolvedCustomProperty ,CssInvalidPropertyValue */
.wrapper {
  height: var(--auto-content-height);
  overflow: -moz-scrollbars-none;
  overflow-y: auto;
  scroll-behavior: smooth;
  -ms-overflow-style: none;
}

.wrapper::-webkit-scrollbar {
  display: none;
  width: 0;
  height: 0;
}

/* noinspection CssUnresolvedCustomProperty */
.content {
  padding: 15pt 15pt var(--auto-tabbar-height);
}

* {
  -webkit-touch-callout: none;
  user-select: none;
}

.van-tabbar {
  width: 90%;
  height: 50pt;
  margin: 10pt 5%;
  background-color: rgb(255 255 255 / 94%);
  border-radius: 15pt;
  box-shadow: 0 0 8pt 5px rgb(0 0 0 / 7%);
}

.van-tabbar-item--active {
  margin: 10pt 20pt;
  color: #fff;
  background-color: #000000d1;
}

.van-hairline--top-bottom::after {
  border-width: 0;
}

.van-tabbar-item {
  border-radius: 10pt;
  transition: margin 0.5s;
}
</style>
