import ins from '@/utils/base.js'
import {userTokenStore} from "@/store/userToken.js";
export const getTotalConsumeNumber = async ()=>{
    const tmpStore = userTokenStore();
    const resp = await ins.post("/consume/getConsumeCount",null,{
        headers:{
            Authorization:tmpStore.token
        }
    })
    return resp;
}