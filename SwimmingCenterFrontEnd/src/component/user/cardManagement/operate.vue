<script setup>
import {h, ref} from 'vue'
import {userInfoTokenStore} from "@/store/userInfoToken.js";
import {
  addMoney,
  consumeMoney,
  getCidWithPrefix,
  getUserCardPrefix,
  makeCard,
  queryForCardInfo,
  setStatus
} from "@/api/Card.js";
import {ElButton, ElInput, ElNotification, ElTag} from "element-plus";
import {Make_debounce} from "@/utils/make_debounce.js";
import {Edit, MagicStick} from "@element-plus/icons-vue";
import success from "../../../../public/success.svg";

const tokenStore = userInfoTokenStore()
const UserData = tokenStore.token
const AddCardDialog = ref(false);
const choiceType = ref("办理储值卡")
const options = ['办理储值卡', '办理折扣卡']
const card = ref({
  'uid': UserData.UID,
  'balance': undefined,
  tid: undefined,
  discount: 1,
})

const discount = ref([{
  label: "5折",
  value: 0.5,
}, {
  label: "7折",
  value: 0.7,
}, {
  label: "8折",
  value: 0.8,
}])
const disdic = {
  0.5: 1200,
  0.7: 800,
  0.8: 500,
}
const test = (val) => {
  card.value.balance = disdic[val]
  console.log(card.value.discount)
}
const init = (val) => {
  if (choiceType.value === "办理储值卡") {
    card.value = {
      'uid': UserData.UID,
      'balance': undefined,
      tid: 1,
      discount: 1,
    }
  } else {
    card.value = {
      'uid': UserData.UID,
      'balance': undefined,
      tid: 2,
      discount: 1,
    }
  }

}
const sumbit_add_card = async () => {
  const data_ = JSON.stringify(card.value)
  const res = await makeCard(data_);
  if (res.code === 0) {
    ElNotification({
      title: 'Success',
      message: '添加卡片信息成功!',
      type: 'success',
    })
  } else {
    ElNotification({
      title: 'Error',
      message: '办卡失败!',
      type: 'error',
    })
  }
  init()
  queryForTable();
  AddCardDialog.value = false;
}
const queryForm = ref({
  'cid': '',
  'username': UserData.UserName,
  'tids': [],
})
const clearQueryForm = () => {
  queryForm.value = {
    'cid': '',
    'username': UserData.UserName,
    'tids': [],
  }
}
const total = ref(20)
const pageNum = ref(1)
const pageSize = ref(10)
const arr = ref([
  {
    "Status": 1,
    "UID": 1,
    "TDescription": "储值卡",
    "UNAME": "ZlcAdmin",
    "Discount": 1.0,
    "UserName": "ZlcAdmin",
    "Balance": 1.0311E7,
    "TID": 1,
    "CID": "518d699abe564fc28a41c7a8ab52109c"
  },
  {
    "Status": 1,
    "UID": 1,
    "TDescription": "折扣卡",
    "UNAME": "ZlcAdmin",
    "Discount": 1.0,
    "UserName": "ZlcAdmin",
    "Balance": 1030000.0,
    "TID": 2,
    "CID": "69ef91c34bc54d65b4e313c9524076c5"
  },
  {
    "Status": 1,
    "UID": 1,
    "TDescription": "储值卡",
    "UNAME": "ZlcAdmin",
    "Discount": 1.0,
    "UserName": "ZlcAdmin",
    "Balance": 100000.0,
    "TID": 1,
    "CID": "7b586887fd9d457fb1a7fbcfbba4d5af"
  }
])
const queryForTable = async () => {
  const formData = new FormData()
  formData.append('pageNum', pageNum.value.toString())
  formData.append('pageSize', pageSize.value.toString())
  formData.append('cid', queryForm.value.cid)
  formData.append('username', queryForm.value.username)
  if (queryForm.value.tids.length !== 0) {
    queryForm.value.tids.forEach(x => {
      formData.append('tids', x)
    })
  }
  console.log(queryForm.value)
  const res = await queryForCardInfo(formData);
  if (res.code === 0) {
    ElNotification({
      title: 'Success',
      message: '卡片数据获取成功',
      type: 'success'
    })
    arr.value = res.data.arr;
    total.value = res.data.total;
  } else {
    ElNotification({
      title: 'Error',
      message: '卡片数据获取失败',
      type: 'error'
    })
  }
}
const pageChange = (currentPage_, pageSize_) => {
  pageNum.value = currentPage_;
  pageSize.value = pageSize_;
  queryForTable()
}
const _queryCidByPrefix = async function (prefix) {
  const res = await getUserCardPrefix(UserData.UserName, prefix);
  return res.data
}
const openAddCardDialog = () => {
  AddCardDialog.value = true;
  init()
}
const async_queryCidByPrefix = Make_debounce(_queryCidByPrefix);
const formatter_for_TID = (row, column, cellValue, index) => {
  return h('div', {
    style: {
      color: cellValue === 1 ? '#6eadc1' : '#0000CD', // 浅蓝色和深蓝色
    }
  }, cellValue === 1 ? '储值卡' : '折扣卡');
};
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
queryForTable()
const updateState = async (cid, state) => {
  const res = await setStatus(cid, state)
  if (res.code === 0) {
    ElNotification({
      type: success,
      message: "状态更新成功",
      title: "Success"
    })
    queryForTable()//刷新数据
  } else {
    ElNotification({
      type: "error",
      message: res.data,
      title: "error"
    })
  }

}
const state = ref({
  'cid': undefined,
  'state': undefined,
})
const centerDialogVisible = ref(false)
const openDialog = (row, state_) => {
  state.value = {
    cid: row.CID,
    state: state_
  }
  centerDialogVisible.value = true
}
const confirm_update_status = () => {
  updateState(state.value.cid, state.value.state);
  centerDialogVisible.value = false

}

