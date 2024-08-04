<script setup>
import {ref, reactive} from 'vue'
import {ElForm, ElFormItem, ElRadioGroup, ElRadioButton, ElInput, ElButton, ElNotification} from 'element-plus';
import {getOldUserInfo, getUserIdByUsername, matchUserNameByPrefix, register, updateUserInfo} from "@/api/User.js";
import success from '@/../public/success.svg'
import {addMoney, BlockCard, makeCard, setStatus, updateTidAndDiscount} from "@/api/Card.js";
import {h} from 'vue';
import {ElTag} from "element-plus";
import {Make_debounce} from "@/utils/make_debounce.js";
import {getCidWithPrefix, queryForCardInfo} from "@/api/Card.js";
import {CreditCard, Edit, MagicStick} from "@element-plus/icons-vue";
// 展示添加卡片的弹窗
const centerDialogVisible = ref(false)
const openAddCardDialog = () => {
  // 把数据变成默认值
  active.value = 0;
  active1.value = 0;
  NewUserForm.value = {
    userid: '',
    username: '',
    password: '',
    UName: '',
    UPhone: '',
    UIdentity: ''
  }
  cardInfo.value = {
    tid: 1, // 默认选择储值卡
    discount: 1, // 默认折扣力度
    balance: null, // 金额
  }
  oldUserForm.value = {
    userid: null,
    username: '',
    UName: '',
    UPhone: '',
    UIdentity: ''
  }
  centerDialogVisible.value = true;
}

// 卡片响应式数据和参数校验规则
const cardInfo = ref({
  tid: 1, // 默认选择储值卡
  discount: 1, // 默认折扣力度
  balance: null, // 金额
});
const rules = {
  discount: [
    {required: true, message: '折扣力度不能为空', trigger: 'blur'},
    {type: 'number', min: 0, max: 1, message: '折扣力度必须在0到1之间', trigger: 'blur'},
  ],
  balance: [
    {required: true, message: '金额不能为空', trigger: 'blur'},
    {type: 'number', min: 0, message: '金额必须大于等于0', trigger: 'blur'},
  ],
};

const registerRules = {
  username: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 5, max: 16, message: '用户名长度在5-16位之间', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 5, max: 16, message: '密码长度在5-16位之间', trigger: 'blur'}
  ]
};


