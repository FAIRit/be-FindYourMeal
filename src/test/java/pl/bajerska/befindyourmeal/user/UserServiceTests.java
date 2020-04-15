package pl.bajerska.befindyourmeal.user;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import pl.bajerska.befindyourmeal.exception.ExceptionAdviceSet;
import pl.bajerska.befindyourmeal.exception.InvalidUserPasswordException;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class UserServiceTests {
    @Test
    public void shouldNotFindUser() {


        UserRepository repository = Mockito.mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(repository);
        Mockito.when(repository.findByUsername("zofia.bajerska")).thenReturn(null);
        String name = new String("zofia.bajerska@gmail.com");

        User user = service.findByUsername(name);

        assertNull(user);
    }

    @Test
    public void shouldFindUser() {

        UserRepository repository = Mockito.mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(repository);
        String name = new String("zofia.bajerska@gmail.com");
        Mockito.when(repository.findByUsername(name)).thenReturn(new User());

        User user = service.findByUsername(name);

        assertNotNull(user);
    }

    @Test
    public void shouldFindAll() {

        UserRepository repository = Mockito.mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(repository);
        List<User> list = new LinkedList<>();
        list.add(new User());
        list.add(new User());
        Mockito.when(repository.findAll()).thenReturn(list);

        List<User> found = (List<User>) service.findAll();

        assertNotNull(found);
        assertEquals(found.size(), list.size());
    }

    @Test
    public void shouldNotFindAll() {

        UserRepository repository = Mockito.mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(repository);
        Mockito.when(repository.findAll()).thenReturn(new LinkedList<>());

        List<User> found = (List<User>) service.findAll();

        assertEquals(found.size(), 0);
    }

    @Test
    public void shouldDeleteUser() {

        UserRepository repository = Mockito.mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(repository);
        String username = new String("haha@gmail.com");
        User user = new User();
        user.setId((long) 2);
        Mockito.when(repository.findByUsername(username)).thenReturn(user);

        boolean status = service.delete(username);

        assertEquals(status, true);
    }

    @Test
    public void shouldNotDeleteUser() {

        UserRepository repository = Mockito.mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(repository);
        String username = new String("haha@gmail.com");
        User user = new User();
        user.setId((long) 2);
        Mockito.when(repository.findByUsername(username)).thenReturn(null);

        boolean status = service.delete(username);

        assertEquals(status, false);
    }

    @Test
    public void shouldUpdateUser() {


        UserRepository repository = Mockito.mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(repository);
        String username = new String("haha@gmail.com");
        User user = new User();
        user.setUsername(username);
        user.setPassword(null);

        Throwable exception = assertThrows(InvalidUserPasswordException.class, () -> service.update(user));


    }

}
