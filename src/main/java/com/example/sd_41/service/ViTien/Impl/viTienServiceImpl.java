package com.example.sd_41.service.ViTien.Impl;

import com.example.sd_41.model.ViTien;

import java.util.UUID;

public interface viTienServiceImpl {

    void add(ViTien viTien);

    void update(UUID id, ViTien updateViTien);

    ViTien findByIdKhachHang(UUID id);

}