const isActive = ref(1); // 默认激活 Box 1
const NewUserAddCard = ref(true)
const changeColor = (box) => {
  isActive.value = box; // 设置当前激活的 Box
  NewUserAddCard.value = box !== 2;
  active.value = 0;
  active1.value = 0;
};
const form = ref(null)
const active = ref(0)//新用户
const active1 = ref(0)//老用户
const next = async () => {
  if (active.value === 0) {
    //   添加用户时的操作
    //第一步:为用户注册
    const res = await register(NewUserForm.value.username, NewUserForm.value.password);
    if (res.code === 0) {
      ElNotification({
        title: 'Success',
        message: '用户注册成功!',
        type: 'success',
      })
      // 第二步:根据用户名获得id
      const resId = await getUserIdByUsername(NewUserForm.value.username);
      NewUserForm.value.userid = resId.data;
      //   第三步,为用户更新基本信息:
      const data_ = JSON.stringify({
        "uid": NewUserForm.value.userid,
        "uname": NewUserForm.value.UName,
        "uphone": NewUserForm.value.UPhone,
        "uidentity": NewUserForm.value.UIdentity
      })
      const res_update = await updateUserInfo(data_);
      if (res_update.code === 0) {
        ElNotification({
          title: 'Success',
          message: '用户信息设置成功!',
          type: 'success',
        })
      } else {
        ElNotification({
          title: 'Error',
          message: '用户信息设置失败了!',
          type: 'Error',
        })
        return
      }
    } else {
      ElNotification({
        title: 'Error',
        message: '用户注册失败了!',
        type: 'Error',
      })
      return
    }
  }
  if (active.value === 1) {
    console.log(NewUserForm.value.userid);
    const data_ = JSON.stringify({
      uid: NewUserForm.value.userid,
      ...cardInfo.value
    })
    const res = await makeCard(data_);
    if (res.code === 0) {
      ElNotification({
        title: 'Success',
        message: '添加卡片信息成功!',
        type: 'success',
      })
    } else {
      return;
    }
  }
  if (active.value === 2) {
    NewUserForm.value = {
      userid: '',
      username: '',
      password: '',
      UName: '',
      UPhone: '',
      UIdentity: ''
    }
    cardInfo.value = {
      tid: 1, // 默认选择储值卡
      discount: 1, // 默认折扣力度
      balance: null, // 金额
    }
  }
  if (active.value++ > 2) active.value = 0
}
const next1 = async () => {
  if (active1.value === 0) {
    if (oldUserForm.value.userid !== null) {
      ElNotification({
        title: 'Success',
        message: '用户信息绑定成功!',
        type: 'success',
      })
    } else {
      ElNotification({
        title: 'Error',
        message: '用户信息绑定失败!',
        type: 'error',
      })
      return
    }

  } else if (active1.value === 1) {
    console.log(NewUserForm.value.userid);
    const data_ = JSON.stringify({
      uid: oldUserForm.value.userid,
      ...cardInfo.value
    })
    const res = await makeCard(data_);
    if (res.code === 0) {
      ElNotification({
        title: 'Success',
        message: '添加卡片信息成功!',
        type: 'success',
      })
    } else {
      return;
    }
  } else if (active1.value === 2) {
    cardInfo.value = {
      tid: 1, // 默认选择储值卡
      discount: 1, // 默认折扣力度
      balance: null, // 金额
    }
    oldUserForm.value = {
      userid: null,
      username: '',
      UName: '',
      UPhone: '',
      UIdentity: ''
    }
  }
  if (active1.value++ > 2) active1.value = 0
}
const NewUserForm = ref({
  userid: '',
  username: '',
  password: '',
  UName: '',
  UPhone: '',
  UIdentity: ''
});

