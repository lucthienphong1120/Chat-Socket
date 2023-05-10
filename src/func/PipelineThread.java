/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package func;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phong
 */
public class PipelineThread {

    // transfer data to pos
    public static void processInput(PipedOutputStream pos) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 10; i++) {
            int x = Integer.parseInt(sc.nextLine());
            pos.write(x);
            pos.flush(); // push pipeline
            Thread.sleep(500);
        }
        pos.close();
        sc.close();
    }

    public static void processOutput(PipedInputStream pis) throws IOException {
        int num = -1;
        while((num = pis.read()) != -1) {
            System.out.println(num + " * 3 = " + (num*3));
        }
    }

    public static void main(String[] args) {
        try {
            PipedInputStream pis = new PipedInputStream();
            PipedOutputStream pos = new PipedOutputStream();
            pos.connect(pis);
            Runnable inProgram = () -> {
                try {
                    processInput(pos);
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(PipelineThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            Runnable outProgram = () -> {
                try {
                    processOutput(pis);
                } catch (IOException ex) {
                    Logger.getLogger(PipelineThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            // start 2 Threads
            new Thread(inProgram).start();
            new Thread(outProgram).start();
        } catch (IOException ex) {
            Logger.getLogger(PipelineThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
