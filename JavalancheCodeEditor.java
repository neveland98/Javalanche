package javalanchecodeeditor;
import java.awt.BorderLayout;

import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Paths;
import javax.swing.text.*;

import jsyntaxpane.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileSystemView;

public class JavalancheCodeEditor extends JFrame implements ActionListener {
    JEditorPane text;
    JFrame screen;
    File currDirectory;
    File currFile;

    JavalancheCodeEditor()
    {
        JButton newProject, execute, compile;
        JMenuBar toolbar;
        JMenu proMenu, fiMenu;
        JMenuItem openProject, createProject, saveProject, closeProject;
        JMenuItem openFile, createFile, closeFile, editFile, saveFile, removeFile;
        //JTabbedPane openFiles;
        JPanel file, project;


        screen = new JFrame("Javalanche Editor");
        //openFiles = new JTabbedPane();
        toolbar = new JMenuBar();
        file = new JPanel();
        project = new JPanel();

        //JFileChooser projectView = new JFileChooser(FileSystemView.getFileSystemView());
        //projectView.setDialogTitle("Project");
        //projectView.setControlButtonsAreShown(false);
        //project.add(projectView);

        text = new JEditorPane();
        DefaultSyntaxKit.initKit();
        text.setContentType("text/java");
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

        JScrollPane scrollPane = new JScrollPane(text);
        screen.setJMenuBar(toolbar);
        screen.setSize(400,500);
        screen.setLayout(new BorderLayout());
        screen.add(project, BorderLayout.LINE_START);
        screen.add(scrollPane, BorderLayout.CENTER);
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
                    text.setText("");
                    File open = new File(currDirectory+"//main.java");
                    currFile = open;
                    if(open.exists()) {
                        Document doc = text.getDocument();
                        FileReader fr = new FileReader(open.getPath());
                        Scanner scan = new Scanner(fr);
                        while (scan.hasNext()) {
                        	doc.insertString(doc.getLength(), scan.nextLine() + '\n', null);
                        }
                        scan.close();
                        fr.close();
                        JOptionPane.showMessageDialog(screen, "Project Opened!");
                    }
                    else {
                        text.setText("<Create File>");
                    }
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
                    currFile = a;

                    try {
                        BufferedWriter w = new BufferedWriter(new FileWriter(a));
                        w.write("<Main File>");
                        w.close();

                        JOptionPane.showMessageDialog(screen, "Project '"+proName+"' Created!");
                    } catch (IOException ex) {
                        Logger.getLogger(JavalancheCodeEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }

        else if (s.equals("Close Project")) {
            currDirectory = null;
            currFile = null;
            text.setText("");
            JOptionPane.showMessageDialog(screen, "Project Closed!");
        }
        else if (s.equals("Save Project")) {    // needs fixing
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
            if (currDirectory != null) {
                f.setCurrentDirectory(currDirectory);
            }
            int r = f.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                File fi = new File(f.getSelectedFile().getAbsolutePath());
                currFile = fi;
                text.setText("");
                try {
                    Document doc = text.getDocument();
                    File open = f.getSelectedFile();
                    FileReader fr = new FileReader(open.getPath());
                    Scanner scan = new Scanner(fr);
                    while (scan.hasNext()) {
                    	doc.insertString(doc.getLength(), scan.nextLine() + '\n', null);
                    	
                    }
                    scan.close();
                }                 
                catch (Exception evt) {
                     JOptionPane.showMessageDialog(screen, evt.getMessage());
                }
            }
        }
        else if (s.equals("Create File")) {
            int r = JOptionPane.showConfirmDialog(null, "Are you sure you want to close current file?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (r == JOptionPane.YES_OPTION) {
                currFile = null;
                saveFile(currFile);
                text.setText("<New File>");
            }
        }
        else if (s.equals("Close File")) {
            int r = JOptionPane.showConfirmDialog(null, "Do you want to save?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (r == JOptionPane.YES_OPTION) {
                saveFile(currFile);
            }
            currFile = null;
            text.setText("");
        }
        else if (s.equals("Edit File")) {
            // edit file
        }
        else if (s.equals("Save File")) {
            saveFile(currFile);
        }
        else if (s.equals("Remove File")) {
            int r = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (r == JOptionPane.YES_OPTION) {
                File f = currFile;
                if (f.delete()) {
                    currFile = null;
                    text.setText("");
                    JOptionPane.showMessageDialog(screen, "File removed from project!");
                } else {
                    JOptionPane.showMessageDialog(screen, "Error");
                }
            }
        }
        else if (s.equals("Execute")){
            // execute
        	Scanner s1;
        	try {
        	s1 = new Scanner(currFile);
        	ArrayList<String> list = new ArrayList<String>();
        	        while (s1.hasNext()){
        	           list.add(s1.next());
        	        }
        	        ArrayList<String> command = new ArrayList<String>();
        	        command = list;
        	        ProcessBuilder pb = new ProcessBuilder(command);
        	        Process a = pb.start();
        	        OutputStream os = a.getOutputStream();
        	        PrintStream ps = new PrintStream(os);
        	        ps.println("success");
        	        ps.flush();
        	        BufferedReader br = new BufferedReader(new InputStreamReader(a.getInputStream()));
        	       String cOutput;
        	       while((cOutput = br.readLine()) != null) {
        	    	   System.out.println(cOutput);
        	       }
        	        s1.close();
        	} catch (FileNotFoundException e1) {
        	e1.printStackTrace();
        	} catch (IOException e1) {
        	e1.printStackTrace();
        	}
        }
        else if (s.equals("Compile")) {
            // compile
        }
    }
    public void saveFile(File f) {
        if (f == null) {
            JFileChooser fc = new JFileChooser();
            if (currDirectory != null) {
                fc.setCurrentDirectory(currDirectory);
            }
            int r = fc.showSaveDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {

                File fi = new File(fc.getSelectedFile().getAbsolutePath()+ ".java");
                try {
                    FileWriter fw = new FileWriter(fi, false);
                    BufferedWriter w = new BufferedWriter(fw);

                    w.write(text.getText());

                    w.flush();
                    w.close();
                    JOptionPane.showMessageDialog(screen, "File Saved!");
                }
                catch (Exception evt) {
                    JOptionPane.showMessageDialog(screen, evt.getMessage());
                }
                currFile = fi;
            }
        }
        else {
            try {
                FileWriter fw = new FileWriter(f, false);
                BufferedWriter w = new BufferedWriter(fw);

                w.write(text.getText());

                w.flush();
                w.close();
                JOptionPane.showMessageDialog(screen, "File Saved!");
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(screen, evt.getMessage());
            }
        }
    }
    public static void main(String args[])
    {
        JavalancheCodeEditor e = new JavalancheCodeEditor();
    }
}
