package org.builovn.storage.validators;

public class Message {
    private String info;
    private Status status;

    public Message(String info, Status status){
        this.info = info;
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status.toString() + '\n' + info;
    }
}
