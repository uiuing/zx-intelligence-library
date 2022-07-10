<script setup>
import { reactive, ref } from "vue";

import store from "../../module/index.js";
import { signIn } from "../../utils/api/student.js";

const username = ref("");
const password = ref("");

const errorMsg = reactive({
  username: "",
  password: "",
});

const callBack = () => {
  if (store.state.activeTabBarIndex === 2) {
    window.location.reload();
  } else {
    window.location.hash = "";
  }
};

const signInStart = () => {
  if (username.value === "") {
    errorMsg.username = "用户名不能为空";
  }
  if (password.value === "") {
    errorMsg.password = "密码不能为空";
  }
  if (username.value !== "" && password.value !== "") {
    errorMsg.username = "";
    errorMsg.password = "";
    signIn(username.value, password.value).then((res) => {
      if (res) {
        // eslint-disable-next-line no-alert
        alert("登陆成功！");
        callBack();
      } else {
        // eslint-disable-next-line no-alert
        alert("账号或密码不正确！");
      }
    });
  }
};
</script>
<template>
  <div class="fillet-button" @click="callBack">
    <van-icon name="arrow-left" />
  </div>
  <div class="main">
    <div>
      <svg
        class="icon"
        height="100"
        p-id="1853"
        t="1657284978369"
        version="1.1"
        viewBox="0 0 1024 1024"
        width="100"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M511.981761 1024a811.427093 811.427093 0 0 0-511.981761-176.249721V300.825763a811.427093 811.427093 0 0 1 511.981761 176.249721A811.491091 811.491091 0 0 1 1023.963521 300.825763v546.924516A811.491091 811.491091 0 0 0 511.981761 1024z m415.98518-621.161871a704.614898 704.614898 0 0 0-355.507335 148.794699L511.981761 600.687081l-60.477846-49.054253A704.486903 704.486903 0 0 0 95.99658 402.838129v353.747398a908.767625 908.767625 0 0 1 415.985181 148.122723 908.767625 908.767625 0 0 1 415.98518-148.122723V402.838129zM511.469779 298.361851a149.178686 149.178686 0 1 1 149.146687-149.178686 149.306681 149.306681 0 0 1-149.146687 149.178686z"
          fill="#0590DF"
          p-id="1854"
        ></path>
      </svg>
      <h1>志行智慧图书馆</h1>
    </div>
    <van-cell-group inset>
      <van-field
        v-model="username"
        :error-message="errorMsg.username"
        label="账号"
        placeholder="请输入账号"
        required
        type="number"
      />
      <van-field
        v-model="password"
        :error-message="errorMsg.password"
        label="密码"
        placeholder="请输入密码"
        required
        type="password"
      />
    </van-cell-group>
    <van-button style="width: 60pt" type="primary" @click="signInStart">
      登陆
    </van-button>
  </div>
</template>
<style scoped>
/* noinspection CssUnresolvedCustomProperty */
.main {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
  height: var(--auto-content-height);
}

.main > div:first-child {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.fillet-button {
  position: fixed;
  margin: 10pt 0 0 20pt;
  box-shadow: 0 0 5pt rgb(0 0 0 / 12%);
}
</style>
