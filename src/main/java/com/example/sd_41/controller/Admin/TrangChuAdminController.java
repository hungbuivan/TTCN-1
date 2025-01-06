package com.example.sd_41.controller.Admin;


import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "TrangChu_Admin")
public class TrangChuAdminController {

    @Autowired
    ServletContext context;

    //Trang mặc định đầu tiên của Admin
     @GetMapping("/listViewShowDetail")
     public String listShowThongTinForm(Model model){

           return "/templates/Admin/trangChu";

     }



    //Chuyển hướng đến trang nhân viên

    //Chuyển hướng đến trang hóa đơn


}
