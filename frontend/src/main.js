import "./assets/main.css";

import {createApp} from "vue";
import App from "./App.vue";
import {createPinia} from "pinia";
import router from "@/router.js";

const app = createApp(App);
const pinia = createPinia();

// TODO learn and implement veeValidate  https://vee-validate.logaretm.com/v4/tutorials/basics/

app.use(router)
app.use(pinia);

app.mount("#app");

