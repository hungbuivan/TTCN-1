package com.example.sd_41.controller.BanHangTaiQuay;

import com.example.sd_41.model.HoaDonChiTiet;
import com.example.sd_41.service.ChuongTrinhGiamGiaHoaDonService;
import com.example.sd_41.service.GiayTheThao.GiayTheThaoChiTietService;
import com.example.sd_41.service.HoaDon.HoaDonChiTietServie;
import com.example.sd_41.service.HoaDon.HoaDonService;
import com.example.sd_41.service.KhachHangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("BanHangTaiQuay")
public class BanHangTaiQuayController {
    

    @Autowired
    private GiayTheThaoChiTietService gttctService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private HoaDonChiTietServie hoaDonChiTietservie;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private ChuongTrinhGiamGiaHoaDonService ctggHDService;


    @GetMapping()
    public String getView(Model model) {


        model.addAttribute("list", gttctService.getAll());
        model.addAttribute("listHDC", hoaDonService.hoaDonCho());
        model.addAttribute("listKH", khachHangService.getAll());

        return "BanHangTaiQuay/BanHangTaiQuayTest";

    }

    @GetMapping("thanhToan/{id}")
    public String getViewPay(Model model, @PathVariable("id") UUID id,HttpSession session) {

        UUID idUser = (UUID) session.getAttribute("idUser");

        // Kiểm tra idUser có tồn tại
        if (idUser != null) {
            // Thực hiện lưu idUser vào hóa đơn
            hoaDonService.luuIdUserVaoHoaDon(id, idUser);

            List<HoaDonChiTiet> listHDCT = hoaDonChiTietservie.getListByID(id);
            int sl = 0;
            BigDecimal tt = new BigDecimal(0);
            for (HoaDonChiTiet hoaDonChiTiet : listHDCT) {
                sl+=Integer.parseInt(hoaDonChiTiet.getSoLuong());
                BigDecimal soLuong = new BigDecimal(hoaDonChiTiet.getSoLuong());
                tt = tt.add(hoaDonChiTiet.getDonGia().multiply(soLuong));
            }
            model.addAttribute("list", listHDCT);
            model.addAttribute("tt", tt);
            model.addAttribute("listCtgg", ctggHDService.getAllBySlTT(String.valueOf(sl), tt));
            model.addAttribute("id", id);

            return "BanHangTaiQuay/thanhToan";


        }else {

//           Nhân viên hoặc admin chưa đăng nhập tài khoản
            return "redirect:/UserLog/login";

        }

    }


}


