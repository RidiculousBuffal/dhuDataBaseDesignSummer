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
export const updateUserInfo = async (data) => {
    const tmpStore = userTokenStore();
    const resp = await ins.post("/userInfo/updateUserInfo", data, {
        headers: {
            Authorization: tmpStore.token,
            "Content-Type": "application/json"
        }
    })
    return resp;
}
export const matchUserNameByPrefix = async (prefix) => {
    const tmpStore = userTokenStore();
    const url = `/userInfo/matchUsername?prefix=${prefix}`
    const resp = await ins.post(url, null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp;
}
export const getOldUserInfo = async (username) => {
    const url = `/userInfo/getSomeUserInfoByUsername?username=${username}`
    const tmpStore = userTokenStore();
    const resp = await ins.post(url, null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp;
}
export const getUNameByPrefix = async (prefix) => {
    const url = `/userInfo/getUNameByPrefix?prefix=${prefix}`
    const tmpStore = userTokenStore();
    const resp = await ins.post(url, null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp;
}
export const adminQueryUserInfo = async (url) => {
    const tmpStore = userTokenStore();
    const resp = await ins.post(url, null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp;
}
export const AdminAddUser = async (username, password, rid) => {
    const url = `/user/AdminAddUser?username=${username}&password=${password}&rid=${rid}`
    const tmpStore = userTokenStore();
    const resp = await ins.post(url, null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp;

}
export const updateUserRole = async (rid, uid) => {
    const url = `/user/updateUserRole?rid=${rid}&uid=${uid}`
    const tmpStore = userTokenStore();
    const resp = await ins.post(url, null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp;

}
export const updateUserState = async (state, uid) => {
    const url = `/user/updateUserState?state=${state}&uid=${uid}`
    const tmpStore = userTokenStore();
    const resp = await ins.post(url, null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp;
}
export const getBlockedUser = async () => {
    const url = '/user/getBlockedUsers';
    const tmpStore = userTokenStore();
    const resp = await ins.post(url, null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp;
}
export const userResetPassword = async (_old, _new, _uid) => {
    const url = `/user/userResetPassword?oldPassword=${_old}&newPassword=${_new}&uid=${_uid}`
    const tmpStore = userTokenStore();
    const resp = await ins.post(url, null, {
        headers: {
            Authorization: tmpStore.token
        }
    })
    return resp;
}