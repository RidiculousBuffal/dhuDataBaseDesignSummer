<script setup>
import {userInfoTokenStore} from "@/store/userInfoToken.js";
import {Plus} from "@element-plus/icons-vue";
import {userTokenStore} from "@/store/userToken.js";
import {updateUserInfo} from "@/api/User.js";
import {ElNotification} from "element-plus";

const tokenStore = userTokenStore()
const UserInfoToken = userInfoTokenStore()
const UserData = UserInfoToken.token
console.log(UserInfoToken.token)

/*
* {
     "UID": 1,
        "UserName": "ZlcAdmin",
        "UserState": true,
        "UAvatar": "https://cos.hpcow.cn/cowimg.jpg",
        "CreateTime": "2024-07-12T22:22:59",
        "UIdentity": "123456789",
        "NickName": "ZlcAdmin",
        "UPHONE": "1234567",
        "RDescription": "管理员",
        "UROLEID": 1
    }*/
const uploadSuccess = async (result) => {
  UserInfoToken.setToken({
    ...UserInfoToken.token,
    UAvatar: result.data
  })
  console.log(UserInfoToken.token)
  const data_ = JSON.stringify({
    "uid": UserInfoToken.token.UID,
    "uavatar": result.data,
  })
  const res_update = await updateUserInfo(data_);
  if (res_update.code === 0) {
    ElNotification({
      title: 'Success',
      message: '头像上传成功!',
      type: 'success',
    })
  } else {
    ElNotification({
      title: 'Error',
      message: '头像上传失败!',
      type: 'Error',
    })
  }
}
const submit = async () => {
  console.log(UserData)
  const data_ = JSON.stringify({
    "uid": UserData.UID,
    "uname": UserData.NickName,
    "uphone": UserData.UPHONE,
    "uidentity": UserData.UIdentity,
    "udescription": UserData.UDescripton
  })
  const res_update = await updateUserInfo(data_);
  if (res_update.code === 0) {
    ElNotification({
      title: 'Success',
      message: '用户信息更新成功!',
      type: 'success',
    })
  } else {
    ElNotification({
      title: 'Error',
      message: '用户信息设置失败了!',
      type: 'Error',
    })
  }

}
</script>

<template>
  <h2>个人信息管理</h2>
  <div class="container" style="display: flex;flex-direction: column;justify-content: center;align-items: center">
    <div class="f1">
      <el-upload ref="uploadRef" action="/api/upload" :name="file" :show-file-list="false"
                 :headers="{'Authorization':tokenStore.token}" class="avatar-uploader"
                 :on-success="uploadSuccess">
        <img v-if="UserInfoToken.token.UAvatar" :src="UserInfoToken.token.UAvatar" class="avatar">
        <el-icon v-else class="avatar-uploader-icon">
          <Plus/>
        </el-icon>
      </el-upload>
    </div>

  </div>
  <div class="f2"
       style="margin-top: 50px;display: flex;flex-direction: column;justify-content: center;align-items: center">

    <el-form v-model="UserData">
      <el-form-item label="用户名" label-width="80px">
        <el-input disabled="disabled" :placeholder="UserData.UserName" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item label="证件号" label-width="80px">
        <el-input v-model="UserData.UIdentity" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item label="姓名" label-width="80px">
        <el-input v-model="UserData.NickName" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item label="手机" label-width="80px">
        <el-input v-model="UserData.UPHONE" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item label="个人介绍" label-width="80px">

        <el-input type="textarea" v-model="UserData.UDescripton"
                  style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item label-width="80px">
        <el-button @click="submit">保存更改</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<style scoped lang="scss">
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