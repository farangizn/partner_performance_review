package org.example.marketing.controller;

import lombok.RequiredArgsConstructor;
import org.example.marketing.entity.Blogger;
import org.example.marketing.entity.BloggerClient;
import org.example.marketing.entity.Client;
import org.example.marketing.entity.Link;
import org.example.marketing.repo.BloggerClientRepository;
import org.example.marketing.repo.BloggerRepository;
import org.example.marketing.repo.ClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final ClientRepository clientRepository;
    private final BloggerRepository bloggerRepository;
    private final BloggerClientRepository bloggerClientRepository;
    private String currentBloggerName;

    @GetMapping("/register/{bloggerName}")
    public String registrationPage(@PathVariable String bloggerName) {
        currentBloggerName = bloggerName;
        return "registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Client client) {

        client.setIsContacted(false);

        clientRepository.save(client);

        Optional<Blogger> bloggerOpt = bloggerRepository.findBloggerByName(currentBloggerName);

        if (bloggerOpt.isPresent()) {

            Blogger blogger = bloggerOpt.get();

            System.out.println(blogger.getName());
            System.out.println(client.getFirstname());

            BloggerClient bloggerClient = BloggerClient.builder().blogger(blogger).client(client).build();
            blogger.setClientNumber(blogger.getClientNumber() + 1);

            bloggerClientRepository.save(bloggerClient);
        }

        return "success";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @PostMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/report")
    public String report(Model model) {

        List<Blogger> bloggers = bloggerRepository.findAll();
        List<BloggerClient> bloggerClients = bloggerClientRepository.findAll();

        model.addAttribute("bloggerClients", bloggerClients);
        model.addAttribute("bloggers", bloggers);

        return "report";
    }

    @GetMapping("/blogger/add")
    public String addBloggerPage() {
        return "addBlogger";
    }

    @PostMapping("/blogger/add")
    public String addBlogger(@ModelAttribute Blogger blogger) {
        System.out.println("hi");
        bloggerRepository.save(blogger);
        Link link1 = Link.builder().blogger(blogger).title("http://localhost:8080/register/" + blogger.getName()).build();

        return "redirect:/admin";
    }

    @GetMapping("/info")
    public String saveContacts(@RequestParam Integer bloggerId, Model model) {
        Optional<Blogger> bloggerOpt = bloggerRepository.findById(bloggerId);
        if (bloggerOpt.isPresent()) {
            Blogger blogger = bloggerOpt.get();
            model.addAttribute("blogger", blogger);
            System.out.println(blogger.getName());
            model.addAttribute("clients", bloggerClientRepository.findBloggerClientByBlogger(blogger));
        }

        return "info";
    }

    @PostMapping("/save")
    public String saveContacts(@RequestParam Integer clientId,
                               @RequestParam Integer bloggerId,
                               @RequestParam(required = false) String isContacted) {
        Optional<Client> clientOpt = clientRepository.findById(clientId);

        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            boolean contacted = isContacted != null && isContacted.equals("on");
            client.setIsContacted(contacted);
            clientRepository.save(client);
        }
        return "redirect:/info?bloggerId=" + bloggerId;
    }

}
