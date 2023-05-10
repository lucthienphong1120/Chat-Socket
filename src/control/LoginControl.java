/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import model.*;
import view.*;
import java.util.*;

/**
 *
 * @author Phong
 */
public class LoginControl {

    private LoginModel model;
    private LoginView view;
    private ArrayList<LoginModel> list;

    public LoginControl(LoginView view) {
        this.view = view;
        list = new ArrayList<LoginModel>();
        list.add(new LoginModel("0987654321", "q2w2e3"));
        list.add(new LoginModel("0983313567", "ki98u7"));
        list.add(new LoginModel("0912345678", "ngaythu5"));
        list.add(new LoginModel("0987452100", "so1dcv"));
    }

    public LoginControl() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean checkUser(LoginModel input) {
        for (LoginModel data : list) {
            if (input.getPassword().equals(data.getPassword())
                    && input.getUsername().equals(data.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public void LoginCheck() {
        model = view.getUserInput();
        if (checkUser(model)) {
            view.showMessage("Success");
        } else {
            view.showMessage("Error");
        }
    }
}
