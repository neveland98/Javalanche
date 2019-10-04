package javalanchecodeeditor;

import java.awt.BorderLayout;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Paths;
import javax.swing.text.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileSystemView;

public class JavalancheCodeEditor extends JFrame implements ActionListener {
    JTextArea text;
    JFrame screen;
    File currDirectory;

    JavalancheCodeEditor()
    {
        JButton newProject, execute, compile;
        JMenuBar toolbar;
        JMenu proMenu, fiMenu;
        JMenuItem openProject, createProject, saveProject, closeProject;
        JMenuItem openFile, createFile, closeFile, editFile, saveFile, removeFile;
        JTabbedPane openFiles;
        JPanel file, project;


        screen = new JFrame("Javalanche Editor");
        openFiles = new JTabbedPane();
        toolbar = new JMenuBar();
        file = new JPanel();
        project = new JPanel();

        JFileChooser projectView = new JFileChooser(FileSystemView.getFileSystemView());
        projectView.setDialogTitle("Project");
        projectView.setControlButtonsAreShown(false);
        project.add(projectView);

        text = new JTextArea();
        file.add(text);

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
        execute.addActionListener(this);

        toolbar.add(proMenu);
        toolbar.add(fiMenu);
        toolbar.add(compile);
        toolbar.add(execute);

        screen.setJMenuBar(toolbar);
        screen.setSize(400,500);
        screen.setLayout(new BorderLayout());
        screen.add(project, BorderLayout.LINE_START);
        screen.add(text, BorderLayout.CENTER);
        screen.setExtendedState(JFrame.MAXIMIZED_BOTH);

        screen.setVisible(true);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed (ActionEvent e)
    {
        String s = e.getActionCommand();

        if (s.equals("Open Project")) {

            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Open Project");
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setAcceptAllFileFilterUsed(false);

            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    currDirectory = new File(fc.getSelectedFile().getAbsolutePath());
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(screen, evt.getMessage());
                }
            }
        }
        else if (s.equals("Create Project")) {
            JFileChooser fs = new JFileChooser(FileSystemView.getFileSystemView());
            JButton create = new JButton(), enter = new JButton();
            fs.setDialogTitle("Choose Project Folder");
            fs.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            String proName = JOptionPane.showInputDialog(screen, "Enter Project Name: ");


            if(fs.showSaveDialog(create) ==JFileChooser.APPROVE_OPTION)
            {
                if(new File(fs.getSelectedFile().getAbsolutePath() + "//"+proName+"//lib").mkdirs())
                {
                    currDirectory = new File(fs.getSelectedFile().getAbsolutePath() + "//"+proName);

                    File a = new File(fs.getSelectedFile().getAbsolutePath()+ "//"+proName+"//main.java");

                    try {
                        if(a.createNewFile())
                        {
                            JOptionPane.showMessageDialog(screen, "Project '"+proName+"' Created!");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(JavalancheCodeEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }

        else if (s.equals("Close Project")) {
            currDirectory = null;
            text.setText("");
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
            text.setText("");

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
            text.setText("<New File>");
        }
        else if (s.equals("Close File")) {
            // ask if want to save
            text.setText("");
        }
        else if (s.equals("Edit File")) {
            // edit file
        }
        else if (s.equals("Save File")) {
            JFileChooser f = new JFileChooser();
            int r = f.showSaveDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {

                File fi = new File(f.getSelectedFile().getAbsolutePath()+ ".java");

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
