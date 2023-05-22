package chatGPT.view;

import chatGPT.control.*;
import chatGPT.model.*;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class chatServerView extends javax.swing.JFrame {

    private DataOutputStream output;
    private DataInputStream input;
    private Socket connection;
    private final int totalClients = 100;
    private final int port = 1234;
    Message server = new Message("Server", "secret");
    Encryption enc = new Encryption();
    Message m = new Message();

    public chatServerView() {
        initComponents();
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        jTextMessage = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        status = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        JgetMessage = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server chat");
        setBackground(new java.awt.Color(51, 255, 204));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(new java.awt.Color(102, 255, 204));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        chatArea.setColumns(20);
        chatArea.setRows(5);
        chatArea.setFocusable(false);
        jScrollPane1.setViewportView(chatArea);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(0, 90, 550, 250);

        jTextMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextMessageActionPerformed(evt);
            }
        });
        jPanel1.add(jTextMessage);
        jTextMessage.setBounds(10, 350, 440, 40);

        jButton1.setBackground(new java.awt.Color(0, 102, 153));
        jButton1.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Send");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(460, 350, 80, 40);

        status.setForeground(new java.awt.Color(255, 255, 255));
        status.setText("Status");
        jPanel1.add(status);
        status.setBounds(0, 60, 190, 30);

        jLabel2.setFont(new java.awt.Font("Myriad Pro", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Server");
        jLabel2.setToolTipText("");
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jLabel2.setRequestFocusEnabled(false);
        jPanel1.add(jLabel2);
        jLabel2.setBounds(190, 10, 190, 70);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Get message:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(380, 10, 170, 16);

        JgetMessage.setEditable(false);
        JgetMessage.setBackground(new java.awt.Color(102, 102, 102));
        JgetMessage.setForeground(new java.awt.Color(255, 255, 255));
        JgetMessage.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        JgetMessage.setFocusable(false);
        JgetMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JgetMessageActionPerformed(evt);
            }
        });
        jPanel1.add(JgetMessage);
        JgetMessage.setBounds(390, 40, 150, 22);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        sendMessage(jTextMessage.getText());
        jTextMessage.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextMessageActionPerformed
        // TODO add your handling code here:
        sendMessage(jTextMessage.getText());
        jTextMessage.setText("");
    }//GEN-LAST:event_jTextMessageActionPerformed

    private void JgetMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JgetMessageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JgetMessageActionPerformed

    public void startRunning() {
        try {
            ServerSocket serversocket = new ServerSocket(port, totalClients);
            status.setText("Waiting for connect...");
            connection = serversocket.accept();

            status.setText("Connected to " + connection.getInetAddress().getHostAddress());
            output = new DataOutputStream(connection.getOutputStream());
            output.flush();
            input = new DataInputStream(connection.getInputStream());
            chatting();
        } catch (IOException ex) {
            Logger.getLogger(chatServerView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void chatting() {
        String message = "";
        do {
            try {
                String data = (String) input.readUTF();
                String rname = data.split("\\|")[0];
                String encMessage = data.split("\\|")[1];
                JgetMessage.setText(encMessage);
                message = enc.decrypt(encMessage, server.getSecretKey());
                if (message == null) {
                    message = "Can't decrypt the message, check the secret key again";
                }
                m.printMessage(chatArea, rname, message);
            } catch (IOException ex) {
                Logger.getLogger(chatServerView.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (!message.equals("END"));
    }

    private void sendMessage(String message) {
        try {
            m.printMessage(chatArea, server.getName(), message, true);
            String encMessage = enc.encrypt(message, server.getSecretKey());
            output.writeUTF(server.getName() + "|" + encMessage);
            output.flush();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Unable to Send Message!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JgetMessage;
    private javax.swing.JTextArea chatArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextMessage;
    private javax.swing.JLabel status;
    // End of variables declaration//GEN-END:variables
}
