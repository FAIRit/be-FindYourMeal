package pl.bajerska.befindyourmeal.user;

public interface UserService {

    User update(User user);

    boolean add(User user);

    User findByEmail(String email);

    boolean delete(String id);

}
