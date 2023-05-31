package Messenger.view;

import javax.swing.Icon;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ChatView extends javax.swing.JFrame {

    private StyledDocument document;
    private SimpleAttributeSet alignCenter;
    private SimpleAttributeSet alignLeft;
    private SimpleAttributeSet alignRight;

    public ChatView() {
        initComponents();
        document = jChatArea.getStyledDocument();
        alignCenter = new SimpleAttributeSet();
        StyleConstants.setAlignment(alignCenter, StyleConstants.ALIGN_CENTER);
        alignLeft = new SimpleAttributeSet();
        StyleConstants.setAlignment(alignLeft, StyleConstants.ALIGN_LEFT);
        alignRight = new SimpleAttributeSet();
        StyleConstants.setAlignment(alignRight, StyleConstants.ALIGN_RIGHT);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Title = new javax.swing.JLabel();
        jChatPanel = new javax.swing.JPanel();
        jSend = new javax.swing.JButton();
        jTextMessage = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jUserList = new javax.swing.JLayeredPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jChatArea = new javax.swing.JTextPane();
        jLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat App");

        Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Title.setText("Welcome back Your name");
        Title.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chat App", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        jChatPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jSend.setText("SEND");
        jSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSendActionPerformed(evt);
            }
        });

        jTextMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextMessageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jChatPanelLayout = new javax.swing.GroupLayout(jChatPanel);
        jChatPanel.setLayout(jChatPanelLayout);
        jChatPanelLayout.setHorizontalGroup(
            jChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jChatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSend)
                .addContainerGap())
        );
        jChatPanelLayout.setVerticalGroup(
            jChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jChatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextMessage)
                    .addComponent(jSend, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("User online");

        jUserList.setLayout(new javax.swing.BoxLayout(jUserList, javax.swing.BoxLayout.PAGE_AXIS));

        jChatArea.setEditable(false);
        jChatArea.setFocusable(false);
        jScrollPane2.setViewportView(jChatArea);

        jLogout.setText("Logout");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jChatPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Title, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jUserList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 198, Short.MAX_VALUE))
                            .addComponent(jLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Title, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(12, 12, 12)
                        .addComponent(jUserList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 321, Short.MAX_VALUE)
                        .addComponent(jLogout))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jChatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextMessageActionPerformed

    }//GEN-LAST:event_jTextMessageActionPerformed

    private void jSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSendActionPerformed

    }//GEN-LAST:event_jSendActionPerformed

    public void resetUserList() {
        jUserList.removeAll();
        jUserList.validate();
        jUserList.repaint();
    }

    public void addUserList(String name, Icon image) {
        UserItem item = new UserItem(name, image);
        jUserList.add(item);
        jUserList.validate();
        jUserList.repaint();
    }

    public void appendMessage(String message, int align) throws BadLocationException {
        String[] words = message.split(" ");
        SimpleAttributeSet alignment;
        switch (align) {
            case 0 -> alignment = alignLeft;
            case 1 -> alignment = alignCenter;
            case 2 -> alignment = alignRight;
            default -> throw new AssertionError();
        }
        int line = 0;

        for (String word : words) {
            if (line + word.length() + 1 > 60) {
                document.insertString(document.getLength(), "\n", null);
                line = 0;
            }

            document.insertString(document.getLength(), word + " ", null);
            line += word.length() + 1;
        }

        document.insertString(document.getLength(), "\n", null);

        int start = document.getLength() - (message.length() + 1);
        int end = document.getLength() - 1;

        document.setParagraphAttributes(start, end, alignment, false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel Title;
    public javax.swing.JTextPane jChatArea;
    private javax.swing.JPanel jChatPanel;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JButton jLogout;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JButton jSend;
    public javax.swing.JTextField jTextMessage;
    public javax.swing.JLayeredPane jUserList;
    // End of variables declaration//GEN-END:variables

}
