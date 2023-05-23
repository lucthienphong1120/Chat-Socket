/*
OutputStream là một lớp nằm trong package java.io. Nó thực hiện ghi các bytes vào một mục tiêu nào đó, VD: file.
*/
/*
FileOutputStream là một lớp con của OutputStream thường được sử dụng để ghi các dữ liệu vào file dưới dạng chữ cái bình thường.
*/
/* Một số phương thức của OutputStream:
write(): ghi một byte vào OutputStream. Tham số chuyền vào phải trong khoảng từ 0-255 (Nếu không nó sẽ bỏ qua).
+ Các hàm write chỉ nhận nháy đơn không nhận nháy kép.
close(): Đóng stream và giải phóng mọi tài nguyên hệ thống được liên kết với nó. Khi Stream đã đóng, các  (tránh sảy ra lỗi "chắc thế.Thầy bảo thế").
flush(): Dùng để đẩy dữ liệu đến địa chỉ và xóa toàn bộ trong bộ nhớ đệm.
*/
/*
package learn.InOutStream;
//Khai báo thư viện
import java.io.*;
public class OutputStreamFunction{
    public static void main(String[] args) throws IOException{
        //Tìm file trong hệ thống
        File tep = new File("D:/JAVA/javatest.txt");
        //Cho OutputStream trỏ vào đấy.
        OutputStream ghi = new FileOutputStream(tep);
        //Ghi từng ký tự vào file đó.
        ghi.write('H');
        ghi.write(101);
        ghi.write(108);
        ghi.write(108);
        ghi.write(111);
        ghi.write(32);
        ghi.write(119);
        ghi.write(101);
        ghi.write(32);
        ghi.write(97);
        ghi.write(114);
        ghi.write(101);
        ghi.write(32);
        ghi.write(104);
        ghi.write(97);
        ghi.write(99);
        ghi.write(107);
        ghi.write(101);
        ghi.write(114);
        //Đóng file.
        ghi.close();
    }
}*/
/*
write(byte[]): ghi một mảng byte vào OutputStream.
*/
/*
package learn.InOutStream;
import java.io.*;
public class OutputStreamFunction{
    public static void main(String[] args) throws IOException{
        //Tìm file trong hệ thống
        File tep = new File("D:/JAVA/javatest.txt");
        //Cho OutputStream trỏ vào đấy.
        OutputStream ghi = new FileOutputStream(tep);
        //Ghi các ký tự vào một mảng.
        byte[] mang = new byte[]{'H',101,108,108,111,32,119,101,32,97,114,101,32,104,97,99,107,101,114};
        //Ghi dữ liệu trong mảng vào file.
        ghi.write(mang);
        //Đóng Stream.
        ghi.close();
    }
}*/
/*
write(byte[],int,int): viết các dữ liệu trong một mảng vào file từ điểm bắt đầu đến điểm dừng.
*/
/*
package learn.InOutStream;
import java.io.*;
public class OutputStreamFunction{
    public static void main(String[] args) throws IOException{
        //Tạo String
        String text = "Hello we are hacker 7355608";
        //Cho String vào mảng
        byte[] mang = text.getBytes();
        //địa chỉ file
        File tep = new File("D:/JAVA/javatest.txt");
        //Đẩy dữ liệu đến file.
        OutputStream ghi = new FileOutputStream(tep);
        //lấy dữ liệu trong mảng từ vị trí bắt đầu đến dừng.
        ghi.write(mang,6,14);
        //Đóng file.
        ghi.close();
    }
}*/
/*
BufferedOutputStream:
+Buffered được sử dụng để đọc và dịch những văn bản từ dạng byte sang dạng chữ cái thông thường. Rồi đẩy vào một file theo OutputStream.
+BufferedOutputStream ghi đè (override) các phương thức được thừa kế từ lớp cha của nó, chẳng hạn như write(), write(byte[]),... để đảm
bảo rằng dữ liệu sẽ được ghi vào buffer chứ không phải ghi vào mục tiêu (chẳng hạn file). Nhưng khi buffer đầy, tất cả dữ liệu trên buffer
sẽ được đẩy vào OutputStream và giải phóng buffer. Bạn cũng có thể gọi phương thức BufferedOutputStream.flush() để chủ động đẩy tất cả dữ
liệu trên buffer sang OutputStream và giải phóng buffer. Dữ liệu cũng được đẩy từ buffer sang OutputStream khi gọi phương thức BufferedOutputStream.close().
*/
/*
package learn.InOutStream;
import java.io.*;
public class OutputStreamFunction{
    public static void main(String[] args) throws IOException{
        //Địa chỉ file.
        File tep = new File("D:/JAVA/javatest.txt");
        OutputStream ghi = new FileOutputStream(tep);
        //BufferedOutputStream như nơi lưu trữ ở giữa
        BufferedOutputStream kethu3 = new BufferedOutputStream(ghi,16384);
        //ghi dữ liệu vào file.
        ghi.write('A');
        ghi.write('\n');
        ghi.write('A');
        ghi.write('\n');
        //Đẩy dữ liệu đến file và thực hiện xóa dữ liệu trong Buff và Output.
        ghi.flush();
        ghi.write('\n');
        ghi.write('A');
        //Đóng file.
        ghi.close();
    }
}*/