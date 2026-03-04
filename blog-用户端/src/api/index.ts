import * as API from './api'

export const paramType = {
    'url1': '/archive/list', //归档页面
    'url2': '/articles', //通过id获取文章
    'url3': '/home', //首页页面
    'url4': '/about/tech' //关于页面
}

export const currentGET = (key: keyof typeof paramType, params?: object) => {
    return API.GET(paramType[key], params)
}

export const currentPOST = (key: string, data?: object) => {
    return API.POST(key, data)
}