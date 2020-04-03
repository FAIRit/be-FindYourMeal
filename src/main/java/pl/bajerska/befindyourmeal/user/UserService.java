package pl.bajerska.befindyourmeal.user;

import pl.bajerska.befindyourmeal.exception.InvalidUserEmailException;
import pl.bajerska.befindyourmeal.exception.InvalidUserPasswordException;
import java.util.List;

public interface UserService {

    User update(User user) throws InvalidUserPasswordException, InvalidUserEmailException;

    User add(User user) throws InvalidUserPasswordException, InvalidUserEmailException;

    boolean delete(String username);

    User findByUsername(String username);

    List<User> findAll(User principal);

}
