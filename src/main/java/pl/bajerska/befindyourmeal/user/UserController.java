package pl.bajerska.befindyourmeal.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private UserService userService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;

    }

    @GetMapping("/")
    public String main(Model model) {
        return "main";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "user";
    }

    @GetMapping("/for-admin")
    public String forAdmin() {
        return "admin";
    }

    @GetMapping("/for-user")
    public String forUser() {
        return "user";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping("/register")
    public String register(User user) {
        user.setRole(UserRole.USER);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.add(user);
        return "add";
    }

    @GetMapping("/user")
    public String user(Model model, Authentication authentication) {
        model.addAttribute("user", userService.findByUsername(((UserDetails) authentication.getPrincipal()).getUsername()));
        return "user";
    }

    @GetMapping("/admin")
    public String getAllUsersList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

}
