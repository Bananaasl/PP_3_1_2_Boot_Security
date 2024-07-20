package ru.kata.spring.boot_security.demo.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repos.RoleRepos;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepos roleRepos;

    @Autowired
    public AdminController(UserService userService, RoleRepos roleRepos) {
        this.userService = userService;
        this.roleRepos = roleRepos;
    }

    @GetMapping
    public String allUserTable(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/user")
    public String showUser(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "user";
    }

    @GetMapping("/new")
    public String createUserForm(@ModelAttribute("user") User user) {
        List<Role> roles = roleRepos.findAll();
        user.setRoles(roles);
        return "new";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String createUpdateForm(@RequestParam(value = "id") Long id, Model model) {
        List<Role> roles = roleRepos.findAll();
        User user = userService.findOne(id);
        user.setRoles(roles);
        model.addAttribute("user", userService.findOne(id));
        return "update";
    }

    @PostMapping("/user")
    public String updateUser(@RequestParam(value = "id") Long id, @ModelAttribute("user") User user) {
        userService.update(id, user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
