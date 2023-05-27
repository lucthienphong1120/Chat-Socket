package finalLab.control;

import finalLab.model.*;

public class Validator {

    public static String checkValid(User user) {
        if (user.getPassword().isEmpty() || user.getUsername().isEmpty()) {
            return Constant.INVALID;
        }
        String username = user.getUsername();
        String password = user.getPassword();
        //Kiem tra xem username co trung password hay khong?
        if (username.equals(password)) {
            return Constant.INVALID;
        }
        if (isValidPassword(password) && isValidUsername(username)) {
            return Constant.VALID;
        } else {
            return Constant.INVALID;
        }
    }

    private static boolean isValidPassword(String password) {
        // Kiểm tra độ dài tối thiểu
        if (password.length() < 6) {
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            // Kiểm tra là chữ hoặc số (không có kí tự đặc biệt)
            if (!Character.isLetterOrDigit(password.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidUsername(String username) {
        // Kiểm tra độ dài và tiền tố
        if (username.length() == 10 && username.startsWith("09")) {
            // Kiểm tra các kí tự còn lại là số
            for (int i = 2; i < username.length(); i++) {
                if (!Character.isDigit(username.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
