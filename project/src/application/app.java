package application;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class app {
    private JTextField textField1;
    private JPanel textEditor;
    private JButton button1;
    private JButton Create;
    private JButton saveProjectButton;


    public app() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new java.io.File("."));
                fc.setDialogTitle("Files");
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (fc.showOpenDialog(button1) == JFileChooser.APPROVE_OPTION){

                }
            }
        });
        saveProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fs = new JFileChooser();
                FileFilter f = new FileNameExtensionFilter(".txt" , "text file");
                fs.setCurrentDirectory(new java.io.File("."));
                fs.setDialogTitle("Save a File");
                fs.setFileFilter(f);
                int result = fs.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION){

                }

            }
        });
        saveProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txt = textField1.getText();
                JFileChooser n = new JFileChooser(".");
                int retval = n.showSaveDialog(null);
                if (retval == JFileChooser.APPROVE_OPTION){
                    File file = n.getSelectedFile();
                    if (file == null){
                        return;
                    }
                    if(!file.getName().toLowerCase().endsWith("txt")){
                        file = new File(file.getParentFile(), file.getName() + ".txt");
                    }
                    try {
                        textField1.write(new OutputStreamWriter(new FileOutputStream(file) , "utf-8"));
                        Desktop.getDesktop().open(file);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("app");
        frame.setContentPane(new app().textEditor);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
