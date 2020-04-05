package pl.bajerska.befindyourmeal.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

    private final BCryptPasswordEncoder passwordEncoder;

    private UserService userService;


    public UserController(UserService userService, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String main(Model model) {
        return "main";
    }

//    @GetMapping("/login")
//    public String login(Model model) {
//        return "login";
//    }

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
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String user(Model model, Authentication authentication) {
        model.addAttribute("user", userService.findByUsername(((UserDetails) authentication.getPrincipal()).getUsername()));
        return "user";
    }

    @GetMapping("/admin")
    public String getAllUsersList(Model model, Authentication authentication) {
        model.addAttribute("user", userService.findAll((User) authentication.getPrincipal()));
        return "admin";
    }

    @GetMapping("/findform")
    public String findForm(Model model) {
        model.addAttribute("finduser", new User());
        return "find";
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public String findUser(Model model, @RequestParam String username) {
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "user";
    }

    @RequestMapping(value = {"/index/edit/{username}", "/user/edit/{username}"}, method = RequestMethod.GET)
    public String editUser(Model model, @PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        user.setPassword("");
        model.addAttribute("user", user);
        return "edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String saveUser(Model model, @ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.update(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/index/delete/{username}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("username") String username) {
        userService.delete(username);
        return "redirect:/admin";
    }

    @GetMapping("/addrecipe")
    public String addrecipe(Model model) {
        return "addrecipe";
    }

    @GetMapping("/findrecipe")
    public String findrecipe(Model model) {
        return "findrecipe";
    }
}
