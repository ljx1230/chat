package com.ljx.chat.wss;

import com.alibaba.fastjson.JSONObject;
import com.ljx.chat.entity.History;
import com.ljx.chat.mapper.HistoryMapper;
import com.ljx.chat.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{userId}")
public class WebSocket {
    private Session session = null;
    private Integer userId;

    private static HistoryMapper historyMapper;
    @Autowired
    public void setHistoryMapper(HistoryMapper historyMapper) {
        WebSocket.historyMapper = historyMapper;
    }
    private static UserMapper userMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        WebSocket.userMapper = userMapper;
    }
    final private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>(); // 保存在线列表
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        // 建立连接
        this.session = session;
        System.out.println("connect:" + userId);
        this.userId = Integer.parseInt(userId);
        webSockets.add(this);
    }

    @OnClose
    public void onClose() {
        // 关闭链接
        System.out.println("disconnected:" + userId);
    }

    @OnMessage
    public void onMessage(String message) { // 处理消息
        JSONObject jsonObject = JSONObject.parseObject(message);
        // System.out.println(message);
        History history = new History();
        history.setSendTime(new Date());
        history.setUserId(userId);
        history.setMessage(jsonObject.get("msg").toString());
        System.out.println(history);
        // 保存历史记录并且发消息
        historyMapper.insert(history);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // System.out.println(sdf.format(historyMapper.selectById(4).getSend_time()));

        JSONObject resp = new JSONObject();
        resp.put("date",sdf.format(history.getSendTime()));
        resp.put("name",userMapper.selectById(userId).getNickname());
        resp.put("message",jsonObject.get("msg").toString());
        sendAllMessage(resp.toJSONString());
    }

    private void sendAllMessage(String message) { // 群发消息

        for(WebSocket webSocket : webSockets) {
            try {
                if(webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
