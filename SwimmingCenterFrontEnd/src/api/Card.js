import ins from '@/utils/base.js'
import {userTokenStore} from "@/store/userToken.js";

export const getCardTotalNumber = async () => {
    const tmpStore = userTokenStore();
    const res = await ins.post("/card/getCardCount", null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return res;
}
export const makeCard = async (data_) => {
    const tmpStore = userTokenStore();
    const resp = await ins.post("/card/makeCard", data_, {
        headers: {
            Authorization: tmpStore.token,
            "Content-Type": "application/json"
        }
    })
    return resp;
}
export const getCidWithPrefix = async (qs) => {
    const url = `/card/getCidWithPrefix?prefix=${qs}`
    const tmpStore = userTokenStore();
    const resp = await ins.post(url, null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp;

}
export const queryForCardInfo = async (formdata) => {
    const tmpStore = userTokenStore();
    const resp = await ins.post("/card/getCardsInfoWithPageHelper", formdata, {
        headers: {
            Authorization: tmpStore.token,
            "Content-Type": "multipart/form-data"
        }
    })
    return resp;
}
export const updateTidAndDiscount = async (cid, tid, discount) => {
    const url = `/card/updateTidAndDiscount?cid=${cid}&tid=${tid}&discount=${discount}`
    const tmpStore = userTokenStore();
    const resp = await ins.post(url, null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp;
}
export const addMoney = async (userId, CID, Money, Description) => {
    const url = `/card/addMoney?userId=${userId}&CID=${CID}&Money=${Money}&description=${Description}`
    const tmpStore = userTokenStore();
    const resp = await ins.post(url, null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp;
}
export const BlockCard = async (data) => {
    const tmpStore = userTokenStore();
    const resp = await ins.post("/card/blockcard", data, {
        headers: {
            "Content-Type": "application/json",
            Authorization: tmpStore.token
        }
    })
    return resp;
}
export const setStatus = async (cid, status) => {
    const url = `/card/setCardStatus?cid=${cid}&status=${status}`
    const tmpStore = userTokenStore();
    const resp = await ins.post(url, null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp;
}