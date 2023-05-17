package chatGPT.model;

import javax.swing.JTextArea;

public class Message {

    private String name = "";
    private String message = "";
    private String encMessage = "";
    private String secretKey = "secret";

    public Message() {
    }
    
    public Message(String name) {
        this.name = name;
    }

    public Message(String name, String secretKey) {
        this.name = name;
        this.secretKey = secretKey;
    }

    public void printMessage(JTextArea chatArea, String name, String message) {
        chatArea.append("\n");
        chatArea.append("[" + name + "]: " + message);
    }

    public void printMessage(JTextArea chatArea, String name, String message, boolean me) {
        if (me == true) {
            chatArea.append("\n");
            chatArea.append("\t\t\t\t[" + name + "]: " + message);
        }
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

    public String getEncMessage() {
        return encMessage;
    }

    public void setEncMessage(String encMessage) {
        this.encMessage = encMessage;
    }
    
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

}
