package com.taskjwt.service;

import com.taskjwt.model.UserDao;
import com.taskjwt.model.UserDto;
import com.taskjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
		UserDao user = userDao.findByPhone(phone);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with phone: " + phone);
		}
		return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(),
				new ArrayList<>());
	}

	public UserDao save(UserDto user) {
		UserDao newUser = new UserDao();
		newUser.setPhone(user.getPhone());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userDao.save(newUser);
	}

	/**
	 * Locates the user based on the username. In the actual implementation, the search
	 * may possibly be case sensitive, or case insensitive depending on how the
	 * implementation instance is configured. In this case, the <code>UserDetails</code>
	 * object that comes back may have a username that is of a different case than what
	 * was actually requested..
	 *
	 * @param phone the username identifying the user whose data is required.
	 * @return a fully populated user record (never <code>null</code>)
	 * @throws UsernameNotFoundException if the user could not be found or the user has no
	 *                                   GrantedAuthority
	 */
	@Override
	public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
		UserDao user = userDao.findByPhone(phone);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with phone: " + phone);
		}
		return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(),
				new ArrayList<>());
	}
}