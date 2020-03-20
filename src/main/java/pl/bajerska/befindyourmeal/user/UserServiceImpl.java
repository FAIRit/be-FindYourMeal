package pl.bajerska.befindyourmeal.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User update(UserLoginData userLoginData, UserType userType) throws InvalidUserPasswordException, InvalidUserEmailException {
        if (userLoginData.getPassword() == null || userLoginData.getPassword().isEmpty()){
            throw new InvalidUserPasswordException("Empty password was given.");
        }
        Pattern pwdPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{6,}$");
        if (!pwdPattern.matcher(userLoginData.getPassword()).matches()){
            throw new InvalidUserPasswordException("Password does not match rules.");
        }
        Pattern mailPattern = Pattern.compile("^[-\\w\\.]+@([-\\w]+\\.)+[a-z]+$");
        if (!mailPattern.matcher(userLoginData.getEmail()).matches()){
            throw new InvalidUserEmailException("Email does not match rules.");
        }
        User user = new User();
        user.setEmail(userLoginData.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userLoginData.getPassword()));
        user.setType(userType);
        return userRepository.save(user);
    }

    @Override
    public User add(UserLoginData userLoginData, UserType userType) throws InvalidUserPasswordException, InvalidUserEmailException {
        if (findByEmail(userLoginData.getEmail()) != null) {
            return null;
        }
        return update(userLoginData, userType);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean delete(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

}


