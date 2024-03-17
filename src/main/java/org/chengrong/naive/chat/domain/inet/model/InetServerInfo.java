package org.chengrong.naive.chat.domain.inet.model;

public class InetServerInfo {
    private String ip; // IP
    private int port; // 端口
    private boolean status; // 状态 true开启 false关闭

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
