package com.example.sd_41.controller.Admin;

import com.example.sd_41.model.Form;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.FormRepository;
import com.example.sd_41.service.admin.FormService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/form")
public class FormController {
    @Autowired
    private FormService formService;
    @Autowired
    private FormRepository formRepository;


    @GetMapping()
    public String form(){
        return "Form/index";
    }

    @GetMapping("/hien-thi")
    public String hienThi(Model model, @RequestParam(name = "num",defaultValue = "0") Integer num) {
        Page<Form> formPage = formRepository.findAll(PageRequest.of(num,3));
        model.addAttribute("list", formPage.getContent());
        model.addAttribute("next",num);
        model.addAttribute("totalPages", formPage.getTotalPages());
        return "/Form/index";
    }
    @GetMapping("/search")
    public String searchByName(@RequestParam("tenForm") String tenForm, Model model) {
        List<Form> searchResults = formService.searchByTen(tenForm);
        model.addAttribute("list", searchResults);
        return "/Form/index";
    }
    @GetMapping("/filter")
    public String filter(@RequestParam("trangThai") int trangThai, Model model) {
        List<Form> searchResults = formService.getAll();
        List<Form> list = new ArrayList<>();
        if(trangThai!=2){
            for (Form kieu : searchResults) {
                if(kieu.getTrangThai()==trangThai){
                    list.add(kieu);
                }

            }
        } else {
            for (Form kieu : searchResults) {
                list.add(kieu);
            }
        }
        model.addAttribute("list", list);
        return "/Form/index";
    }
    @GetMapping("/view-add")
    public String viewadd(Model model){
        model.addAttribute("form",new Form());
        return "Form/view-add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("form") @Valid Form form,
                      BindingResult bindingResult,
                      Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("form",form);
            return "/Form/view-add";
        }else {
            formService.save(form);
            return "redirect:/form/hien-thi";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id){
        formService.delete(id);
        return "redirect:/form/hien-thi";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable UUID id, Model model){
        Form form = formService.getOne(id);
        model.addAttribute("form",form);
        return "Form/detail";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") String id,Form form){
        formService.save(form);
        return "redirect:/form/hien-thi";
    }

}
