package Forms;
//کتابخانه های استفاده شده
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.*;
// کلاس ویرایشگر متن
public class TextEditor{
    // مسیر فایل
    private String Path;
    // نام فایل
    private String FileName;
    // شی از کلاس زیر برای بازگردانی اطلاعات
    private UndoManager undoManager;
    //تابع سازنده
    public TextEditor(){
        Path = FileName = "";
        CreatePopupMenu();
        undoredo();
    }
    //اشیای مربوط به فرم
    private JPanel panelTextEditor;
    private JPanel panel;
    private JTextArea textArea;
    private JScrollPane scrollpane;
    private JPopupMenu TextAreaPopupmenu;
    //منو باری راست کلیک
    private void CreatePopupMenu(){
        TextAreaPopupmenu = new JPopupMenu();
        textArea = new JTextArea();
        scrollpane.setViewportView(textArea);
        // عملیات ها برای کپی
        JMenuItem item1 = new JMenuItem("Copy");
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(textArea.getSelectedText() != null) {
                    StringSelection stringSelection = new StringSelection(textArea.getSelectedText().toString());
                    Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clpbrd.setContents(stringSelection, null);
                }
            }
        });
        TextAreaPopupmenu.add(item1);
        // عملیات ها برای برش
        JMenuItem item2 = new JMenuItem("Cut");
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(textArea.getSelectedText() != null){
                    StringSelection stringSelection = new StringSelection(textArea.getSelectedText().toString());
                    Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
                    clpbrd.setContents (stringSelection, null);
                    textArea.replaceSelection("");
                }
            }
        });
        TextAreaPopupmenu.add(item2);
        // عملیات ها برای گذاشتن
        JMenuItem item3 = new JMenuItem("Paste");
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    textArea.replaceSelection("");
                    textArea.insert((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor),textArea.getCaretPosition());
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        TextAreaPopupmenu.add(item3);
        //textArea.setComponentPopupMenu(TextAreaPopupmenu);
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1)
                    TextAreaPopupmenu.show(textArea,e.getX(),e.getY());
            }
        });
        textArea.add(TextAreaPopupmenu);
    }
    // این تابع پنل را برای ست کردن منو نوار وظیفه به تنابع main میفرستد
    public JPanel GetPanelForm(){
        return this.panelTextEditor;
    }
    //این تابع هم منو نوار وظیفه را طراحی کرده و باز میگرداند
    public JMenuBar CreateMenubar(){
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");
        JMenuItem item1 = new JMenuItem("Save");
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Save();
            }
        });
        JMenuItem item2 = new JMenuItem("Save As");
        item2.setMnemonic(KeyEvent.VK_N);
        KeyStroke keyStrokeToOpen0 = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
        item2.setAccelerator(keyStrokeToOpen0);
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SaveAs();
            }
        });
        JMenuItem item3 = new JMenuItem("new Windows");
        item3.setMnemonic(KeyEvent.VK_N);
        KeyStroke keyStrokeToOpen = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
        item3.setAccelerator(keyStrokeToOpen);
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                NewWindows();
            }
        });
        JMenuItem item4 = new JMenuItem("open file");
        item4.setMnemonic(KeyEvent.VK_O);
        KeyStroke keyStrokeToOpen1 = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
        item4.setAccelerator(keyStrokeToOpen1);
        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                OpenFile();
            }
        });
        JMenuItem item5 = new JMenuItem("_________");
        item5.setEnabled(false);
        JMenuItem item6 = new JMenuItem("Exit");
        item6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(1);
            }
        });
        menu.add(item1);menu.add(item2);
        menu.add(item3);menu.add(item4);
        menu.add(item5);menu.add(item6);
        menu.setSize(50,50);

        JMenu menu1 = new JMenu("Font Size");
        JMenuItem menuItem11 = new JMenuItem("Small");
        menuItem11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FontSmall();
            }
        });
        JMenuItem menuItem12 = new JMenuItem("Normal");
        menuItem12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FontMedium();
            }
        });
        JMenuItem menuItem13= new JMenuItem("Large");
        menuItem13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FontBig();
            }
        });
        menu1.add(menuItem11);menu1.add(menuItem12);menu1.add(menuItem13);

        menuBar.add(menu);
        menuBar.add(menu1);
        return menuBar;
    }
    //تابع ذخیره اطلاعات
    private void Save(){
        if(Path == "" || Path.isBlank() || Path.isEmpty()){
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            chooser.setDialogTitle("Browse the folder to process");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(panelTextEditor) == JFileChooser.APPROVE_OPTION) {
                Path = chooser.getSelectedFile().toString();
            } else {
                JOptionPane.showMessageDialog(null, "No Selection ","Errorr",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        int res = JOptionPane.showConfirmDialog(null,"Do you want to save?","Message",JOptionPane.YES_NO_OPTION);
        if(res == 0){
            String TextAll = textArea.getText();
            try {
                if(FileName == "" || FileName.isBlank() || FileName.isEmpty())
                {
                    FileName = JOptionPane.showInputDialog(null,"Enter the name File:");
                    FileName += ".txt";
                }
                File f = new File(Path+"/"+FileName);
                try {
                    f.delete();
                }
                catch (Exception e){
                    System.out.println(e.getMessage().toString());                }
                FileWriter fwriter = new FileWriter("/"+Path+"/"+FileName);
                fwriter.write(TextAll);
                fwriter.close();
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Cant Save.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        else
            return;
    }
    //تابع ذخیره اطلاعات
    private void SaveAs(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        chooser.setDialogTitle("Browse the folder to process");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(panelTextEditor) == JFileChooser.APPROVE_OPTION) {
            Path = chooser.getSelectedFile().toString();
            int res = JOptionPane.showConfirmDialog(null,"Do you want to save?","Message",JOptionPane.YES_NO_OPTION);
            if(res == 0){
                String TextAll = textArea.getText();
                try {
                    FileName = JOptionPane.showInputDialog(null,"Enter the name File:");
                    FileName += ".txt";
                    FileWriter fwriter = new FileWriter("/"+Path+"/"+FileName);
                    fwriter.write(TextAll);
                    fwriter.close();
                }catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Cant Save.","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            else
                return;
        } else {
            JOptionPane.showMessageDialog(null, "No Selection ","Errorr",JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    //تابع برای ایجاد صفحه جدید
    private void NewWindows(){
        JFrame frame = new JFrame("TextEditor");
        TextEditor textEditor = new TextEditor();
        frame.setSize(400,400);
        frame.setLocation(100,100);
        frame.setJMenuBar(textEditor.CreateMenubar());
        frame.setContentPane(textEditor.GetPanelForm());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
    //تابع برای بازکردن فایل متنی
    private void OpenFile(){
        if(!(textArea.getText().isEmpty() || textArea.getText().isBlank() || textArea.getText() == ""))
            Save();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(panelTextEditor);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            Path = selectedFile.getParent();
            FileName = (selectedFile.getName());
            try {
                FileReader freader = new FileReader(selectedFile.getAbsolutePath());
                int i = 0;
                String txtall="";
                textArea.setText("");
                while ((i=freader.read())!=-1){
                    txtall += String.valueOf((char) i);
                }

                txtall.replaceAll("\\r\\n","\r\n");
                textArea.setText(txtall);
                freader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //برای تنظیمات اندازه قلم
    private void FontSmall(){
        Font font = textArea.getFont();
        float size = 12;
        textArea.setFont(font.deriveFont(size));
    }
    private void FontMedium(){
        Font font = textArea.getFont();
        float size = 16;
        textArea.setFont(font.deriveFont(size));
    }
    private void FontBig(){
        Font font = textArea.getFont();
        float size = 20;
        textArea.setFont(font.deriveFont(size));
    }
    //برای پیاده سازی undo & Redo
    private void undoredo(){
        undoManager = new UndoManager();
        Document doc = textArea.getDocument();
        doc.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {

                undoManager.addEdit(e.getEdit());

            }
        });

        InputMap im = textArea.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = textArea.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");

        am.put("Undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                } catch (CannotUndoException exp) {
                    exp.printStackTrace();
                }
            }
        });
        am.put("Redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (undoManager.canRedo()) {
                        undoManager.redo();
                    }
                } catch (CannotUndoException exp) {
                    exp.printStackTrace();
                }
            }
        });
    }
}
/*if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    e.consume();
                }*/