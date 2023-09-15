import Forms.TextEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("TextEditor");
        TextEditor textEditor = new TextEditor();
        frame.setSize(400,400);
        frame.setLocation(100,100);
        frame.setJMenuBar(textEditor.CreateMenubar());
        frame.setContentPane(textEditor.GetPanelForm());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
