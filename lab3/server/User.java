package server;
//package model;
import java.io.*;

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    
	private String username;
	private String password;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof User) {
			User user = (User)obj;
			return this.username.equals(user.getUsername()) 
						&& this.password.equals(user.getPassword());
		}
		return false;
	}
	
}