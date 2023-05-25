package chatGPT2.control;

import chatGPT2.model.MessageModel;
import java.util.List;

public class NewClass {

    public static void main(String[] args) {
        MessageControl messageControl = new MessageControl("./src/data.json");
        // write file
        messageControl.saveMessage(new MessageModel("test 1", "test", "test"));
        messageControl.saveMessage(new MessageModel("test 2", "test", "test"));
        messageControl.saveMessage(new MessageModel("test 3", "test", "test"));
        messageControl.saveMessage(new MessageModel("test 4", "test", "test"));
        // read file
        List<MessageModel> listMessage = messageControl.loadMessages();
        System.out.println(listMessage.size());
        for (MessageModel m : listMessage) {
            System.out.println("...");
            System.out.println(m.getName() + " " + m.getMessage());
        }
        
    }
}