const AddMoneyDialog = ref(false)
const AddMoneyForm = ref({
  userId: UserData.UID,
  CID: undefined,
  Money: undefined,
  Description: "普通存钱"
})
const openAddMoneyDialog = (row) => {
  AddMoneyForm.value={
    userId: UserData.UID,
    CID: undefined,
    Money: undefined,
    Description: "普通存钱"
  }
  AddMoneyForm.value.CID = row.CID;
  AddMoneyDialog.value = true;
  console.log(AddMoneyForm.value)
  console.log(UserData)
}
const saveMoneyfunc = async () => {
  const res = await
      addMoney(AddMoneyForm.value.userId, AddMoneyForm.value.CID, AddMoneyForm.value.Money, AddMoneyForm.value.Description);
  if (res.code === 0) {
    ElNotification({
      type: success,
      message: "存款成功",
      title: "Success"
    })
    queryForTable()//刷新信息
    AddMoneyDialog.value = false;
  } else {
    ElNotification({
      type: "error",
      message: "存款失败",
      title: "error"
    })
  }
}
const consumeMoneyFunc = async () => {
  const res = await
      consumeMoney(AddMoneyForm.value.userId, AddMoneyForm.value.CID, AddMoneyForm.value.Money,
          AddMoneyForm.value.Description);
  if (res.code === 0) {
    ElNotification({
      type: success,
      message: "付款成功",
      title: "Success"
    })
    queryForTable()//刷新信息
    ConsumeMoneyDialog.value = false;
  } else {
    ElNotification({
      type: "error",
      message: "付款失败",
      title: "error"
    })
  }
}
const ConsumeMoneyDialog = ref(false)
const openConsumeMoneyDialog = (row) => {
  AddMoneyForm.value={
    userId: UserData.UID,
    CID: undefined,
    Money: 50,
    Description: "普通存钱"
  }
  updateMoneyAndDescription(selectedOption.value)
  AddMoneyForm.value.CID = row.CID;
  ConsumeMoneyDialog.value = true;
}

