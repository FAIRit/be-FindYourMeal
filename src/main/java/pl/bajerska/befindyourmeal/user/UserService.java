package pl.bajerska.befindyourmeal.user;

public interface UserService {

    User update(UserLoginData userLoginData, UserType userType) throws InvalidUserPasswordException, InvalidUserEmailException;

    User add(UserLoginData userLoginData, UserType userType) throws InvalidUserPasswordException, InvalidUserEmailException;

    User findByEmail(String email);

    boolean delete(String id);

}
