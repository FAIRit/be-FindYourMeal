package pl.bajerska.befindyourmeal.exception;

import pl.bajerska.befindyourmeal.user.User;

public class EmptyIngredientsStringException extends RuntimeException {
    public EmptyIngredientsStringException() {
        super("Ingredients string is empty");
    }
}
