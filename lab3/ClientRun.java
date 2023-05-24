package lab3;


public class ClientRun {

	public static void main(String[] args) {
		ClientView view = new ClientView();
		ClientControl control = new ClientControl(view);
		view.setVisible(true);
	}

}