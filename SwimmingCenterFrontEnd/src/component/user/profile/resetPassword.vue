<script setup>
import {ref} from 'vue'
import {userTokenStore} from "@/store/userToken.js";
import {userInfoTokenStore} from "@/store/userInfoToken.js";
import {userResetPassword} from "@/api/User.js";
import {ElNotification} from "element-plus";

const tokenStore = userInfoTokenStore()
const registerData = ref({
  'currentPassword': '',
  'password': '',
  'confirmPassword': ''
})
const submitForm = async () => {
  const res = await
      userResetPassword(registerData.value.currentPassword, registerData.value.password, tokenStore.token.UID);
  if (res.code === 0) {
    ElNotification({
      type: "success",
      message: 'Reset Successfully',
      title: 'Success'
    })
  }
}
const RegRules = ref({
  currentPassword: [{required: true, message: '请输入密码', trigger: 'change'},],
  password: [
    {required: true, message: '请输入密码', trigger: 'change'},
    {min: 5, max: 16, message: '密码长度在5-16位之间', trigger: 'change'}
  ],
  confirmPassword: [
    {required: true, message: '请再次输入密码', trigger: 'change'},
    {validator: validateConfirmPassword, trigger: 'change'}
  ]
})

function validateConfirmPassword(rule, value, callback) {
  if (value !== registerData.value.password) {
    callback(new Error('两次密码输入不一致'));
  } else {
    callback();
  }
}

</script>

<template>
  <h2>修改密码</h2>
  <div class="container">
    <el-form :model="registerData" label-width="100px" :rules="RegRules">
      <el-form-item label="原密码" prop="currentPassword">
        <el-input v-model="registerData.currentPassword" type="password" placeholder="请输入原密码"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="password">
        <el-input v-model="registerData.password" type="password" placeholder="新密码"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input v-model="registerData.confirmPassword" type="password" placeholder="确认新密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped lang="scss">
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 70vh;

}

h2 {
  color: #000;
  text-align: center;
  margin-bottom: 20px;
}

.el-form {
  scale: 1.5;
  background-color: rgba(255, 255, 255, 0.15);
  padding: 20px;
  border-radius: 15px;
  backdrop-filter: blur(10px);
}

.el-form-item {
  margin-bottom: 15px;
}

.el-input {
  border-radius: 10px;
  border: none;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.el-button {
  width: 100%;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
}

.el-button:hover {
  background-color: #72EDF2;
}

</style>