package jpabook.start.jwt.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jpabook.start.jwt.model.User;
import jpabook.start.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;

// http://localhost:8080/login => 여기서 동작을 안한다
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = userRepository.findByUsername(username);
		return new PrincipalDetails(userEntity);
	}

}
