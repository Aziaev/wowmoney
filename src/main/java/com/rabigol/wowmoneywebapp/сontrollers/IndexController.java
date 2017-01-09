package com.rabigol.wowmoneywebapp.сontrollers;

import com.rabigol.wowmoneywebapp.domain.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.rabigol.wowmoneywebapp.service.user.UserServiceImpl.getUserId;
import static com.rabigol.wowmoneywebapp.service.user.UserServiceImpl.getUsername;

@Controller
@RequestMapping("/")
public class IndexController {


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("username", getUsername());
        model.addAttribute("userId", getUserId());
        return "/index";
    }
}
