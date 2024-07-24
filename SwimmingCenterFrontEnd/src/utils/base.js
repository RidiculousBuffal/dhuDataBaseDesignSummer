import axios from 'axios'
import {ElMessage} from "element-plus";
import {ElNotification} from "element-plus";

const ins = axios.create({
    baseURL: '/api'
})
ins.interceptors.response.use((resp) => {
    return resp.data;
}, (error) => {
    ElNotification({
        title: 'Error',
        message: 'Opps',
        type: 'error',
    })
})
export default ins