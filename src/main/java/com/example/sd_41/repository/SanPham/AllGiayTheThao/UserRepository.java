package com.example.sd_41.repository.SanPham.AllGiayTheThao;

import com.example.sd_41.model.KhachHang;
import com.example.sd_41.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByTenUserContainingOrderById(String tenUser);
 // Sửa lại phương thức lọc trạng thái
    List<User> findByTrangThaiOrderById(int trangThai);

    //Todo code đăng nhập

    User findByEmailAndAndMatKhau(String email,String matKhau);

    //Todo code finde email

    @Query("select p from User p where p.email= ?1")
    User findByEmail(String email);


}
