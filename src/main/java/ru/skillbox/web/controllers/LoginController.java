package ru.skillbox.web.controllers;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.skillbox.app.services.LoginService;
import ru.skillbox.web.dto.LoginForm;

@Controller
public class LoginController {

//    private final Logger logger = Logger.getLogger(LoginController.class);
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
//        logger.info("GET /login returns login_page.html");
        model.addAttribute("loginForm", new LoginForm());
        return "login_page";
    }

    @PostMapping("/login/auth")
    public String authenticate(LoginForm loginForm) {
        if(loginService.authenticate(loginForm)) {
//            logger.info("login OK redirect to book shelf");
            return "redirect:/books/shelf";
        }
        else {
//            logger.info("login FAIL redirect back to login");
            return "redirect:/login";
        }
    }
}
