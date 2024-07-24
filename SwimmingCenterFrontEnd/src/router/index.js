import {createRouter,createWebHistory} from "vue-router";
import Layout from "@/view/Layout.vue";
import Login from '@/view/Login.vue';
import Test from '@/component/public/Hello.vue'
const routes = [
    {
        'path':'/',
        component:Login,
        name:'Login'
    }
]
const router = createRouter({
    history:createWebHistory(),
    routes
})

export default router;