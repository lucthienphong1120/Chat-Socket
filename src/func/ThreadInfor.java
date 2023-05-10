/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package func;

/**
 *
 * @author Phong
 */
public class ThreadInfor extends Thread {

    @Override
    public void run() {
        System.out.println("Thread Started: " + this.getName());
    }

    public void hello() {
        String name = Thread.currentThread().getName();
        long id = Thread.currentThread().threadId();
        System.out.println("Hello from " + name + " - " + id);
    }

    public static void main(String[] args) {
        ThreadInfor t = new ThreadInfor();
        t.setName("T");
        System.out.println("before start()," + Thread.currentThread().getName() + ".isAlive() = " + t.isAlive());
        t.start();
        System.out.println("just after start()," + Thread.currentThread().getName() + ".isAlive() = " + t.isAlive());
        t.hello();
        System.out.println("The end of start()," + Thread.currentThread().getName() + ".isAlive() = " + t.isAlive());
    }
}
