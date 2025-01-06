package com.example.sd_41.service.GiayTheThao;

import com.example.sd_41.model.GiayTheThao;
import com.example.sd_41.model.GiayTheThaoChiTiet;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;



@Service
public class GiayTheThaoChiTietService {

    @Autowired
    private GiayTheThaoChiTietRepository repo;

    //Todo code bán hàng tại quầy
    public List<GiayTheThaoChiTiet> getAll(){

        return repo.findAll();

    }

    public List<GiayTheThaoChiTiet> searchByName(String name) {
        System.out.println("Name: "+name);
        return repo.findAllByNameProduct(name);
    }

    public GiayTheThaoChiTiet getByGttMsSize(UUID idGtt, UUID idMauSac, UUID idSize) {

        return repo.findByGiayTheThaoAndSizeAndMauSac(idGtt, idSize, idMauSac);
    }
    public void saveAll(List<GiayTheThaoChiTiet> list) {
        repo.saveAll(list);
    }
    public List<GiayTheThaoChiTiet> getAllByGiayTheThao(GiayTheThao gtt) {
        return repo.findByGiayTheThao(gtt);
    }
 // Phương thức xóa một chi tiết giày thể thao theo GiayTheThaoId và chi tiết GiayTheThaoChiTietId
    public void deleteByGiayTheThaoAndChiTietId(UUID giayTheThaoId, UUID giayTheThaoChiTietId) {
        // Lấy tất cả chi tiết giày thể thao liên quan đến GiayTheThaoId
        List<GiayTheThaoChiTiet> chiTietList = repo.findAll()
            .stream()
            .filter(chiTiet -> chiTiet.getGiayTheThao().getId().equals(giayTheThaoId))
            .collect(Collectors.toList());

        // Kiểm tra xem có chi tiết giày thể thao với giayTheThaoChiTietId không
        for (GiayTheThaoChiTiet chiTiet : chiTietList) {
            if (chiTiet.getId().equals(giayTheThaoChiTietId)) {
                repo.deleteById(chiTiet.getId());
                break; // Ngừng vòng lặp khi đã xóa xong chi tiết
            }
        }
    }
}
