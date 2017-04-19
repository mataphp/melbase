

import java.io.*;

public class Test {
	 

	   public static void main(String args[]){
		   File file=new File(args[0]);
		   boolean exists = file.exists();
		   if (!exists) {
			   // It returns false if File or directory does not exist
			   System.out.println("the file or directory "
					   + "you are searching does not exist : " + exists);
			   
		   } else {
		   // It returns true if File or directory exists
			   System.out.println("the file or "
				   + "directory you are searching does exist : " + exists);
		   }
	 }
}
