<template>
    <ContentField>
            <div class="mb-3">
                <label class="form-label">用户名</label>
                <input v-model="username" type="text" class="form-control" name="username">
            </div>
            <div class="mb-3">
                <label for="exampleInputPassword1" class="form-label">密码</label>
                <input v-model="password" type="password" class="form-control" name="password" id="exampleInputPassword1">
            </div>
            <span style="color: red;">{{ message }}</span>
            <br/>
            <button @click="login" class="btn btn-primary">登录</button>
    </ContentField>
</template>

<script>
import ContentField from '@/components/ContentField.vue';
import { ref } from 'vue';
import $ from 'jquery';

export default {
    setup() {
        // const store = useStore();
        let message = ref("");
        let username = ref("");
        let password = ref("");
        const login = () => {
            $.ajax({
                url: "http://localhost:8081/login",
                type: "post",
                data:{
                    username: username.value,
                    password: password.value
                },
                success(resp) {
                    if(resp.state === "fail") {
                        message.value = "用户名或密码错误";
                    } else {
                        localStorage.setItem("isLogin",true);
                        localStorage.setItem("userId",resp.user.id);
                        window.location.href = "http://localhost:8080/chat/";
                    }
                }
            });
        };
        return {
            message,
            username,
            password,
            login
        }
    },
    components: {
        ContentField,
    }
}
</script>

<style scoped>

</style>