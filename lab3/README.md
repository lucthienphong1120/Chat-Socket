## Lab03
Xây dựng chương trình Chat ở như Case Study 2 nhưng client sẽ gọi hàm(qua cơ chế RMI từ server) để lấy danh sách các client đã từng đăng nhập vào server.

a) Hãy làm chức năng bất kỳ client nào đăng nhập vào thì server cũng báo cho nó danh sách các nick đã đăng nhập trước. Cụ thể hơn: client đăng nhập xong thì sẽ gọi hàm RMI của server để lấy danh sách đăng nhập
b) Bất kỳ client nào đăng nhập thì server cũng báo cho các client còn lại (đã đăng nhập trước) về sự có mặt của client mới
c) Bất kỳ client nào đăng xuất thì server cũng xoá khỏi danh sách các client đã đăng nhập và báo cho các client khác.
