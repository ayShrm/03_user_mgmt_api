package com.ayush.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ayush.constants.Constants;
import com.ayush.dto.UserDto;
import com.ayush.entity.Token;
import com.ayush.entity.UserDetails;
import com.ayush.props.AppProperties;
import com.ayush.repository.TokenRepo;
import com.ayush.repository.UserDetailsRepo;
import com.ayush.utility.generatePassword;

@Service
public class UserServiceImpl implements UserService {

	private TokenRepo tokenRepo;

	private UserDetailsRepo userDtlsRepo;

	private Map<String, String> messages;

	public UserServiceImpl(TokenRepo tokenRepo, UserDetailsRepo userDtlsRepo, AppProperties appProps) {
		this.tokenRepo = tokenRepo;
		this.userDtlsRepo = userDtlsRepo;
		this.messages = appProps.getMessages();
	}

	@Override
	public boolean register(UserDto userDto) {
		UserDetails usrDtls = new UserDetails();
		usrDtls.setFullName(userDto.getFullName());
		usrDtls.setMobile(userDto.getMobile());
		usrDtls.setDob(userDto.getDob());
		usrDtls.setEmailId(userDto.getUserEmail());
		usrDtls.setGender(userDto.getGender());
		usrDtls.setSsn(userDto.getSsn());
		usrDtls.setPassword(generatePassword.generateSecurePwd());
		UserDetails save = userDtlsRepo.save(usrDtls);
		Token token = new Token(usrDtls);
		tokenRepo.save(token);
		return save.getUserId() != null;
	}

	@Override
	public String activate(UserDto userDto) {
		String emailId = userDto.getUserEmail();
		String tempPwd = userDto.getTempPwd();
		String newPwd = userDto.getNewPwd();
		String cnfmPwd = userDto.getCnfmPwd();

		UserDetails repoEmail = userDtlsRepo.findByEmail(emailId);
		if (repoEmail != null) {
			if (repoEmail.getPassword().equals(tempPwd)) {
				if (newPwd.equals(cnfmPwd)) {
					repoEmail.setActive(true);
					repoEmail.setPassword(cnfmPwd);
					Token findToken = tokenRepo.findToken(repoEmail);
					findToken.setToken(null);
					tokenRepo.save(findToken);
					userDtlsRepo.save(repoEmail);
					return messages.get(Constants.acc_Act);
				} else {
					return messages.get(Constants.pwd_Mismatch);
				}
			} else {
				return messages.get(Constants.inc_Temp_Pwd);
			}
		} else {
			return messages.get(Constants.user_Not_Exists);
		}
	}

	@Override
	public List<UserDetails> getAllUsersData() {
		return userDtlsRepo.findAll();
	}

	@Override
	public UserDetails getUserbyId(Integer userId) {
		Optional<UserDetails> findById = userDtlsRepo.findById(userId);
		if (findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

	@Override
	public boolean update(UserDetails userDetails) {
		userDtlsRepo.save(userDetails);
		return userDetails.getUserId() != null;
	}

	@Override
	public boolean deleteUserById(Integer userId) {
		boolean status = false;
		try {
			userDtlsRepo.deleteById(userId);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean activeSw(Integer userId, Boolean status) {
		Optional<UserDetails> findById = userDtlsRepo.findById(userId);
		if (findById.isPresent()) {
			UserDetails userDetails = findById.get();
			userDetails.setActive(status);
			userDtlsRepo.save(userDetails);
			return true;
		}
		return false;
	}

	@Override
	public String logIn(String userEmail, String pwd) {
		UserDetails user = userDtlsRepo.findByEmail(userEmail);
		if (user == null) {
			return messages.get(Constants.user_Not_Exists);
		}
		if (!user.getPassword().equals(pwd)) {
			return messages.get(Constants.inc_Pwd);
		}
		if (!user.isActive()) {
			return messages.get(Constants.acc_Not_Act);
		}
		return messages.get(Constants.welcome) + user.getFullName();
	}

	@Override
	public UserDetails getUserbyEmail(String userEmail) {
		return userDtlsRepo.findByEmail(userEmail);
	}

	@Override
	public void save(UserDetails user) {
		userDtlsRepo.save(user);
	}
}
