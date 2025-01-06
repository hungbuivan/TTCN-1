package com.example.sd_41.service.admin;

import com.example.sd_41.model.KhachHang;
import com.example.sd_41.model.User;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.UserRepository;
import com.example.sd_41.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService  implements UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getOne(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public void save(User user) {

        this.userRepository.save(user);

    }

    public void delete(UUID id) {
        this.userRepository.deleteById(id);
    }

    public List<User> searchByTen(String tenUser) {
        return userRepository.findByTenUserContainingOrderById(tenUser);
    }

    public List<User> searchByTrangThai(Integer trangThai) {
        return userRepository.findByTrangThaiOrderById(trangThai);
    }



    public User findByEmail(String email) {

        return userRepository.findByEmail(email);

    }

    //Update lại thông tin của khách hàng
    @Override
    public void update(UUID id, User update){

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()){

            User user = optionalUser.get();

            user.setMaUser(update.getMaUser());
            user.setEmail(update.getEmail());
            user.setMatKhau(update.getMatKhau());
            user.setTenUser(update.getTenUser());
            user.setGioiTinh(update.getGioiTinh());
            user.setNgaySinh(update.getNgaySinh());
            user.setMaCCCD(update.getMaCCCD());
            user.setHoKhau(update.getHoKhau());
            user.setSoDienThoai(update.getSoDienThoai());
            user.setDiaChi(update.getDiaChi());
            user.setGhiChu(update.getGhiChu());
            user.setNgayTao(update.getNgayTao());
            user.setNgaySua(update.getNgaySua());
            user.setTrangThai(update.getTrangThai());
            user.setRole(update.getRole());

            userRepository.save(user);

        }

    }

}
