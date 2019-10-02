package com.cafebabe.marmotmq.net;

import com.cafebabe.marmotmq.core.ListenersMap;
import com.cafebabe.marmotmq.core.QueneFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Stormstout-Chen
 * 接收网络请求，注册监听，或将消息推送到对应的MsgQuene中
 */
public class AcceptNetWork implements Runnable {
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(5672);

            while (true) {

                Socket socket = serverSocket.accept();

                System.out.println(socket.getInetAddress());

                InputStream inputStream = null;
                try {
                    //获取输入流
                    inputStream = socket.getInputStream();

                    //获取请求内容，格式为，首行为请求的方法，之后为请求的参数 顺序为K,value,time,后两项可不传 全部由换行符分割
                    byte[] bytes = new byte[1024];

                    int length = inputStream.read(bytes);

                    String[] requestBody = new String(bytes, 0, length).split("\r\n");

                    String queneName = requestBody[0];

                    //通信格式如下：
                    //监听：queneName
                    //发送：queneName \r\n lengthOfMsg \r\n msg
                    if (requestBody.length == 1){
                        //注册监听
                        socket.setKeepAlive(true);
                        ListenersMap.addListener(queneName,socket);

                    }else {
                        //发送消息
                        sendMsg2Quene(queneName,requestBody[2]);

                    }

                    //sout
                    System.out.println("quene1:"+QueneFactory.getQuene("quene1"));
                    if (QueneFactory.getQuene("quene1") != null)
                        System.out.println(QueneFactory.getQuene("quene1").size());

                } catch (Exception e) {e.printStackTrace();}

            }

        } catch (IOException e) {e.printStackTrace();}
    }

    //将消息放入队列的方法
    private void sendMsg2Quene(String queneName, String msg) {
        //无此队列则自动创建队列,并启动一个负责推送该队列消息的线程
        if (QueneFactory.getQuene(queneName) == null) {
            synchronized (this) {
                if (QueneFactory.getQuene(queneName) == null) {
                    QueneFactory.creatQuene(queneName);
                    new Thread(new SendNetWork(queneName)).start();
                }
            }
        }

        QueneFactory.getQuene(queneName).add(msg);
    }
}
