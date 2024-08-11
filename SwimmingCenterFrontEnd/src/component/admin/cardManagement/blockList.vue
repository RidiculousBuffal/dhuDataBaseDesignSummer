<script setup>
import {h, ref} from 'vue'
import {ElButton, ElNotification, ElTag} from "element-plus";
import {matchUserNameByPrefix} from "@/api/User.js";
import {Make_debounce} from "@/utils/make_debounce.js";
import {getCidWithPrefix, queryBlockCards} from "@/api/Card.js";

const total = ref(20)
const pageNum = ref(1)
const pageSize = ref(10)
const arr = ref([
      {
        "Status": 1,
        "BDescription": "违反规则",
        "UNAME": "ZlcAdmin",
        "UserName": "ZlcAdmin",
        "BExpire": "2024-08-28T13:14:15",
        "CID": "479f8602c3d249bf94e6377baf5150a9",
        "BCreateTime": "2024-08-28T12:57:35"
      }
    ]
)
const queryForm = ref({
  'cid': '',
  'username': '',
})
const clearQueryForm = () => {
  queryForm.value = {
    'cid': '',
    'username': '',
  }
}
const formatter_for_state = (row, column, cellValue, index) => {
  switch (cellValue) {
    case 1:
      return h(ElTag, {type: 'success'}, '正常');
    case 2:
      return h(ElTag, {type: 'danger'}, '封禁');
    case 3:
      return h(ElTag, {type: 'info'}, '已退卡');
    case 4:
      return h(ElTag, {type: 'warning'}, '已挂失');
    default:
      return null; // 如果 cellValue 不匹配任何情况，返回 null 或其他默认值
  }
};
const _queryUsernameByPrefix = async function (prefix) {
  const res = await matchUserNameByPrefix(prefix);
  console.log(res.data)
  return res.data;
}
const querySearchAsync = Make_debounce(_queryUsernameByPrefix)
const _queryCidByPrefix = async function (prefix) {
  const res = await getCidWithPrefix(prefix);
  return res.data
}
const async_queryCidByPrefix = Make_debounce(_queryCidByPrefix);
const queryForTable = async () => {
  const res = await queryBlockCards(pageNum.value, pageSize.value, queryForm.value.cid, queryForm.value.username);
  if (res.code === 0) {
    ElNotification({
      type: 'success',
      message: '获取封禁卡片列表成功',
      title: 'Success'
    })
    arr.value = res.data.arr;
    total.value = res.data.total;
  } else {
    ElNotification({
      type: 'error',
      message: '获取封禁卡片列表失败',
      title: 'Error'
    })
  }
}
const pageChange = (currentPage_, pageSize_) => {
  pageNum.value = currentPage_;
  pageSize.value = pageSize_;
  queryForTable()
}
queryForTable()
</script>

<template>
  <h2>封禁卡片查询</h2>
  <div class="container">
    <div class="func" style="display: flex;">
      查询参数:&nbsp;卡号:&nbsp;
      <el-autocomplete v-model="queryForm.cid" placeholder="input cid"
                       :fetch-suggestions="async_queryCidByPrefix"
                       placement="bottom-start" style="width: 300px;"></el-autocomplete>
      &nbsp;用户名:&nbsp;
      <el-autocomplete v-model="queryForm.username" placeholder="input username"
                       :fetch-suggestions="querySearchAsync"
                       placement="bottom-start" style="width: 300px;"></el-autocomplete>
      &nbsp
      <el-button  style="margin-left: auto" @click="clearQueryForm">清空</el-button>
      <el-button   @click="queryForTable">查询</el-button>
    </div>
  </div>
  <div class="table">
    <el-table :data="arr" height="70vh">
      <el-table-column label="卡号" prop="CID"></el-table-column>
      <el-table-column label="用户名" prop="UserName"></el-table-column>
      <el-table-column label="用户姓名" prop="UNAME"></el-table-column>
      <el-table-column label="规定封禁开始时间" prop="BCreateTime"></el-table-column>
      <el-table-column label="规定封禁结束时间" prop="BExpire"></el-table-column>
      <el-table-column label="当前卡片状态" prop="Status" :formatter="formatter_for_state"></el-table-column>
    </el-table>
    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
                   layout="jumper, total, sizes, prev, pager, next" :total="total"
                   style="margin-top: 10px; justify-content: flex-end" @change="pageChange"/>
  </div>
</template>

<style scoped lang="scss">

</style>