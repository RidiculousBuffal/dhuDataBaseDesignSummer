import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import App from './App.vue'
import 'element-plus/dist/index.css'
import piniaPersist from 'pinia-plugin-persist'
const app = createApp(App)
const pinia = createPinia();
import router from "@/router/index.js";
pinia.use(piniaPersist)
app.use(pinia)
app.use(router)
app.use(ElementPlus)
app.mount('#app')
