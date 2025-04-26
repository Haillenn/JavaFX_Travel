# Phần Mềm Quản Lý Tour Du Lịch

## Chức Năng Chính

### 1. Cơ sở dữ liệu
- **User**: Quản lý tài khoản người dùng.
- **Tour**: Lưu trữ thông tin các tour du lịch.
- **Booking**: Quản lý đơn đặt tour của người dùng.

### 2. Giao diện và Chức năng

#### Giao diện Đăng Nhập
- Người dùng có tài khoản có thể đăng nhập vào hệ thống.

#### Giao diện Đăng Ký
- Người dùng mới có thể đăng ký tài khoản.

#### Giao diện Admin
- **Quản lý Tour**: Thêm, sửa, xóa, xem chi tiết tour du lịch.
- **Quản lý Book Tour**: Xác nhận hoặc từ chối đơn đặt tour.

#### Giao diện User
- **Xem danh sách tour**: Tham khảo thông tin các tour đang mở.
- **Đặt Tour**: Lựa chọn và đặt tour mong muốn.
- **Lịch sử Đặt Tour**: 
  - Xem lại các tour đã đặt.
  - Có thể hủy tour nếu chưa được xác nhận.

#### Đăng Xuất
- Sau khi đăng xuất sẽ quay trở lại trang đăng nhập.

---

## Hướng Dẫn Cài Đặt và Chạy Phần Mềm

### Yêu cầu hệ thống
- Java JDK 21 trở lên
- JavaFX SDK (nếu cần)
- Máy tính hệ điều hành Windows

### Các bước thực hiện

1. **Tải project** về máy tính.
2. **Giải nén** file tải về.
3. Mở thư mục dự án đã giải nén.
4. **Chạy file `Main.java`** để khởi động chương trình.
   - (Nếu mở bằng IDE như Eclipse hoặc IntelliJ: Click chuột phải vào `Main.java` → Run)
   - (Nếu chạy bằng dòng lệnh: Compile và chạy `Main`)

---

## Ghi chú

- Tất cả thư viện cần thiết (JavaFX...) đã được đính kèm trong thư mục `lib`.
- Cơ sở dữ liệu sử dụng: Microsoft Access (file `.accdb` đi kèm trong project).
- Khi sử dụng lần đầu: Đảm bảo **đường dẫn đến database** không bị thay đổi.

---

✅ Chỉ cần tải về → giải nén → chạy file `Main` là sử dụng được phần mềm.  
Chúc bạn sử dụng thuận lợi! 🚀
