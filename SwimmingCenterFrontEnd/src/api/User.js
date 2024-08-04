import ins from '@/utils/base.js'
import {userTokenStore} from "@/store/userToken.js";

export const register = async (Username, Password) => {
    const url = `/user/register?username=${Username}&password=${Password}`
    return await ins.post(url)
}
export const userLogin = async (Username, Password) => {
    const data = JSON.stringify({
        'username': Username,
        'password': Password
    })
    const resp = await ins.post("/user/login", data, {
        headers: {
            "Content-Type": "application/json"
        }
    });
    return resp;
}
export const getUserPath = async () => {
    const tmpStore = userTokenStore()
    const resp = await ins.post("/user/path", null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp
}
export const getUserOwnInfo = async () => {
    const tmpStore = userTokenStore();
    const resp = await ins.post("/userInfo/", null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp
}
export const getUserCount = async () => {
    const tmpStore = userTokenStore();
    const resp = await ins.post("/userInfo/getUserCount", null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp
}
export const getMemberCount = async () => {
    const tmpStore = userTokenStore();
    const resp = await ins.post("/userInfo/getMemberCount", null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp
}
export const getUserIdByUsername = async (username) => {
    const url = `/userInfo/getIdByUsername?username=${username}`
    const tmpStore = userTokenStore();
    const resp = await ins.post(url, null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp;
}
export const updateUserInfo = async (data)=>{
    const tmpStore = userTokenStore();
    const resp = await ins.post("/userInfo/updateUserInfo",data,{
        headers:{
            Authorization:tmpStore.token,
            "Content-Type":"application/json"
        }
    })
    return resp;
}
export const matchUserNameByPrefix = async(prefix)=>{
    const tmpStore = userTokenStore();
    const url = `/userInfo/matchUsername?prefix=${prefix}`
    const resp = await ins.post(url, null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp;
}
export const getOldUserInfo = async (username)=>{
    const url = `/userInfo/getSomeUserInfoByUsername?username=${username}`
    const tmpStore = userTokenStore();
    const resp = await ins.post(url, null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp;
}