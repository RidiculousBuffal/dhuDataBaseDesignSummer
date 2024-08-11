<script setup>
import {ref, reactive, h} from 'vue'
import {ElButton, ElNotification, ElTag} from "element-plus";
import {
  AdminAddUser,
  adminQueryUserInfo,
  getUNameByPrefix,
  matchUserNameByPrefix,
  updateUserInfo,
  updateUserRole, updateUserState
} from "@/api/User.js";
import {Make_debounce} from "@/utils/make_debounce.js";
import {Edit, MagicStick, Plus} from "@element-plus/icons-vue";
import {userTokenStore} from "@/store/userToken.js";

const registerRules = {
  username: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 5, max: 16, message: '用户名长度在5-16位之间', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 5, max: 16, message: '密码长度在5-16位之间', trigger: 'blur'}
  ],
  rid: [{required: true}]

};
const total = ref(20)
const pageNum = ref(1)
const pageSize = ref(10)
const arr = ref([
  {
    "UID": 1,
    "UNAME": "ZlcAdmin",
    "UserName": "ZlcAdmin",
    "UserState": true,
    "Description": "管理员",
    "UAVATAR": "https://cos.hpcow.cn/cowimg.jpg",
    "RID": 1
  },
  {
    "UID": 2,
    "UNAME": "test123",
    "UserName": "test123",
    "UserState": true,
    "Description": "普通用户",
    "RID": 3
  },
  {
    "UID": 3,
    "UNAME": "憨批cow",
    "UserName": "testUser1",
    "UserState": true,
    "Description": "普通用户",
    "UIDENTITY": "28937483274893",
    "RID": 3,
    "UPHONE": "17717699396"
  },
  {
    "UID": 4,
    "UNAME": "testUser02",
    "UserName": "testUser02",
    "UserState": false,
    "Description": "普通用户",
    "RID": 3
  },
  {
    "UID": 6,
    "UNAME": "hey",
    "UserName": "testUser05",
    "UserState": true,
    "Description": "普通用户",
    "UIDENTITY": "122222",
    "RID": 3,
    "UPHONE": "11111"
  }
])
const queryForm = ref({
  'username': '',
  'UName': '',
  'RID': null,
})
const clear_queryForm = () => {
  queryForm.value = {
    'username': '',
    'UName': '',
    'RID': null,
  }
}
const _queryUsernameByPrefix = async function (prefix) {
  const res = await matchUserNameByPrefix(prefix);
  console.log(res.data)
  return res.data;
}
const querySearchAsync = Make_debounce(_queryUsernameByPrefix)
const _queryUNameByPrefix = async function (prefix) {
  const res = await getUNameByPrefix(prefix);
  console.log(res.data)
  return res.data;
}
const querySearchAsync1 = Make_debounce(_queryUNameByPrefix)

