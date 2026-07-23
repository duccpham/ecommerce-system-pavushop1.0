package com.shop.pavushop.service;

public interface OtpService {

    String generateOtp();
    void saveOtp(String email,String otp);
    boolean verifyOtp(String email,String otp);
    void deleteOtp(String email);


}
