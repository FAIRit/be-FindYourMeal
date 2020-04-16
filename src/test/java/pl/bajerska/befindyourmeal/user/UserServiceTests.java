package pl.bajerska.befindyourmeal.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import pl.bajerska.befindyourmeal.exception.InvalidUserEmailException;
import pl.bajerska.befindyourmeal.exception.InvalidUserPasswordException;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class UserServiceTests {


    private UserRepository userRepository;
    private UserServiceImpl userService;

    @Before
    public void setUp(){
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }


    @Test
    public void shouldNotFindUser() {

        String name = new String("zofia.bajerska@gmail.com");
        Mockito.when(userRepository.findByUsername(name)).thenReturn(null);

        User user = userService.findByUsername(name);

        assertNull(user);
    }

    @Test
    public void shouldFindUser() {

        String name = new String("zofia.bajerska@gmail.com");
        Mockito.when(userRepository.findByUsername(name)).thenReturn(new User());

        User user = userService.findByUsername(name);

        assertNotNull(user);
    }

    @Test
    public void shouldFindAll() {

        List<User> list = new LinkedList<>();
        list.add(new User());
        list.add(new User());
        Mockito.when(userRepository.findAll()).thenReturn(list);

        List<User> found = (List<User>) userService.findAll();

        assertNotNull(found);
        assertEquals(found.size(), list.size());
    }

    @Test
    public void shouldNotFindAll() {

        Mockito.when(userRepository.findAll()).thenReturn(new LinkedList<>());

        List<User> found = (List<User>) userService.findAll();

        assertEquals(found.size(), 0);
    }

    @Test
    public void shouldDeleteUser() {

        String username = new String("haha@gmail.com");
        User user = new User();
        user.setId((long) 2);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(user);

        boolean status = userService.delete(username);

        assertEquals(status, true);
    }

    @Test
    public void shouldNotDeleteUser() {

        String username = new String("haha@gmail.com");
        User user = new User();
        user.setId((long) 2);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(null);

        boolean status = userService.delete(username);

        assertEquals(status, false);
    }

    @Test
    public void shouldNotUpdateUserWithNullPassword() {

        String username = new String("haha@gmail.com");
        User user = new User();
        user.setUsername(username);
        user.setPassword(null);

        Throwable exception = assertThrows(InvalidUserPasswordException.class, () -> userService.update(user));

    }

    @Test
    public void shouldNotUpdateUserWithEmptyPassword() {

        String username = new String("haha@gmail.com");
        User user = new User();
        user.setUsername(username);
        user.setPassword("");

        Throwable exception = assertThrows(InvalidUserPasswordException.class, () -> userService.update(user));

    }

    @Test
    public void shouldNotUpdateUserWithNullUsername() {

        User user = new User();
        user.setUsername(null);
        user.setPassword("Kasia123");

        Throwable exception = assertThrows(InvalidUserEmailException.class, () -> userService.update(user));

    }

    @Test
    public void shouldNotUpdateUserWithUsernameWithoutAtSign() {

        User user = new User();
        user.setUsername("zofia.bajerskagmail.com");
        user.setPassword("Kasia123");

        Throwable exception = assertThrows(InvalidUserEmailException.class, () -> userService.update(user));

    }

    @Test
    public void shouldNotUpdateUserWithUsernameWithoutDot() {

        User user = new User();
        user.setUsername("zofia.bajerska@gmailcom");
        user.setPassword("Kasia123");

        Throwable exception = assertThrows(InvalidUserEmailException.class, () -> userService.update(user));

    }
    @Test
    public void shouldNotUpdateUserWithEmptyUsername() {

        User user = new User();
        user.setUsername("");
        user.setPassword("Kasia123");

        Throwable exception = assertThrows(InvalidUserEmailException.class, () -> userService.update(user));

    }

    @Test
    public void shouldUpdateUser() {

        User user = new User();
        user.setUsername("haha@gmail.com");
        user.setPassword("Kasia123");
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User updated = userService.update(user);
        assertEquals(updated.getUsername(), user.getUsername());
        assertEquals(updated.getPassword(), user.getPassword());

    }

    @Test
    public void shouldNotAddUser() {

        User user = new User();
        user.setUsername("haha@gmail.com");
        user.setPassword("Kasia123");
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        User added = userService.add(user);
        assertNull(added);
    }

    @Test
    public void shouldAddUser() {

        User user = new User();
        user.setUsername("haha@gmail.com");
        user.setPassword("Kasia123");
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User added = userService.add(user);
        assertEquals(added.getUsername(), user.getUsername());
        assertEquals(added.getPassword(), user.getPassword());

    }

}
