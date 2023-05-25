package chatGPT2.control;

import chatGPT2.model.*;
import java.io.File;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;

public class MessageControl {

    private String fileName;
    private JSONArray jsonArray;

    public MessageControl(String fileName) {
        this.fileName = fileName;
        jsonArray = new JSONArray();
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

        jsonArray.add(jsonObject);
        try (FileWriter fileWriter = new FileWriter(fileName, false)) {
            fileWriter.write(jsonArray.toJSONString() + "\n");
        } catch (IOException ex) {
            Logger.getLogger(MessageControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<MessageModel> loadMessages() {
        List<MessageModel> listMessage = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("name");
            String message = (String) jsonObject.get("message");
            String time = (String) jsonObject.get("time");
            listMessage.add(new MessageModel(name, message, time));
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