const options1 = [
  {label: '畅游2小时', value: 0},
  {label: '畅游一星期', value: 1},
  {label: '畅游一整月', value: 2}
];

// 选择的选项
const selectedOption = ref(0);

// 更新金额和描述
const updateMoneyAndDescription = (value) => {
  switch (value) {
    case 0: // 畅游2小时
      AddMoneyForm.value.Description = options1[0].label;
      AddMoneyForm.value.Money = 50;
      break;
    case 1: // 畅游一星期
      AddMoneyForm.value.Description = options1[1].label;
      AddMoneyForm.value.Money = 150;
      break;
    case 2: // 畅游一整月
      AddMoneyForm.value.Description = options1[2].label;
      AddMoneyForm.value.Money = 1000;
      break;
  }
};

// 初始化默认值
updateMoneyAndDescription(selectedOption.value);
</script>

<template>
  <h1>卡片管理</h1>
  <div class="func">
    查询参数:&nbsp;卡号:&nbsp;
    <el-autocomplete v-model="queryForm.cid" placeholder="input cid"
                     :fetch-suggestions="async_queryCidByPrefix"
                     placement="bottom-start" style="width: 300px;"></el-autocomplete>
    &nbsp;卡类型:&nbsp;
    <el-checkbox-group v-model="queryForm.tids">
      <el-checkbox-button :value="1">
        储值卡
      </el-checkbox-button>
      <el-checkbox-button :value="2">
        折扣卡
      </el-checkbox-button>
    </el-checkbox-group>
    <el-button type="primary" style="margin-left: 25px" @click="clearQueryForm">清空</el-button>
    <el-button type="primary" @click="queryForTable">查询</el-button>
    <el-button type="primary" style="margin-left: auto" @click="openAddCardDialog">Add card</el-button>
  </div>
  <div class="table">
    <el-table :data="arr" height="70vh">
      <el-table-column label="卡号" prop="CID" width="300"></el-table-column>
      <el-table-column label="用户名" prop="UserName"></el-table-column>
      <el-table-column label="用户姓名" prop="UNAME"></el-table-column>
      <el-table-column label="卡类型" prop="TID" :formatter="formatter_for_TID"></el-table-column>
      <el-table-column label="折扣率" prop="Discount"></el-table-column>
      <el-table-column label="余额" prop="Balance"></el-table-column>
      <el-table-column label="状态" prop="Status" :formatter="formatter_for_state"></el-table-column>
      <el-table-column label="操作" width="400">
        <template #default="{row}">
          <el-button :disabled="row.TID!==1 || row.Status!==1" @click="openAddMoneyDialog(row)">充值</el-button>
          <el-button :disabled="row.Status!==1" @click="openConsumeMoneyDialog(row)">消费</el-button>
          <el-button type="warning" :disabled="row.Status!==1" @click="openDialog(row,4)">挂失</el-button>
          <el-button type="danger" :disabled="row.Status!==1" @click="openDialog(row,3)">退卡</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
                   layout="jumper, total, sizes, prev, pager, next" :total="total"
                   style="margin-top: 10px; justify-content: flex-end" @change="pageChange"/>
  </div>
  <el-dialog v-model="AddMoneyDialog" center width="600px" title="存款" style="border-radius: 50px;
             box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
    <div class="container">
      <h2>请输入存款金额:</h2>
      <el-input v-model.number="AddMoneyForm.Money" type="number"></el-input>
      <h2>请输入充值原因:</h2>
      <el-input v-model="AddMoneyForm.Description" placeholder="普通存钱"></el-input>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="AddMoneyDialog = false">Cancel</el-button>
        <el-button type="primary" @click="saveMoneyfunc">
          Confirm
        </el-button>
      </div>
    </template>
  </el-dialog>
  <el-dialog v-model="ConsumeMoneyDialog" center width="600px" title="消费" style="border-radius: 50px;
             box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
    <div class="container" style="display: flex;flex-direction: column;justify-content: center;align-items: center">
      <el-segmented
          v-model="selectedOption"
          @change="updateMoneyAndDescription"
          :options="options1"
          style="margin-bottom: 20px;"
      ></el-segmented>

      <span style="scale: 1.2">消费金额:<el-input
          v-model="AddMoneyForm.Money"
          :disabled="true"
          placeholder="金额"
          style="width: 300px;"
      ></el-input></span>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="AddMoneyDialog = false">Cancel</el-button>
        <el-button type="primary" @click="consumeMoneyFunc">
          Confirm
        </el-button>
      </div>
    </template>
  </el-dialog>
  <el-dialog v-model="AddCardDialog" title="Add Card" center width="600px"
             style="border-radius: 50px;
             box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
    <div class="container"
         style="display: flex;flex-direction: column;justify-content: center;align-items: center;">
      <el-segmented :options="options" v-model="choiceType" @change="init"></el-segmented>
      <el-form :model="card" style="margin-top: 30px" v-show="choiceType==='办理储值卡'">
        <el-form-item label="输入预存款金额" label-width="120px">
          <el-input type="number" v-model.number="card.balance"></el-input>
        </el-form-item>
        <el-form-item label-width="120px">
          <el-button @click="sumbit_add_card">确认办理</el-button>
        </el-form-item>
      </el-form>
      <el-form :model="card" style="margin-top: 30px" v-show="choiceType==='办理折扣卡'">

        <el-form-item label="选择折扣类型" label-width="120px">
          <el-segmented v-model="card.discount" :options="discount" @change="test">
            <template #default="{ item }">
              <div class="flex flex-col items-center gap-2 p-2">
                <div>{{ item.label }}</div>
              </div>
            </template>
          </el-segmented>
        </el-form-item>
        <el-form-item label-width="120px" label="规定存款金额">
          <el-input type="number" disabled="disabled" v-model.number="card.balance"></el-input>
        </el-form-item>
        <el-form-item label-width="120px">
          <el-button @click="sumbit_add_card">确认办理</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>
  <el-dialog
      v-model="centerDialogVisible"
      title="Warning"
      width="500"
      center
      class="custom-dialog"
      style="box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);border-radius: 30px">

  <span class="warning-message">
    您正在执行{{ state.state === 3 ? "退卡" : "挂失" }}操作
  </span>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="centerDialogVisible = false" class="cancel-button">Cancel</el-button>
        <el-button type="primary" @click="confirm_update_status" class="confirm-button">
          Confirm
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss">
.el-segmented {
  --el-segmented-item-selected-color: var(--el-text-color-primary);
  --el-segmented-item-selected-bg-color: #ffd100;
  --el-border-radius-base: 16px;

}

.func {
  display: flex;
  flex-direction: row;
  justify-items: flex-start;
  align-items: center;
}


.warning-message {
  color: #FF4D4F; /* 警告信息红色 */
  font-size: 2em; /* 更大的字体 */
  margin: 20px 0; /* 上下边距 */
  text-align: center; /* 居中对齐 */
  display: block; /* 块级元素，确保占据整行 */
  font-weight: 600;
}

.dialog-footer {
  display: flex; /* 使用弹性布局 */
  justify-content: space-between; /* 按钮间距 */
}

.cancel-button {
  background-color: #f5f5f5; /* 浅灰色背景 */
  color: #333; /* 深色文字 */
  border: 1px solid #ccc; /* 边框 */
  border-radius: 10px; /* 圆角 */
  transition: background-color 0.3s; /* 背景色过渡效果 */
}

.cancel-button:hover {
  background-color: #e0e0e0; /* 悬停时变化 */
}

.confirm-button {
  border-radius: 10px; /* 圆角 */
  transition: background-color 0.3s; /* 背景色过渡效果 */
}

.confirm-button:hover {
  background-color: #4CAF50; /* 悬停时变成绿色 */
}
</style>