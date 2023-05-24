package chatGPT2.model;

import java.util.Date;

public class Message {

    private String name = "";
    private String message = "";
    private Date time; 

    public Message(String name, String message, Date time) {
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
