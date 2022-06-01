package ru.skillbox.app.services;

//import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.skillbox.web.dto.LoginForm;

@Service
public class LoginService {

//    private final Logger logger = Logger.getLogger(LoginService.class);

    public boolean authenticate(LoginForm loginForm) {
//        logger.info("try auth with user-form: " + loginForm);
        return loginForm.getUsername().equals("root") && loginForm.getPassword().equals("123");
    }
}
