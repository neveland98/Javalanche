import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class KeywordCounter {

    private static String[] JAVA_KEYWORDS = new String[]{"String",
            "System", "abstract", "assert", "boolean", "break", "byte", "case",
            "catch", "char", "class", "const", "continue", "default", "do",
            "double", "else", "enum", "extends", "false", "final",
            "finally", "float", "for", "goto", "if", "implements",
            "import", "instanceof", "int", "interface", "long", "native",
            "new", "null", "package", "private", "protected", "public", "return", "short",
            "static", "strictfp", "super", "switch", "synchronized", "this", "throw",
            "throws", "transient", "true", "try", "void",
            "volatile", "while"};

    static int countKeywords(File f) throws IOException {
        int numkeys = 0;
        boolean commented = false;
        String line;
        Scanner sc = new Scanner(f);
        while(sc.hasNextLine()) {
            line = sc.nextLine();
            if(line.contains("/*")) {
                commented = true;
            }
            if(!commented) {
                for(String i: line.split("\\s+")) {
                    if(i.equals("//")) {
                        break;
                    }
                    for(String w: JAVA_KEYWORDS) {
                        if(i.equals(w)) {
                            numkeys++;
                        }
                    }
                }
            }
            if(line.contains("*/")) {
                commented = false;
            }
        }
        return numkeys;
    }
}