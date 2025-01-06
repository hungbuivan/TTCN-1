package com.example.sd_41.service.GioHang;

import com.example.sd_41.model.GioHangChiTiet;
import com.example.sd_41.repository.BanHang.GioHangChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GioHangChiTietService implements GioHangChiTietImpl{

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    //Todo code delete giày thể thao chi tiết ra khỏi giỏ hàng

    public void delete(UUID id){

        if(gioHangChiTietRepository.existsById(id)){

            gioHangChiTietRepository.deleteById(id);

        }

    }


    public void themVaoGioHangChiTiet(GioHangChiTiet gioHangChiTiet){

        gioHangChiTietRepository.save(gioHangChiTiet);

    }

    //
    @Override
    public void add(GioHangChiTiet gioHangChiTiet){

        gioHangChiTietRepository.save(gioHangChiTiet);

    }

    @Override
    public GioHangChiTiet findById(UUID id){

        Optional<GioHangChiTiet> optionalGioHangChiTiet = gioHangChiTietRepository.findById(id);
        return optionalGioHangChiTiet.orElse(null);

    }

    //Todo code update lại giỏ hàng
    @Override
    public void update(UUID id, GioHangChiTiet update) {

        Optional<GioHangChiTiet> optionalGioHangChiTiet = gioHangChiTietRepository.findById(id);
        if (!optionalGioHangChiTiet.isPresent()) {
            throw new RuntimeException("...,");
        }

        GioHangChiTiet gioHangChiTiet = optionalGioHangChiTiet.get();
        gioHangChiTiet.setGioHang(update.getGioHang());
        gioHangChiTiet.setGiayTheThaoChiTiet(update.getGiayTheThaoChiTiet());
        gioHangChiTiet.setSoLuong(update.getSoLuong());
        gioHangChiTiet.setDonGia(update.getDonGia());
        gioHangChiTiet.setTrangThai(update.getTrangThai());
        gioHangChiTietRepository.save(gioHangChiTiet);

    }


}
