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

       screen.add(text);
       screen.setSize(400,500);
       screen.setLayout(new BorderLayout());
       screen.getContentPane().add(toolbar, BorderLayout.PAGE_START);
       screen.setVisible(true);
       
       screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    // when button pressed
    public void actionPerformed (ActionEvent e)
    {
       String s = e.getActionCommand();

       if (s.equals("Open Project")) {
          final JFileChooser fc = new JFileChooser();
//                fc.setCurrentDirectory(new java.io.File("."));
//                fc.setDialogTitle("Files");
//                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          fc.showOpenDialog(null);
          File f = fc.getSelectedFile();
          String filename = f.getAbsolutePath();
          try{
             FileReader reader = new FileReader(filename);
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
       else if (s.equals("Create Project")) {
          JFileChooser fs = new JFileChooser();
          FileFilter f = new FileNameExtensionFilter(".txt" , "text file");
          fs.setCurrentDirectory(new java.io.File("."));
          fs.setDialogTitle("Save a File");
          fs.setFileFilter(f);
          int result = fs.showSaveDialog(null);
          if (result == JFileChooser.APPROVE_OPTION){

          }
       }
       else if (s.equals("Close Project")) {
          // close project
       }
       else if (s.equals("Save Project")) {
          String txt = text.getText();
          JFileChooser n = new JFileChooser(".");
          n.setDialogTitle("Create");
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
                Desktop.getDesktop().open(file);
             } catch (FileNotFoundException ex) {
                ex.printStackTrace();
             } catch (IOException ex) {
                ex.printStackTrace();
             }
          }
       }
       else if (s.equals("Open File")) {
          // open file
       }
       else if (s.equals("Create File")) {
          // create file
       }
       else if (s.equals("Edit File")) {
          // edit file
       }
       else if (s.equals("Save File")) {
          // save file
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
