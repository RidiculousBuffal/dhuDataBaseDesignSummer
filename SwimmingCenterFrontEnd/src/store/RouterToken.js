import {defineStore} from 'pinia'
import {ref} from 'vue'

export const RouterTokenStore = defineStore(
    'token-3247327984732897439287', () => {
        const MyRoutes = ref([]);
        const setToken = (arr=[]) => {
            MyRoutes.value = arr;
        }
        const removeToken = () => {
            MyRoutes.value = []
        }
        const getToken = ()=>{
            return MyRoutes.value
        }
        return {MyRoutes, setToken, removeToken, getToken}
    },{
        persist:true
    }
)