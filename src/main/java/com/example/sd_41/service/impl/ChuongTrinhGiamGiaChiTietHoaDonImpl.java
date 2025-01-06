package com.example.sd_41.service.impl;

import com.example.sd_41.model.ChuongTrinhGiamGiaChiTietHoaDon;
import com.example.sd_41.model.ChuongTrinhGiamGiaHoaDon;

import java.util.List;

public interface ChuongTrinhGiamGiaChiTietHoaDonImpl {

    public List<ChuongTrinhGiamGiaChiTietHoaDon> getById(ChuongTrinhGiamGiaHoaDon ctggHD);
    public List<ChuongTrinhGiamGiaChiTietHoaDon> getAllByTrangThaiHD();

}
