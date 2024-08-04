<script setup>
import {ref} from 'vue'
import {getUserCount, getMemberCount} from "@/api/User.js";
import {getCardTotalNumber} from "@/api/Card.js";
import {getTotalConsumeNumber} from '@/api/Consume.js'
import {CreditCard, Tickets} from "@element-plus/icons-vue";

const userNumber = ref(0)
const MemberNumber = ref(0)
const cardNumber = ref(0)
const consumeNumber = ref(0)
const getUserNumber = async function () {
  const res = await getUserCount()
  console.log(res.data)
  userNumber.value = res.data;
}
const getMemberNumber = async function () {
  const res = await getMemberCount()
  console.log(res.data)
  MemberNumber.value = res.data;
}
const getCardNumber = async function () {
  const res = await getCardTotalNumber()
  console.log(res.data)
  cardNumber.value = res.data;
}
const getConsumeNumber = async function () {
  const res = await getTotalConsumeNumber()
  console.log(res.data)
  consumeNumber.value = res.data;
}
getUserNumber();
getMemberNumber();
getCardNumber();
getConsumeNumber();
</script>

<template>
  <div class="container">
    <div class="box box1">
      <el-row>
        <el-col span="10">
          <User style="width: 10em;height: 10em"/>
        </el-col>
        <el-col span="14" style="display: flex; justify-content: center;align-items: center;margin-left: 30px">
          <div class="Note">
            <div class="Title">
              当前注册用户数已达:
            </div>
            <div id="regPeople">
              <span style="font-size: 80px"> {{ userNumber }}</span><span>人</span>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="box box2">
      <el-row>
        <el-col span="10">
          <Star style="width: 10em;height: 10em"/>
        </el-col>
        <el-col span="14" style="display: flex; justify-content: center;align-items: center;margin-left: 30px">
          <div class="Note">
            <div class="Title">
              当前注册会员数已达:
            </div>
            <div id="Member">
              <span style="font-size: 80px"> {{ MemberNumber }}</span><span>人</span>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="box box3">
      <el-row>
        <el-col span="10" style="padding: 0;margin: 0">
          <CreditCard style="width: 10em;height: 10em"/>
        </el-col>
        <el-col span="14" style="display: flex; justify-content: center;align-items: center;margin-left: 30px">
          <div class="Note">
            <div class="Title">
              当前办卡数量已达:
            </div>
            <div id="card">
              <span style="font-size: 80px"> {{ cardNumber }}</span><span>张</span>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <div class="box box4">
      <el-row>
        <el-col span="10">
          <Tickets style="width: 10em;height: 10em"/>
        </el-col>
        <el-col span="14" style="display: flex; justify-content: center;align-items: center;margin-left: 30px">
          <div class="Note">
            <div class="Title">
              当前交易总量已达:
            </div>
            <div id="consume">
              <span style="font-size: 80px "> {{ consumeNumber }}</span><span>笔</span>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<style scoped lang="scss">
.container {
  display: flex;
  flex-wrap: wrap;
  height: 92.5vh; /* 设置容器高度为视口高度 */
}

.box {
  flex: 1 0 50%; /* 每个矩形占容器宽度的50% */
  height: 50%; /* 每个矩形占容器高度的50% */
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  color: white;
}

.box1 {
  background-color: #975959; /* 矩形 1 的颜色 */
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
}

.box2 {
  background-color: rgba(12, 97, 77, 0.64); /* 矩形 2 的颜色 */
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
}

.box3 {
  background-color: #dce6c2; /* 矩形 3 的颜色 */
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
}

.box4 {
  background-color: rgba(237, 228, 173, 0.68); /* 矩形 4 的颜色 */
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
}

.Note {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-size: 40px;

}
</style>