const querySearch = async () => {
  let url;
  if (queryForm.value.RID !== null) {
    url =
        `/userInfo/AdminQueryUser?PageNum=${pageNum.value}&PageSize=${pageSize.value}&rid=${queryForm.value.RID}&username=${queryForm.value.username}&UName=${queryForm.value.UName}`
  } else {
    url =
        `/userInfo/AdminQueryUser?PageNum=${pageNum.value}&PageSize=${pageSize.value}&username=${queryForm.value.username}&UName=${queryForm.value.UName}`
  }

  const res = await adminQueryUserInfo(url)
  if (res.code === 0) {
    ElNotification({
      type: "success",
      message: "获取用户数据成功",
      title: "Success"
    })
    arr.value = res.data.arr;
    total.value = res.data.total;
  } else {
    ElNotification({
      type: "error",
      message: res.message,
      title: "Error!"
    })
  }
}
querySearch()
const pageChange = (currentPage_, pageSize_) => {
  pageNum.value = currentPage_;
  pageSize.value = pageSize_;
  querySearch()
}
const formatter_for_rid = (row, column, cellValue, index) => {
  switch (cellValue) {
    case 1:
      return h(ElTag, {type: 'success'}, '管理员');
    case 2:
      return h(ElTag, {type: 'primary'}, '雇员');
    case 3:
      return h(ElTag, {type: 'info'}, '普通用户');
    case 4:
      return h(ElTag, {type: 'warning'}, '会员');
    default:
      return null; // 如果 cellValue 不匹配任何情况，返回 null 或其他默认值
  }
};
const formated_for_none_value = (row, column, cellValue, index) => {
  if (cellValue === null || cellValue === undefined) {
    return h(ElTag, {type: 'danger'}, '未填写');
  } else {
    return cellValue
  }
}
const add_user = ref(false)
const open_add_user_Dialog = () => {
  add_user.value = true
  addUserForm.value = {
    username: '',
    password: '',
    rid: '',
  }
}
const addUserForm = ref({
  username: '',
  password: '',
  rid: undefined,
})
const addUser = async () => {
  const res = await AdminAddUser(addUserForm.value.username, addUserForm.value.password, addUserForm.value.rid);
  if (res.code === 0) {
    ElNotification({
      type: "success",
      message: "添加成功",
      title: "success"
    })
    addUserForm.value = {
      username: '',
      password: '',
      rid: undefined,
    }
    querySearch()
  } else {
    ElNotification({
      type: "error",
      message: res.message,
      title: "Error"
    })
  }

}
const dialogVisible = ref(false);
const isEditing = ref(false);
const userData = ref({
  UID: undefined,
  UNAME: "",
  UserName: "",
  UserState: undefined,
  UDescription: "",
  UIDENTITY: "",
  Description: "",
  UPHONE: "",
  UAVATAR: "",
  RID: undefined
});
const editRow = (row) => {
  console.log(row)
  userData.value = {
    UID: undefined,
    UNAME: "",
    UserName: "",
    UserState: undefined,
    UDescription: "",
    UIDENTITY: "",
    Description: "",
    UPHONE: "",
    UAVATAR: "",
    RID: undefined
  }
  userData.value = {...userData.value, ...row}
  dialogVisible.value = true
}
const disableUser = () => {
  userData.value.UserState = false;
};
const uploadRef = ref('')
const tokenStore = userTokenStore()
const uploadSuccess = (result) => {
  userData.value.UAVATAR = result.data
}
const submit = async () => {
  const data_ = JSON.stringify({
    "uid": userData.value.UID,
    "uname": userData.value.UNAME,
    "uphone": userData.value.UPHONE,
    "uidentity": userData.value.UIDENTITY,
    "uavatar": userData.value.UAVATAR,
    "udescription": userData.value.UDescription
  })
  const res_update = await updateUserInfo(data_);
  if (res_update.code === 0) {
    ElNotification({
      title: 'Success',
      message: '用户信息更新成功!',
      type: 'success',
    })
    querySearch()
    dialogVisible.value = false;
    isEditing.value = false;
  } else {
    ElNotification({
      title: 'Error',
      message: '用户信息设置失败了!',
      type: 'Error',
    })
  }

}
const updateRole = async () => {
  if(userData.value.RID === undefined||userData.value===''){
    return;
  }
  const res = await updateUserRole(userData.value.RID, userData.value.UID);
  if (res.code === 0) {
    ElNotification({
      type: "success",
      message: "更新角色成功",
      title: "success"
    })
    querySearch()
  } else {
    ElNotification({
      type: "error",
      message: "更新角色失败",
      title: "error"
    })
  }

}
const updateState = async()=>{
  const res = await updateUserState(userData.value.UserState,userData.value.UID);
  if (res.code === 0) {
    ElNotification({
      type: "success",
      message: "更新角色状态成功",
      title: "success"
    })
    querySearch()
  } else {
    ElNotification({
      type: "error",
      message: "更新角色状态失败",
      title: "error"
    })
  }
}
</script>

