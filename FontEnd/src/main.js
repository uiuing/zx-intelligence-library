import "vant/lib/index.css";

import { createApp } from "vue";

import App from "./App.vue";
import store from "./module";

const app = createApp(App);
app.use(store);
app.mount("#app");
