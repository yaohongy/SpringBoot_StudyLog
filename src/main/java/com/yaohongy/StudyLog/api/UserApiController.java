package com.yaohongy.StudyLog.api;

import java.util.Optional;

import com.yaohongy.StudyLog.entities.User;
import com.yaohongy.StudyLog.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserApiController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/{username}")
    public ResponseEntity<?> GetUserByUsername(@PathVariable String username) {
        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isPresent()) return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PostMapping("")
    public ResponseEntity<?> SaveUser(@RequestBody User user) {
        user = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> UpdateUserByUsername(@PathVariable String username, @RequestBody User user) {
        Optional<User> optionalUser = userService.findByUsername(username);
        if (!optionalUser.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        user.setId(optionalUser.get().getId());
        user.setUsername(optionalUser.get().getUsername());
        userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("User updated");
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> DeleteUserByUsername(@PathVariable String username) {
        Optional<User> optionalUser = userService.findByUsername(username);
        if (!optionalUser.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        userService.delete(optionalUser.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted");
    }

    @GetMapping("")
    public ResponseEntity<?> GetAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "Integer.MAX_VALUE") int perPage ) {
        Page<User> users = userService.findAllByPage(page, perPage);
        return new ResponseEntity<>(users.get(), HttpStatus.OK);
    }

}
