package com.example.sd_41.service.impl;

import com.example.sd_41.model.KhachHang;
import com.example.sd_41.model.User;

import java.util.UUID;

public interface UserServiceImpl {

    User findByEmail(String email);

    void update(UUID id, User update);


}
