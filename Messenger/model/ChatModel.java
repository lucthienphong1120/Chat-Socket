package Messenger.model;

import java.util.ArrayList;
import java.util.List;

public class ChatModel {
    private List<String> userList;
    private List<MessageModel> messageHistory;

    public ChatModel() {
        userList = new ArrayList<>();
        messageHistory = new ArrayList<>();
    }

    public void addUser(String username) {
        userList.add(username);
    }

    public void removeUser(String username) {
        userList.remove(username);
    }

    public List<String> getUserList() {
        return userList;
    }

    public void addMessage(MessageModel message) {
        messageHistory.add(message);
    }

    public List<MessageModel> getMessageHistory() {
        return messageHistory;
    }
}
