package com.example.sd_41.repository;

import com.example.sd_41.model.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface KhachHangRepository extends JpaRepository<KhachHang, UUID> {
	
	

    @Query("select kh from KhachHang kh where (?1 is null or kh.tenKhachHang like ?1) and (?1 is null or kh.email like ?1) and (?1 is null or kh.matKhau like ?1) and (?1 is null or kh.gioiTinh like ?1) and (?2 is null or kh.ngaySinh=?2) and (?1 is null or kh.soDienThoai like ?1) and (?1 is null or kh.diaChi like ?1) and (?1 is null or kh.ghiChu like ?1) and (?3 is null or kh.trangThai=?3)")
    Page<KhachHang> searchKH(String keyword, String tenKhachHang, String email, String matKhau, String gioiTinh, String ngaySinh, String soDienThoai, String diaChi, Pageable pageable);

    //Todo code check email and password

    KhachHang findByEmailAndMatKhau(String email,String matKhau);

//    KhachHang findById(UUID id);

    KhachHang findByMaKhachHang(String maKhachHang);

    List<KhachHang> findByTenKhachHang(String tenKhachHang);

    @Query("select p from KhachHang p where p.email= ?1")
    KhachHang findByEmail(String email);


}
