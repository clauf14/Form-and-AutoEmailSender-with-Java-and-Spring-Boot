package com.example.forms;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private EmailService emailService;

    public HomeController(EmailService emailService){
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/register")
    public String userRegistration(@ModelAttribute Customer customer, Model model){
        model.addAttribute("firstName", customer.getFirstName());
        model.addAttribute("lastName", customer.getLastName());
        model.addAttribute("email", customer.getEmail());
        this.emailService.sendMessage(
                customer.getEmail(),
                "\uD83C\uDF89Here is your surprise!\uD83E\uDD73",
                "\uD83D\uDE4F\uD83C\uDFFBThank you for completing my form " + customer.getFirstName() + ", and helping me develop my simple API.\nThis is a test message.\n" +
                        "\uD83D\uDCDDHere is your data from the form -> " + customer.toString() + "\n⚙️Here is the source code used for this API -> https://github.com/clauf14/Form-and-AutoEmailSender-with-Java-and-Spring-Boot" +
                        "\n\uD83D\uDC4B\uD83C\uDFFBThank you so much and have a great day!"
        );
        System.out.println("Mail sent succsessfully!");
        return "thanks";
    }
}
