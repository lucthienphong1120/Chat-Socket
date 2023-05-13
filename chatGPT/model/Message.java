/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatGPT.model;

import javax.swing.JTextArea;

/**
 *
 * @author Phong
 */
public class Message {

    String name = "";
    String message = "";
    String encMessage = "";

    public Message() {
    }

    public void printMessage(JTextArea chatArea, String name, String message) {
        chatArea.append("\n");
        chatArea.append("[" + name + "]: " + message);
    }

    public void printMessage(JTextArea chatArea, String name, String message, boolean me) {
        if (me == true) {
            chatArea.append("\n");
            chatArea.append("\t\t[" + name + "]: " + message);
        }
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

}
