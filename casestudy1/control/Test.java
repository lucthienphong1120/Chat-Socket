/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package casestudy1.control;

import casestudy1.view.*;

/**
 *
 * @author Phong
 */
public class Test {

    public static void main(String[] args) {
        LoginView view = new LoginView();
        LoginControl control = new LoginControl(view);
        view.setVisible(true);
    }

}
