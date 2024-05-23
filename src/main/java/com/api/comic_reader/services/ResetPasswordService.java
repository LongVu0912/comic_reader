package com.api.comic_reader.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.api.comic_reader.dtos.requests.OtpRequest;
import com.api.comic_reader.dtos.requests.ResetPasswordRequest;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class ResetPasswordService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    private Map<String, Integer> otpData = new ConcurrentHashMap<>();
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("comic.pantech@gmail.com");
        message.setTo(toEmail);

        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
    }

    public void storeOtp(String key, int otp) {
        otpData.put(key, otp);
        // Schedule a task to remove the OTP after 5 minutes
        executorService.schedule(() -> otpData.remove(key), 5, TimeUnit.MINUTES);
    }

    public int retrieveOtp(String key) {
        return otpData.getOrDefault(key, -1);
    }

    public void clearOtp(String key) {
        otpData.remove(key);
    }

    public List<String> getEmails() {
        return new ArrayList<>(otpData.keySet());
    }

    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        if (this.getEmails().contains(resetPasswordRequest.getEmail())) {
            throw new AppException(ErrorCode.ALREADY_SENT_OTP);
        }

        int otp = new Random().nextInt(900000) + 100000;

        this.storeOtp(resetPasswordRequest.getEmail(), otp);

        this.sendEmail(resetPasswordRequest.getEmail(), "Your OTP is: " + otp, "Reset Password");
    }

    public void verifyOtp(OtpRequest otpRequest) {
        int storedOtp = this.retrieveOtp(otpRequest.getEmail());

        String randomPassword = String.valueOf(new Random().nextInt(900000) + 100000);

        if (otpRequest.getOtp() == storedOtp) {
            userService.changePasswordByEmail(otpRequest.getEmail(), randomPassword);

            this.clearOtp(otpRequest.getEmail());

            this.sendEmail(otpRequest.getEmail(), "Your new password is: " + randomPassword, "New Password");
        } else {
            throw new AppException(ErrorCode.INVALID_OTP);
        }
    }
}
