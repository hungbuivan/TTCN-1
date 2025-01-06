package com.example.sd_41.service.ViTien;

import com.example.sd_41.model.ViTien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.sd_41.service.ViTien.Impl.viTienServiceImpl;
import com.example.sd_41.repository.ViTien.viTienRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class viTienService implements viTienServiceImpl{

    @Autowired
    viTienRepository viTienRepository;

    @Override
    public void add(ViTien viShop) {

        viTienRepository.save(viShop);

    }

    @Override
    public void update(UUID id, ViTien updateVi) {

        Optional<ViTien> optionalViShop = viTienRepository.findById(id);

        if (optionalViShop.isPresent()){

            ViTien viShop = optionalViShop.get();
            viShop.setMaViTien(updateVi.getMaViTien());
            viShop.setKhachHang(updateVi.getKhachHang());
            viShop.setThanhTien(updateVi.getThanhTien());
            viShop.setTrangThai(updateVi.getTrangThai());

            viTienRepository.save(viShop);

        }
    }

    @Override
    public ViTien findByIdKhachHang(UUID id) {

        return viTienRepository.findByIdKhachHang(id);

    }




}
