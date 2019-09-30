package com.cafebabe.marmotmq.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Stormstout-Chen
 * 核心业务类 维护queneFactory
 * 保证每条quene都是单例的
 */
public class QueneFactory {

    private static ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queneFactory = new ConcurrentHashMap<>();

    public static ConcurrentLinkedQueue<String> getQuene(String queneName){

        return queneFactory.get(queneName);

    }

    public static synchronized void creatQuene(String queneName){

        if (queneFactory.get(queneName) == null){
            queneFactory.put(queneName,new ConcurrentLinkedQueue<String>());
        }

    }
}
