// import * as API from './api'
import request from './request'

export const paramType = {
    'url1': '/behind/user/login', //登录
    'url2': '/behind/user/modify',//修改密码
    'url3': '/behind/user/refresh',//刷新页面（检查密钥状态）
    'url4': '/behind/user/exit',//退出登录
    'url5': '/behind/misce/home',//获取杂项配置里的首页设置
    'url6': '/behind/misce/tech',//获取砸向配置里的关于页技术选项
    'url7': '/behind/misce/techupdate',//更新数据
    'url8': '/behind/misce/homeupdate',//更新数据
    'url9': '/behind/articles/insert',//发布文章
    'url10': '/behind/archive/list',//获取归档文章列表
    'url11': '/behind/articles',//获取文章详情（编辑用）
    'url12': '/behind/articles/update',//更新文章
    'url13': '/behind/articles/delete',//删除文章
    'url14': '/behind/upload',//上传图片
}

export const currentGET = (key: keyof typeof paramType, params?: object) => {
    // return API.GET(paramType[key], params)
    return request.get(paramType[key], { params })
}

export const currentPOST = (key: keyof typeof paramType, data?: object) => {
    //return API.POST(paramType[key], data)
    return request.post(paramType[key], data)
}