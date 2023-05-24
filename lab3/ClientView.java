package lab3;

import javax.swing.*;

import server.User;

//import controller.LoginListener;
//import model.User;
//import model.UserList;
import java.awt.*;
import java.awt.event.*;

public class ClientView extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField phonenumberField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public ClientView() {
        this.init();
    }

    public void init() {
        // Thiết lập giao diện đăng nhập
        this.setSize(300, 150);
        this.setTitle("Đăng nhập");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(3, 2, 10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel phonenumberLabel = new JLabel("Số điện thoại:");
        contentPane.add(phonenumberLabel);

        phonenumberField = new JTextField();
        contentPane.add(phonenumberField);

        JLabel passwordLabel = new JLabel("Mật khẩu:");
        contentPane.add(passwordLabel);

        passwordField = new JPasswordField();
        contentPane.add(passwordField);

        JLabel emptyLabel = new JLabel("");
        contentPane.add(emptyLabel);
        
        loginButton = new JButton("Đăng nhập");
        contentPane.add(loginButton);
        

        this.setContentPane(contentPane);
        this.pack();
        this.setLocationRelativeTo(null);
        //this.setVisible(true);
    }

    public String getPhoneNumber() {
        return phonenumberField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    // Thêm phương thức getUser() trả về một đối tượng LoginModel
    public User getUser() {
        String phonenumber = getPhoneNumber();
        String password = getPassword();
        return new User(phonenumber, password);
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }
    
    public void showMessage(String str) {
    	if("Success".equals(str)){
    		JOptionPane.showMessageDialog(this, "Login successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
    	}
    	else {
    		JOptionPane.showMessageDialog(this, "Invalid username and/or password!", "Message", JOptionPane.ERROR_MESSAGE);
    	}
    }

}