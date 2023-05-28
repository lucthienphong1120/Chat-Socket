package Messenger.model;

import java.io.Serializable;

public class MessageModel implements Serializable {

    private String name = "Anonymous";
    private String message = "";
    private String time = "00:00:00";

    public MessageModel() {
    }

    public MessageModel(String name, String message, String time) {
        this.name = name;
        this.message = message;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
