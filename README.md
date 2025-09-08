# CinemaKotlin
Đặc vé xem phim cho rạp phim
1.	CÀI ĐẶT ANDROID STUDIO
Bước 1: Tải xuống Android Studio
•	Truy cập trang web chính thức của Android Studio để tải về:
        https://developer.android.com/studio
•	Nhấn download phiên bản mới nhất phù hợp với hệ điều hành đang sử dụng.
•	Chấp nhận các điều khoản để có thể tải xuống (nếu có)
Bước 2: Cài đặt Android Studio
•	Mở tệp đã tải xong trên đường dẫn trang web và làm theo hướng dẫn
•	Lựa chọn các thành phần cài đặt bao gồm:
•	Android Studio
•	Android SDK
•	Android Virtual Device (nếu cần)
•	Nhấn Next và chọn đường dẫn cài đặt thích hợp
•	Nhấn Install để tiến hành tải và cài đặt
Bước 3: Cấu hình ban đầu
•	Sau khi cài đặt xong, mở Android Studio
•	Hệ thóng sẽ yêu cầu tải về các thành phần bổ sung như BuildGradle, hãy Next để tiếp tục.
•	Chờ quá trình tải và cài đặt hoàn tất, sau đó Finish công việc
2. MỞ ANDROID STUDIO VÀ DỰ ÁN CỦA BẠN
Cách 1: Mở từ Android Studio
1.	Mở Android Studio bằng cách:
o	Windows: Tìm Android Studio trong menu Start và nhấp mở.
o	macOS: Vào Finder, tìm Android Studio và nhấn mở.
2.	Khi Android Studio mở lên, chọn “Open” hoặc “Open an existing project”.
3.	Trong hộp thoại hiện ra, điều hướng đến thư mục chứa dự án của bạn.
4.	Nhấn OK để mở dự án.
Cách 2: Mở từ thư mục dự án
1.	Mở File Explorer (Windows) hoặc Finder (macOS).
2.	Tìm đến thư mục chứa dự án của bạn.
3.	Nhấp chuột phải vào file build.gradle.kts hoặc settings.gradle.kts.
4.	Chọn “Open With” → “Android Studio” để mở.
3. CHẠY ỨNG DỤNG TRÊN TRÌNH GIẢ LẬP HOẶC THIẾT BỊ THẬT
Cách 1: Chạy trên trình giả lập (Emulator)
🔹 Nếu bạn chưa có thiết bị ảo (AVD), hãy làm theo các bước sau:
1.	Trên thanh công cụ của Android Studio, nhấp vào Device Manager (hình điện thoại).
2.	Nhấn "Create Device".
3.	Chọn một thiết bị (ví dụ: Pixel 5) và nhấn Next.
4.	Chọn phiên bản hệ điều hành Android (nên chọn phiên bản mới nhất có sẵn).
5.	Nhấn "Finish" để tạo thiết bị ảo.
6.	Sau khi tạo xong, nhấp vào "Launch Emulator" để khởi động thiết bị.
🔹 Chạy ứng dụng trên trình giả lập:
1.	Nhìn lên thanh công cụ, nhấn vào nút Run (▶).
2.	Chờ quá trình build hoàn tất và ứng dụng sẽ mở trên trình giả lập.
Cách 2: Chạy trên thiết bị thật
1.	Kết nối điện thoại Android với máy tính bằng cáp USB.
2.	Trên điện thoại, mở Cài đặt (Settings) > Giới thiệu điện thoại (About phone).
3.	Tìm đến Số bản dựng (Build number) và nhấn 7 lần để bật chế độ Nhà phát triển (Developer Mode).
4.	Quay lại Cài đặt > Tùy chọn nhà phát triển (Developer options).
5.	Bật Gỡ lỗi USB (USB Debugging).
6.	Trên Android Studio, nhấp vào Select Deployment Target, chọn điện thoại của bạn.
7.	Nhấn Run (▶) để chạy ứng dụng trên điện thoại thật.
