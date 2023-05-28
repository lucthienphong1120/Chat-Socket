package Messenger.control;

import Messenger.model.*;
import java.io.File;
import java.io.FileReader;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MessageControl {

    private String fileName;

    public MessageControl(String fileName) {
        this.fileName = fileName;
//        createFile();
//        resetFile();
//        // Đăng ký sự kiện xoá file khi đóng chương trình
//        Runtime.getRuntime().addShutdownHook(new Thread(this::deleteFile));
    }

    public void saveMessage(MessageModel message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", generateID());
        jsonObject.put("name", message.getName());
        jsonObject.put("message", message.getMessage());
        jsonObject.put("time", message.getTime());

        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(jsonObject.toJSONString() + "\n");
        } catch (IOException ex) {
            Logger.getLogger(MessageControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<MessageModel> loadMessages() {
        List<MessageModel> listMessage = new ArrayList<>();
        try (FileReader fileReader = new FileReader(fileName)) {
            JSONParser jsonParser = new JSONParser();
            StringBuilder jsonContent = new StringBuilder();
            int character;
            while ((character = fileReader.read()) != -1) {
                jsonContent.append((char) character);
            }
            String jsonString = jsonContent.toString().trim();
            if (!jsonString.isEmpty()) {
                String[] messageArray = jsonString.split("\n");
                for (String messageStr : messageArray) {
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(messageStr);
                    String name = (String) jsonObject.get("name");
                    String message = (String) jsonObject.get("message");
                    String time = (String) jsonObject.get("time");
                    listMessage.add(new MessageModel(name, message, time));
                }
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(MessageControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listMessage;
    }

    private List<JSONObject> readMessage() {
        List<JSONObject> data = new ArrayList<>();
        try (FileReader fileReader = new FileReader(fileName)) {
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(fileReader);
            if (obj != null) {
                String jsonString = obj.toString().trim();
                if (!jsonString.isEmpty()) {
                    String[] messageArray = jsonString.split("\n");
                    for (String messageStr : messageArray) {
                        JSONObject jsonObject = (JSONObject) jsonParser.parse(messageStr);
                        data.add(jsonObject);
                    }
                }
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(MessageControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    public void createFile() {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(MessageControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void resetFile() {
        try (FileWriter fileWriter = new FileWriter(fileName, false)) {
            // Ghi một dòng trống vào file để xoá hết dữ liệu cũ
            fileWriter.write("");
        } catch (IOException ex) {
            Logger.getLogger(MessageControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteFile() {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    private String generateID() {
        return "ID_" + System.currentTimeMillis();
    }
}
