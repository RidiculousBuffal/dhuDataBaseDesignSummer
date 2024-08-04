import {defineStore} from 'pinia'
import {ref} from 'vue'

export const userTokenStore = defineStore(
    'token-7328473287498327', () => {
        const token = ref('');
        const setToken = (str) => {
            token.value = str;
        }
        const removeToken = () => {
            token.value = ''
        }
        return {token, setToken, removeToken}
    },{
        persist:true
    }
)