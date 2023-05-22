package chatGPT.view;

import chatGPT.control.*;
import chatGPT.model.*;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class chatClientView extends javax.swing.JFrame {

    private DataOutputStream output;
    private DataInputStream input;
    private final String serverIP;
    private int port = 1234;
    Message client = new Message("Client", "secret");
    Encryption enc = new Encryption();
    Message m = new Message();

    public chatClientView(String serverIP) {
        initComponents();
        this.setTitle("Client");
        this.setVisible(true);
        this.serverIP = serverIP;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextMessage = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        JgetMessage = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 51));
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(null);

        jTextMessage.setToolTipText("text\tType your message here...");
        jTextMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextMessageActionPerformed(evt);
            }
        });
        jPanel1.add(jTextMessage);
        jTextMessage.setBounds(10, 350, 440, 40);

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
        chatArea.setFocusable(false);
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

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Get message:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(370, 10, 180, 16);

        JgetMessage.setEditable(false);
        JgetMessage.setBackground(new java.awt.Color(102, 102, 102));
        JgetMessage.setForeground(new java.awt.Color(255, 255, 255));
        JgetMessage.setFocusable(false);
        JgetMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JgetMessageActionPerformed(evt);
            }
        });
        jPanel1.add(JgetMessage);
        JgetMessage.setBounds(380, 40, 160, 22);

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

    private void jTextMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextMessageActionPerformed
        // TODO add your handling code here:
        sendMessage(jTextMessage.getText());
        jTextMessage.setText("");
    }//GEN-LAST:event_jTextMessageActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        sendMessage(jTextMessage.getText());
        jTextMessage.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void JgetMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JgetMessageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JgetMessageActionPerformed

    public void startRunning() {
        status.setText("Cannot find server...");
        try {
            Socket socket = new Socket(serverIP, port);

            status.setText("Connected to: " + socket.getInetAddress().getHostAddress());
            output = new DataOutputStream(socket.getOutputStream());
            output.flush();
            input = new DataInputStream(socket.getInputStream());
            chatting();
        } catch (IOException ex) {
            Logger.getLogger(chatClientView.class.getName()).log(Level.SEVERE, null, ex);
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
                message = enc.decrypt(encMessage, client.getSecretKey());
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
            m.printMessage(chatArea, client.getName(), message, true);
            String encMessage = enc.encrypt(message, client.getSecretKey());
            output.writeUTF(client.getName() + "|" + encMessage);
            output.flush();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Unable to Send Message!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JgetMessage;
    private javax.swing.JTextArea chatArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextMessage;
    private javax.swing.JLabel status;
    // End of variables declaration//GEN-END:variables
}
