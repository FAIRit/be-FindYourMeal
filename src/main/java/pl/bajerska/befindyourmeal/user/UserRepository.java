package pl.bajerska.befindyourmeal.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserRepository extends CrudRepository<User, Long>, QueryByExampleExecutor<User> {

    User findByUsername(String username);

    Optional<User> findById (long id);

    boolean existsById (long id);

    void deleteById (long id);
}
