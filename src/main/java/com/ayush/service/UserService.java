package com.ayush.service;

import java.util.List;

import com.ayush.dto.AccountActDto;
import com.ayush.dto.LoginDto;
import com.ayush.dto.UserDto;
import com.ayush.entity.UserDetails;

public interface UserService {

	public boolean register(UserDto userDto);

	public String activate(AccountActDto actDto);

	public List<UserDetails> getAllUsersData();

	public UserDetails getUserbyId(Integer userId);

	public UserDetails getUserbyEmail(String userEmail);

	public boolean update(UserDetails userDetails);

	public boolean deleteUserById(Integer userId);

	public boolean activeSw(Integer userId, Boolean status);

	public String logIn(LoginDto loginDto);

	public void save(UserDetails user);
}
