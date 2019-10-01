package com.cafebabe.marmotmq.net;

import com.cafebabe.marmotmq.core.ListenersMap;
import com.cafebabe.marmotmq.core.QueneFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Stormstout-Chen
 * 发送消息的任务，每条quene对应一个
 */
public class SendNetWork implements Runnable {

    private String queneName;

    private ConcurrentLinkedQueue<String> msgQuene;

    private ConcurrentLinkedQueue<Socket> listeners;

    SendNetWork(String queneName){
        this.queneName = queneName;
    }

    @Override
    public void run() {

        while (true){

            msgQuene = QueneFactory.getQuene(queneName);
            listeners = ListenersMap.getListeners(queneName);

            if (msgQuene.isEmpty() || ListenersMap.getListeners(queneName) == null || ListenersMap.getListeners(queneName).size() == 0){
                try {
                    synchronized (this){
                        this.wait(1000);
                    }
                    continue;
                } catch (InterruptedException e) {e.printStackTrace();}
            }

            String msg = msgQuene.poll();

            sendMsg(msg);

        }

    }

    private void sendMsg(String msg) {

        for (Socket listener : listeners) {
            try {
                OutputStream outputStream = listener.getOutputStream();
                outputStream.write(msg.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
