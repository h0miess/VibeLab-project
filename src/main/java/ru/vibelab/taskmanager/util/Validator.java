package ru.vibelab.taskmanager.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Validator {

    public boolean isStringWithTimeValid(String stringWithTime) {
        if(stringWithTime.isBlank()) return false;
        return stringWithTime.matches("^(\\d+м)? ?(\\d+н)? ?(\\d+д)? ?(\\d+ч)? ?(\\d+мин)?$");
    }

    public boolean isPasswordValid(String password) {
        return password.matches("^[A-Za-zА-ЯЁа-яё0-9!@#$%^&*()_+\\-=\\[\\]{};':\"|,.<>/?]{8,}$");
    }

    public boolean isNameAndSurnameValid(String name, String surname) {
        if (name.isBlank() || surname.isBlank()) return false; // имя не пустое
        if (!name.matches("^[а-яёА-ЯЁa-zA-Z]+(?:-[а-яёА-ЯЁa-zA-Z]+)?$"))
            return false; // имя допускает только буквы и дефис
        if (!surname.matches("^[а-яёА-ЯЁa-zA-Z]+(?:[- ][а-яёА-ЯЁa-zA-Z]+)?$"))
            return false; // фамилия допускает только буквы, пробел и дефис

        return true;
    }

    public boolean isTitleValid(String title) {
        return title.matches("^[а-яёА-ЯЁa-zA-Z0-9 _-]*$");
    }

    public boolean isPositionValid(String title) {
        return title.matches("^[а-яёА-ЯЁa-zA-Z0-9_-]*$");
    }

}
