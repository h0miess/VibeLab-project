package ru.vibelab.taskmanager.services.Impl;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vibelab.taskmanager.exceptions.NotificationNotFoundException;
import ru.vibelab.taskmanager.exceptions.WrongNotificationIdException;
import ru.vibelab.taskmanager.mappers.NotificationMapper;
import ru.vibelab.taskmanager.models.Notification;
import ru.vibelab.taskmanager.models.NotificationTypes;
import ru.vibelab.taskmanager.models.User;
import ru.vibelab.taskmanager.models.responses.NotificationResponse;
import ru.vibelab.taskmanager.repositories.NotificationRepository;
import ru.vibelab.taskmanager.repositories.UserRepository;
import ru.vibelab.taskmanager.services.api.NotificationService;

import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void createNotification(@NonNull NotificationTypes type, @NonNull String message, @NonNull User user) {

        var notification = Notification.builder()
                .date(ZonedDateTime.now())
                .type(type)
                .message(message)
                .userId(user.getId())
                .build();

        List<Notification> notifications = notificationRepository.findAllByUserId(user.getId());

        if(notifications.size() > 5) {
            notificationRepository.delete(notifications.get(0));
        }

        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationResponse> getAllForUser(@NonNull Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).get();
        List<Notification> notifications = notificationRepository.findAllByUserId(user.getId());

        return notifications.stream().map(NotificationMapper::modelToResponse).toList();
    }

    @Override
    public NotificationResponse getNotificationById(Principal principal, Long notificationId) {
        User user = userRepository.findByEmail(principal.getName()).get();

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));

        if(!notification.getUserId().equals(user.getId())) {
            throw new WrongNotificationIdException("Это уведомление не относится к ткущему пользователю.");
        }

        return NotificationMapper.modelToResponse(notification);
    }

    @Override
    @Transactional
    public NotificationResponse markAsRead(Principal principal, Long notificationId) {
        User user = userRepository.findByEmail(principal.getName()).get();

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));

        if(!notification.getUserId().equals(user.getId())) {
            throw new WrongNotificationIdException("Это уведомление не относится к ткущему пользователю.");
        }

        notification.setRead(true);

        notificationRepository.save(notification);
        return NotificationMapper.modelToResponse(notification);
    }

    @Override
    @Transactional
    public void deleteNotification(Principal principal, Long notificationId) {
        User user = userRepository.findByEmail(principal.getName()).get();

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));

        if(!notification.getUserId().equals(user.getId())) {
            throw new WrongNotificationIdException("Это уведомление не относится к ткущему пользователю.");
        }

        notificationRepository.deleteById(notificationId);
    }

}
