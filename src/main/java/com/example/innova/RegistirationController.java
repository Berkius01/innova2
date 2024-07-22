package com.example.innova;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RegistirationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path="admin/user/create/user" ,params= {"add"})
    public ModelAndView createUser(UserModel user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ModelAndView("redirect:http://localhost:8080/admin/home");
        //return userRepository.save(user);
    }
}
