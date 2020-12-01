package com.maciej.checkflix.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WatchlistEmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WatchlistEmailService.class);

    public static final String NEW_CARD_EMAIL = "New card email";
    public static final String ONCE_A_DAY_EMAIL = "Once a day email";

//    private final JavaMailSender javaMailSender;
//    private final MailCreatorService mailCreatorService;
}
