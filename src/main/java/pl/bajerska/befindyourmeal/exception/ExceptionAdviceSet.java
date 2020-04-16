package pl.bajerska.befindyourmeal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ExceptionAdviceSet {


    @ExceptionHandler(InvalidUserEmailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String invalidUserEmailHandler(InvalidUserEmailException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(InvalidUserPasswordException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String invalidUserPasswordHandler(InvalidUserPasswordException ex) {
        return ex.getMessage();
    }

}
