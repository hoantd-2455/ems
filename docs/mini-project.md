# Mini Project: Employee Management System
### Chức năng chính:
- Quản lý nhân viên (Thêm, Sửa, Xóa, Tìm kiếm, Xem danh sách)
- Đăng ký / Đăng nhập
- Phân quyền (Admin/User)
- Thống kê dữ liệu nhân viên (theo phòng ban, số lượng, xu hướng)
#### Công nghệ: Spring Boot + Spring Data JPA + REST API + Thymeleaf + MySQL/PostgresModule 

---

### Module 1: Getting Started with Spring Boot

Nội dung
- Giới thiệu Spring Boot, Starter, Auto Configuration● Tạo project với Spring Initializr
- Cấu trúc project + chạy Hello World REST API

Thực hành (Mini Project – Lab 1)
- Tạo project employee-management
- Viết API /hello để kiểm tra project chạy thành công

---

### Module 2: Custom Bean & IoC
Nội dung

- Bean là gì? IoC Container là gì?
- Tạo Bean bằng @Component, @Service, @Repository
- Định nghĩa Bean bằng @Bean trong @Configuration class
- Cơ chế Dependency Injection (@Autowired, Constructor Injection)

Thực hành (Mini Project – Lab 2)

- Tạo một UtilityService (ví dụ: format chuỗi, tạo mã nhân viên tự động) → đánhdấu @Service
- Định nghĩa 1 custom bean @Bean trong @Configuration (ví dụ:PasswordEncoder hoặc ModelMapper)
- Inject bean này vào Controller/Service của Employee Management để sử dụng

---

### Module 3: REST API cơ bản
Nội dung
- @RestController, @RequestMapping, @GetMapping, @PostMapping
- Path variable & Request param
- @RequestBody và ResponseEntity

Thực hành (Mini Project – Lab 3)

- Tạo API lấy danh sách nhân viên (dữ liệu in-memory)
- Tạo API thêm nhân viên mới

---

### Module 4: Spring Boot + Database (Spring Data JPA)

Nội dung
- Cấu hình Database (MySQL/Postgres)
- Entity + Repository (JpaRepository)
- CRUD với DB

Thực hành (Mini Project – Lab 4)

- Tạo bảng employee (id, name, email, department_Id)
- Tạo bảng department
- Tạo relation giữa employee và department
- Tích hợp CRUD Employee với DB
- Bổ sung chức năng tìm kiếm nhân viên theo tên hoặc phòng ban

---

### Module 5: Validation & Exception Handling

Nội dung
- Bean Validation (@NotNull, @Size, @Email)
- @Valid + BindingResult
- Global Exception Handling (@ControllerAdvice)

Thực hành (Mini Project – Lab 5)

- Thêm validation cho Employee (email hợp lệ, name không rỗng)
- Xử lý lỗi khi không tìm thấy Employee (404 Not Found)
- Thông báo lỗi rõ ràng khi request sai định dạng dữ liệu

---

### Module 6: Spring Boot Web (MVC + Thymeleaf)
Nội dung
- Tích hợp spring-boot-starter-thymeleaf
- Controller + Model + View
- Data Binding + Form Handling

Thực hành (Mini Project – Lab 6)

- Trang /employees/list hiển thị danh sách nhân viên từ DB
- Form /employees/add để thêm nhân viên mới
- Trang tìm kiếm nhân viên theo tên/phòng ban, hiển thị kết quả

---

### Module 7: Logging & Profiles

Nội dung

- Logging với SLF4J + Logback
- Profiles (application-dev.yml, application-prod.yml)

Thực hành (Mini Project – Lab 7)

- Log khi thêm/sửa/xóa nhân viên
- Tách cấu hình DB theo profile dev/prod

---

### Module 8: Advanced Spring Boot

Nội dung

- Spring Boot Actuator (health, metrics)
- Scheduling (@Scheduled)
- Caching (@EnableCaching, @Cacheable)

Thực hành (Mini Project – Lab 8)

- Thêm API báo cáo tổng số nhân viên (có caching 1 phút)
- Scheduled task log ra console mỗi 30s với nội dung “System running”

---

### Module 9: Spring Security Basics

Nội dung

- Spring Security Starter
- Basic Authentication
- Custom User + Role (Admin/User)
- JWT Authentication (cơ bản)

Thực hành (Mini Project – Lab 9)
- Tạo User entity (username, password, role)
- Đăng ký + đăng nhập
- USER chỉ được xem danh sách, ADMIN được CRUD nhân viên

---

### Module 10: Reporting & Analytics (Thống kê)

Nội dung
- Viết các API thống kê dữ liệu nhân viên
- Dùng @Query trong Repository để tính toán báo cáo
- Hiển thị thống kê bằng REST API + Thymeleaf

Thực hành (Mini Project – Lab 10)
- Thống kê số lượng nhân viên theo phòng ban
- Thống kê tổng số nhân viên trong hệ thống
- Hiển thị kết quả thống kê trên trang web /employees/statistics