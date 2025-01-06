package com.example.sd_41.repository.SanPham.AllGiayTheThao;

import com.example.sd_41.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FormRepository extends JpaRepository<Form, UUID> {

    List<Form> findByTenFormContainingOrderById(String tenForm);

    List<Form> findByTrangThai(int trangThai);

}