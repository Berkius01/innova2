package com.example.innova;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping(path= "innova/admin",method= {RequestMethod.GET,RequestMethod.DELETE,RequestMethod.POST,RequestMethod.PUT})
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("admin/user/get")
    public ModelAndView adminGetAll(){
        ModelAndView mav = new ModelAndView("list-users.html");
        mav.addObject("users",userRepository.findAll());
        return mav;
    }
}
