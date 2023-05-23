/*Trong java dòng (Stream "lồng"), là chuỗi dữ liệu được đọc(.read) từ nơi được lưu trữ (data source) và được 
ghi(.write) tới nơi cần lưu trữ (data destination).
*/
/*InputStream đơn giản nhận các dữ liệu ở dạng byte (Byte Stream). Không thể tạo đối tượng của lớp trừu tượng nên
không thể gọi nó trực tiếp. Có thể gọi bằng các lớp con.
FileInputStream là một lớp con của InputStream thường được sử dụng để đọc các file và nhận được dữ liệu dạng bytes.
*/
/*Một lưu ý khá quan trọng với InputStream.
+InputStream sẽ đọc dữ liệu ngay khi có(Nhập dữ liệu từ bàn phím Input sẽ đọc ngay khi vừa nhập từ bàn phím không cần enter).
+InputStream sẽ đọc từ phải trước nhưng nếu in ra vẫn sẽ theo từ trái.
*/
/* Những hàm tự tìm hiểu:
int.readAllBytes();
int.readNBytes(int): đọc tối đa đến byte ở vị trí nhập từ InputStream.
TransferTo(OutputStream): sử dụng để đọc file và đẩy về OutputStream(nó sẽ trả về byte). Khi xong InputStream sẽ ở cuối stream. Phương thức này không đóng In/OutputStream.
markSupported(): kiểm tra InputStream có hỗ trợ hoạt động mark(int) hay không.
mark(int):cho phép đánh dấu bị trí hiện tại trên stream. Có thể đọc tiếp các bytes tiếp theo, và gọi phương thức reset() để quay trở lại vị trí đánh dấu trước đó.
reset(): Nếu sử sụng phương thức mark() thì reset() để quay trở lại vị trí đã được đánh dấu.
*/
/*Một số phương thức của InputStream:
int.read(): đọc thông tin dạng byte, giá trị của byte trả về là số nguyên trong khoảng 0-255 hoặc trả về -1 nếu ở cuối.
Phương thức này sẽ (block) cho tới khi byte sẵn có để đọc hoặc sảy ra lỗi IO hoặc đọc hết Stream.
int.close(): Đóng stream và giải phóng mọi tài nguyên hệ thống được liên kết với nó. Khi Stream đã đóng, các  (tránh sảy ra lỗi "chắc thế.Thầy bảo thế").
*/
/*
package learn.InOutStream;
//Khai báo thư viện io.
import java.io.*;
public class InputStreamFunction {
    //Tạo hàm main qua ngoại lệ IOException (các ngoại lệ liên quan đến đọc ghi).
    public static void main(String[] args) throws IOException{
        //Đọc thông tin từ chuỗi nhập
        InputStream dich = System.in;
        int code;
        //Tạo vòng lặp code sẽ lấy từng byte mà dich đọc được và in ra
        while((code = dich.read())!= -1){
            //In ra số byte và ký tự khi dịch byte bằng char.
            System.out.println(code + "" + (char)code);
        }
        //Đóng InputStream lại.
        dich.close();
    }
}
*/
/*
int.read(byte[]): đọc các byte rồi cho vào một mảng. Sử dụng read(byte[]) có hiệu suất cao hơn vì giảm số lần đọc
từ stream.
*/
/* Bài này khá khó hiểu.
package learn.InOutStream;
import java.io.*;
public class InputStreamFunction {
    public static void main(String[] args) throws IOException{
        //Nhận chuỗi từ nhập bàn phím.
        InputStream dich = System.in;
        //Tạo một mảng tạm thời(mang co 10 gia trị).
        byte[] mang = new byte[10];
        //Đếm số byte tối đa 10 ở trên.
        int dembyte = -1;
        //số lần thực hiện để hết dữ liệu.
        int lan = 0;
        while ((dembyte = dich.read(mang))!=-1){
            //Tăng số lần mỗi khi đọc xong.
            lan ++;
            //In thông tin.
            System.out.println("Lần đọc thứ: "+ lan);
            System.out.println("Số byte đọc ở lần này: "+dembyte+" (tối đa 10 giá trị)");
            for(int i=0; i<dembyte;i++){
                //Tạo biến hứng mảng và 0xff(0xff là một số được biểu thị dạng hex. Nó tương ứng với FF(255,255))             
                int readed = mang[i]&0xff;
                System.out.println(mang[i]+" "+readed+" "+(char)readed);
            }
        }
        //Đóng stream.
        dich.close();
    }
}
*/
/*
read(byte[], int, int): đọc byte trong mảng từ đâu đến đâu.
*/
/*
package learn.InOutStream;
import java.io.*;
public class InputStreamFunction {
    public static void main(String[] args) throws IOException{
        //Nhận chuỗi từ nhập bàn phím.
        InputStream dich = System.in;
        //Tạo một mảng tạm thời(mang co 10 gia trị).
        byte[] mang = new byte[10];
        //Đếm số byte tối đa 10 ở trên.
        int dembyte = -1;
        //số lần thực hiện để hết dữ liệu.
        int lan = 0;
        //Tạo while đọc thông tin từ nhập và gán vào mảng 8 giá trị.
        while ((dembyte = dich.read(mang,0,8))!=-1){
            //Tăng số lần mỗi khi đọc xong.
            lan ++;
            //In thông tin.
            System.out.println("Lần đọc thứ: "+ lan);
            System.out.println("Số byte đọc ở lần này: "+dembyte+" (tối đa 10 giá trị)");
            for(int i=0; i<dembyte;i++){
                //Tạo biến hứng mảng và 0xff(0xff là một số được biểu thị dạng hex. Nó tương ứng với FF(255,255))             
                int readed = mang[i]&0xff;
                System.out.println(mang[i]+" "+readed+" "+(char)readed);
            }
        }
        //Đóng stream.
        dich.close();
    }
}
*/
/*
ship(int/long): block đến khi nào đến vị trí nhập trong mảng.
*/
/*
package learn.InOutStream;
import java.io.*;
public class InputStreamFunction {
    public static void main(String[] args) throws IOException {
        //Nhập chuỗi 25 ký tự.
        String s = "123456789-987654321-ABCDE";
        //Cho dữ liệu vào một mảng
        byte[] bytes = s.getBytes();
        //chuyển mảng sang dạng byte.
        InputStream is = new ByteArrayInputStream(bytes);
        //Tạo hai biến đọc kí tự mảng(nó chỉ đọc được một ký tự).
        int firstByteCode = is.read();
        int secondByteCode = is.read();
        //In ra ký tự được đọc đầu tiên (1).
        System.out.println("First byte: " + (char) firstByteCode);
        //In ra ký tự được đọc thứ hai (2).
        System.out.println("Second byte: " + (char) secondByteCode);
        //ship 18 ký tự trong chuỗi.
        is.skip(18);
        int code;
        //chạy vòng lặp in ra những ký tự còn lại.
        while ((code = is.read()) != -1) {
            System.out.println(code +" " + (char) code);
        }
        is.close();
    }
}
*/
