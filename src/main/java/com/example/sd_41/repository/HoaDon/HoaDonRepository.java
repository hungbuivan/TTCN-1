package com.example.sd_41.repository.HoaDon;

import com.example.sd_41.model.GiaoDichViChiTiet;
import com.example.sd_41.model.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, UUID> {

    @Query("select hd from HoaDon hd where hd.khachHang.id = ?1 and hd.trangThai = ?2")
    Page<HoaDon> listHoaDonFindByKhachHangAndTrangThai(UUID idKhachHang, int trangThai, Pageable pageable);

    @Query("select hd from HoaDon hd where hd.trangThai = ?1")
    Page<HoaDon> listHoaDonFindByTrangThai(int trangThai, Pageable pageable);

    // Todo code dành cho thống kê

    @Query("SELECT SUM(hd.thanhTien) FROM HoaDon hd WHERE hd.trangThai = 4 ")
    BigDecimal tinhTongDoanhThu();

    @Query("SELECT COUNT(hd.maHoaDon) FROM HoaDon hd WHERE hd.trangThai = 4 ")
    Integer tongDonHang();

    @Query("SELECT COUNT(hd.maHoaDon) FROM HoaDon hd WHERE hd.trangThai = 4 ")
    Integer tongHoaDonTaiQuay();

    @Query("SELECT COUNT(hd.maHoaDon) FROM HoaDon hd WHERE hd.trangThai = 4 ")
    Integer tongHoaDonOnline();

    /*
    @Query("SELECT gttct.sanPham.maSanPham, SUM(hdct.soLuong) " +
            "FROM HoaDon h " +
            "JOIN HoaDonChiTiet hdct ON h.id = hdct.hoaDon.id " +
            "JOIN GiayTheThaoChiTiet gttct ON hdct.giayTheThaoChiTiet.id = gttct.id " +
            "WHERE h.trangThai = 4 " + // Trạng thái đã thanh toán (hoặc theo điều kiện khác)
            "GROUP BY gttct.sanPham.maSanPham " +
            "ORDER BY SUM(hdct.soLuong) DESC")
    Object[] timSanPhamBanChayNhat();

*/

    //thống kê theo ngày
    @Query("SELECT SUM(hd.thanhTien) FROM HoaDon hd WHERE hd.trangThai = 4 AND CAST(hd.ngayTao AS date) = CAST(CURRENT_DATE AS date)")
    BigDecimal tinhTongDoanhThuNgay();

    @Query("SELECT COUNT(hd.maHoaDon) FROM HoaDon hd WHERE hd.trangThai = 4 AND CAST(hd.ngayTao AS date) = CAST(CURRENT_DATE AS date)")
    Integer tongDonHangNgay();

    @Query("SELECT COUNT(hd.maHoaDon) FROM HoaDon hd WHERE hd.trangThai = 4 AND hd.hinhThuc = 1 AND CAST(hd.ngayTao AS date) = CAST(CURRENT_DATE AS date)")
    Integer tongHoaDonTaiQuayNgay();

    @Query("SELECT COUNT(hd.maHoaDon) FROM HoaDon hd WHERE hd.trangThai = 4 AND hd.hinhThuc = 0 AND CAST(hd.ngayTao AS date) = CAST(CURRENT_DATE AS date)")
    Integer tongHoaDonOnlineNgay();

    
    
 // Thống kê tổng doanh thu theo khoảng thời gian
        @Query("SELECT SUM(hd.thanhTien) FROM HoaDon hd WHERE hd.ngayTao BETWEEN :startDate AND :endDate")
        BigDecimal tinhTongDoanhThuTheoKhoangThoiGian(
            @Param("startDate") LocalDateTime startDate, 
            @Param("endDate") LocalDateTime endDate
        );

        @Query("SELECT COUNT(hd.maHoaDon) FROM HoaDon hd WHERE hd.ngayTao BETWEEN :startDate AND :endDate")
        Integer tongDonHangTheoKhoangThoiGian(
            @Param("startDate") LocalDateTime startDate, 
            @Param("endDate") LocalDateTime endDate
        );

        @Query("SELECT COUNT(hd.maHoaDon) FROM HoaDon hd WHERE hd.hinhThuc = 1 AND hd.ngayTao BETWEEN :startDate AND :endDate")
        Integer tongHoaDonTaiQuayTheoKhoangThoiGian(
            @Param("startDate") LocalDateTime startDate, 
            @Param("endDate") LocalDateTime endDate
        );

        @Query("SELECT COUNT(hd.maHoaDon) FROM HoaDon hd WHERE hd.hinhThuc = 0 AND hd.ngayTao BETWEEN :startDate AND :endDate")
        Integer tongHoaDonOnlineTheoKhoangThoiGian(
            @Param("startDate") LocalDateTime startDate, 
            @Param("endDate") LocalDateTime endDate
        );

    
    
    
    
    
    
    // Todo code áp dụng cho chương trình khuyến mãi cho hóa đơn
    @Query("SELECT SUM(hdct.soLuong) FROM HoaDonChiTiet hdct WHERE hdct.hoaDon.id = ?1")
    String tongSl(UUID id);

    // Todo code bán hàng tại quầy
    List<HoaDon> findAllByTrangThaiAndHinhThucOrderByNgayTaoDesc(int trangThai, int hinhThuc);

    // Todo code thêm sản phẩm cho hóa đơn
    @Query("SELECT COALESCE(SUM(hdct.donGia), 0) FROM HoaDonChiTiet hdct WHERE hdct.hoaDon.id = :hoaDonId")
    BigDecimal calculateTotalDonGiaByHoaDonId(@Param("hoaDonId") UUID hoaDonId);

    // Tìm kiếm theo mã
    @Query("SELECT hd FROM HoaDon hd WHERE hd.maHoaDon = ?1")
    HoaDon findByMa(String ma);

}
