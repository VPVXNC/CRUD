package myapp.controller;

import myapp.model.User;
import myapp.service.UserService;
import myapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String listOfUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/allUsers";
    }

    @GetMapping("/newPage")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/newUser";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/editTable";
    }

    @PostMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.updateUser(user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }
}
