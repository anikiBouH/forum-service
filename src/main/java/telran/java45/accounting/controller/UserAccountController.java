package telran.java45.accounting.controller;

import java.util.Base64;

import org.apache.coyote.http11.filters.VoidInputFilter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java45.accounting.dto.RolesResponseDto;
import telran.java45.accounting.dto.UserAccountResponseDto;
import telran.java45.accounting.dto.UserRegisterDto;
import telran.java45.accounting.dto.UserUpdateDto;
import telran.java45.accounting.service.UserAccountService;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserAccountController {

	final UserAccountService userAccountService;
	
	@PostMapping("/register")
	public UserAccountResponseDto register(@RequestBody UserRegisterDto userRegisterDto) {
		return userAccountService.addUser(userRegisterDto);
	}
	
	@PostMapping("/login")
	public UserAccountResponseDto login(@RequestHeader("Authorization") String token) {
		String[] credentials = getCredentials(token);
		return userAccountService.getUser(credentials[0]);
	}

	private String[] getCredentials(String token) {
		String[] basicAuthStrings = token.split(" ");
		String decode = new String(Base64.getDecoder().decode(basicAuthStrings[1]));
		return decode.split(":");
	}
	
	@DeleteMapping("/user/{login}")
	public UserAccountResponseDto deleteUser(@PathVariable String login) {
		return userAccountService.removeUser(login);
	}
	
	@PutMapping("/user/{login}")
	public UserAccountResponseDto updateUser(@PathVariable String login,@RequestBody UserUpdateDto userUpdateDto) {
		return userAccountService.editUser(login, userUpdateDto);
	}
	
	@PutMapping("/user/{login}/role/{role}")
	public RolesResponseDto addRole(@PathVariable String login,@PathVariable String role) {
		return userAccountService.changeRolesList(login, role, true);
	}
	
	@DeleteMapping("/user/{login}/role/{role}")
	public RolesResponseDto deleteRole(@PathVariable String login,@PathVariable String role) {
		return userAccountService.changeRolesList(login, role, false);
	}
	
	@PutMapping("/password")
	public void changePassword(@RequestHeader("Authorization") String token, @RequestHeader("X-Password") String newPassword) {
		String[] credentials = getCredentials(token);
		userAccountService.changePassword(credentials[0], newPassword);
	}
}
