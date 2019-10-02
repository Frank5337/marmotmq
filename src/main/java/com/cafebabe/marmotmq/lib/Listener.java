package com.cafebabe.marmotmq.lib;

import java.net.Socket;

/**
 * @author Stormstout-Chen
 * 对监听者的抽象，一个Listener对象封装了一个监听者的所有属性
 */
public class Listener {

    //此监听者的连接
    private Socket socket;

    //累计发送消息失败的次数 超过15次则认为该监听者已挂
    private Integer fialTimes = 0;

    public Listener(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Integer getFialTimes() {
        return fialTimes;
    }

    public void setFialTimes(Integer fialTimes) {
        this.fialTimes = fialTimes;
    }

}
