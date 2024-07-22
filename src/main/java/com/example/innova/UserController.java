package com.example.innova;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Optional;

@Controller
//@RequestMapping(path= "innova/admin",method= {RequestMethod.GET,RequestMethod.DELETE,RequestMethod.POST,RequestMethod.PUT})
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TransactionService transactionService;



    @GetMapping("admin/user/get")
    public ModelAndView userGetAll(){
        ModelAndView mav = new ModelAndView("list-users.html"); //admin bütün kullanıcıları görüntüler
        mav.addObject("users",userRepository.findAll());
        return mav;
    }

    @PostMapping("admin/user/post")
    public ModelAndView postCustomer(){ //adminin kullanıcı eklemek için kullanacağı form yüklenir
        return new ModelAndView("add-user.html");
    }
/*
    @PostMapping(path="post", params= {"add"})			// o sayfanın kayıt etmesi
    public ModelAndView addCustomer(UserModel user) {
        userRepository.save(user.setPassword());
        return new ModelAndView("redirect:/api/v1/customer/get");
    }*/

    @GetMapping("admin/home")
    public ModelAndView adminHome(){ //admin default home
        ModelAndView mav = new ModelAndView("admin_home.html");
        mav.addObject("transactions",transactionRepository.findAll());
        return mav;
    }

    @PostMapping("admin/post")//user transaction post
    public ModelAndView transactionPost(){ //adminin transaction ekleyeceği yer
        return new ModelAndView("add-transaction.html");
    }

    @GetMapping("admin/transaction/get")
    public ModelAndView transactionGetAll(){//adminin bütün transactionu gördüğü yer
        ModelAndView mav = new ModelAndView("list-transactions.html");
        mav.addObject("transactions",transactionRepository.findAll());
        return mav;
    }

    @DeleteMapping("admin/user/delete/{id}")
    public ModelAndView deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
        ModelAndView mav = new ModelAndView("list-users.html"); //admin bütün kullanıcıları görüntüler
        mav.addObject("users",userRepository.findAll());
        return mav;
    }

    @PutMapping("admin/user/{id}")
    public ModelAndView updateUser(@PathVariable Long id){
        //userRepository.
        return (new ModelAndView("update-user.html")).addObject("id",id);
    }
    /*
    @PutMapping("admin/users/{id}")
    public ModelAndView update(@PathVariable Long id,
                               @RequestParam("username")String username,
                               @RequestParam("email")String email,
                               @RequestParam("password")String password,
                               @RequestParam("role")String role){

        userRepository.updateUser(id,username,email,passwordEncoder.encode(password),role);
        return new ModelAndView("redirect:http://localhost:8080/admin/user/get");
    }*/
    @PostMapping("admin/update/{id}")
    public ModelAndView update(@PathVariable Long id, UserModel user){
        userRepository.updateUser(id,user.username,user.email,user.password,user.role);
        return new ModelAndView("redirect:http://localhost:8080/admin/user/get");
    }





    @GetMapping("user/home")
    public ModelAndView userHome(Authentication authentication){
        ModelAndView mav = new ModelAndView("user-home.html");
        String username = authentication.getName();
        Optional<UserModel> user = userRepository.findByUsername(username);
        Long id = user.get().id;
        mav.addObject("transactions",transactionRepository.getAllByUserModel(id));

        return mav;
    }

    @PostMapping("user/post")//user transaction post
    public ModelAndView userPost(){
        return new ModelAndView("add-transaction.html");
    }

    @PostMapping(path = "user/post/transaction",params= {"add"})
    public ModelAndView createUserTransaction(Transaction transaction) {
        UserModel user;
        user = new UserModel();
        user.setId(getCurrentUserId());
        transaction.setUser(user);
        transaction.setDate(LocalDate.now());
        transactionRepository.save(transaction);
        return new ModelAndView("user-home.html");
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // Burada kullanıcı adını almak için userDetails.getUsername() kullanıyoruz.
        // Ancak, kullanıcı kimliğini almak için farklı bir yaklaşıma ihtiyacınız olabilir.
        // Örneğin, kullanıcı adını kullanarak veri tabanından kullanıcı kimliğini sorgulamak.
        Optional<UserModel> user = userRepository.findByUsername(userDetails.getUsername());
        System.out.println("username+++++"+userDetails.getUsername());
        System.out.println(user.map(UserModel::getId).orElseThrow(()->new RuntimeException("User Not Found")));
        return user.map(UserModel::getId).orElseThrow(()->new RuntimeException("User Not Found"));
        //}
        // return null;
    }


/*
    @PostMapping("transaction")
    public ModelAndView transactionPost(){

    }*/
}
