package chatGPT2.control;

import chatGPT2.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageControl {

    private String fileName;

    public MessageControl(String fileName) {
        this.fileName = fileName;
    }

    public void saveMessage(MessageModel message) {
        JSONArray jsonArray = loadMessages();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", generateID());
        jsonObject.put("name", message.getName());
        jsonObject.put("message", message.getMessage());
        jsonObject.put("time", message.getTime());
        jsonArray.add(jsonObject);

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(jsonArray.toJSONString());
            System.out.println("Đã lưu tin nhắn vào file thành công.");
        } catch (IOException ex) {
            Logger.getLogger(MessageControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<MessageModel> loadAllMessages() {
        JSONArray jsonArray = loadMessages();
        List<MessageModel> messages = new ArrayList<>();

        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("name");
            String message = (String) jsonObject.get("message");
            String time = (String) jsonObject.get("time");
            messages.add(new MessageModel(name, message, time));
        }

        return messages;
    }

    private JSONArray loadMessages() {
        JSONArray jsonArray = new JSONArray();

        try (FileReader fileReader = new FileReader(fileName)) {
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(fileReader);
            if (obj != null) {
                jsonArray = (JSONArray) obj;
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(MessageControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jsonArray;
    }

    private String generateID() {
        return "ID_" + System.currentTimeMillis();
    }
}
