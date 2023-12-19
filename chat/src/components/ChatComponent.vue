<template >
  <el-container class="layout-container-demo" style="height: 500px">
    <el-aside width="200px">
      <el-scrollbar>
        <el-table :data="userList">
          <el-table-column prop="nickname" label="用户列表" width="150" />
        </el-table>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <div style="text-align: center;">
          <span style="font-size:30px">聊天室</span>
        </div>
      </el-header>

      <el-main>
        <el-scrollbar>
          <el-table :data="tableData">
            <el-table-column prop="date" label="时间" width="160" />
            <el-table-column prop="name" label="发送者 " width="120" />
            <el-table-column prop="message" label="消息" />
          </el-table>
        </el-scrollbar>
      </el-main>

      <el-footer>
        <textarea class="form-control" v-model="textarea" @keydown="sendMsg" aria-label="With textarea"></textarea>
      </el-footer>
    </el-container>

  </el-container>
</template>
  
<script>
import { onMounted, onUnmounted, ref } from 'vue'
import $ from "jquery";
import { useStore } from 'vuex';

const debounce = (fn, delay) => {
  let timer = null;

  return function () {
    let context = this;

    let args = arguments;

    clearTimeout(timer);

    timer = setTimeout(function () {
      fn.apply(context, args);
    }, delay);
  };
};

// 解决ERROR ResizeObserver loop completed with undelivered notifications.

const _ResizeObserver = window.ResizeObserver;

window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
  constructor(callback) {
    callback = debounce(callback, 16);

    super(callback);
  }
};
export default {

  setup() {
    const store = useStore();
    onMounted(() => {
      $.ajax({
        url: "http://localhost:8081/getinfo",
        type: "get",
        data: {
          id: store.state.userId,
        },
        success(resp) {
          store.commit("updateUser", { username: resp.username, nickname: resp.nickname });
        }
      });

    });
    
    const socketUrl = `ws://localhost:8081/websocket/${store.state.userId}/`;
    let socket = new WebSocket(socketUrl);

    socket.onopen = () => {
      console.log("connect!");
    };

    


    onUnmounted(() => {
      socket.close();
    });

    const sendMsg = event => {
      if (event.shiftKey && event.keyCode === 13) {
        document.execCommand('insertLineBreak'); // 换行
        event.preventDefault();
        return false;
      } else if (event.keyCode === 13) { // 回车键
        console.log("回车发送");
        socket.send(JSON.stringify({
                    msg: textarea.value,
                }));
        textarea.value = "";
        event.preventDefault();
        return false;
      }
      
    };



    let user = [];
    console.log(user);
    const textarea = ref("");
    let dataList = [];
    const tableData = ref(dataList);
    const userList = ref(user);
    $.ajax({
      url: "http://localhost:8081/getUserList",
      type: "get",
      success(resp) {
        for (let i = 0; i < resp.length; ++i) {
          userList.value.push({ "nickname": resp[i].nickname, "username": resp[i].username });
        }
      }
    });

    $.ajax({
      url: "http://localhost:8081/getHistory",
      type: "get",
      success(resp) {
        for (let i = 0; i < resp.length; ++i) {
          tableData.value.push({ "date": resp[i].date, "name": resp[i].name ,"message": resp[i].message});
        }
      }
    });

    socket.onmessage = msg => {
      console.log("receive message" + msg.data);
      const data = JSON.parse(msg.data);
      tableData.value.push(data);
    };

    return {
      tableData,
      textarea,
      userList,
      sendMsg,
    };
  },
}



</script>
  
<style scoped>
.layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}

.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}

.layout-container-demo .el-menu {
  border-right: none;
}

.layout-container-demo .el-main {
  padding: 0;
}

.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
</style>
  