package pl.bajerska.befindyourmeal.user;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping("/add")
    @ApiOperation(value = "Preparing empty User class as container for User provided data.", response = String.class)
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping("/register")
    @ApiOperation(value = "Adding new User.", response = String.class)
    public String register(User user) {
        user.setRole(UserRole.USER);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.add(user);
        return "redirect:/login";
    }

    @GetMapping("/user")
    @ApiOperation(value = "Get information about currently logged in user.", response = String.class)
    public String user(Model model, Authentication authentication) {
        model.addAttribute("user", userService.findByUsername(((UserDetails) authentication.getPrincipal()).getUsername()));
        return "user";
    }

    @GetMapping("/admin")
    @ApiOperation(value = "Get information about currently logged in admin and information about all users.", response = String.class)
    public String getAllUsersList(Model model, Authentication authentication) {
        model.addAttribute("user", userService.findAll());

        return "admin";
    }

    @GetMapping("/findform")
    @ApiOperation(value = "Preparing empty user class to get User by username.", response = String.class)
    public String findForm(Model model) {
        model.addAttribute("finduser", new User());
        return "find";
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ApiOperation(value = "Get User by username.", response = String.class)
    public String findUser(Model model, @RequestParam String username) {
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "user";
    }

    @RequestMapping(value = {"/index/edit/{username}", "/user/edit/{username}"}, method = RequestMethod.GET)
    @ApiOperation(value = "Editing User - username, password and role.", response = String.class)
    public String editUser(Model model, @PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        user.setPassword("");
        model.addAttribute("user", user);
        return "edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "Changing password for the User.", response = String.class)
    public String saveUser(Model model, @ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.update(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/index/delete/{username}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete User by username.", response = String.class)
    public String deleteUser(@PathVariable("username") String username) {
        userService.delete(username);
        return "redirect:/admin";
    }

}
