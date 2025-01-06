package com.example.sd_41.controller.ThongKe;

import com.example.sd_41.model.HoaDon;
import com.example.sd_41.repository.HoaDon.HoaDonChiTietRepository;
import com.example.sd_41.repository.HoaDon.HoaDonRepository;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoChiTietRepository;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class ThongKeHoaDonController {

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private GiayTheThaoRepository giayTheThaoRepository;

    @Autowired
    private GiayTheThaoChiTietRepository giayTheThaoChiTietRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @GetMapping("/thongKeChuanData")
    public String thongKeChuanData(Model model){

        //Tổng danh thu cho sản phẩm đã bán
        BigDecimal tongDoanhThu = hoaDonRepository.tinhTongDoanhThuNgay();
        model.addAttribute("tongDoanhThu",tongDoanhThu);

        //Tổng đơn hàng cho sản phẩm đã bán
        Integer tongDonHang = hoaDonRepository.tongDonHangNgay();
        model.addAttribute("tongDonHang",tongDonHang);

        //Tổng số lượng sản phẩm sẵn có trong kho
        Integer totalQuantity = giayTheThaoChiTietRepository.sumSoLuong();
        model.addAttribute("totalQuantity",totalQuantity);

        //
        Integer tongHoaDonTaiQuay = hoaDonRepository.tongHoaDonTaiQuayNgay();
        model.addAttribute("tongHoaDonTaiQuay",tongHoaDonTaiQuay);

        //Tổng số lượng hóa đơn online đã bán
        Integer tongHoaDonOnline = hoaDonRepository.tongHoaDonOnlineNgay();
        model.addAttribute("tongHoaDonOnline",tongHoaDonOnline);



        return "ThongKe/test";

    }
   

    //Thống kê theo khoảng thời gian
    @GetMapping("/thongKeTheoKhoangThoiGian")
    public String thongKeTheoKhoangThoiGian(
        @RequestParam(value = "startDate", required = false) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        
        @RequestParam(value = "endDate", required = false) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        
        Model model) {

        // Nếu không có tham số ngày, sử dụng ngày hiện tại
        if (startDate == null || endDate == null) {
            startDate = LocalDate.now(); // Ngày hiện tại
            endDate = LocalDate.now();   // Ngày hiện tại
        }

        // Chuyển đổi từ LocalDate sang LocalDateTime
        LocalDateTime startDateTime = startDate.atStartOfDay(); // 00:00:00 của ngày
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX); // 23:59:59 của ngày

        // Tính tổng doanh thu trong khoảng thời gian
        BigDecimal tongDoanhThu = hoaDonRepository.tinhTongDoanhThuTheoKhoangThoiGian(startDateTime, endDateTime);
        model.addAttribute("tongDoanhThu", tongDoanhThu);

        // Tính tổng đơn hàng trong khoảng thời gian
        Integer tongDonHang = hoaDonRepository.tongDonHangTheoKhoangThoiGian(startDateTime, endDateTime);
        model.addAttribute("tongDonHang", tongDonHang);

        // Tính tổng số lượng hóa đơn tại quầy / online trong khoảng thời gian
        Integer tongHoaDonTaiQuay = hoaDonRepository.tongHoaDonTaiQuayTheoKhoangThoiGian(startDateTime, endDateTime);
        model.addAttribute("tongHoaDonTaiQuay", tongHoaDonTaiQuay);

        Integer tongHoaDonOnline = hoaDonRepository.tongHoaDonOnlineTheoKhoangThoiGian(startDateTime, endDateTime);
        model.addAttribute("tongHoaDonOnline", tongHoaDonOnline);

        // Trả kết quả về trang view
        return "ThongKe/test";
    }

    
    
    
   //Thống kê hóa đơn

    @GetMapping("/thongke-data-hoadon")
    @ResponseBody
    public String thongKeDataHD() {
        List<HoaDon> hdct = hoaDonRepository.findAll();

        ObjectMapper objectMapper = new ObjectMapper();
        // Thêm module để hỗ trợ Java 8 date/time
        objectMapper.registerModule(new JavaTimeModule());

        try {
            String jsonData = objectMapper.writeValueAsString(hdct);
            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }



}
