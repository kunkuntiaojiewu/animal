package com.example.animal.controller;

import com.example.animal.entity.User;
import com.example.animal.jwt.TokenUtil;
import com.example.animal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class userController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        user = userService.login(user.getUserName(), user.getPassword());
        String token;
        System.out.println(user);
        if (user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
        }
        token = TokenUtil.sign(user);
        System.out.println(token);
        return ResponseEntity.ok().body(token);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        User register = userService.register(user.getUserName(), user.getPassword());
        if (register == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名已存在");
        }
        return ResponseEntity.ok().body("创建成功");
    }
    @PutMapping("/updatePassword")
    public void updateAvatar(String newPassword, @RequestHeader("Authorization") String authHeader){
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 通常JWT token是通过"Bearer "前缀来传递的
            token = authHeader.substring(7); // 移除"Bearer "前缀
        }
        System.out.println(token);
    }
}
