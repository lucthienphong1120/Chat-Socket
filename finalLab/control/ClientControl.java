package finalLab.control;

import finalLab.model.*;
import finalLab.view.ClientView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.*;
import java.rmi.Naming;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientControl {

    private ClientView view;
    private int serverHost;
    private int serverPort;

    public ClientControl(ClientView view) {
        this.view = view;
    }

    public class LoginListener implements ActionListener {

        private ClientControl control;

        public LoginListener(ClientControl control) {
            this.control = control;
        }

        public void registry(String username, int port) {
            try {
                Registry registry = LocateRegistry.createRegistry(port);
                registry.rebind(username, new ClientNoticeImpl());
                System.out.println("Client lien lac voi Registry thanh cong");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //Lấy thông tin đăng nhập ở ClientView
            User model = control.view.getUserInput();
            // check validator from client input
            if (Constant.VALID.equals(Validator.checkValid(model))) {
                try {
                    String hostname = "localhost";
                    int port = 6868;
                    Socket socket = new Socket(hostname, port);
                    OutputStream out = socket.getOutputStream();
                    ObjectOutputStream obj = new ObjectOutputStream(out);
                    obj.writeObject(model);

                    //Nhan ket qua tu Server
                    InputStream in = socket.getInputStream();
                    BufferedReader bf = new BufferedReader(new InputStreamReader(in));
                    String response = bf.readLine();
                    if (response.equals("Success")) {
                        control.view.showMessage(true, "Login successfully!");
                        System.out.println("User " + model.getUsername() + " logins sucessfully");
                        registry(model.getUsername(), model.getPort());
                        
                        AvailableUsersInterface serverRMI
                                = (AvailableUsersInterface) Naming.lookup("rmi://localhost:789/serverRMI");
                        ArrayList<String> allOtherUsers = serverRMI.getAllAvailableUsers();
                        if (allOtherUsers != null && !allOtherUsers.isEmpty()) {
                            for (String name : allOtherUsers) {
                                if (!name.equals(model.getUsername())) {
                                    System.out.println("\t Available user " + name);
                                }
                            }
                        } else {
                            System.out.println("\t No one in the room!");
                        }
                        serverRMI.updateRMIClient(model);
                    } else {
                        control.view.showMessage(false, "Invalid username and/or password!");
                    }
                    in.close();
                    bf.close();

                    obj.flush();
                    obj.close();
                    out.close();
                } catch (IOException | NotBoundException ex) {
                    Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                control.view.showMessage(false, "You got a validation error!");
            }
        }
    }
}
