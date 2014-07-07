import java.awt.*;
import javax.swing.*;
 
public class EncryptorGUI {
    private JFrame frame;
    private String[] name;
     
    public EncryptorGUI() {
        frame = new JFrame();
        String n[] = {"Input", 
                      "Output", 
                      "hint...", 
                      "New", 
                      "Load", 
                      "Save", 
                      "Encode", 
                      "Decode", 
                      "Clear", 
                      "Copy"};
        name = n;
    }
     
    public void run() {
        frame.setSize(600, 160);
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          
        JLabel n0 = new JLabel(name[0]);
        GridBagConstraints c0 = new GridBagConstraints();
        c0.gridx = 0;
        c0.gridy = 0;
        c0.gridwidth = 1;
        c0.gridheight = 1;
        c0.weightx = 0;
        c0.weighty = 0;
        c0.fill = GridBagConstraints.NONE;
        c0.anchor = GridBagConstraints.WEST;
        frame.add(n0, c0);
         
        JLabel n1 = new JLabel(name[1]);
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 1;
        c1.gridwidth = 1;
        c1.gridheight = 1;
        c1.weightx = 0;
        c1.weighty = 0;
        c1.fill = GridBagConstraints.NONE;
        c1.anchor = GridBagConstraints.WEST;
        frame.add(n1, c1);
         
        JLabel n2 = new JLabel(name[2]);
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 3;
        c2.gridwidth = 7;
        c2.gridheight = 1;
        c2.weightx = 0;
        c2.weighty = 0;
        c2.fill = GridBagConstraints.BOTH;
        c2.anchor = GridBagConstraints.WEST;
        frame.add(n2, c2);
 
        JTextField n3 = new JTextField();
        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 1;
        c3.gridy = 0;
        c3.gridwidth = 6;
        c3.gridheight = 1;
        c3.weightx = 0;
        c3.weighty = 0;
        c3.fill = GridBagConstraints.BOTH;
        c3.anchor = GridBagConstraints.WEST;
        frame.add(n3, c3);
         
        JTextField n4 = new JTextField();
        GridBagConstraints c4 = new GridBagConstraints();
        c4.gridx = 1;
        c4.gridy = 1;
        c4.gridwidth = 6;
        c4.gridheight = 1;
        c4.weightx = 0;
        c4.weighty = 0;
        c4.fill = GridBagConstraints.BOTH;
        c4.anchor = GridBagConstraints.WEST;
        frame.add(n4, c4);
                
        for (int i = 0; i < 7; i++) {
            JButton n5 = new JButton(name[i + 3]);
            GridBagConstraints c5 = new GridBagConstraints();
            c5.gridx = i;
            c5.gridy = 2;
            c5.gridwidth = 1;
            c5.gridheight = 1;
            c5.weightx = 0;
            c5.weighty = 0;
            c5.fill = GridBagConstraints.BOTH;
            c5.anchor = GridBagConstraints.CENTER;
            frame.add(n5, c5);
        }
         
        frame.setVisible(true);
    }
}