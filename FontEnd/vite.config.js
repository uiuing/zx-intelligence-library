import vue from "@vitejs/plugin-vue";
import { VantResolver } from "unplugin-vue-components/resolvers";
import Components from "unplugin-vue-components/vite";

export default {
  base: "",
  plugins: [
    vue(),
    Components({
      resolvers: [VantResolver()],
    }),
  ],
  server: {
    proxy: {
      "/api": {
        target: "<dev-host>",
        changeOrigin: true,
      },
    },
  },
};
