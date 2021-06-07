package GUI;

import javax.swing.*;
import java.awt.*;

public class ServerGUI extends JPanel {

    public ServerGUI() {
        this.setLayout(
                new BoxLayout(this, BoxLayout.Y_AXIS)
        );

        JTextArea txtInfo = new JTextArea();
        add(createHeader());
        add(txtInfo);
    }

    private JPanel createHeader(){
        JPanel panel = new JPanel(new GridLayout(1,2));
        JLabel lblPort = new JLabel("Port");
        JTextField txtPort = new JTextField();

        panel.add(lblPort);
        panel.add(txtPort);

        return panel;
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new ServerGUI(), BorderLayout.CENTER);

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
