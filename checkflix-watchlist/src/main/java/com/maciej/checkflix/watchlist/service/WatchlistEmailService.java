package com.maciej.checkflix.watchlist.service;

import com.maciej.checkflix.watchlist.domain.mail.Mail;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WatchlistEmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WatchlistEmailService.class);

    private final JavaMailSender javaMailSender;

    public void send(final Mail mail) {
        LOGGER.info("Starting email preparation...");
        try {
            javaMailSender.send(createMailMessage(mail));
            LOGGER.info("Email has been sent.");
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getCause());
            throw new MailSendException("Mail was not sent.");
        }
    }


    public SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        // Needed on my PC, as it is not configured to have a fully qualified domain name
        mailMessage.setFrom(mail.getMailFrom());
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        return mailMessage;
    }
}
