package com.silverfang.game_api.service;

import com.silverfang.game_api.dao.UserServiceInterface;
import com.silverfang.game_api.model.UserTable;
import com.silverfang.game_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository userRepository;
    @Override
    public void saveUser(UserTable userTable) {
    userRepository.save(userTable);
    }

    @Override
    public UserTable findUser(String name) {
        return userRepository.findUserTableByName(name);
    }
}
