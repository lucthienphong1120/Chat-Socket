package lab3;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.*;
import javax.swing.*;

import server.User;
import server.AvailableUsersInterface;

public class ClientControl {
	//private User model;
	private ClientView view;
	private int serverHost;
	private int serverPort;
	
	//private ArrayList<User> list;
	
	public ClientControl(ClientView view) {
	    
		this.view = view;
		this.view.addLoginListener(new LoginListener(this));
		/*list = new ArrayList<LoginModel>();
		list.add(new User("0987654321", "q2w2e3"));*/
	}
	
	/*public boolean checkUser(String request) {
		/*for(User m : list) {
			if(m.getPassword().equals(user.getPassword())
			&& m.getUsername().equals(user.getUsername())
					
					) {
				return true;
			}
		}
		if("Login Successfully".equals(request)) {
			view.showMessage(request);
		}
		return false;
	}*/
	
	class LoginListener implements ActionListener{
		private ClientControl control;
		
		public LoginListener(ClientControl control) {
			this.control = control;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			//Lấy thông tin đăng nhập ở ClientView
			User model = control.view.getUser();
			if(Constant.VALID.equals(Validator.checkValid(model))) {
				String hostname = "localhost";
				int port = 6868;
				try(Socket socket = new Socket(hostname, port)){
					OutputStream out = socket.getOutputStream();
					ObjectOutputStream obj = new ObjectOutputStream(out);
					obj.writeObject(model);
					
					//Nhan ket qua tu Server
					InputStream in = socket.getInputStream();
					BufferedReader bf = new BufferedReader(new InputStreamReader(in));
					String response = bf.readLine();
					control.view.showMessage(response);
					if("Success".equals(response)) {
						System.out.print("User " + model.getUsername() + " logins sucessfully: \n");
						try {
							AvailableUsersInterface availUsers = 
									(AvailableUsersInterface)Naming.lookup("rmi://localhost:789/availUsers");
							ArrayList<String> allOtherUsers = availUsers.getAllAvailableUsers();
							for(String name : allOtherUsers) {
								if(!name.equals(model.getUsername()))
									System.out.println("\t Available user " + name);
							}
						}catch(Exception ex) {
							ex.printStackTrace();
						}
					}
					in.close();
					bf.close();
					
					obj.flush();
					obj.close();
					out.close();
				}catch(Exception ex) {
					ex.printStackTrace();
					System.out.println("Server not found");
				}
			}
			else {
				control.view.showMessage("Wrong username or password");
			}
			
		}
		
	}
}