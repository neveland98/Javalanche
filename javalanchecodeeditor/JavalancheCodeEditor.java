import jsyntaxpane.DefaultSyntaxKit;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Collections;
import java.util.Vector;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.Document;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.*;


public class JavalancheCodeEditor extends JFrame implements ActionListener {
    private JEditorPane text;
    private JFrame screen;
    private File currDirectory;
    private File currFile;
    private JPanel tree;
    private JLabel keyw;
    private JTabbedPane openFiles;
    private FileSystemTree t;
    private int flag;

    JavalancheCodeEditor()
    {
        JButton execute, compile, kcount;
        JMenuBar toolbar;
        JMenu proMenu, fiMenu;
        JMenuItem openProject, createProject, saveProject, closeProject;
        JMenuItem openFile, createFile, closeFile, saveFile, removeFile;
        JPanel file, project, bottom;

        /* JFileChooser javabin = new JFileChooser();
        javabin.setDialogTitle("Select Java Bin File");
        javabin.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if(javabin.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            Path = javabin.getSelectedFile().getAbsolutePath();
        }*/

        screen = new JFrame("Javalanche Editor");
        openFiles = new JTabbedPane();
        toolbar = new JMenuBar();
        file = new JPanel(new GridLayout(1,1));
        project = new JPanel(new GridLayout(1,1));
        bottom = new JPanel();
        keyw = new JLabel("Number of keywords: ");
        tree = new JPanel(new GridLayout(1,1));
        tree.setVisible(false);

        //JFileChooser projectView = new JFileChooser(FileSystemView.getFileSystemView());
        //projectView.setDialogTitle("Project");
        //projectView.setControlButtonsAreShown(false);
        //project.add(projectView);

        text = new JEditorPane();
        DefaultSyntaxKit.initKit();
        text.setContentType("text/java");

        proMenu = new JMenu("Project");
        openProject = new JMenuItem("Open Project");
        createProject = new JMenuItem("Create Project");
        saveProject = new JMenuItem("Save Project");
        closeProject = new JMenuItem("Close Project");

        fiMenu = new JMenu("File");
        openFile = new JMenuItem("Open File");
        createFile = new JMenuItem("Create File");
        closeFile = new JMenuItem("Close File");
        saveFile = new JMenuItem("Save File");
        removeFile = new JMenuItem("Remove File");


        openProject.addActionListener(this);
        createProject.addActionListener(this);
        saveProject.addActionListener(this);
        closeProject.addActionListener(this);

        openFile.addActionListener(this);
        createFile.addActionListener(this);
        closeFile.addActionListener(this);
        saveFile.addActionListener(this);
        removeFile.addActionListener(this);

        proMenu.add(openProject);
        proMenu.add(createProject);
        proMenu.add(saveProject);
        proMenu.add(closeProject);

        fiMenu.add(openFile);
        fiMenu.add(createFile);
        fiMenu.add(closeFile);
        fiMenu.add(saveFile);
        fiMenu.add(removeFile);

        compile = new JButton("Compile");
        execute = new JButton("Execute");
        kcount = new JButton("Update");

        compile.addActionListener(this);
        execute.addActionListener(this);
        kcount.addActionListener(this);

        toolbar.add(proMenu);
        toolbar.add(fiMenu);
        toolbar.add(compile);
        toolbar.add(execute);

        text.setFont(new Font("Courier New", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(text);
        file.add(scrollPane, BorderLayout.CENTER);
        project.add(openFiles);
        openFiles.addTab("untitled",file);

        bottom.add(kcount);
        bottom.add(keyw);

        screen.setJMenuBar(toolbar);
        screen.setLayout(new BorderLayout());
        screen.add(project, BorderLayout.CENTER);
        screen.add(tree, BorderLayout.WEST);
        screen.add(bottom, BorderLayout.PAGE_END);

        screen.setSize(1000,800);
        screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        screen.setVisible(true);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed (ActionEvent e) {
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
                    File open = new File(currDirectory + "//Main.java");
                    String a = currDirectory.getName();

                    if (open.exists()) {
                        currFile = open;
                        Document doc = text.getDocument();
                        FileReader fr = new FileReader(open.getPath());
                        Scanner scan = new Scanner(fr);
                        while (scan.hasNext()) {
                            doc.insertString(doc.getLength(), scan.nextLine() + '\n', null);
                        }
                        scan.close();
                        fr.close();
                        openFiles.setTitleAt(0, currFile.getName());
                    }
                    else {
                        currFile = null;
                        openFiles.setTitleAt(0, "untitled");
                        text.setText("<Create Main File>");
                    }
                    t = new FileSystemTree(new File(currDirectory.getAbsolutePath()));
                    tree.add(t);
                    tree.setVisible(true);
                    JOptionPane.showMessageDialog(screen, "Project " + a + " opened!");
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(screen, evt.getMessage());
                }
            }
        }
        else if (s.equals("Create Project")) {
            JFileChooser fs = new JFileChooser(FileSystemView.getFileSystemView());
            JButton create = new JButton(); //enter = new JButton();
            fs.setDialogTitle("Choose Project Folder");
            fs.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            String proName = JOptionPane.showInputDialog(screen, "Enter Project Name: ");


            if (fs.showSaveDialog(create) == JFileChooser.APPROVE_OPTION) {
                if (new File(fs.getSelectedFile().getAbsolutePath() + "//" + proName + "//lib").mkdirs()) {
                    currDirectory = new File(fs.getSelectedFile().getAbsolutePath() + "//" + proName);

                    File a = new File(fs.getSelectedFile().getAbsolutePath() + "//" + proName + "//Main.java");
                    currFile = a;

                    try {
                        BufferedWriter w = new BufferedWriter(new FileWriter(a));
                        w.write("public class Main {\n" +
                                "\n" +
                                "    public static void main(String[] args) {\n" +
                                "\t// write your code here\n" +
                                "    }\n" +
                                "}");
                        w.close();
                        text.setText("<Open a file>");
                        t = new FileSystemTree(new File(currDirectory.getAbsolutePath()));
                        tree.add(t);
                        JOptionPane.showMessageDialog(screen, "Project '" + proName + "' created!");
                    } catch (IOException ex) {
                        Logger.getLogger(JavalancheCodeEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            tree.setVisible(true);
        }
        else if (s.equals("Close Project")) {
            if (currDirectory == null) {
                JOptionPane.showMessageDialog(screen, "No project currently opened.");
            }
            else {
                String a = currDirectory.getName();
                int r = JOptionPane.showConfirmDialog(null, "Are you sure you want to close project?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (r == JOptionPane.YES_OPTION) {
                    openFiles.setTitleAt(0, "untitled");
                    currDirectory = null;
                    currFile = null;
                    text.setText("");
                    tree.remove(t);
                    tree.setVisible(false);
                    JOptionPane.showMessageDialog(screen, "Project " + a + " closed!");
                }
            }
        }
        else if (s.equals("Save Project")) {    // needs fixing
            if (currDirectory == null) {
                JOptionPane.showMessageDialog(screen, "No project currently opened.");
            }
            else {
                saveFile(currFile);
                JOptionPane.showMessageDialog(screen, "Project " + currDirectory.getName() + " saved!");
            }
            /*
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
            */
        }
        else if (s.equals("Open File")) {
            int k = 0;
            if (currFile != null) {
                int r = JOptionPane.showConfirmDialog(null, "Are you sure you want to open a different file?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (r == JOptionPane.YES_OPTION) {
                    k = 1;
                }
            }
            if (currFile == null || k == 1) {
                JFileChooser f = new JFileChooser();
                if (currDirectory != null) {
                    f.setCurrentDirectory(currDirectory);
                }
                int r = f.showOpenDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    File fi = new File(f.getSelectedFile().getAbsolutePath());
                    currFile = fi;
                    openFiles.setTitleAt(0, fi.getName());
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
                    } catch (Exception evt) {
                        JOptionPane.showMessageDialog(screen, evt.getMessage());
                    }
                }
            }
        }
        else if (s.equals("Create File")) {
            if (currFile == null) {
                saveFile(currFile);
                text.setText("<New File>");
            }
            else {
                int r = JOptionPane.showConfirmDialog(null, "Are you sure you want to close current file?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (r == JOptionPane.YES_OPTION) {
                    currFile = null;
                    saveFile(currFile);
                    text.setText("<New File>");
                }
            }
        }
        else if (s.equals("Close File")) {
            if (currFile == null) {
                JOptionPane.showMessageDialog(screen, "No file currently opened.");
            }
            else {
                String a = currFile.getName();
                int r = JOptionPane.showConfirmDialog(null, "Do you want to save?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (r == JOptionPane.YES_OPTION) {
                    saveFile(currFile);
                }
                currFile = null;
                openFiles.setTitleAt(0, "untitled");
                keyw.setText("Number of keywords: ");
                text.setText("");
                JOptionPane.showMessageDialog(screen, "File " + a + " closed!");
            }
        }
        else if (s.equals("Save File")) {
            saveFile(currFile);
            JOptionPane.showMessageDialog(screen, "File saved!");
        }
        else if (s.equals("Remove File")) {
            if (currFile == null) {
                JOptionPane.showMessageDialog(screen, "No file currently opened.");
            }
            else {
                String a = currFile.getName();
                int r = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove file?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (r == JOptionPane.YES_OPTION) {
                    File f = currFile;
                    if (f.delete()) {
                        currFile = null;
                        openFiles.setTitleAt(0, "untitled");
                        text.setText("");
                        JOptionPane.showMessageDialog(screen, "File " + a + " removed from project!");
                    } else {
                        JOptionPane.showMessageDialog(screen, "Error");
                    }
                }
            }
        }
        else if (s.equals("Execute")) {
            if (currFile == null) {
                JOptionPane.showMessageDialog(screen, "No file currently opened.");
            }
            else {
                if (flag==1) {
                    try {
                        Runtime.getRuntime().exec("cmd /c start cmd.exe /k \"cd "
                                + currFile.getParentFile().getAbsolutePath() + " && java Main\"");
                        flag = 0;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(screen, "Compile file first.");
                }
            }
        }
        else if (s.equals("Compile")) {
            if (currFile == null) {
                JOptionPane.showMessageDialog(screen, "No file currently opened.");
            }
            else {
                saveFile(currFile);
                File main = new File(currFile.getParentFile().getAbsolutePath() + "//Main.java");
                if (main.exists()) {
                    try {
                        Runtime.getRuntime().exec("cmd /c start cmd.exe /k \"cd "
                                + currFile.getParentFile().getAbsolutePath() + " && javac Main.java\"");
                        flag = 1;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(screen, "No Main.java file found.");
                }
            }
        }
        else if (s.equals("Update")) {
            if (currFile == null) {
                JOptionPane.showMessageDialog(screen, "No file currently opened.");
            }
            else {
                saveFile(currFile);
                try {
                    keyw.setText("Number of keywords: " + KeywordCounter.countKeywords(currFile));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    private void saveFile(File f) {
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

                    openFiles.setTitleAt(0,fi.getName());
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
                openFiles.setTitleAt(0,f.getName());
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(screen, evt.getMessage());
            }
        }
    }
    static class FileSystemTree extends JPanel{
        FileSystemTree(File dir)
        {
            setLayout(new BorderLayout());

            final JTree fTree = new JTree(addNodes(null, dir));

            fTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

            fTree.addTreeSelectionListener(new TreeSelectionListener(){

                @Override
                public void valueChanged(TreeSelectionEvent tse) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) fTree.getLastSelectedPathComponent();

                    if(node == null) {
                        return;
                    }

                    System.out.println("You Chose Node : " + node);
                }
            });

            JScrollPane scrollp = new JScrollPane();
            scrollp.getViewport().add(fTree);
            add(BorderLayout.CENTER, scrollp);


        }
        /*DefaultMutableTreeNode addNode(DefaultMutableTreeNode top, File dir)
        {

        }*/

        DefaultMutableTreeNode addNodes(DefaultMutableTreeNode top, File dir) {
            String thePath = dir.getPath();

            DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(thePath);

            if(top != null) {
                top.add(curDir);
            }

            File temp;
            Vector files = new Vector();
            Vector fullList = new Vector();
            String[] allFiles = dir.list(null);

            assert allFiles != null;
            Collections.addAll(fullList, allFiles);

            Collections.sort(fullList, String.CASE_INSENSITIVE_ORDER);

            for(int i = 0; i < fullList.size(); i++) {
                String curObj = (String) fullList.elementAt(i);
                String newPath;

                if(thePath.equals(".")) {
                    newPath = curObj;
                }
                else {
                    newPath = thePath + File.separator+ curObj;

                }

                if((temp = new File(newPath)).isDirectory()) {
                    addNodes(curDir, temp);
                }
                else {
                    files.addElement(curObj);
                }
            }
            for(int fnum = 0; fnum < files.size(); fnum++) {
                curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
            }
            return curDir;
        }
    }
}
