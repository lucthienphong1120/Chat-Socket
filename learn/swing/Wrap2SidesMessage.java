package learn.swing;


import javax.swing.*;
import javax.swing.text.*;

public class Wrap2SidesMessage {

    private JTextPane chatPane;
    private StyledDocument document;
    private SimpleAttributeSet leftAlignment;
    private SimpleAttributeSet rightAlignment;

    public Wrap2SidesMessage() {
        JFrame frame = new JFrame("Chat App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        chatPane = new JTextPane();
        chatPane.setEditable(false);

        document = chatPane.getStyledDocument();

        leftAlignment = new SimpleAttributeSet();
        StyleConstants.setAlignment(leftAlignment, StyleConstants.ALIGN_LEFT);

        rightAlignment = new SimpleAttributeSet();
        StyleConstants.setAlignment(rightAlignment, StyleConstants.ALIGN_RIGHT);

        frame.add(new JScrollPane(chatPane));
        frame.setVisible(true);
    }

    private void appendMessage(String sender, String message, boolean alignRight) {
        try {
            document.insertString(document.getLength(), sender + ": ", null);

            String[] words = message.split(" ");
            int lineLength = 0;

            for (String word : words) {
                if (lineLength + word.length() + 1 > 60) {
                    document.insertString(document.getLength(), "\n", null);
                    lineLength = 0;
                }

                document.insertString(document.getLength(), word + " ", null);
                lineLength += word.length() + 1;
            }

            document.insertString(document.getLength(), "\n", null);

            int start = document.getLength() - (sender.length() + message.length() + 2);
            int end = document.getLength() - 1;

            SimpleAttributeSet alignment = alignRight ? rightAlignment : leftAlignment;
            document.setParagraphAttributes(start, end, alignment, false);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String sender, String message) {
        appendMessage(sender, message, true);
    }

    public void receiveMessage(String sender, String message) {
        appendMessage(sender, message, false);
    }

    public static void main(String[] args) {
        Wrap2SidesMessage chatApp = new Wrap2SidesMessage();
        chatApp.receiveMessage("B", "xin chào dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài");
        chatApp.sendMessage("A", "abc dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài");
        chatApp.receiveMessage("C", "123 dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài");
        chatApp.receiveMessage("B", "hello dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài");
        chatApp.sendMessage("A", "alo dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài");
        chatApp.receiveMessage("B", "hello dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài dài");
        chatApp.sendMessage("A", "123 dài dài");
    }
}
