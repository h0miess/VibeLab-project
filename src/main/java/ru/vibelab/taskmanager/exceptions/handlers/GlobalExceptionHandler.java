package ru.vibelab.taskmanager.exceptions.handlers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vibelab.taskmanager.exceptions.*;
import ru.vibelab.taskmanager.models.responses.ErrorResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        String message = e.getMessage();
        if(message == null)
            message = "User not found";

        var error = ErrorResponse.builder()
                .errorType("UserNotFound")
                .errorMessage(message)
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BoardNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleBoardNotFoundException(BoardNotFoundException e) {
        String message = e.getMessage();
        if(message == null)
            message = "Board not found";
        var error = ErrorResponse.builder()
                .errorType("BoardNotFound")
                .errorMessage(message)
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ColumnNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleColumnNotFoundException(ColumnNotFoundException e) {
        String message = e.getMessage();
        if(message == null)
            message = "Column not found";
        var error = ErrorResponse.builder()
                .errorType("ColumnNotFound")
                .errorMessage(message)
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException e) {
        String message = e.getMessage();
        if(message == null)
            message = "Task not found";
        var error = ErrorResponse.builder()
                .errorType("TaskNotFound")
                .errorMessage(message)
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponse> handleValidException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errors.add("Field: " + fieldError.getField() +
                    "; Error: " + fieldError.getDefaultMessage());
        }

        var error = ErrorResponse.builder()
                .errorType("NotValidArgument")
                .errorMessage("Аргумент(ы) запроса некорректные")
                .errors(errors)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        String message = e.getMessage();
        if(message == null)
            message = "Пользователь с такой почтой уже существует";
        var error = ErrorResponse.builder()
                .errorType("UserAlreadyExists")
                .errorMessage(message)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    private ResponseEntity<ErrorResponse> handleInvalidPasswordException(InvalidPasswordException e) {
        String message = e.getMessage();
        if(message == null)
            message = "Некорректный пароль. Длина пароля минимум 8 символов. " +
                      "Допускаются только латиница, кириллица и специальные символы";
        var error = ErrorResponse.builder()
                .errorType("InvalidPassword")
                .errorMessage(message)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPositionException.class)
    private ResponseEntity<ErrorResponse> handleInvalidPositionException(InvalidPositionException e) {
        String message = e.getMessage();
        if(message == null)
            message = "Поле должность не должно содержать пробелы/невидимый текст";
        var error = ErrorResponse.builder()
                .errorType("InvalidPosition")
                .errorMessage(message)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidNameException.class)
    private ResponseEntity<ErrorResponse> handleInvalidNameException(InvalidNameException e) {
        String message = e.getMessage();
        if(message == null)
            message = "Имя и фамилия не должны быть пустыми. Пробелы и дефисы в имени не допускаются.";
        var error = ErrorResponse.builder()
                .errorType("InvalidNameOrSurname")
                .errorMessage(message)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidColumnNumberException.class)
    private ResponseEntity<ErrorResponse> handleInvalidColumnNumberException(InvalidColumnNumberException e) {
        String message = e.getMessage();
        if(message == null)
            message = "Номер столбца не может быть больше, чем их количество";
        var error = ErrorResponse.builder()
                .errorType("InvalidColumnNumber")
                .errorMessage(message)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParentTaskValueException.class)
    private ResponseEntity<ErrorResponse> handeInvalidParentTaskValueException(InvalidParentTaskValueException e) {
        var error = ErrorResponse.builder()
                .errorType("InvalidParentTaskValue")
                .errorMessage(e.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTimeException.class)
    private ResponseEntity<ErrorResponse> handleInvalidTimeException(InvalidTimeException e) {
        var error = ErrorResponse.builder()
                .errorType("InvalidTime")
                .errorMessage(e.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTitleException.class)
    private ResponseEntity<ErrorResponse> handeInvalidTitleException (InvalidTitleException e) {
        var error = ErrorResponse.builder()
                .errorType("InvalidTitleException")
                .errorMessage(e.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoAccessException.class)
    private ResponseEntity<ErrorResponse> handleNoAccessException(NoAccessException e) {
        String message = e.getMessage();
        if(message == null)
            message = "Это действие недоступно";
        var error = ErrorResponse.builder()
                .errorType("NoAccessException")
                .errorMessage(message)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidPeriodException.class)
    private ResponseEntity<ErrorResponse> handleInvalidPeriodException(InvalidPeriodException e) {
        var error = ErrorResponse.builder()
                .errorType("InvalidPeriod")
                .errorMessage(e.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<String> errors = new ArrayList<>();
        for(ConstraintViolation<?> error : constraintViolations) {
            errors.add("Field: " + error.getPropertyPath() + "; Error: " + error.getMessage());
        }

        var error = ErrorResponse.builder()
                .errorType("NotValidArgument")
                .errorMessage("Аргумент(ы) запроса некорректные")
                .errors(errors)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegistrationAlreadyConfirmedException.class)
    private ResponseEntity<ErrorResponse> handleRegistrationAlreadyConfirmedException(RegistrationAlreadyConfirmedException e) {
        var error = ErrorResponse.builder()
                .errorType("RegistrationAlreadyConfirmed")
                .errorMessage("Регистрация уже подтверждена.")
                .build();

       return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredConfirmationLinkException.class)
    private ResponseEntity<ErrorResponse> handleExpiredLinkException(ExpiredConfirmationLinkException e) {
        var error = ErrorResponse.builder()
                .errorType("ExpiredConfirmationLink")
                .errorMessage(e.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VerificationTokenNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleVerificationTokenNotFoundException(VerificationTokenNotFoundException e) {
        var error = ErrorResponse.builder()
                .errorType("VerificationTokenNotFound")
                .errorMessage("Не найден токен для подтверждения регистрации.")
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailSendingException.class)
    private ResponseEntity<ErrorResponse> handleEmailSendingException(EmailSendingException e) {
        var error = ErrorResponse.builder()
                .errorType("EmailSendingError")
                .errorMessage("Ошибка с отправкой письма на электронную почту.")
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleNotificationNotFoundException(NotificationNotFoundException e) {
        var error = ErrorResponse.builder()
                .errorType("NotificationNotFound")
                .errorMessage(e.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongNotificationIdException.class)
    private ResponseEntity<ErrorResponse> handleWrongNotificationIdException(WrongNotificationIdException e) {
        var error = ErrorResponse.builder()
                .errorType("WrongNotificationId")
                .errorMessage(e.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectUserIdException.class)
    private ResponseEntity<ErrorResponse> handleIncorrectUserIdException(IncorrectUserIdException e) {
        var error = ErrorResponse.builder()
                .errorType("IncorrectUserId")
                .errorMessage(e.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyInBoardException.class)
    private ResponseEntity<ErrorResponse> handleUserAlreadyInBoardException(UserAlreadyInBoardException e) {
        var error = ErrorResponse.builder()
                .errorType("UserAlreadyInBoard")
                .errorMessage(e.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
