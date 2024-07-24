<script setup>
import {ref, reactive} from "vue";
import {userLogin, getUserPath} from "@/api/User.js";
import {register} from "@/api/User.js";
import {ElNotification} from "element-plus";
import {userTokenStore} from "@/store/userToken.js";
import {convert_route} from "@/utils/route_convert.js";
import {useRouter} from "vue-router";
import {RouterTokenStore} from "@/store/RouterToken.js";
import Layout from "@/view/Layout.vue";
import Test from "@/component/public/Hello.vue";

const router_token_store = RouterTokenStore();
const register_ = ref(false)
const my_router = useRouter();
const registerData = ref({
  username: '',
  password: '',
  confirmPassword: ''
});
const goReg = () => {
  register_.value = true
}
const registerRules = {
  username: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 5, max: 16, message: '用户名长度在5-16位之间', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 5, max: 16, message: '密码长度在5-16位之间', trigger: 'blur'}
  ],
  confirmPassword: [
    {required: true, message: '请再次输入密码', trigger: 'blur'},
    {validator: validateConfirmPassword, trigger: 'blur'}
  ]
};

function validateConfirmPassword(rule, value, callback) {
  if (value !== registerData.value.password) {
    callback(new Error('两次密码输入不一致'));
  } else {
    callback();
  }
}

function submitForm() {
  registerForm.value.validate((valid) => {
    if (valid) {
      // 表单校验通过，可以执行注册逻辑
      registerUser();
    } else {
      // 表单校验失败，给出错误提示
      alert('请输入正确的注册信息');
    }
  });
}

function getAllCurrentRoutes() {
  const currRoutes = my_router.getRoutes();
  // console.log(currRoutes)
  currRoutes.forEach(x => console.log(x))
}

getAllCurrentRoutes()

function removeOtherRoutes() {
  const currRoutes = my_router.getRoutes();
  currRoutes.forEach(x => {
    if (x.name !== 'Login' && x.name !== 'Layout') {
      my_router.removeRoute(x.name)
      console.log(x.name)
    }
  })
}

//每次移除多余的路由
removeOtherRoutes()

async function registerUser() {
  const data = await register(registerData.value.username, registerData.value.password)
  if (data.code === 0) {
    ElNotification({
      title: 'Success',
      message: '注册成功',
      type: 'success',
    })
    register_.value = false
  } else {
    ElNotification({
      title: 'Error',
      message: '注册失败',
      type: 'error',
    })
  }
  for (var key in registerData.value) {
    registerData.value[key] = ''
  }
}

const login = async () => {
  const data = await userLogin(registerData.value.username, registerData.value.password)
  if (data.code === 0) {
    ElNotification({
      'title': 'Success',
      'message': 'Login Success',
      'type': 'success'
    })
    const tmpStore = userTokenStore()
    tmpStore.removeToken()
    console.log(data.data)
    tmpStore.setToken(data.data)
    console.log(tmpStore.token)
    //设置路由
    const path_data = await getUserPath();
    const path = path_data.data.path;
    console.log(path)
    var converted = []
    router_token_store.removeToken();
    converted = await convert_route(path, 0);
    router_token_store.setToken(converted)
    console.log(converted)
    removeOtherRoutes();
    my_router.addRoute(
        {
          'path': '/layout',
          component: Layout,
          name: 'Layout',
          children: converted
        })
    console.log("add Routes successfully")
    console.log(my_router.getRoutes())
    await my_router.push('/layout')
  } else {
    ElNotification({
      'title': 'Error',
      'message': data.message,
      'type': 'success'
    })
  }
  for (var key in registerData.value) {
    registerData.value[key] = ''
  }
}
const registerForm = ref(null);

</script>

