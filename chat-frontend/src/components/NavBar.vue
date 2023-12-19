<template>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <router-link class="navbar-brand" :to="{name: 'chat_index'}">聊天室</router-link>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                <router-link class="nav-link" aria-current="page" :to="{name:'chat_index'}">聊天</router-link>
                </li>
            </ul>
            <ul class="nav-item" v-if="$store.state.user.nickname !== ''">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle navbar-text" href="#" role="button"  data-bs-toggle="dropdown">
                        {{ $store.state.user.nickname }}
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" @click="logout">退出登录</a></li>
                    </ul>
                </li>
            </ul>

            <span class="navbar-text" v-if="$store.state.user.nickname === null || $store.state.user.nickname === ''">
                登录
            </span>
            </div>
        </div>
    </nav>
</template>
  
<script>
    import { useStore } from 'vuex';
    import $ from 'jquery';

    export default {
    setup() {
        const store = useStore();
        const logout = () => {
            $.ajax({
                url: "http://localhost:8081/logout",
                type: "get",
                success(resp) {
                    if(resp.state === "success") {
                        store.commit("updateUser",{ userId: -1,username: "",nickname: "" });
                        localStorage.clear();
                        window.location.href = "http://localhost:8080/login/";
                    }
                }
            });
        };
        return {
            logout,
        }
    }
    }
  
  
</script>
  
<style>

</style>
  