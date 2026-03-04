declare module "*.vue" {
    import type { DefineComponent } from "vue";
    const component: DefineComponent<{}, {}, any>;
    export default component;
}
// 告诉 TypeScript：遇到这个路径，请把它当做一个合法的模块处理
declare module '@kangc/v-md-editor/lib/preview' {
    const component: any;
    export default component;
}

// 如果你还用到了主题，也可以顺便一起声明了
declare module '@kangc/v-md-editor/lib/theme/github.js' {
    const theme: any;
    export default theme;
}