<template>
  <div class="outer">
    <div class="banner">
      <span class="tag2">Contact US</span>
      <span class="tag1">Login Page</span>
      <span class="tag3">About US</span>
    </div>
    <div class="inner">
      <div class="title">
        <h1>Welcome To DHU SWIMMING POOL</h1>
      </div>
      <Transition name="slide-fade">
        <div class="login-form" v-show="!register_">
          <el-row width="450px">
            <el-col :span="7">
              <p style="font-size: 25px;font-weight: bolder;margin-left: 40px;text-align: right">用户名:</p>
            </el-col>
            <el-col :span="15" style="align-items: center;display: flex">
              <el-input style="height: 40px;" placeholder="Please Input Your Username" v-model="registerData.username"
              ></el-input>
            </el-col>
          </el-row>
          <el-row width="450px">
            <el-col :span="7">
              <p style="font-size: 25px;font-weight: bolder;margin-left: 40px;text-align: right">密码:</p>
            </el-col>
            <el-col :span="15" style="align-items: center;display: flex">
              <el-input style="height: 40px;" type="password" v-model="registerData.password"
                        placeholder="Please Input Your PassWord"></el-input>
            </el-col>
          </el-row>
          <el-row style="width:500px;display: flex;justify-content: center;margin-top: 40px">
            <el-button type="primary" style="font-size: 25px;height: 40px;font-family: 'CMU Typewriter Text'"
                       @click="login()">Login
            </el-button>
          </el-row>
          <el-row style="width:500px;display: flex;justify-content: flex-end;margin-top: 55px;">
            <el-link style="font-size: 15px;margin-right: 10px" @click="register_=true">No Account? Register!</el-link>
          </el-row>
        </div>
      </Transition>
      <Transition name="slide-fade">
        <div class="register" v-show="register_">
          <el-form ref="registerForm" :model="registerData" :rules="registerRules" label-width="120px">
            <el-form-item label="用户名" prop="username" style="font-weight: bolder;font-size: 30px;margin-top: 20px">
              <el-input style="width: 200px;" v-model="registerData.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password" style="margin-top: 20px;font-weight: bolder">
              <el-input type="password" v-model="registerData.password" style="width: 200px;"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword" style="margin-top: 20px;font-weight: bolder">
              <el-input type="password" v-model="registerData.confirmPassword" style="width: 200px;"></el-input>
            </el-form-item>
            <el-form-item style="margin-left: 0px" label-width="0">
              <el-button type="primary" @click="submitForm" style="font-size: 25px;height: 40px;font-family:
            'CMU Typewriter Text'">Register
              </el-button>
            </el-form-item>
          </el-form>
          <el-row style="width:500px;display: flex;justify-content: flex-end;margin-top: 40px;">
            <el-link style="font-size: 15px;margin-right: 10px" @click="register_=false">Has Account? Login!</el-link>
          </el-row>
        </div>
      </Transition>
    </div>
  </div>
</template>

<style scoped lang="scss">
.outer {
  height: 100vh;
  width: 100%;
  background: linear-gradient(135deg, #c7e9fb, #a6defa, #80d4f9, #5bc9f8, #35bef7);

  display: flex;
}

.inner {
  background: rgba(253, 243, 243, 0.8);
  box-shadow: 10px 10px 5px 1px rgba(0, 0, 0, 0.3);
  display: flex;
  height: 500px;
  width: 500px;
  margin: auto;
  flex-direction: column;
  border-radius: 20px;
}

.title {
  margin-left: auto;
  text-align: center;
}

.banner {
  position: absolute;
  height: 10%;
  width: 100%;
  background-color: #c7e9fb;
  display: flex;
  justify-content: space-around;
  align-items: center;
  font-family: "CMU Typewriter Text";
}

.tag1 {
  height: 60px;
  width: 100px;
  display: flex;
  text-align: center;
  align-items: center;
  justify-content: center;
  background-color: #a6defa;
  border-color: #80d4f9;
  border-radius: 20px;
  transition: box-shadow 0.3s ease-in-out;
}

.tag1:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  cursor: pointer;
}

.tag2:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  cursor: pointer;
}

.tag3:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  cursor: pointer;
}

.tag2 {

  height: 60px;
  width: 100px;
  display: flex;
  text-align: center;
  align-items: center;
  justify-content: center;
  background-color: #a6defa;
  border-color: #80d4f9;
  border-radius: 20px;
  transition: box-shadow 0.3s ease-in-out;
}

.tag3 {

  height: 60px;
  width: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #a6defa;
  border-color: #80d4f9;
  border-radius: 20px;
  transition: box-shadow 0.3s ease-in-out;

}

:deep(.el-form-item__label) {
  width: 90px;
  font-size: 20px;
  font-weight: bolder;
  color: black;
}

.el-form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

/*
  Enter and leave animations can use different
  durations and timing functions.
*/
.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}


.slide-fade-enter-from {
  transform: translateX(20px);
  opacity: 0;
}
</style>