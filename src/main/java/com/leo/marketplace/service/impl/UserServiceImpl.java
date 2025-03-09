package com.leo.marketplace.service.impl;

import com.leo.marketplace.common.ErrorCode;
import com.leo.marketplace.exception.BusinessException;
import com.leo.marketplace.model.User;
import com.leo.marketplace.repository.UserRepository;
import com.leo.marketplace.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.convert.QueryMapper;
import org.springframework.stereotype.Service;
import com.leo.marketplace.model.request.UserRegisterRequest;


import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.leo.marketplace.model.User.Role.CUSTOMER;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public long userRegister(String username, String fullname, String email, String password, String checkPassword) {
        if (StringUtils.isAnyBlank(username, fullname, email, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Missing required fields");
        }
        if (username.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Username must be at least 4 characters long");
        }
        if (password.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Password must be at least 8 characters long");
        }
        if (!checkIllegalityStr(username)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Username contains illegal characters");
        }
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Passwords do not match");
        }

        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Username is already taken");
        }

        Optional<User> existingEmail = userRepository.findByEmail(email);
        if (existingEmail.isPresent()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Email is already in use");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setFullname(fullname);
        newUser.setEmail(email);
        newUser.setRole(CUSTOMER);

        userRepository.save(newUser);
        return newUser.getId();
    }

    public boolean checkIllegalityStr(String str) {
        String regex = "^[a-zA-Z0-9 .,!?'\"@#$%^&*()_\\-+=~`<>:;/\\\\|]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
