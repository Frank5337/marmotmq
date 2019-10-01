package com.cafebabe.marmotmq.core;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Stormstout-Chen
 * 核心业务类 维护各条队列的所有监听者
 */
public class ListenersMap {

    private static volatile ConcurrentHashMap<String, ConcurrentLinkedQueue<Socket>> listenersMap = new ConcurrentHashMap<>();

    //获取一条队列的所有监听者
    public static ConcurrentLinkedQueue<Socket> getListeners(String queneName){

        return listenersMap.get(queneName);

    }

    //为一条队列添加一个监听者
    public static void addListener(String queneName,Socket socket){

        if (listenersMap.get(queneName) == null){
            synchronized (ListenersMap.class){
                if (listenersMap.get(queneName) == null){
                    listenersMap.put(queneName,new ConcurrentLinkedQueue<Socket>());
                }
            }
        }

        listenersMap.get(queneName).add(socket);

    }

}
