package com.api.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.blog.entities.User;
import com.api.blog.exceptions.ResourceNotFoundException;
import com.api.blog.payloads.UserDto;
import com.api.blog.repository.UserRepo;
import com.api.blog.services.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser =this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","id", userId ));
		user.setName(userDto.getName());;
		user.setEmail(userDto.getEmail());;
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = userToDto(updatedUser); 
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","id", userId ));
		UserDto userDto  = this.userToDto(user);
		return userDto;
	}

	@Override
	public List<UserDto> geAllUsers() {
		List<User> users =this.userRepo.findAll();
		List<UserDto> userDtos = users.stream()
				.map(user-> this.userToDto(user))
				.collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","id", userId ));
		this.userRepo.delete(user);

	}
	
	private User dtoToUser(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
		/*
		 * User user = new User(); user.setId(userDto.getId());
		 * user.setName(userDto.getName()); user.setEmail(userDto.getEmail());
		 * user.setPassword(userDto.getPassword()); user.setAbout(userDto.getAbout());
		 */
		return user;		
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto = modelMapper.map(user, UserDto.class);
		/*
		 * UserDto userDto = new UserDto(); userDto.setId(user.getId());
		 * userDto.setName(user.getName()); userDto.setEmail(user.getEmail());
		 * userDto.setPassword(user.getPassword()); userDto.setAbout(user.getAbout());
		 */
		return userDto;
	}

}
