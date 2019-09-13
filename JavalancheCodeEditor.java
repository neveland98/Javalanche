/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javalanchecodeeditor;

/**
 *
 * @author Trigg
 */

import java.awt.BorderLayout;
import java.io.File;
import javax.swing.*;
import java.awt.*;

public class JavalancheCodeEditor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       JFrame initialScreen;
       JButton newProject, execute, compile;
       JMenuBar toolbar;
       JMenu proMenu, fileMenu;
       JMenuItem open, createNew, saveProject, closeProject;
        
       initialScreen = new JFrame("Javalanche Editor");
       toolbar = new JMenuBar();
       
       proMenu = new JMenu("Project");
       open = new JMenuItem("Open");
       createNew = new JMenuItem("Create New Project");
       saveProject = new JMenuItem("Save Project");
       closeProject = new JMenuItem("Close Project");
       
       proMenu.add(open);
       proMenu.add(createNew);
       proMenu.add(saveProject);
       proMenu.add(closeProject);
       
       
       fileMenu = new JMenu("File");
       compile = new JButton("Compile");
       execute = new JButton("Execute");
       
       
       toolbar.add(proMenu);
       toolbar.add(fileMenu);
       toolbar.add(compile);
       toolbar.add(execute);

       initialScreen.setSize(400,500);
       initialScreen.setLayout(new BorderLayout());
       initialScreen.getContentPane().add(toolbar, BorderLayout.PAGE_START);
       initialScreen.setVisible(true);
       
       initialScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
