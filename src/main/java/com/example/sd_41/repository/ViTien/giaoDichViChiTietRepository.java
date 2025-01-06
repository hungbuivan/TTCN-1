package com.example.sd_41.repository.ViTien;

import com.example.sd_41.model.GiaoDichViChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface giaoDichViChiTietRepository extends JpaRepository<GiaoDichViChiTiet, UUID> {

    @Query("select gd from GiaoDichViChiTiet gd where gd.viTien.khachHang.id = ?1 and gd.trangThai in (1, 2) order by gd.ngayGiaoDich desc")
    List<GiaoDichViChiTiet> findAllByKhachHang(UUID id);

    @Query("select gd from GiaoDichViChiTiet gd where gd.maGiaoDichViChiTiet = ?1")
    GiaoDichViChiTiet findByMa(String ma);


}
