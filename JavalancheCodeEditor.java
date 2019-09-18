package javalanchecodeeditor;

import java.awt.BorderLayout;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.*;
import javax.swing.text.*;
import java.util.Scanner;

public class JavalancheCodeEditor extends JFrame implements ActionListener {
   JTextArea text;
   JFrame screen;

   JavalancheCodeEditor()
   {
       JButton newProject, execute, compile;
       JMenuBar toolbar;
       JMenu proMenu, fiMenu;
       JMenuItem openProject, createProject, saveProject, closeProject;
       JMenuItem openFile, createFile, closeFile, editFile, saveFile, removeFile;
        
       screen = new JFrame("Javalanche Editor");
       toolbar = new JMenuBar();
       text = new JTextArea();
       
       proMenu = new JMenu("Project");
       openProject = new JMenuItem("Open Project");
       createProject = new JMenuItem("Create Project");
       saveProject = new JMenuItem("Save Project");
       closeProject = new JMenuItem("Close Project");

       fiMenu = new JMenu("File");
       openFile = new JMenuItem("Open File");
       createFile = new JMenuItem("Create File");
       closeFile = new JMenuItem("Close File");
       editFile = new JMenuItem("Edit File");
       saveFile = new JMenuItem("Save File");
       removeFile = new JMenuItem("Remove File");


       openProject.addActionListener(this);
       createProject.addActionListener(this);
       saveProject.addActionListener(this);
       closeProject.addActionListener(this);

       openFile.addActionListener(this);
       createFile.addActionListener(this);
       closeFile.addActionListener(this);
       editFile.addActionListener(this);
       saveFile.addActionListener(this);
       removeFile.addActionListener(this);

       proMenu.add(openProject);
       proMenu.add(createProject);
       proMenu.add(saveProject);
       proMenu.add(closeProject);

       fiMenu.add(openFile);
       fiMenu.add(createFile);
       fiMenu.add(closeFile);
       fiMenu.add(editFile);
       fiMenu.add(saveFile);
       fiMenu.add(removeFile);

       compile = new JButton("Compile");
       execute = new JButton("Execute");

       compile.addActionListener(this);
       compile.addActionListener(this);
       
       toolbar.add(proMenu);
       toolbar.add(fiMenu);
       toolbar.add(compile);
       toolbar.add(execute);

       screen.setContentPane(text);
       screen.setJMenuBar(toolbar);
       screen.setSize(400,500);
       screen.setLayout(new BorderLayout());
       screen.setVisible(true);
       screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed (ActionEvent e)
    {
       String s = e.getActionCommand();

       if (s.equals("Open Project")) {
          final JFileChooser fc = new JFileChooser();
//                fc.setCurrentDirectory(new java.io.File("."));
//                fc.setDialogTitle("Files");
//                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          int r = fc.showOpenDialog(null);
          if (r == JFileChooser.APPROVE_OPTION){
            File f = new File (fc.getSelectedFile().getAbsolutePath());
          try{
             FileReader reader = new FileReader(f);
             BufferedReader br = new BufferedReader(reader);
             text.read(br,null);
             br.close();
             text.requestFocus();
          } catch (FileNotFoundException ex) {
             ex.printStackTrace();
          } catch (IOException ex) {
             ex.printStackTrace();
          }
          }
       }
       else if (s.equals("Create Project")) {
           JFileChooser fs = new JFileChooser();
           int result = fs.showSaveDialog(null);
           if (result == JFileChooser.APPROVE_OPTION){
               FileFilter f = new FileNameExtensionFilter(".txt" , "text file");
                fs.setCurrentDirectory(new java.io.File("."));
                fs.setDialogTitle("Create");
                fs.setFileFilter(f);
          }
       }
       else if (s.equals("Close Project")) {
          // close project
       }
       else if (s.equals("Save Project")) {
          String txt = text.getText();
          JFileChooser n = new JFileChooser(".");
          n.setDialogTitle("Save");
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
                text.write(new OutputStreamWriter(new FileOutputStream(file) , "utf-8"));
             } catch (FileNotFoundException ex) {
                ex.printStackTrace();
             } catch (IOException ex) {
                ex.printStackTrace();
             }
          }
       }
       else if (s.equals("Open File")) {
           JFileChooser f = new JFileChooser();

           int r = f.showOpenDialog(null);

           if (r == JFileChooser.APPROVE_OPTION) {
               File fi = new File(f.getSelectedFile().getAbsolutePath());

               try {
                   File open = f.getSelectedFile();
                   Scanner scan = new Scanner(new FileReader(open.getPath()));
                   while (scan.hasNext()) {
                       text.append(scan.nextLine() + "\n");
                   }
               }
               catch (Exception evt) {
                   JOptionPane.showMessageDialog(screen, evt.getMessage());
               }
           }
       }
       else if (s.equals("Create File")) {
          text.setText("");
       }
       else if (s.equals("Close File")) {
           // close file
       }
       else if (s.equals("Edit File")) {
           // edit file
       }
       else if (s.equals("Save File")) {
           JFileChooser f = new JFileChooser();
           int r = f.showSaveDialog(null);
           if (r == JFileChooser.APPROVE_OPTION) {

               File fi = new File(f.getSelectedFile().getAbsolutePath());

               try {
                   FileWriter fw = new FileWriter(fi, false);
                   BufferedWriter w = new BufferedWriter(fw);

                   w.write(text.getText());

                   w.flush();
                   w.close();
               }
               catch (Exception evt) {
                   JOptionPane.showMessageDialog(screen, evt.getMessage());
               }
           }
       }
       else if (s.equals("Remove File")) {
          // remove file
       }
       else if (s.equals("Execute")){
          // execute
       }
       else if (s.equals("Compile")) {
          // compile
       }
    }
   public static void main(String args[])
   {
      JavalancheCodeEditor e = new JavalancheCodeEditor();
   }
}
