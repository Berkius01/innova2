package com.example.innova;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserRepository userRepository;




    @PostMapping(path = "admin/transaction",params= {"add"})
    public ModelAndView createAdminTransaction(Transaction transaction) {
        UserModel user;
        user = new UserModel();
        user.setId(getCurrentUserId());
        transaction.setUser(user);
        transaction.setDate(LocalDate.now());
        transactionService.createTransaction(transaction);
        return new ModelAndView("redirect:http://localhost:8080/admin/home");
    }

    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable Long id) {
        return transactionService.getTransaction(id);
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
    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        return transactionService.updateTransaction(id, transaction);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }*/
/*
    @GetMapping("/total/{userId}")
    public Double getTotalSpending(@PathVariable Long userId) {
        return transactionService.getTotalSpending(userId);
    }*/
}