<script setup>
import {h, ref} from "vue";
import {getCidWithPrefix} from "@/api/Card.js";
import {Make_debounce} from "@/utils/make_debounce.js";
import {matchUserNameByPrefix} from "@/api/User.js";
import {ElButton, ElNotification, ElTag} from "element-plus";
import {Edit, MagicStick} from "@element-plus/icons-vue";
import {getConsumeInfo} from "@/api/Consume.js";

const clearQueryForm = () => {
  queryForm.value = {
    'cid': '',
    TimeRange: [],
    username: ''
  }
}
const queryForm = ref({
  'cid': '',
  TimeRange: [],
  username: ''
})

const total = ref(20)
const pageNum = ref(1)
const pageSize = ref(10)
const submitQueryForm = async () => {
  let url = ""
  if (queryForm.value.TimeRange.length === 0) {
    url = `/consume/getInfo?pageNum=${pageNum.value}&PageSize=${pageSize.value}&cid=${queryForm.value.cid}&username=${queryForm.value.username}`
  } else {
    url = `/consume/getInfo?pageNum=${pageNum.value}&PageSize=${pageSize.value}&cid=${queryForm.value.cid}&username=${queryForm.value.username}&startTime=${queryForm.value.TimeRange[0].getTime()}&endTime=${queryForm.value.TimeRange[1].getTime()}`
  }
  const res = await getConsumeInfo(url);
  if (res.code === 0) {
    ElNotification({
      title: 'Success',
      message: '交易信息获取成功',
      type: 'success'
    })
    arr.value = res.data.arr;
    total.value = res.data.total;
  } else {
    ElNotification({
      title: 'Error',
      message: 'Error',
      type: 'error'
    })
  }
}
const _queryCidByPrefix = async function (prefix) {
  const res = await getCidWithPrefix(prefix);
  return res.data
}
submitQueryForm()
const pageChange = (currentPage_, pageSize_) => {
  pageNum.value = currentPage_;
  pageSize.value = pageSize_;
  submitQueryForm()
}
const async_queryCidByPrefix = Make_debounce(_queryCidByPrefix);
const _queryUsernameByPrefix = async function (prefix) {
  const res = await matchUserNameByPrefix(prefix);
  console.log(res.data)
  return res.data;
}
const querySearchAsync = Make_debounce(_queryUsernameByPrefix)
const submitSearch = () => {
  submitQueryForm()
}

const arr = ref([
  {
    "Status": 1,
    "UID": 5,
    "UNAME": "dhu12",
    "UserName": "testUser03",
    "CreateTime": "2024-08-03T14:15:14",
    "Consume_Save_Balance": 10000.0,
    "currentBalance": 10000.0,
    "description": "新卡办理",
    "CID": "2da19d794d12438a809dbf0f77e5942c"
  },
  {
    "Status": 1,
    "UID": 10,
    "UNAME": "中国工业",
    "UserName": "testuser11",
    "CreateTime": "2024-08-03T14:25:58",
    "Consume_Save_Balance": 100000.0,
    "currentBalance": 100000.0,
    "description": "新卡办理",
    "CID": "2e306c3ed42c4a4ab1f604345f11a938"
  },
  {
    "Status": 1,
    "UID": 1,
    "UNAME": "ZlcAdmin",
    "UserName": "ZlcAdmin",
    "CreateTime": "2024-07-28T11:50:27",
    "Consume_Save_Balance": 1.0301E7,
    "currentBalance": 1.0301E7,
    "description": "新卡办理",
    "CID": "3977930932504ddd9735d8fb42728f21"
  },
  {
    "Status": 1,
    "UID": 1,
    "UNAME": "ZlcAdmin",
    "UserName": "ZlcAdmin",
    "CreateTime": "2024-07-28T11:49:51",
    "Consume_Save_Balance": 1312312.45,
    "currentBalance": 1312312.45,
    "description": "新卡办理",
    "CID": "3ee800cd029f41b2bb0b44e82d34d84d"
  },
  {
    "Status": 1,
    "UID": 1,
    "UNAME": "ZlcAdmin",
    "UserName": "ZlcAdmin",
    "CreateTime": "2024-07-28T11:50:00",
    "Consume_Save_Balance": 1.33241231245E9,
    "currentBalance": 1.33241231245E9,
    "description": "新卡办理",
    "CID": "479f8602c3d249bf94e6377baf5150a9"
  }
])
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
</script>

<template>
  <h2>交易管理</h2>
  <div class="container">
    <div class="func">
      查询参数:&nbsp 卡号:&nbsp;
      <el-autocomplete v-model="queryForm.cid" placeholder="input cid"
                       :fetch-suggestions="async_queryCidByPrefix"
                       placement="bottom-start" style="width: 250px;"></el-autocomplete>
      &nbsp;时间范围:
      <el-date-picker
          v-model="queryForm.TimeRange"
          type="datetimerange"
          range-separator="To"
          start-placeholder="Start date"
          end-placeholder="End date"
          style="width: 100px;"
      />
      &nbsp;用户名:&nbsp;
      <el-autocomplete v-model="queryForm.username" placeholder="input username"
                       :fetch-suggestions="querySearchAsync"
                       placement="bottom-start" style="width: 250px;"></el-autocomplete>
      &nbsp
      <el-button type="primary" @click="clearQueryForm">清空</el-button>
      <el-button type="primary" @click="submitSearch">查询</el-button>
    </div>
    <div class="table">
      <el-table :data="arr" height="70vh">
        <el-table-column label="卡号" prop="CID" width="270"></el-table-column>
        <el-table-column label="用户名" prop="UserName" width="100"></el-table-column>
        <el-table-column label="用户姓名" prop="UNAME"></el-table-column>
        <el-table-column label="时间" prop="CreateTime" width="150"></el-table-column>
        <el-table-column label="消费/存款金额" prop="Consume_Save_Balance"></el-table-column>
        <el-table-column label="当时余额" prop="currentBalance"></el-table-column>
        <el-table-column label="描述" prop="description"></el-table-column>
        <el-table-column label="卡片状态" prop="Status" :formatter="formatter_for_state"></el-table-column>
      </el-table>
      <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
                     layout="jumper, total, sizes, prev, pager, next" :total="total"
                     style="margin-top: 10px; justify-content: flex-end" @change="pageChange"/>
    </div>
  </div>
</template>

<style scoped lang="scss">
.func {
  display: flex;
  align-items: center;
}
</style>