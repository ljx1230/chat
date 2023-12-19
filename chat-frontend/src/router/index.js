import { createRouter, createWebHistory } from 'vue-router'
import ChatView from '@/views/ChatView'
import LoginView from '@/views/LoginView'
import store from '@/store'

const routes = [
  {
    path: "/",
    redirect: "/chat/",
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/chat/",
    name: "chat_index",
    component: ChatView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/login/",
    name: "login",
    component: LoginView,
    meta: {
      requestAuth: false,
    }
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.requestAuth && !store.state.login) {
    next({ name: "login" });
  } else {
    next();
  }
})

export default router
