package com.web2.lab2.controller;

import com.web2.lab2.model.UserTable;
import com.web2.lab2.repository.UsertableRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    UsertableRepository usertableRepository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/api/unsafeLogin")
    public String unsafeLogin(Model model, @RequestParam("username") String username, @RequestParam("password") String password) {

        String passwordHash = DigestUtils.sha512Hex(password).substring(0, 50);

        try {
            // Hardcoded jer ne mogu hostati 2 baze podataka
            // UserTable user = usertableRepository.findUserTableByUsername(username);

            UserTable user = new UserTable();
            user.setUsername("Mario");
            user.setPassword(DigestUtils.sha512Hex("lozinka").substring(0, 50));
            //if(user.equals(null)) {
            if(!user.getUsername().equals(username)){
                // Wrong username
                System.out.println("Username doesn't exist");
                model.addAttribute("status", "Username doesn't exist");
                model.addAttribute("color", "red");
                return "index";
            } else if (!passwordHash.equals(user.getPassword())) {
                // Wrong password
                System.out.println("Incorrect password");
                model.addAttribute("status", "Incorrect password");
                model.addAttribute("color", "red");
                return "index";
            } else {
                // Login successful
                System.out.println("Login successful");
                model.addAttribute("status", "Login successful");
                model.addAttribute("color", "green");
                return "index";
            }
        } catch(Exception e) {
            System.out.println("Username doesn't exist");
            model.addAttribute("status", "Username doesn't exist");
            model.addAttribute("color", "red");
            return "index";
        }
    }

    @PostMapping("/api/safeLogin")
    public String safeLogin(Model model, @RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            password = password.substring(0, 50);
            //UserTable user = usertableRepository.findUserTableByUsername(username);
            UserTable user = new UserTable();
            user.setUsername("Mario");
            user.setPassword(DigestUtils.sha512Hex("lozinka").substring(0, 50));
            //if(user.equals(null)) {
            if(!user.getUsername().equals(username)){
                // Wrong username
                System.out.println("Wrong username or password");
                model.addAttribute("status", "Wrong username or password");
                model.addAttribute("color", "red");
                return "index";
            } else if (!password.equals(user.getPassword())) {
                // Wrong password
                System.out.println("Wrong username or password");
                model.addAttribute("status", "Wrong username or password");
                model.addAttribute("color", "red");
                return "index";
            } else {
                // Login successful
                System.out.println("Login successful");
                model.addAttribute("status", "Login successful");
                model.addAttribute("color", "green");
                return "index";
            }
        } catch(Exception e) {
            System.out.println("Wrong username or password");
            model.addAttribute("status", "Wrong username or password");
            model.addAttribute("color", "red");
            return "index";
        }
    }
}
