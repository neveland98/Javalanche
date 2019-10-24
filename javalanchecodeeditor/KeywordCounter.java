package javalanchecodeeditor;
import java.io.*;
import java.util.Scanner;
public class KeywordCounter {
	//attr
	private int keywords;
	//constructor
	KeywordCounter() {keywords = 0;}
	//methods
	int countKeywords(File f) throws IOException {
		int numkeys = 0;
		int search;
		boolean commented = false;
		String line = new String();
		Scanner sc = new Scanner(f);
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			if(line.contains("/*")) {
				commented = true;
			}
			if(!commented) {
				if(line.contains("//")) {
					if(line.indexOf("//") != 0) {
						line = line.substring(0, line.indexOf("//") - 1);
					}
				}
				if(line.contains("if") || line.contains("else") || line.contains("while") || line.contains("for")) {
					numkeys++;
				}
			}
			if(line.contains("*/")) {
				commented = false;
			}

		}
		
		return numkeys;
	}
	
}
