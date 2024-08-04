import {defineStore} from 'pinia'
import {ref} from 'vue'

export const userInfoTokenStore = defineStore(
    'token-238478932743274982379', () => {
        const token = ref({});
        const setToken = (obj) => {
            token.value = obj;
        }
        const removeToken = () => {
            token.value = {}
        }
        return {token, setToken, removeToken}
    },{
        persist:true
    }
)