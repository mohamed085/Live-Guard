package com.liveguard.service;

import com.liveguard.domain.Email;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface SendEmailService {

    void send(Email email) throws MessagingException, UnsupportedEncodingException;

    void sendEmailToNonSentEmailsJob();
}
