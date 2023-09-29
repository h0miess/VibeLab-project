package ru.vibelab.taskmanager.services.Impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.vibelab.taskmanager.config.PropertiesConfig;
import ru.vibelab.taskmanager.exceptions.EmailSendingException;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final PropertiesConfig propertiesConfig;
    private final String REGISTER_CONFIRM_MESSAGE;

    public EmailService(JavaMailSender mailSender, PropertiesConfig propertiesConfig) {
        this.mailSender = mailSender;
        this.propertiesConfig = propertiesConfig;
        this.REGISTER_CONFIRM_MESSAGE = "Здравствуйте! Пожалуйста, подтвердите вашу регистрацию, перейдя по ссылке:<br>"
                + "<a href=\"http://" + propertiesConfig.getServerAddress() + ":" + propertiesConfig.getServerPort() + "/api/v1/auth/register/confirm?token=%s\">Подтвердить регистрацию</a>";
    }
    public void sendRegisterConfirmEmail(String to, String token) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("rutwit0410@gmail.com");
            helper.setTo(to);
            helper.setSubject("Подтверждение регистрации на сервисе FullTime");
            helper.setText(String.format(REGISTER_CONFIRM_MESSAGE, token), true);

        } catch (MessagingException e) {
            throw new EmailSendingException();
        }

        mailSender.send(message);
    }
}