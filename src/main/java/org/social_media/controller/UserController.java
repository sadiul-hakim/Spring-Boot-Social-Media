package org.social_media.controller;

import lombok.RequiredArgsConstructor;
import org.social_media.model.User;
import org.social_media.pojo.UserDTO;
import org.social_media.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/save")
    public ResponseEntity<UserDTO> saveUser(@RequestBody User user){
        UserDTO userDTO = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user,@PathVariable long userId){
        UserDTO userDTO = userService.update(user,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }
    @GetMapping("/find/{userId}")
    public ResponseEntity<UserDTO> find(@PathVariable long userId){
        UserDTO user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/find-all")
    public ResponseEntity<List<UserDTO>> findAll(){
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable long userId){
        boolean deleted = userService.deleteUser(userId);
        return ResponseEntity.ok(Collections.singletonMap("message",STR."User deleted successfully : \{deleted}"));
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<UserDTO> searchUser(@PathVariable String query){
        UserDTO user = userService.searchUser(query);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/follow/{userId}/{followingUserId}")
    public ResponseEntity<?> followUser(@PathVariable long userId,@PathVariable long followingUserId){
        boolean followed = userService.addFollowing(userId, followingUserId);

        if(followed){
            return ResponseEntity.ok(Collections.singletonMap("message",STR."User \{userId} followed user \{followingUserId} successfully."));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message","Could not follow user."));
        }
    }

    @GetMapping("/follower/{userId}")
    public ResponseEntity<List<UserDTO>> findFollowers(@PathVariable long userId){
        List<UserDTO> followers = userService.findFollowers(userId);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<UserDTO>> findFollowings(@PathVariable long userId){
        List<UserDTO> followings = userService.findFollowings(userId);
        return ResponseEntity.ok(followings);
    }
}
