/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package casestudy2.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import casestudy2.model.*;
import casestudy2.view.*;

/**
 *
 * @author Phong
 */
public class ClientControl {

    private User model;
    private ClientView view;
    private ArrayList<User> list;

    public ClientControl(ClientView view) {
        this.view = view;
        list = new ArrayList<User>();
        list.add(new User("0987654321", "q2w2e3"));
        list.add(new User("0983313567", "ki98u7"));
        list.add(new User("0912345678", "ngaythu5"));
        list.add(new User("0987452100", "so1dcv"));
    }

    public boolean checkUser(User input) {
        for (User data : list) {
            if (input.getPassword().equals(data.getPassword())
                    && input.getUsername().equals(data.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public class LoginListener implements ActionListener {

        private ClientControl control;

        public LoginListener(ClientControl control) {
            this.control = control;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            User model = control.view.getUserInput();
            if (control.checkUser(model)) {
                view.showMessage("Success");
            } else {
                view.showMessage("Error");
            }
        }
    }
}
