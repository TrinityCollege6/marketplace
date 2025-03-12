package com.leo.marketplace.service;

import com.leo.marketplace.model.User;
import com.leo.marketplace.model.request.UserRegisterRequest;
import org.springframework.stereotype.Service;

public interface UserService {

    public long userRegister(String username, String fullname, String email, String password, String checkPassword);
    public User userLogin(String username, String password);

}


