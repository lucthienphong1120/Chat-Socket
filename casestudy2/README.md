## Case study 2
Login từ xa dùng giao thức TCP/IP

+ Trên server TCP có danh sách users gồm hai trường: username và
password.
+ Chương trình phía client TCP phải hiện giao diện đồ họa, trong đó
có một ô text để nhập username (là số điện thoại), một ô text để nhập
password, và một nút nhấn Login.
+ Khi nút Login được click, chương trình client sẽ gửi thông tin đăng
nhập trên form giao diện, và gửi sang server theo giao thức TCP
+ Tại phía server, mỗi khi nhận được thông tin đăng nhập gửi từ
client, nó sẽ tiến hành kiểm tra trong danh sách xem có tài khoản nào
trùng với thông tin đăng nhập nhận được hay không.
+ Sau khi có kết quả kiểm tra (đăng nhập đúng, hoặc sai), server TCP
sẽ gửi kết quả này về cho client tương ứng, theo đúng giao thức
TCP.
+ Ở phía client, sau khi nhận được kết quả đăng nhập (đăng nhập
đúng, hoặc sai) từ server, nó sẽ hiển thị thông báo tương ứng với kết
quả nhận được: nếu đăng nhập đúng thì thông báo login thành công.
Nếu đăng nhập sai thì thông báo là phonenumber/password không
đúng.
+ Yêu cầu kiến trúc hệ thống ở cả hai phía client và server đều được
thiết kế theo mô hình MVC