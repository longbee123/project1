package com.example.demo.controller;




import com.example.demo.entity.User;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.request.RegisterForm;
import com.example.demo.model.request.UpdateForm;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;

@Controller
public class TemplateController {
    @Autowired
    private UserService userService;
    @GetMapping("/listuser")
    public String list(Model model){
        model.addAttribute("listuser" ,userService.getListUser());
        return "list";
    }
    @GetMapping("/listuser/search")
        public String search(@ModelAttribute("name") String name , Model model){
            if(StringUtils.isEmpty(name)){
                return "redirect:/listuser";
            }
            model.addAttribute("listuser" , userService.search(name));
            return "list";
    }
    @GetMapping("/listuser/add")
    public String add(){

        return "form";
    }
//    @PostMapping("/listuser/create")
//    public String create( @ModelAttribute("register") @Valid RegisterForm form, BindingResult result, RedirectAttributes redirect) {
//        if (result.hasErrors()) {
//           return "form";
//        }
//        userService.createUser(form);
//        redirect.addFlashAttribute("successMessage", "Created user successfully!");
//        return "redirect:/listuser";
//    }
    @GetMapping("/listuser/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("user",userService.getUserById(id));

        return "formupdate";
    }
//    @PostMapping("/listuser/save")
//    public String save(@Valid UserDto userdto,BindingResult result, RedirectAttributes redirect) {
//        if (result.hasErrors()) {
//            return "formupdate";
//        }
//        userService.updateUser(userdto);
//        redirect.addFlashAttribute("successMessage", "Update user successfully!");
//        return "redirect:/listuser";
//
//    }
//    @GetMapping("/listuser/{id}/delete")
//    public String delete(@PathVariable("id") int id , RedirectAttributes redirect){
//        userService.deleteUser(id);
//        redirect.addFlashAttribute("successMessage", "Delete user successfully!");
//        return "redirect:/listuser";
//    }
}
