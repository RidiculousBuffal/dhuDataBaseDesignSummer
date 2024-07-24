<script setup>
import {ref} from 'vue'
import {useRouter} from "vue-router";
import Nav from '@/component/public/Nav.vue'

const router = useRouter();
import {RouterTokenStore} from "@/store/RouterToken.js";

const router_token_store = RouterTokenStore();
const route_array = ref([])
route_array.value = router_token_store.MyRoutes;

</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header style="width: 100%;height: 60px;padding:0;display: flex;justify-content: flex-end">
        <div
            style="border:5px solid black;width:60px;border-radius: 100%;display: flex;justify-content: center;align-items:center;
            ">
          img
        </div>
      </el-header>
      <el-container>
        <el-aside width="200px" style="border: black; height: 92vh">
          <el-menu router default-active="2">
            <Nav :routes="route_array"></Nav>
          </el-menu>
        </el-aside>
        <el-main style="padding: 0">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped lang="scss">

</style>