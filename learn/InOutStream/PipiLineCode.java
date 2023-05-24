/* PipeLine:
+Một chương trình Server khi hoạt động có thể sẽ tạo ra nhiều Thread để phục vụ các request của client. Các Thread đều có bộ nhớ riêng, bộ đếm chương trình riêng
(Program Counter) và chúng có thể hoạt động độc lập. Tuy nhiên các thread vẫn cần có thể trao đổi thông tin với nhau.
+PipeLine là một trong những cơ chế cho phép nhiều Thread (có thể xuất phát cùng một chương trình hoặc không) trao đổi dữ liệu cho nhau.
*/
package learn.InOutStream;
import java.io.*;
import java.util.Scanner;
public class PipiLineCode{
    public static void main(String[] args) {
        //Tạo hai đường ống vào và ra.
        PipedInputStream pin = new PipedInputStream();
        PipedOutputStream pout = new PipedOutputStream();
        //kết nối hai đường ống với nhau.
        try{
            pout.connect(pin);
        }catch(Exception er){
            er.printStackTrace();
        }
        /*
        //Tạo hai Lambda:
        +Lambda 1: Để nhận thông tin từ hàm "processInput(pout) và đẩy nó đến processOutput(pin) qua câu lệnh kết nối ở bước 3"
        +Lambda 2: Để lấy thông tin từ Pipe vào nó và thực hiện
        */
        Runnable rootin = () -> processInput(pout);
        Runnable rootout = () -> processOutput(pin);
        //Hai Thread tương ứng với hai người dùng.
        new Thread(rootin).start();
        new Thread(rootout).start();
    }
    //Quy trình của Nhập:
    public static void processInput(PipedOutputStream pout){
        try{
            //Nhập dữ liệu từ bàn phím.
            Scanner mynumber = new Scanner(System.in);
            //Vòng lặp <=10.
            for(int i=0; i<=10;i++){
                //Lấy tham số chuyền vào ở dạng String.
                String getnumber = mynumber.nextLine();
                //Đổi lại thành dạng int.
                int change = Integer.parseInt(getnumber);
                //viết (đẩy) vào đường ống.
                pout.write((byte)change);
                pout.flush();
                Thread.sleep(500);
            }
        }catch(Exception err){
            err.printStackTrace();
        }
    }
    public static void processOutput(PipedInputStream pin){
        try{
            int num = -1;
            while((num = pin.read())!=-1){
                System.out.println("I read "+num+" and then triple "+3*num);
            }
        }catch(Exception errr){
            errr.printStackTrace();
        }
    }
}