// 老用户表单
const oldUserForm = ref({
  userid: null,
  username: '',
  UName: '',
  UPhone: '',
  UIdentity: ''
});
const _queryUsernameByPrefix = async function (prefix) {
  const res = await matchUserNameByPrefix(prefix);
  console.log(res.data)
  return res.data;
}
const querySearchAsync = Make_debounce(_queryUsernameByPrefix)
const _getOldUserInfo = async (item) => {
  const res = await getOldUserInfo(oldUserForm.value.username);
  oldUserForm.value.userid = res.data.UID;
  oldUserForm.value.UIdentity = res.data.UIDENTITY
  oldUserForm.value.UPhone = res.data.UPHONE
  oldUserForm.value.UName = res.data.UNAME
}
// table
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
const dialog_edit_val = ref({
  "Status": 1,
  "UID": 1,
  "TDescription": "####",
  "UNAME": "#####",
  "Discount": 1.0,
  "UserName": "#######",
  "Balance": 1,
  "TID": 1,
  "CID": "#######"
})
const _queryCidByPrefix = async function (prefix) {
  const res = await getCidWithPrefix(prefix);
  return res.data
}
const async_queryCidByPrefix = Make_debounce(_queryCidByPrefix);
const queryForm = ref({
  'cid': '',
  'username': '',
  'tids': [],
})
const clearQueryForm = () => {
  queryForm.value = {
    'cid': '',
    'username': '',
    'tids': [],
  }
}
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
queryForTable()
const pageChange = (currentPage_, pageSize_) => {
  pageNum.value = currentPage_;
  pageSize.value = pageSize_;
  queryForTable()
}
const editDialog = ref(false)
const editRow = (row) => {
  dialog_edit_val.value = {...row}
  editDialog.value = true;
}
const saveMoney = ref(false)
const saveMoneyBalance = ref(0);
const saveMoneyReason = ref("普通存钱")
const updateCardInfo = async () => {
  const res = await
      updateTidAndDiscount(dialog_edit_val.value.CID, dialog_edit_val.value.TID, dialog_edit_val.value.Discount);
  if (res.code === 0) {
    ElNotification({
      type: success,
      message: "更新信息成功",
      title: "Success"
    })
  } else {
    ElNotification({
      type: "error",
      message: "更新信息失败",
      title: "error"
    })
  }
}
const saveMoneyDialog = () => {
  updateCardInfo();
  saveMoneyBalance.value = 0;
  saveMoneyReason.value = "普通存钱"
  saveMoney.value = true;
}
const saveMoneyfunc = async () => {
  const res = await
      addMoney(dialog_edit_val.value.UID, dialog_edit_val.value.CID, saveMoneyBalance.value, saveMoneyReason.value);
  if (res.code === 0) {
    ElNotification({
      type: success,
      message: "存款成功",
      title: "Success"
    })
    dialog_edit_val.value.Balance += saveMoneyBalance.value;
    queryForTable()//刷新信息
    saveMoney.value = false;
  } else {
    ElNotification({
      type: "error",
      message: "存款失败",
      title: "error"
    })
  }
}
const statusDialog = ref(false)
const openStatusDialog = (row) => {
  dialog_edit_val.value = {...row}
  ChangedStatus.value = dialog_edit_val.value.Status
  statusDialog.value = true;
}
const ChangedStatus = ref(1)
const expireDate = ref(new Date())
const shortcuts = [
  {
    text: 'Tomorrow',
    value: () => {
      const date = new Date()
      date.setDate(date.getDate() + 1)
      return date
    },
  },
  {
    text: 'Next Week',
    value: () => {
      const date = new Date()
      date.setDate(date.getDate() + 7)
      return date
    },
  },
  {
    text: 'Next Month',
    value: () => {
      const date = new Date()
      date.setDate(date.getDate() + 31)
      return date
    },
  },
]
const banDescription = ref('')
const submitChangeState = async () => {
//   判断状态:
  if (ChangedStatus == 2) {
    //   封禁
    const data = JSON.stringify({
      'cid': dialog_edit_val.value.CID,
      'bDescription': banDescription.value,
      'bExpire': expireDate.value.getTime()
    })
    const res = await BlockCard(data);
    if (res.code === 0) {
      ElNotification({
        type: success,
        message: "封禁成功",
        title: "Success"
      })
      statusDialog.value = false;
      queryForTable()//刷新数据
    } else {
      ElNotification({
        type: "error",
        message: res.data,
        title: "error"
      })

    }
  } else {
    const res = await setStatus(dialog_edit_val.value.CID, ChangedStatus.value)
    if (res.code === 0) {
      ElNotification({
        type: success,
        message: "状态更新成功",
        title: "Success"
      })
      statusDialog.value = false;
      queryForTable()//刷新数据
    } else {
      ElNotification({
        type: "error",
        message: res.data,
        title: "error"
      })
    }
  }
}
</script>

