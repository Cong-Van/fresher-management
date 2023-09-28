package com.vmo.freshermanagement.intern.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface CronjobService {

    void sendFresherMarkByEmail() throws MessagingException, UnsupportedEncodingException;

}
