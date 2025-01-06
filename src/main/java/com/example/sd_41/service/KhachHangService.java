package com.example.sd_41.service;

import com.example.sd_41.model.GioHang;
import com.example.sd_41.model.KhachHang;
import com.example.sd_41.repository.KhachHangRepository;
import com.example.sd_41.repository.BanHang.GioHangChiTietRepository;
import com.example.sd_41.repository.BanHang.GioHangRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

@Service
public class KhachHangService implements KhachHangImpl {
	
	@Autowired
    private GioHangRepository gioHangRepository;
    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    public List<KhachHang> findKhachHang(String tenKhachHang) {
        if (tenKhachHang != null) {
            return khachHangRepository.findByTenKhachHang(tenKhachHang);
        } else {
            return khachHangRepository.findAllByStatus(1);
        }
    }

    public Page<KhachHang> searchKH(String keyword, String tenKhachHang, String email, String matKhau, String gioiTinh, String ngaySinh, String soDienThoai, String diaChi, Pageable pageable){
        return khachHangRepository.searchKH(keyword, tenKhachHang, email, matKhau, gioiTinh, ngaySinh, soDienThoai, diaChi, pageable);
    }

    //Tìm trong giỏ hàng
    public KhachHang findById(UUID id){

        return khachHangRepository.findById(id).orElse(null);

    }

    //Tìm kiếm khách hàng theo id
    public KhachHang findByEmail(String email) {

        return khachHangRepository.findByEmail(email);

    }

    //Update lại thông tin của khách hàng
    @Override
    public void update(UUID id, KhachHang update){

        Optional<KhachHang> optionalKhachHang = khachHangRepository.findById(id);

        if (optionalKhachHang.isPresent()){

            KhachHang khachHang = optionalKhachHang.get();
            khachHang.setMaKhachHang(update.getMaKhachHang());
            khachHang.setTenKhachHang(update.getTenKhachHang());
            khachHang.setNgaySinh(update.getNgaySinh());
            khachHang.setDiaChi(update.getDiaChi());
            khachHang.setThanhPho(update.getThanhPho());
            khachHang.setHuyen(update.getHuyen());
            khachHang.setSoDienThoai(update.getSoDienThoai());
            khachHang.setEmail(update.getEmail());
            khachHang.setMatKhau(update.getMatKhau());
            khachHang.setTrangThai(update.getTrangThai());
            khachHangRepository.save(khachHang);
        }

    }

    //Todo code dành cho bán hàng tại quậy
    public List<KhachHang> getAll() {
        return khachHangRepository.findAllByStatus(1);
    }
    
    @Transactional
    public void deleteKhachHang(UUID khachHangId) {
        // Lấy danh sách giỏ hàng liên quan đến khách hàng
        List<GioHang> gioHangList = gioHangRepository.findByKhachHang_Id(khachHangId);

        for (GioHang gioHang : gioHangList) {
            // Xóa chi tiết giỏ hàng trước
            gioHangChiTietRepository.deleteByGioHang_Id(gioHang.getId());

            // Xóa giỏ hàng
            gioHangRepository.delete(gioHang);
        }

        // Xóa khách hàng
        khachHangRepository.deleteById(khachHangId);
    }
    
 

}
