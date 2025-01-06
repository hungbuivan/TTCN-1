package com.example.sd_41.service.GioHang;

import com.example.sd_41.model.GioHangChiTiet;

import java.util.UUID;

public interface GioHangChiTietImpl {

    void add(GioHangChiTiet gioHangChiTiet);

    GioHangChiTiet findById(UUID id);

    void update(UUID id, GioHangChiTiet update);

}
