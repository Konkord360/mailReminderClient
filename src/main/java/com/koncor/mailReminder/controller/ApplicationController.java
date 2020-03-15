package com.koncor.mailReminder.controller;

import com.koncor.mailReminder.accessDataJPA.User;
import com.koncor.mailReminder.accessDataJPA.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ApplicationController {

    private final UserRepository userRepository;
    //
    private final PasswordEncoder passwordEncoder;

    public ApplicationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());

        return "register";
    }

    //
    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView;

        if (this.userRepository.findByUsername(user.getUsername()) != null) {
            bindingResult.rejectValue("username", "userAlreadyExists");
            modelAndView = new ModelAndView("register");
            modelAndView.addObject("user", user);
            return modelAndView;
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        modelAndView = new ModelAndView("redirect:/login");
        user.setUsername(user.getUsername().toUpperCase());
        user.setPassword(hashedPassword);

        this.userRepository.save(user);
        System.out.println(user.toString());

        return modelAndView;
    }

    //
    @GetMapping("/dashboard")
    public String displayDashboard(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = new User(username, "", true);
        System.out.println(username);
        model.addAttribute("user", user);
        return "dashboard";
    }

    //for Future uses
// If user will be successfully authenticated he/she will be taken to the login secure page.
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminPage() {

        ModelAndView m = new ModelAndView();
        m.addObject("title", "Spring Security Custom Login Form Example");
        m.addObject("message", "This is protected page!");
        m.setViewName("admin");

        return m;
    }

    // Spring security will see this message.
    @GetMapping(value = "/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView m = new ModelAndView();
        if (error != null) {
            m.addObject("error", "Invalid username and password error.");
        }

        if (logout != null) {
            m.addObject("msg", "You have left successfully.");
        }

        m.setViewName("login");
        return m;
    }
}
