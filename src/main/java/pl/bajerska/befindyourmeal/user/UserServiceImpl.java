package pl.bajerska.befindyourmeal.user;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.bajerska.befindyourmeal.exception.InvalidUserEmailException;
import pl.bajerska.befindyourmeal.exception.InvalidUserPasswordException;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
    }

    @Override
    public User update(User user) throws InvalidUserPasswordException, InvalidUserEmailException {
        if (user.getPassword() == null || user.getPassword().isEmpty()){
            throw new InvalidUserPasswordException(user);
        }
//        Pattern pwdPattern = Pattern.compile("^((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})$");
//        if (!pwdPattern.matcher(user.getPassword()).matches()){
//            throw new InvalidUserPasswordException(user);
//        }
        Pattern mailPattern = Pattern.compile("^[-\\w\\.]+@([-\\w]+\\.)+[a-z]+$");
        if (!mailPattern.matcher(user.getUsername()).matches()){
            throw new InvalidUserEmailException(user);
        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User add(User user) throws InvalidUserPasswordException, InvalidUserEmailException {
        if (findByUsername(user.getUsername()) != null) {
            return null;
        }
        return update(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean delete(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            userRepository.deleteById(user.getId());
            return true;
        }
        return false;
    }

    @Override
    @ModelAttribute("users")
    public List<User> findAll(User principal) {
        return (List<User>) userRepository.findAll();
    }


}

