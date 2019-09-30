package com.cafebabe.marmotmq.net;

import com.cafebabe.marmotmq.core.QueneFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * @author Stormstout-Chen
 * 任务 接收请求，将消息推送到对应的MsgQuene中
 */
public class NetWork implements Runnable {
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(5672);

            while (true) {

                System.out.println("quene1:"+QueneFactory.getQuene("quene1"));

                Socket socket = serverSocket.accept();
                InputStream inputStream = null;
                try {
                    //获取输入流
                    inputStream = socket.getInputStream();

                    //获取请求内容，格式为，首行为请求的方法，之后为请求的参数 顺序为K,value,time,后两项可不传 全部由换行符分割
                    byte[] bytes = new byte[1024];

                    int length = inputStream.read(bytes);

                    String[] requestBody = new String(bytes, 0, length).split("\r\n");

                    //sout
                    System.out.println(">>>>>>>>>>>>>>>>>>>>");
                    System.out.println(new String(bytes));
                    System.out.println(Arrays.toString(requestBody));
                    System.out.println(">>>>>>>>>>>>>>>>>>>>");

                    String queneName = requestBody[0];

                    if (QueneFactory.getQuene(queneName) == null) {
                        synchronized (this) {
                            if (QueneFactory.getQuene(queneName) == null) {
                                QueneFactory.creatQuene(queneName);
                            }
                        }
                    }

                    QueneFactory.getQuene(queneName).add(requestBody[2]);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
