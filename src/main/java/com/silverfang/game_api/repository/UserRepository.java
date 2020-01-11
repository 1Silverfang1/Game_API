package com.silverfang.game_api.repository;

import com.silverfang.game_api.model.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserTable,Integer> {
    UserTable findUserTableByName(String user);
}
