package com.silverfang.game_api.controller;


import com.silverfang.game_api.SecurityConfig.MyUserDetailsService;
import com.silverfang.game_api.dao.UserServiceInterface;
import com.silverfang.game_api.jwt.JwtUtil;
import com.silverfang.game_api.model.AuthRequest;
import com.silverfang.game_api.model.AuthResponse;
import com.silverfang.game_api.model.UserTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController {
    @Autowired
    private UserServiceInterface userRepository;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping(value = "/login")
    public ResponseEntity<?> createJwtToken(@RequestBody AuthRequest userTable) throws Exception {
        if(userTable.getUserName()==null||userTable.getPassword()==null)
        {
            return new ResponseEntity<>("Required values are not Present in the request Body",HttpStatus.BAD_REQUEST);
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userTable.getUserName(), userTable.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Username or Password Incorrect",HttpStatus.FORBIDDEN);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userTable.getUserName());
            final String jwtToken = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(jwtToken));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> saveUser(@RequestBody AuthRequest user) throws Exception {
        if(user.getUserName()==null||user.getPassword()==null)
        {
            return new ResponseEntity<>("Required values are not Present in the request Body",HttpStatus.BAD_REQUEST);
        }
        UserTable userTable = userRepository.findUser(user.getUserName());
        if (userTable == null) {
                userDetailsService.save(user);
                return ResponseEntity.ok("Author Registered!");
            }
         else
             {
                return new ResponseEntity<>("User already exist",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetMyPass(@RequestBody AuthRequest authRequest) {
        if(authRequest.getUserName()==null||authRequest.getPassword()==null)
        {
            return new ResponseEntity<>("Required values are not Present in the request Body",HttpStatus.BAD_REQUEST);
        }
        String userName = authRequest.getUserName();
        UserTable userTable = userRepository.findUser(userName);
        if (userTable == null) {
            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        } else {
            userDetailsService.save(authRequest);
        }
        return new ResponseEntity<>("Password Successfully Changed",HttpStatus.OK);
    }
}
