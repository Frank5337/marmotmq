package com.cafebabe.marmotmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MarmotmqApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void testSet() throws Exception {

        //读消息文件
        File file = new File("src/test/msgTemplates/message.txt");

        FileInputStream fileInputStream = new FileInputStream(file);

        byte[] bytes = new byte[1024];
        int requestLength = fileInputStream.read(bytes);

        //模拟cs交互，建立连接并将消息写出去
        Socket socket = new Socket("localhost",5672);

        OutputStream outputStream = socket.getOutputStream();

        outputStream.write(bytes,0,requestLength);


    }
}
