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

    // This map stores the OTPs for each email
    private Map<String, Integer> otpData = new ConcurrentHashMap<>();

    // This executor service is used to schedule tasks
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    // This method sends an email with the given subject and body to the given email address
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("comic.pantech@gmail.com");
        message.setTo(toEmail);

        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
    }

    // This method stores the given OTP for the given key (email) and schedules a task to remove it after 1 minutes
    public void storeOtp(String key, int otp) {
        otpData.put(key, otp);
        executorService.schedule(() -> otpData.remove(key), 1, TimeUnit.MINUTES);
    }

    // This method retrieves the OTP for the given key (email). If no OTP is found, it returns -1
    public int retrieveOtp(String key) {
        return otpData.getOrDefault(key, -1);
    }

    // This method removes the OTP for the given key (email)
    public void clearOtp(String key) {
        otpData.remove(key);
    }

    // This method returns a list of all emails for which an OTP is stored
    public List<String> getEmails() {
        return new ArrayList<>(otpData.keySet());
    }

    // This method resets the password for the given email. It generates a new OTP, stores it, and sends it via email
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        if (this.getEmails().contains(resetPasswordRequest.getEmail())) {
            throw new AppException(ErrorCode.ALREADY_SENT_OTP);
        }

        int otp = new Random().nextInt(900000) + 100000;

        this.storeOtp(resetPasswordRequest.getEmail(), otp);

        this.sendEmail(
                resetPasswordRequest.getEmail(), "Your OTP is: " + otp, "Reset Password, OTP is valid for 1 minutes.");
    }

    // This method verifies the given OTP. If it matches the stored OTP, it generates a new password, changes the user's
    // password, removes the OTP, and sends the new password via email
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
