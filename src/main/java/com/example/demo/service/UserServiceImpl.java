package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.DuplicateRecordException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.mapper.UserMapper;

import com.example.demo.model.request.RegisterForm;
import com.example.demo.model.request.UpdateForm;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<UserDto> getListUser() {
        List<User> users = userRepository.findAll();

        List<UserDto> usersdto = new ArrayList<>();
        for(User user : users){
            usersdto.add(UserMapper.toUserDto(user));
        }
        return usersdto;
    }

    @Override
    public UserDto getUserById(int id) {
      Optional<User> users = userRepository.findById(id);
       if(users.isEmpty()){
           throw new NotFoundException("No user is founded!!");
       }
      return UserMapper.toUserDto(users.get());
    }

    @Override
    public UserDto createUser(RegisterForm form) {
       User user = userRepository.findByEmail(form.getEmail());
        if(user != null){
            throw new DuplicateRecordException("Email đã tồn tại");
        }
        User user1 = UserMapper.toUser(form);
        userRepository.save(user1);
        return UserMapper.toUserDto(user1);
    }



    @Override
    public UserDto updateUser(UpdateForm form , int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new NotFoundException("No user is Founded");
        }else{
            User user1 = UserMapper.toUser(form , id);
            user1.setEmail(user.get().getEmail());
            userRepository.save(user1);
            return UserMapper.toUserDto(user1);
        }
    }

    @Override
    public void updateUser(UserDto userDto) {
        Optional<User> user = userRepository.findById(userDto.getId());
        if(user.isEmpty()){
            throw new NotFoundException("No user is founded");
        }
        user.get().setId(userDto.getId());
        user.get().setName(userDto.getName());
        user.get().setEmail(userDto.getEmail());
        user.get().setPhone(userDto.getPhone());
        userRepository.save(user.get());


    }


    @Override
    public void deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new NotFoundException("No use is founded");
        }
        else{
            userRepository.deleteById(id);
        }
    }

    @Override
    public List<UserDto> search(String name) {
        List<User> user = userRepository.findByNameContaining(name);
        List<UserDto> userDto = new ArrayList<>();
        for(User user1 : user){
            userDto.add(UserMapper.toUserDto(user1));
        }
        return userDto;
    }
}
