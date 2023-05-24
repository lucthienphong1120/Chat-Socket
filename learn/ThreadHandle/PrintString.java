package learn.ThreadHandle;

import java.io.*;

public class PrintString {

    public static void main(String[] args) {
        OutputStream os = System.out;
        PrintWriter pw = new PrintWriter(os);
        pw.write("Example: ");
        pw.println("This is an example of PrintWrite instead of using System.out");
        pw.write("Done!\n");
        pw.flush();
    }

}
