package org.social_media.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.social_media.model.User;
import org.social_media.pojo.UserDTO;
import org.social_media.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDTO save(User user) {
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Transactional
    public UserDTO update(User user, long userId) {

        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            return convertToDTO(user);
        }

        User userModel = existingUser.get();
        userModel.setFirstName(!user.getFirstName().isEmpty() ? user.getFirstName() : userModel.getFirstName());
        userModel.setLastName(!user.getLastName().isEmpty() ? user.getLastName() : userModel.getLastName());
        userModel.setEmail(!user.getEmail().isEmpty() ? user.getEmail() : userModel.getEmail());
        userModel.setPassword(!user.getPassword().isEmpty() ? user.getPassword() : userModel.getPassword());
        userModel.setGender(!user.getGender().isEmpty() ? user.getGender() : userModel.getGender());

        return convertToDTO(userModel);
    }

    public boolean deleteUser(long userId){
        userRepository.deleteById(userId);
        return true;
    }

    public UserDTO findById(long userId) {
        User user = userRepository.findById(userId).orElse(new User());
        return convertToDTO(user);
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userList = new ArrayList<>();
        users.forEach(user -> userList.add(convertToDTO(user)));
        return userList;
    }

    public UserDTO searchUser(String query){
        User user = userRepository.searchUser(query).orElse(new User());
        return convertToDTO(user);
    }

    public List<UserDTO> findFollowers(long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) return Collections.emptyList();

        List<Long> followerList = user.get().getFollowers();
        List<UserDTO> followers = new ArrayList<>();
        followerList.forEach(id->{
            followers.add(findById(id));
        });

        return followers;
    }

    public List<UserDTO> findFollowings(long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) return Collections.emptyList();

        List<Long> followingList = user.get().getFollowing();
        List<UserDTO> following = new ArrayList<>();
        followingList.forEach(id->{
            following.add(findById(id));
        });

        return following;
    }

    @Transactional
    public boolean addFollowing(long userId,long followingUserId){
        try{

            if(userId == followingUserId) return false;

            Optional<User> userOptional = userRepository.findById(userId);
            if(userOptional.isEmpty()){
                return false;
            }

            Optional<User> followingUserOptional = userRepository.findById(followingUserId);
            if(followingUserOptional.isEmpty()){
                return false;
            }

            User user = userOptional.get();
            User followingUser = followingUserOptional.get();

            user.getFollowing().add(followingUserId);
            followingUser.getFollowers().add(userId);

            return true;
        }catch (Exception ex){
            return false;
        }
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getGender(),
                user.getStartDate()
        );
    }
}
