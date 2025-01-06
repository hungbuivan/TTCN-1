package com.example.sd_41.service.GiayTheThao;

import com.example.sd_41.model.GiayTheThao;
import com.example.sd_41.model.GiayTheThaoChiTiet;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoChiTietRepository;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GiayTheThaoService implements GiayTheThaoImpl{

    @Autowired
    private GiayTheThaoRepository giayTheThaoRepository;

    public List<GiayTheThao> findGiayTheThao(String GiayTheThao){

        if(GiayTheThao !=null){

            return giayTheThaoRepository.findByTenGiayTheThao(GiayTheThao);

        }else{

             return giayTheThaoRepository.findAll();

        }

    }

    @Override
    public List<GiayTheThao> getAll() {

        return this.giayTheThaoRepository.findAll();
    }

    @Override
    public List<GiayTheThao> getAllWithoutInCTGGCTSP(UUID id) {
        return this.giayTheThaoRepository.getAllWithoutInCTGGCTSP();
    }

    @Override
    public GiayTheThao getOne(UUID id) {
        return this.giayTheThaoRepository.findById(id).get();
    }


    public int countGttInCtgg() {
        return this.giayTheThaoRepository.countGttInCtgg();
    }
    
    public void update(GiayTheThao gtt){
        giayTheThaoRepository.save(gtt);
    }
    
    public void deleteGiayTheThao(UUID id) {
        giayTheThaoRepository.deleteById(id);
    }

 
    
}
