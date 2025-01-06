package com.example.sd_41.repository.HoaDon;

import com.example.sd_41.model.GiayTheThaoChiTiet;
import com.example.sd_41.model.HoaDon;
import com.example.sd_41.model.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, UUID> {

    List<HoaDonChiTiet> findByHoaDon_Id(UUID id);

    //Todo code bán hàng tại quầy
    List<HoaDonChiTiet> findAllByHoaDon(HoaDon hd);

    int countByHoaDonAndGiayTheThaoChiTiet(HoaDon hd, GiayTheThaoChiTiet gttct);

    HoaDonChiTiet findByHoaDonAndGiayTheThaoChiTiet(HoaDon hd, GiayTheThaoChiTiet gttct);

    //Todo code
    List<HoaDonChiTiet> findByHoaDon_IdAndTrangThai(UUID idHoaDon, int trangThai);


}
