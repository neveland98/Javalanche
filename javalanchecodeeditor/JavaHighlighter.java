package javalanchecodeeditor;

import java.lang.String;

package net.andreinc.colortextpane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaHighlighter extends JFrame {

    public static final Color DEFAULT_KEYWORD_COLOR = Color.blue;

    public static final String[] JAVA_KEYWORDS = new String[] { "abstract",
            "assert", "boolean", "break", "byte", "case", "catch", "char",
            "class", "const", "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float", "for", "goto",
            "if", "implements", "import", "instanceof", "int", "long",
            "native", "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super", "switch",
            "synchronized", "this", "throw", "throws", "transient", "try",
            "void", "volatile", "while", "false", "null", "true" ,"abstract", "assert", "boolean",
            "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "extends", "false",
            "final", "finally", "float", "for", "goto", "if", "implements",
            "import", "instanceof", "int", "System", "out", "print()", "println()",
            "new", "null", "package", "private", "protected", "public", "interface",
            "long", "native", "return", "short", "static", "strictfp", "super", "switch",
            "synchronized", "this", "throw", "throws", "transient", "true",
            "try", "void", "volatile", "while", "String"};
    public static String JAVA_KEYWORDS_REGEX;

    static {
        StringBuilder buff = new StringBuilder("");
        buff.append("(");
        for (String keyword : JAVA_KEYWORDS) {
            buff.append("\\b").append(keyword).append("\\b").append("|");
        }
        buff.deleteCharAt(buff.length() - 1);
        buff.append(")");
        JAVA_KEYWORDS_REGEX = buff.toString();
    }

    private JPanel contentPane;
    private JToolBar toolBar;
    private JTextPane textEditor;
    private JScrollPane textEditorScrollPane;
    private JButton highLightBtn;
    private StyledDocument textEditorDoc;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JavaHighlighter frame = new JavaHighlighter();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public JavaHighlighter() {
        setTitle("Java Syntax Highlighter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        toolBar = new JToolBar();
        contentPane.add(toolBar, BorderLayout.NORTH);

        highLightBtn = new JButton("Highlight Syntax");
        highLightBtn.addActionListener(highLightBtnClick());
        toolBar.add(highLightBtn);

        textEditor = new JTextPane();
        textEditorDoc = textEditor.getStyledDocument();
        textEditor.getDocument().putProperty(
                DefaultEditorKit.EndOfLineStringProperty, "\n");
        textEditorScrollPane = new JScrollPane(textEditor);
        textEditorScrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        contentPane.add(textEditorScrollPane, BorderLayout.CENTER);
    }

    public ActionListener highLightBtnClick() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                clearTextColors();
                Pattern pattern = Pattern.compile(JAVA_KEYWORDS_REGEX);
                System.out.println(pattern.pattern());
                Matcher match = pattern.matcher(textEditor.getText());
                while (match.find()) {
                    updateTextColor(match.start(), match.end() - match.start());
                }

            }
        };
    }

    public void updateTextColor(int offset, int length, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground, c);
        textEditorDoc.setCharacterAttributes(offset, length, aset, true);
    }

    public void clearTextColors() {
        updateTextColor(0, textEditor.getText().length(), Color.BLACK);
    }

    public void updateTextColor(int offset, int length) {
        updateTextColor(offset, length, DEFAULT_KEYWORD_COLOR);
    }
}