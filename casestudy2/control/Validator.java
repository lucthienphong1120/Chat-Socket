package casestudy2.control;

import casestudy2.model.*;

public class Validator {

    public static String checkValid(User user) {
        if (user != null) {//tham so user phai khac null
            if (user.getPassword().isEmpty()
                    && user.getUsername().isEmpty()
                    && user.getPassword() != null
                    && user.getUsername() != null) {

                String username = user.getUsername();

                if (isValidUsername(username)) {
                    String password = user.getPassword();

                    //Kiem tra xem username co trung password hay khong?
                    if (!username.equals(password)) {
                        if (isValidPassword(password)) {
                            return Constant.VALID;
                        }
                    }
                }

            }
        }
        return Constant.INVALID;
    }

    private static boolean isValidPassword(String password) {
        if (password.length() >= 6 && password.length() <= 10) {
            for (int i = 0; i < password.length(); i++) {
                if (!Character.isDigit(password.charAt(i))
                        && !Character.isAlphabetic(password.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static boolean isValidUsername(String username) {
        //Kiem tra username bat dau bang 0, 9?
        if (username.charAt(0) == '0'
                && username.charAt(1) == '9'
                && username.length() == 10) {

            //Kiem tra xem username co du 10 ky tu toan so hay khong
            for (int i = 2; i < 10; i++) {
                if (username.charAt(i) < '0' || username.charAt(i) > '9') {
                    return false;
                }
            }
            return true;

        }
        return false;
    }
}
