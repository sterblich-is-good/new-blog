import axios from "axios";

//设置axios的默认值
axios.defaults.baseURL = import.meta.env.VITE_API_URL;

//默认get方法
/**
 * export 
 * async 异步请求
 *  */
export const GET = async (url: string, params?: object) => {
    try {
        const { data: res } = await axios.get(url, {
            params: params,
        })
        return res
    }
    catch (error) {
        return error
    }
}

//默认post方法
export const POST = async (url: string, data?: object) => {
    try {
        const { data: res } = await axios.post(url, data)
        return res
    }
    catch (error) {
        return error
    }
}