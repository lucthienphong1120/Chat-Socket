package chatGPT2.control;

import chatGPT2.model.*;
import java.io.BufferedReader;
import java.io.File;
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
        createFile();
        resetFile();
        // Đăng ký sự kiện xoá file khi đóng chương trình
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

    public List<MessageModel> loadAllMessages() {
        List<MessageModel> listMessage = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            JSONParser jsonParser = new JSONParser();
            while ((line = reader.readLine()) != null) {
                JSONObject jsonObject = (JSONObject) jsonParser.parse(line);
                String name = (String) jsonObject.get("name");
                String message = (String) jsonObject.get("message");
                String time = (String) jsonObject.get("time");
                listMessage.add(new MessageModel(name, message, time));
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(MessageControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listMessage;
    }
    
    private void createFile() {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(MessageControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void resetFile() {
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
