package chatOld.view;

import chatOld.control.*;
import chatOld.model.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class chatServerView extends javax.swing.JFrame {

    Encryption enc = new Encryption();
    Socket connection;
    Message server;

    public chatServerView() {
        initComponents();
    }
    
    public chatServerView(Socket connection, Message server) {
        this.connection = connection;
        this.server = server;
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
        jButton1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
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

        jLabel2.setFont(new java.awt.Font("Myriad Pro", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Server");
        jLabel2.setToolTipText("");
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jLabel2.setRequestFocusEnabled(false);
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 10, 190, 70);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Get message:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(330, 10, 220, 16);

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
        JgetMessage.setBounds(330, 40, 210, 22);

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

    public void chatting() {
        String message = "";
        System.out.println("server chat");
        do {
            try {
                DataInputStream input = new DataInputStream(connection.getInputStream());
                String data = (String) input.readUTF();
                String rname = data.split("\\|")[0];
                String encMessage = data.split("\\|")[1];
                JgetMessage.setText(encMessage);
                message = enc.decrypt(encMessage, server.getSecretKey());
                if (message == null) {
                    message = "Can't decrypt the message, check the secret key again";
                }
                server.printMessage(chatArea, rname, message);
            } catch (IOException ex) {
                Logger.getLogger(chatServerView.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (!message.equals("END"));
    }

    public void sendMessage(String message) {
        try {
            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            server.printMessage(chatArea, server.getName(), message, true);
            String encMessage = enc.encrypt(message, server.getSecretKey());
            output.writeUTF(server.getName() + "|" + encMessage);
            output.flush();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Unable to Send Message!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(loginControlView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(loginControlView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(loginControlView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(loginControlView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chatServerView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField JgetMessage;
    public javax.swing.JTextArea chatArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextField jTextMessage;
    // End of variables declaration//GEN-END:variables
}
