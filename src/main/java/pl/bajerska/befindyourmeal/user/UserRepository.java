package pl.bajerska.befindyourmeal.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends CrudRepository<User, Long>, QueryByExampleExecutor<User> {

    User findByEmail(String email);

    User findById (String id);

    boolean existsById (String id);

    void deleteById (String id);
}
