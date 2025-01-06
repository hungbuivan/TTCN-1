package com.example.sd_41.repository.ChuongTrinhGiamGia;

import com.example.sd_41.model.ChuongTrinhGiamGiaChiTietHoaDon;
import com.example.sd_41.model.ChuongTrinhGiamGiaHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChuongTrinhGiamGiaChiTietHoaDonRepository extends JpaRepository<ChuongTrinhGiamGiaChiTietHoaDon, UUID> {

    List<ChuongTrinhGiamGiaChiTietHoaDon> findAllByCtggHD(ChuongTrinhGiamGiaHoaDon ctggHD);
    @Query("select c from ChuongTrinhGiamGiaChiTietHoaDon c join HoaDon h on c.id=h.id where h.trangThai = 1")
    List<ChuongTrinhGiamGiaChiTietHoaDon> getAllByTrangThaiHD();


}
