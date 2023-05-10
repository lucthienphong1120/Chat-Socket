package chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class chat_client extends javax.swing.JFrame {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private final String serverIP;
    private Socket connection;
    private final int port = 6789;
    final static String secretKey = "secret";
    Encryption encyrDecry = new Encryption();

    public chat_client(String serverIP) {
        initComponents();
        this.setTitle("Client");
        this.setVisible(true);
        status.setVisible(true);
        this.serverIP = serverIP;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 51));
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(null);

        jTextField1.setToolTipText("text\tType your message here...");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1);
        jTextField1.setBounds(10, 350, 440, 40);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(460, 350, 80, 40);

        chatArea.setColumns(20);
        chatArea.setRows(5);
        jScrollPane1.setViewportView(chatArea);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(0, 90, 550, 250);

        jLabel2.setFont(new java.awt.Font("Myriad Pro", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Client");
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel2);
        jLabel2.setBounds(190, 10, 180, 70);

        status.setForeground(new java.awt.Color(255, 255, 255));
        status.setText("Status");
        jPanel1.add(status);
        status.setBounds(0, 60, 190, 30);
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 400, 400);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        setSize(new java.awt.Dimension(566, 443));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

        sendMessage(jTextField1.getText());
        jTextField1.setText("");
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        sendMessage(jTextField1.getText());
        jTextField1.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    public void startRunning() {
        try {
            status.setText("Attempting Connection ...");
            connection = new Socket(serverIP, port);

            status.setText("Connected to: " + connection.getInetAddress().getHostAddress());
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            whileChatting();
        } catch (IOException ex) {
            Logger.getLogger(chat_client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void whileChatting() {
        jTextField1.setEditable(true);
        do {
            try {
                message = (String) input.readObject();
                chatArea.append("\n" + message);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(chat_client.class.getName()).log(Level.SEVERE, null, ex);
            }

        } while (!message.equals("Client - END"));
    }

    private void sendMessage(String message) {
        try {
            chatArea.append("\nME(Client) - " + message);
            String encryptedmsg = encyrDecry.encrypt(message, secretKey);
            System.out.println(encryptedmsg);
            output.writeObject("                                                             (enc):" + encryptedmsg);
            Encryption e = new Encryption();
            message = e.decrypt(encryptedmsg, secretKey);
            output.writeObject("                                                             Client(decrypt) - " + message);
            output.flush();
        } catch (IOException ex) {
            chatArea.append("\n Unable to Send Message");
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea chatArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel status;
    // End of variables declaration//GEN-END:variables
}
