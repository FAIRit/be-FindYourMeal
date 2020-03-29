package pl.bajerska.befindyourmeal.exception;

import pl.bajerska.befindyourmeal.user.User;

public class InvalidUserEmailException extends RuntimeException {
    public InvalidUserEmailException(User user) {
        super("Email does not match rules.");
    }
}
