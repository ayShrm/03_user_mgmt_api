package com.ayush.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.constants.Constants;
import com.ayush.dto.AccountActDto;
import com.ayush.dto.LoginDto;
import com.ayush.dto.UserDto;
import com.ayush.entity.Token;
import com.ayush.entity.UserDetails;
import com.ayush.props.AppProperties;
import com.ayush.repository.TokenRepo;
import com.ayush.service.EmailService;
import com.ayush.service.UserService;
import com.ayush.utility.ReadFile;

@RestController
public class UserRestController {

	private TokenRepo tokenRepo;

	private UserService userService;

	private EmailService emailService;

	private Map<String, String> messages;

	public UserRestController(TokenRepo tokenRepo, UserService userService, EmailService emailService,
			AppProperties appProps) {
		this.tokenRepo = tokenRepo;
		this.userService = userService;
		this.emailService = emailService;
		this.messages = appProps.getMessages();
	}

	@PostMapping("/register")
	public ResponseEntity<String> submit(@RequestBody UserDto userDto, HttpServletRequest request) {
		UserDetails userDetails = userService.getUserbyEmail(userDto.getUserEmail());
		if (userDetails == null) {
			boolean register = userService.register(userDto);
			String appUrl = request.getScheme() + "://" + request.getServerName();
			if (register) {
				UserDetails user = userService.getUserbyEmail(userDto.getUserEmail());
				String fullName = userService.getUserbyEmail(userDto.getUserEmail()).getFullName();
				String mailTo = userService.getUserbyEmail(userDto.getUserEmail()).getEmailId();
				String password = userService.getUserbyEmail(mailTo).getPassword();
				String mailSub = messages.get(Constants.account_Act);
				String mailBody = ReadFile.readMailBody(fullName, password, messages.get(Constants.act_File_Name),
						appUrl + ":8080/activate?token=" + tokenRepo.findToken(user).getToken());
				emailService.sendMail(mailTo, mailSub, mailBody);
				return new ResponseEntity<>(messages.get(Constants.acct_Reg), HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<>(messages.get(Constants.user_Exists), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/activate")
	public ResponseEntity<String> activate(@RequestParam("token") String cnfmToken, @RequestBody AccountActDto actDto) {
		Optional<Token> tkn = tokenRepo.findUserByToken(cnfmToken);
		if (tkn.isPresent()) {
			String activate = userService.activate(actDto);
			return new ResponseEntity<>(activate, HttpStatus.OK);
		}
		return new ResponseEntity<>(messages.get(Constants.tkn_Exp), HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
		return new ResponseEntity<>(userService.logIn(loginDto), HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDetails>> users() {
		List<UserDetails> allUsersData = userService.getAllUsersData();
		return new ResponseEntity<>(allUsersData, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<UserDetails> editUser(@PathVariable Integer userId) {
		UserDetails user = userService.getUserbyId(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping("/user")
	public ResponseEntity<String> updatePlan(@RequestBody UserDetails user) {
		boolean isUpdated = userService.update(user);
		if (isUpdated) {
			return new ResponseEntity<>(messages.get(Constants.user_Update_Succ), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(messages.get(Constants.user_Update_Fail), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/user/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
		boolean isDeleted = userService.deleteUserById(userId);
		if (isDeleted) {
			return new ResponseEntity<>(messages.get(Constants.user_Del_Succ), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(messages.get(Constants.user_Del_Fail), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/status/{userId}/{status}")
	public ResponseEntity<String> statusChange(@PathVariable Integer userId, @PathVariable Boolean status) {
		boolean isStatusChanged = userService.activeSw(userId, status);
		if (isStatusChanged) {
			return new ResponseEntity<>(messages.get(Constants.acct_status_Succ), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(messages.get(Constants.acct_status_Fail), HttpStatus.OK);
		}
	}

	@PostMapping("/recoverPwd")
	public ResponseEntity<String> recoverPwd(@RequestParam("email") String userEmail, HttpServletRequest request) {
		String url = "";
		UserDetails user = userService.getUserbyEmail(userEmail);
		if (user == null) {
			return new ResponseEntity<>(messages.get(Constants.acc_Not_Find), HttpStatus.BAD_REQUEST);
		}
		String mailTo = user.getEmailId();
		String mailSub = messages.get(Constants.pwd_Rec_Req);
		String mailBody = ReadFile.readMailBody(user.getFullName(), user.getPassword(), messages.get(Constants.rec_File_Name), url);
		emailService.sendMail(mailTo, mailSub, mailBody);
		return new ResponseEntity<>(messages.get(Constants.pwd_Sent), HttpStatus.OK);
	}
}
