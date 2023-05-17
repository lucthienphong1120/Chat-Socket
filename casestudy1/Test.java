package casestudy1;

import casestudy1.control.*;
import casestudy1.view.*;

public class Test {

    public static void main(String[] args) {
        LoginView view = new LoginView();
        LoginControl control = new LoginControl(view);
        view.setVisible(true);
    }

}
