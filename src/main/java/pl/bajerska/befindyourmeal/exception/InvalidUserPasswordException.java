package pl.bajerska.befindyourmeal.exception;

import pl.bajerska.befindyourmeal.user.User;


public class InvalidUserPasswordException extends RuntimeException {
    public InvalidUserPasswordException(User user) {
        super("Your password doesnt fulfill the rules.");
    }
}
