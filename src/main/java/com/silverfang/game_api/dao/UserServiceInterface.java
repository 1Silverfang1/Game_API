package com.silverfang.game_api.dao;

import com.silverfang.game_api.model.UserTable;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserServiceInterface {
void saveUser(UserTable userTable);
UserTable findUser(String name);
}
