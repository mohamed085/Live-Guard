package com.liveguard.service;

import com.liveguard.domain.VerificationCode;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {


    void sendVerificationEmail(VerificationCode code) throws UnsupportedEncodingException, MessagingException;

    void sendResetTokenPassword(String email, String passwordToken) throws UnsupportedEncodingException, MessagingException;

    void sendVerificationEmailToUnsendedJob() throws UnsupportedEncodingException, MessagingException;
}
