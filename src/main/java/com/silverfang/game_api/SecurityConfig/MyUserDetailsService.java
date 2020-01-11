package com.silverfang.game_api.SecurityConfig;


import com.silverfang.game_api.dao.UserServiceInterface;
import com.silverfang.game_api.model.AuthRequest;
import com.silverfang.game_api.model.UserTable;
import com.silverfang.game_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private PasswordEncoder bcryptEncoder;
    @Autowired
    private UserServiceInterface userRepository;
    @Override
    public UserDetails loadUserByUsername(String User) throws UsernameNotFoundException {

        UserTable userTable= userRepository.findUser(User);
        if(userTable==null)
        {
            throw new UsernameNotFoundException("user not in the database" + User);
        }

        return new MyUserDetails(userTable.getName(),userTable.getPassword());
    }
    public void save(AuthRequest user) {
        UserTable newUser = userRepository.findUser(user.getUserName());

        if (newUser == null) {

            UserTable newuser= new UserTable();
            newuser.setName(user.getUserName());
            newuser.setPassword(bcryptEncoder.encode(user.getPassword()));
            userRepository.saveUser(newuser);
        }
        else{

            newUser.setName(user.getUserName());
            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            userRepository.saveUser(newUser);
        }
    }
}
