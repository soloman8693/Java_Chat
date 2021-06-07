package GUI;

import javax.swing.*;
import java.awt.*;

public class ClientGUI extends JPanel {

    public ClientGUI() {
        this.setLayout(
                new BoxLayout(this, BoxLayout.Y_AXIS)
        );
        JTextArea txtInfo = new JTextArea();

        add(txtInfo);
        add(createHeader());
    }

    private JPanel createHeader(){
        JPanel panel = new JPanel(new GridLayout(1,2));
        JButton btnSend = new JButton("Send");
        JTextField txtPort = new JTextField();

        panel.add(txtPort);
        panel.add(btnSend);

        return panel;
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new ClientGUI(), BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }

}
