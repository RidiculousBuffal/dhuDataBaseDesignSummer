<script setup>
import {ref} from 'vue'
import {getBlockedUser, updateUserState} from "@/api/User.js";
import {ElNotification} from "element-plus";

const getBlocked = async () => {
  const res = await getBlockedUser();
  if (res.code === 0) {
    ElNotification({
      'type': "success",
      'message': "获取封禁用户信息成功",
      'title': "Success"
    })
    arr.value = res.data
  }
}
getBlocked()
const arr = ref([
  {
    "UID": 2,
    "UserName": "test123",
    "UserState": false,
    "UserPassWord": "fcea920f7412b5da7be0cf42b8c93759",
    "RID": 4
  },
  {
    "UID": 4,
    "UserName": "testUser02",
    "UserState": false,
    "UserPassWord": "5e543256c480ac577d30f76f9120eb74",
    "RID": 3
  }
])
const cellStyle = (row, colum, rowIndex, columnIndex) => {
  return {textAlign: "center"}
}
const removeBlock = async (row) => {
  const res = await updateUserState("true", row.UID);
  console.log(row.UID)
  if (res.code === 0) {
    ElNotification({
      type: 'success',
      message: '移出成功',
      title: 'Success'
    })
  }
  getBlocked();
}
</script>

<template>
  <div class="container">
    <h2>封禁用户管理</h2>
    <el-table :data="arr" height="82vh" :cell-style="cellStyle" :header-cell-style="cellStyle" v-show="arr.length>0">
      <el-table-column label="UID" prop="UID"></el-table-column>
      <el-table-column label="UserName" prop="UserName"></el-table-column>
      <el-table-column label="Role" prop="RID"></el-table-column>
      <el-table-column label="操作">
        <template #default="{row}">
          <el-button @click="removeBlock(row)">解封用户</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-empty :image-size="300" v-show="arr.length===0" description="NoBlockedUser"></el-empty>
  </div>
</template>

<style scoped lang="scss">

</style>