<template>
  <div class="container">
    <h2>用户管理</h2>
    <div class="queryAndAdd" style="display: flex;align-items: center;justify-content: center">
      查询参数:&nbsp;用户姓名:&nbsp;
      <el-autocomplete v-model="queryForm.UName" placeholder="input UName"
                       :fetch-suggestions="querySearchAsync1"
                       placement="bottom-start" style="width: 300px;"></el-autocomplete>
      &nbsp;用户名:&nbsp;
      <el-autocomplete v-model="queryForm.username" placeholder="input username"
                       :fetch-suggestions="querySearchAsync"
                       placement="bottom-start" style="width: 300px;"></el-autocomplete>
      &nbsp;账号类型:&nbsp;
      <el-select v-model="queryForm.RID" style="width: 150px" clearable="true">
        <el-option label="管理员" value="1"></el-option>
        <el-option label="雇员" value="2"></el-option>
        <el-option label="用户" value="3"></el-option>
        <el-option label="会员" value="4"></el-option>
      </el-select>
      <el-button type="primary" @click="clear_queryForm" style="margin-left: 10px">清空</el-button>
      <el-button type="primary" @click="querySearch()">查询</el-button>
      <el-button type="primary" style="margin-left: auto" @click="open_add_user_Dialog">Add User</el-button>
    </div>
    <div class="table">
      <el-table :data="arr" height="70vh">
        <el-table-column label="UID" prop="UID"></el-table-column>
        <el-table-column label="用户姓名" prop="UNAME"></el-table-column>
        <el-table-column label="用户名" prop="UserName"></el-table-column>
        <el-table-column label="证件号" prop="UIDENTITY" :formatter="formated_for_none_value"></el-table-column>
        <el-table-column label="手机号" prop="UPHONE" :formatter="formated_for_none_value"></el-table-column>
        <el-table-column label="身份" prop="RID" :formatter="formatter_for_rid"></el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="{row}">
            <el-button :icon="Edit" circle type="primary" @click="editRow(row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
                     layout="jumper, total, sizes, prev, pager, next" :total="total"
                     style="margin-top: 10px; justify-content: flex-end" @change="pageChange"/>
    </div>
  </div>
  <el-dialog v-model="add_user" title="增加用户" width="600" center style="  border-radius: 10px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);">
    <el-form :model="addUserForm" label-width="100px" :rules="registerRules" style="padding: 30px;">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="addUserForm.username" class="custom-input" style="width: 350px"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="addUserForm.password" class="custom-input" style="width: 350px"></el-input>
      </el-form-item>
      <el-form-item label="身份" class="custom-radio-group" prop="rid">
        <el-radio-group v-model="addUserForm.rid" size="large">
          <el-radio-button label="管理员" value="1"/>
          <el-radio-button label="雇员" value="2"/>
          <el-radio-button label="普通用户" value="3"/>
          <el-radio-button label="会员" value="4"/>
        </el-radio-group>
      </el-form-item>
      <el-form-item label-width="0">
        <el-button style="margin-left: auto;margin-right: auto;font-size: 15px;" @click="addUser">提交</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
  <el-dialog v-model="dialogVisible" width="60%" title="用户信息">
    <div style="display: flex; justify-content: center; margin-bottom: 20px;">
      <el-switch v-model="isEditing" active-text="编辑模式" inactive-text="展示模式"></el-switch>
    </div>
    <div style="display: flex; justify-content: space-between;" class="b">
      <div style="flex: 1; margin-right: 20px; text-align: center; margin-top: 5%;">
        <el-upload ref="uploadRef" action="/api/upload" :name="file" :show-file-list="false"
                   :headers="{'Authorization':tokenStore.token}" class="avatar-uploader"
                   :disabled="isEditing===false" :on-success="uploadSuccess">
          <img v-if="userData.UAVATAR" :src="userData.UAVATAR" class="avatar">
          <el-icon v-else class="avatar-uploader-icon">
            <Plus/>
          </el-icon>
        </el-upload>
        <div style="margin-top: 20px">用户姓名:
          <el-input v-model="userData.UNAME" placeholder="请输入姓名" v-if="isEditing"
                    class="custom-input"
                    style="width:150px"/>
          <div v-else>{{ userData.UNAME }}</div>

        </div>
      </div>
      <div style="flex: 2;">
        <p><strong>用户名:</strong> {{ userData.UserName }}</p>
        <p>
          <strong>用户手机号:</strong>
          <el-input v-model="userData.UPHONE" placeholder="请输入手机号" v-if="isEditing" class="custom-input"/>
          <span v-else>{{ userData.UPHONE }}</span>
        </p>
        <p>
          <strong>用户证件号:</strong>
          <el-input v-model="userData.UIDENTITY" placeholder="请输入证件号" v-if="isEditing" class="custom-input"/>
          <span v-else>{{ userData.UIDENTITY }}</span>
        </p>
        <p>
          <strong>职位描述:</strong>
          <el-select v-model="userData.RID" style="width: 150px" clearable="true" v-if="isEditing"
                     @change="updateRole">
            <el-option label="管理员" value="1"></el-option>
            <el-option label="雇员" value="2"></el-option>
            <el-option label="用户" value="3"></el-option>
            <el-option label="会员" value="4"></el-option>
          </el-select>
          <span v-else>{{ userData.Description }}</span>
        </p>
        <p>
          <strong>用户描述:</strong>
          <el-input type="textarea" v-model="userData.UDescription" placeholder="请输入描述" v-if="isEditing"
                    class="custom-input"/>
          <span v-else>{{ userData.UDescription }}</span>
        </p>
        <p>
          <strong>用户状态:</strong>
          <el-switch
              v-model="userData.UserState"
              :disabled="!isEditing"
              active-text="可用"
              inactive-text="被封禁"
              @change="updateState"
          ></el-switch>
        </p>

      </div>
    </div>
    <template #footer>
      <el-button @click="submit">保存修改</el-button>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss">
:deep(.el-dialog__header) {
  background-color: #409EFF;
  color: white;
  font-size: 20px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

:deep(.el-dialog--center .el-dialog__body) {
  padding: 20px;
  background-color: #fff;
  color: #666;
}

:deep(.custom-input .el-input__wrapper) {
  border-radius: 5px;
  border-color: #409EFF;

  &:focus {
    border-color: #66b1ff;
    box-shadow: 0 0 5px rgba(102, 177, 255, 0.5);
  }
}

:deep(.custom-radio-group .el-radio-button) {
  margin: 0 10px 0 0;
  border-color: #409EFF;

  .el-radio-button__inner {
    border-radius: 20px;
  }

  &.is-active .el-radio-button__inner {
    background-color: #409EFF;
    border-color: #409EFF;
    color: white;
  }

  &:hover .el-radio-button__inner {
    border-color: #c5d2e1;
    background-color: rgba(102, 177, 255, 0.45);
  }
}

:deep(.custom-button) {
  background-color: #409EFF;
  color: white;
  border: none;

  &:hover {
    background-color: #66b1ff;
  }
}

:deep(.el-input .el-input__wrapper) {
  width: 300px;
  border-radius: 20px;

}

.b {
  font-size: 20px;
}

.b strong {
  display: block;
}

.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}

</style>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>