<template>
  <div class="container">
    <div class="title">
      <h1>卡片管理</h1>
    </div>
    <div class="func">
      查询参数:&nbsp;卡号:&nbsp;
      <el-autocomplete v-model="queryForm.cid" placeholder="input cid"
                       :fetch-suggestions="async_queryCidByPrefix"
                       placement="bottom-start" style="width: 300px;"></el-autocomplete>
      &nbsp;用户名:&nbsp;
      <el-autocomplete v-model="queryForm.username" placeholder="input username"
                       :fetch-suggestions="querySearchAsync"
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
      <el-button type="primary" style="margin-left: auto" @click="clearQueryForm">清空</el-button>
      <el-button type="primary" style="margin-left: auto" @click="queryForTable">查询</el-button>
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
        <el-table-column label="操作" width="100">
          <template #default="{row}">
            <el-button :icon="Edit" circle type="primary" @click="editRow(row)"></el-button>
            <el-button :icon="MagicStick" circle type="primary" @click="openStatusDialog(row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
                     layout="jumper, total, sizes, prev, pager, next" :total="total"
                     style="margin-top: 10px; justify-content: flex-end" @change="pageChange"/>
    </div>
  </div>
  <el-dialog class="dialog_" v-model="centerDialogVisible" style="border-radius: 20px;font-size: 30px" title="ADD Card"
             width="600"
             center>
    <div class="topButton">
      <div class="box box1" :class="{ active: isActive === 1, inactive: isActive !== 1 }" @click="changeColor(1)">
        新用户添加
      </div>
      <div class="box box2" :class="{ active: isActive === 2, inactive: isActive !== 2 }" @click="changeColor(2)">
        老用户添加
      </div>
    </div>
    <div class="newUserMain" v-show="NewUserAddCard">
      <el-steps style="max-width: 600px" :active="active" finish-status="success">
        <el-step title="Step 1" description="创建用户"/>
        <el-step title="Step 2" description="创建卡片"/>
        <el-step title="Step 3" description="完成!"/>
      </el-steps>
      <div class="step" v-show="active===0">
        <el-form :model="NewUserForm" label-width="100px" class="form-container" :rules="registerRules">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="NewUserForm.username" placeholder="请输入用户名"/>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="NewUserForm.password" type="password" placeholder="请输入密码"/>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="NewUserForm.UName" placeholder="请输入姓名"/>
          </el-form-item>
          <el-form-item label="手机号">
            <el-input v-model="NewUserForm.UPhone" placeholder="请输入手机号"/>
          </el-form-item>
          <el-form-item label="证件号">
            <el-input v-model="NewUserForm.UIdentity" placeholder="请输入证件号"/>
          </el-form-item>
        </el-form>
      </div>
      <div v-show="active===1">
        <el-form ref="form" :model="cardInfo" label-width="120px" :rules="rules">
          <el-form-item label="卡类型">
            <el-radio-group v-model="cardInfo.tid">
              <el-radio-button label=1>储值卡</el-radio-button>
              <el-radio-button label=2>折扣卡</el-radio-button>
            </el-radio-group>
          </el-form-item>

          <el-form-item v-if="cardInfo.tid == 2" label="折扣力度" prop="discount">
            <el-input type="number" v-model.number="cardInfo.discount" placeholder="请输入折扣力度" step="0.1"/>
          </el-form-item>

          <el-form-item label="预存余额" prop="balance">
            <el-input type="number" v-model.number="cardInfo.balance" placeholder="请输入预存款余额" step="100"/>
          </el-form-item>

        </el-form>
      </div>
      <div v-show="active===2" style="display: flex;justify-items: center;align-items: center;flex-direction: column">
        <img :src="success" style="width: 200px;">
        <h2>添加成功!点击右上角按钮关闭!</h2>
      </div>
      <div style="display: flex;">
        <el-button style="margin-top: 12px;margin-left: auto" @click="next">Next step</el-button>
      </div>
    </div>
    <!--    老用户添加-->
    <div class="newUserMain" v-show="!NewUserAddCard">
      <el-steps style="max-width: 600px" :active="active1" finish-status="success">
        <el-step title="Step 1" description="绑定老用户"/>
        <el-step title="Step 2" description="创建卡片"/>
        <el-step title="Step 3" description="完成!"/>
      </el-steps>
      <div class="step" v-show="active1===0">
        <el-form :model="oldUserForm" label-width="100px" class="form-container">
          <el-form-item label="用户名">
            <el-autocomplete v-model="oldUserForm.username" placeholder="please input username"
                             :fetch-suggestions="querySearchAsync" placement="bottom-start" @select="_getOldUserInfo">
            </el-autocomplete>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="oldUserForm.UName" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="手机号">
            <el-input v-model="oldUserForm.UPhone" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="证件号">
            <el-input v-model="oldUserForm.UIdentity" disabled="disabled"></el-input>
          </el-form-item>
        </el-form>

      </div>
      <div v-show="active1===1">
        <el-form ref="form" :model="cardInfo" label-width="120px" :rules="rules">
          <el-form-item label="卡类型">
            <el-radio-group v-model="cardInfo.tid">
              <el-radio-button label=1>储值卡</el-radio-button>
              <el-radio-button label=2>折扣卡</el-radio-button>
            </el-radio-group>
          </el-form-item>

          <el-form-item v-if="cardInfo.tid == 2" label="折扣力度" prop="discount">
            <el-input type="number" v-model.number="cardInfo.discount" placeholder="请输入折扣力度" step="0.1"/>
          </el-form-item>

          <el-form-item label="预存余额" prop="balance">
            <el-input type="number" v-model.number="cardInfo.balance" placeholder="请输入预存款余额" step="100"/>
          </el-form-item>
        </el-form>
      </div>
      <div v-show="active1===2" style="display: flex;justify-items: center;align-items: center;flex-direction: column">
        <img :src="success" style="width: 200px;">
        <h2>添加成功!点击右上角按钮关闭!</h2>
      </div>
      <div style="display: flex;">
        <el-button style="margin-top: 12px;margin-left: auto" @click="next1">Next step</el-button>
      </div>
    </div>
  </el-dialog>
  <el-dialog class="dialog_" v-model="editDialog" style="border-radius: 20px;font-size:30px;" title="编辑卡片"
             width="1000" center>
    <el-row>
      <el-col :span="12" style="display: flex;flex-direction: column;justify-items:flex-start;align-items: center">
        <CreditCard style="width: 15em;height: 15em"/>
        <div style="font-weight: 800;font-family: 'CMU Typewriter Text'">
          CardID:{{ dialog_edit_val.CID }}
        </div>
      </el-col>
      <el-col :span="8"
              style="display: flex;flex-direction: column;font-size: 20px;align-items: flex-start;margin-left: auto;justify-content: space-around">
        <div style="display: flex;">
          <div class="label" style="width: 120px">持卡人:
          </div>
          {{ dialog_edit_val.UNAME }}
        </div>
        <div style="display: flex">
          <div class="label" style="width: 120px">卡类型:</div>
          <el-radio-group v-model="dialog_edit_val.TID">
            <el-radio-button label="储值卡" value=1 @click="dialog_edit_val.Discount=1"></el-radio-button>
            <el-radio-button label="折扣卡" value=2></el-radio-button>
          </el-radio-group>
        </div>
        <div style="display: flex;">
          <div class="label" style="width: 120px">折扣力度:</div>
          <el-input type="number" v-model.number="dialog_edit_val.Discount" style="width: 150px;"
                    :disabled="dialog_edit_val.TID==1" placeholder="1"></el-input>
        </div>
        <div style="display: flex;">
          <div class="label" style="width: 120px">余额:</div>
          <el-input type="number" v-model.number="dialog_edit_val.Balance" style="width: 150px;"
                    disabled="disabled"></el-input>
          <el-button :disabled="dialog_edit_val.TID==2" @click="saveMoneyDialog">存款</el-button>
        </div>

      </el-col>
    </el-row>
    <template #footer style="display: flex;">
      <el-button style="margin-left: auto">Save</el-button>
    </template>
  </el-dialog>
  <el-dialog v-model="saveMoney" title="Save Money" width="500" center style="border-radius: 20px;font-size:30px;">
    <h2>请输入存款金额:</h2>
    <el-input v-model.number="saveMoneyBalance" type="number"></el-input>
    <h2>请输入充值原因:</h2>
    <el-input v-model="saveMoneyReason" placeholder="普通存钱"></el-input>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="saveMoney = false">Cancel</el-button>
        <el-button type="primary" @click="saveMoneyfunc">
          Confirm
        </el-button>
      </div>
    </template>
  </el-dialog>
  <el-dialog v-model="statusDialog" title="修改卡状态" width="1000" center
             style="border-radius: 20px;font-size: 30px;">
    <el-row>
      <el-col :span="12" style="display: flex;justify-content: center;align-items: center">

        <CreditCard style="width: 15em;height: 15em;"/>
        <div
            style="font-weight: 800;font-family: 'CMU Typewriter Text';">
          CardID:{{ dialog_edit_val.CID }}
        </div>

      </el-col>
      <el-col :span="10"
              style="display: flex;flex-direction: column;font-size: 20px;align-items: flex-start;margin-left: auto;justify-content: space-around">
        <div style="display: flex; justify-items: center;align-items: center">
          <h2 style="width: 270px;">当前卡片状态为:</h2>
          <el-tag v-show="dialog_edit_val.Status==1" type="success" style="scale: 2">正常</el-tag>
          <el-tag v-show="dialog_edit_val.Status==2" type="danger" style="scale: 2">封禁</el-tag>
          <el-tag v-show="dialog_edit_val.Status==3" type="info" style="scale: 2">退卡</el-tag>
          <el-tag v-show="dialog_edit_val.Status==4" type="warning" style="scale: 2">挂失</el-tag>
        </div>
        <div style="display: flex; flex-direction: column ;justify-items: center;align-items: flex-start">
          <h2>修改状态至:</h2>
          <el-radio-group v-model="ChangedStatus" style="scale: 1.5;margin-left: 60px">
            <el-radio-button label="正常" value=1></el-radio-button>
            <el-radio-button label="封禁" value=2></el-radio-button>
            <el-radio-button label="退卡" value=3></el-radio-button>
            <el-radio-button label="挂失" value=4></el-radio-button>
          </el-radio-group>
        </div>

        <div v-show="ChangedStatus==2" style="display: flex; flex-direction: column;justify-items: center;align-items:
        flex-start;">
          <h2>请选择封禁到期时间:</h2>
          <el-date-picker v-model="expireDate" type="datetime" placeholder="Select Date"
                          :shortcuts="shortcuts" style="scale: 1.5;width:190px;margin-left: auto;margin-right: auto"
          ></el-date-picker>
        </div>
        <div v-show="ChangedStatus==2" style="display: flex; flex-direction: column;justify-items: center;align-items:
        flex-start;">
          <h2>请输入封禁理由:</h2>
          <el-input v-model="banDescription" placeholder="Input Reason" style="scale:1.5;width:200px;margin-left: 50px"
          ></el-input>
        </div>
      </el-col>
    </el-row>
    <template #footer>
      <el-button type="primary" @click="submitChangeState">提交</el-button>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss">
.func {
  display: flex;
  flex-direction: row;
  justify-items: flex-start;
  align-items: center;
}


.topButton {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0;
  font-family: "Times New Roman", "Kaiti";
  gap: 20px; /* 盒子之间的间距 */
}

.box {
  width: 150px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  cursor: pointer;
  transition: background-color 0.2s ease; /* 添加过渡动画 */
}

.box1 {
  background-color: #ff5733; /* 初始颜色 */
}

.box2 {
  background-color: #33c1ff; /* 初始颜色 */
}

.box.active {
  background-color: #19f4da; /* 点击后的颜色 */
  opacity: 1; /* 完全不透明 */
  border-radius: 30px;
}

.box.inactive {
  background-color: transparent; /* 点击后的透明 */
  opacity: 0.5; /* 轻微透明 */
  border-radius: 30px;
}

.form-container {
  max-width: 600px; /* 设置表单最大宽度 */
  margin: 20px auto; /* 居中表单 */
}

.el-form-item {
  display: flex;
  align-items: center; /* 垂直对齐 */
}
</style>