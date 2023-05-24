package chatGPT2.model;

import java.util.ArrayList;
import java.util.List;

public class ChatModel {
    private List<String> userList;
    private List<Message> messageHistory;

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

    public void addMessage(Message message) {
        messageHistory.add(message);
    }

    public List<Message> getMessageHistory() {
        return messageHistory;
    }
}
