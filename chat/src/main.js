import { createApp } from 'vue'
import store from './store'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import * as Icons from '@element-plus/icons-vue'


const app = createApp(App).use(router).use(store).use(ElementPlus);
for (let iconName in Icons) {
    app.component(iconName, Icons[iconName])
}
app.mount("#app");


