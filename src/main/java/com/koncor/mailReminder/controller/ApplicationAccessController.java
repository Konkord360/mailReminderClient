package com.koncor.mailReminder.controller;

import com.koncor.mailReminder.model.User;
import com.koncor.mailReminder.services.SecurityService;
import com.koncor.mailReminder.services.UserService;
import com.koncor.mailReminder.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ApplicationAccessController {
    private final UserService userService;
    private final SecurityService securityService;
    private UserValidator userValidator;

    @Autowired
    public ApplicationAccessController(UserService userService, SecurityService securityService, UserValidator userValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute("user", new User());

        if (securityService.findLoggedInUsername() != null && !securityService.findLoggedInUsername().equals("anonymousUser"))
            return "redirect:/dashboard";
        return "register";
    }

    //
    @PostMapping("/register")
    public String registerUser(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.save(user);
        securityService.autoLogin(user.getUsername(), user.getPasswordConfirm());
        System.out.println("LOGGED IN USER ******************* " + securityService.findLoggedInUsername());
        return "redirect:/dashboard";
    }

    //
    @GetMapping("/dashboard")
    public String displayDashboard(Model model) {
        String username = securityService.findLoggedInUsername();

        User user = new User(username, "");
        System.out.println(username);
        model.addAttribute("user", user);
        return "dashboard";
    }

    @PostMapping("/dashboard")
    public String dashboard(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        return "dashboard";
    }

    //for Future uses
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminPage() {

        ModelAndView m = new ModelAndView();
        m.addObject("title", "Spring Security Custom Login Form Example");
        m.addObject("message", "This is protected page!");
        m.setViewName("admin");

        return m;
    }

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
        if (securityService.findLoggedInUsername() != null && !securityService.findLoggedInUsername().equals("anonymousUser")) {
            m.setViewName("redirect:/dashboard");
            return m;
        }
        m.setViewName("login");
        return m;
    }
}
