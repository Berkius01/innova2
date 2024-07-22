package com.example.innova;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping(path= "innova/admin",method= {RequestMethod.GET,RequestMethod.DELETE,RequestMethod.POST,RequestMethod.PUT})
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("admin/user/get")
    public ModelAndView userGetAll(){
        ModelAndView mav = new ModelAndView("list-users.html");
        mav.addObject("users",userRepository.findAll());
        return mav;
    }

    @PostMapping("admin/user/post")
    public ModelAndView postCustomer(){
        return new ModelAndView("add-user.html");
    }
/*
    @PostMapping(path="post", params= {"add"})			// o sayfanın kayıt etmesi
    public ModelAndView addCustomer(UserModel user) {
        userRepository.save(user.setPassword());
        return new ModelAndView("redirect:/api/v1/customer/get");
    }*/

    @GetMapping("admin/home")
    public ModelAndView adminHome(){
        ModelAndView mav = new ModelAndView("admin_home.html");
        mav.addObject("transactions",transactionRepository.findAll());
        return mav;
    }

    @PostMapping("admin/post")//user transaction post
    public ModelAndView transactionPost(){
        return new ModelAndView("add-transaction.html");
    }

    @GetMapping("admin/transaction/get")
    public ModelAndView transactionGetAll(){
        ModelAndView mav = new ModelAndView("list-transactions.html");
        mav.addObject("transactions",transactionRepository.findAll());
        return mav;
    }




    @GetMapping("user/home")
    public ModelAndView userHome(){
        ModelAndView mav = new ModelAndView("user-home.html");
        mav.addObject("transactions",transactionRepository.findAll());
        return mav;
    }

    @PostMapping("user/post")//user transaction post
    public ModelAndView userPost(){
        return new ModelAndView("add-transaction.html");
    }
/*
    @PostMapping("transaction")
    public ModelAndView transactionPost(){

    }*/
}
