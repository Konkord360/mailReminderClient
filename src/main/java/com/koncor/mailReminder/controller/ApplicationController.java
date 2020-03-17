package com.koncor.mailReminder.controller;

import com.koncor.mailReminder.model.User;
import com.koncor.mailReminder.repository.UserRepository;
import com.koncor.mailReminder.security.SecurityService;
import com.koncor.mailReminder.services.UserService;
import com.koncor.mailReminder.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());

        return "register";
    }

    //
    @PostMapping("/register")
    public String registerUser(@ModelAttribute @Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(user);

        securityService.autoLogin(user.getUsername(), user.getPasswordConfirm());
        System.out.println("LOGGED IN USER: " + securityService.findLoggedInUsername());
        return "redirect:/dashboard";
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

        User user = new User(username, "");
        System.out.println(username);
        model.addAttribute("user", user);
        return "dashboard";
    }

    @PostMapping("/dashboard")
    public String dashboard(@ModelAttribute @Valid User user, BindingResult bindingResult){
        System.out.println("TUTAJ");
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
