## Case study 1
Xây dựng một ứng dụng cho phép người dùng đăng nhập theo tài khoản của mình

+ Trên giao diện đang nhập có 2 ô văn bản cho phép người dùng nhập
phonenumber/password, và một nút nhấn Login để người dùng click vào đăng nhập.
+ Giao diện kiểm tra xem phonenumber có hợp lệ không (có 09 hoặc 03 ở trước, đủ 10
số và toàn số) và password có hợp lệ không (từ 6-10 ký tự, có số và chữ cái, không
chứa ký tự đặc biệt)
+ Khi người dùng click vào nút Login, hệ thống phải kiểm tra trong danh sách (lưu sẵn)
xem có phonenumber/password đấy không. Nếu có thì thông báo thành công, nếu sai thì
thông báo username/password không hợp lệ.
+ Hệ thống phải được thiết kế và cài đặt theo mô hình MVC