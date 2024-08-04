<script setup>
import {ref} from 'vue'
import {useRouter} from "vue-router";
import {getUserOwnInfo} from "@/api/User.js";
import {userInfoTokenStore} from "@/store/userInfoToken.js";
import Nav from '@/component/public/Nav.vue'

const router = useRouter();
import {RouterTokenStore} from "@/store/RouterToken.js";

const router_token_store = RouterTokenStore();
const route_array = ref([])
route_array.value = router_token_store.MyRoutes;
let userInfoTokenStore_ = userInfoTokenStore()
userInfoTokenStore_.removeToken();//先移出再加入
const userInfo = ref({})
const _getUserOwnInfo = async function(){
  const res = await getUserOwnInfo();
  console.log(res)
  userInfo.value = res.data;
  userInfoTokenStore_.setToken(res.data)
}
_getUserOwnInfo();//首次进入页面调用一次

</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header style="width: 100%;height: 60px;padding:0;display: flex;align-items: center;justify-content:
      space-around ; border-bottom: 1px dashed black ; background-color: aliceblue">
        <el-button type="primary" size="large" style="margin-right: auto;">Login Out</el-button>
        <h1 style="font-family: 'CMU Typewriter Text';">Welcome To DHU Swimming Center {{userInfo.UROLEID === 1 ? "ADMIN Panel" : "UserPanel"}}</h1>
          <img style="border-radius: 50%;width: 60px;height: 60px;margin-left: auto" :src="userInfo.UAvatar"/>
      </el-header>
      <el-container>
        <el-aside width="200px" style="border: black; height: 92vh ; border-right: 1px dashed darkblue">
          <el-menu router default-active="2">
            <Nav :routes="route_array"></Nav>
          </el-menu>
        </el-aside>
        <el-main style="padding: 0 ">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped lang="scss">

</style>