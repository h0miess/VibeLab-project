package ru.vibelab.taskmanager.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationTypes {
    ACCOUNT_LOGIN, NEW_BOARD, MADE_ADMIN, INVITATION, KICK, REPORT

}
