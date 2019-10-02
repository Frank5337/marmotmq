package com.cafebabe.marmotmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.Socket;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class MarmotmqApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void testSendMsg2Quene() throws Exception {

        //读消息文件
        File file = new File("src/test/msgTemplates/message.txt");

        FileInputStream fileInputStream = new FileInputStream(file);

        byte[] bytes = new byte[1024];
        int requestLength = fileInputStream.read(bytes);

        //模拟cs交互，建立连接并将消息写出去
        Socket socket = new Socket("localhost",5672);

        OutputStream outputStream = socket.getOutputStream();

        outputStream.write(bytes,0,requestLength);


        OutputStream outputStream2 = socket.getOutputStream();

        System.out.println(outputStream == outputStream2);
    }

    @Test
    public void testLinten() throws IOException {

        Socket socket = new Socket("localhost",5672);

        OutputStream outputStream = socket.getOutputStream();

        outputStream.write("quene1".getBytes());

        InputStream inputStream = socket.getInputStream();

        byte[] bytes = new byte[1024];

        int length = inputStream.read(bytes);

        while (length != 0){
            System.out.println(length);

            String s = new String(bytes, 0, length);

            System.out.println(s);

            length = inputStream.read(bytes);
        }


    }
}
