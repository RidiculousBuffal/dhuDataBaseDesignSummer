<template>
  <div class="welcome-container">
    <el-card class="welcome-card" shadow="hover">
      <h2 class="welcome-title">欢迎来到游泳馆系统</h2>
      <p class="welcome-message">
        在这里，您可以轻松管理您的游泳卡和消费记录。
      </p>
    <el-row>
      <el-col :span="12">
      <el-list class="function-list">
        <el-list-item>
          <h3>管理游泳卡</h3>
          <p>您可以在 <strong>CardManage-operate</strong> 页面管理自己的游泳卡，包括充值、退卡和查看卡片信息。</p>
        </el-list-item>
        <el-list-item>
          <h3>查询消费记录</h3>
          <p>您可以在 <strong>CardManage-query</strong> 页面随时随地查看您的消费记录，了解每次消费的详细信息。</p>
        </el-list-item>
        <el-list-item>
          <h3>修改密码</h3>
          <p>您可以在 <strong>profile-resetPassword</strong> 页面修改您的登录密码，确保账户安全。</p>
        </el-list-item>
        <el-list-item>
          <h3>修改用户信息</h3>
          <p>您可以在 <strong>profile-userProfile</strong> 页面更新您的个人信息，包括昵称、联系方式等，保持信息的最新状态。</p>
        </el-list-item>
      </el-list>
      </el-col>
      <el-col :span="8">
        <div class="container" style="margin-top: 35px;margin-left:50%;display: flex;flex-direction: column;" >
          <el-icon style="font-size: 100px"><Postcard /></el-icon>
          <el-icon style="font-size: 100px;margin-top: 20px"><Tickets /></el-icon>
          <el-icon style="font-size: 100px;margin-top: 20px"><Key /></el-icon>
          <el-icon style="font-size: 100px;margin-top: 20px"><InfoFilled /></el-icon>
        </div>
      </el-col>
    </el-row>
    </el-card>
  </div>
</template>

<script setup>
// 无需额外的逻辑
import {InfoFilled} from "@element-plus/icons-vue";
</script>

<style scoped>
.welcome-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; /* 让容器占满整个视口高度 */
  background-color: #f0f2f5; /* 背景颜色 */

}

.welcome-card {
  width: 100%; /* 卡片宽度 */
  padding: 20px; /* 内边距 */
  border-radius: 20px; /* 圆角 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 阴影 */
  text-align: center; /* 文字居中 */
  height: 80vh;
}

.welcome-title {
  font-size: 24px; /* 标题字体大小 */
  margin-bottom: 10px; /* 下边距 */
  color: #333; /* 字体颜色 */
}

.welcome-message {
  font-size: 16px; /* 消息字体大小 */
  margin-bottom: 20px; /* 下边距 */
  color: #666; /* 字体颜色 */
}

.function-list {
  text-align: left; /* 左对齐 */
}

.el-list-item {
  margin-bottom: 15px; /* 列表项之间的间距 */
  padding: 10px; /* 内边距 */
  border: 1px solid #e0e0e0; /* 边框 */
  border-radius: 10px; /* 圆角 */
  transition: background-color 0.3s; /* 背景色过渡效果 */
}

.el-list-item:hover {
  background-color: #f5f5f5; /* 悬停时背景颜色变化 */
}
</style>