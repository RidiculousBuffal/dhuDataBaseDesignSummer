import ins from '@/utils/base.js'
import {userTokenStore} from "@/store/userToken.js";

export const register = async (Username,Password)=>{
    const url = `/user/register?username=${Username}&password=${Password}`
    return await ins.post(url)
}
export const userLogin = async (Username,Password) =>{
    const data = JSON.stringify({
        'username':Username,
        'password':Password
    })
    const resp = await ins.post("/user/login",data,{
        headers:{
            "Content-Type":"application/json"
        }
    });
    return resp;
}
export const getUserPath = async()=>{
    const tmpStore = userTokenStore()
    const resp = await ins.post("/user/path",null,{
        headers:{
            Authorization:tmpStore.token
        }
    })
    return resp
}