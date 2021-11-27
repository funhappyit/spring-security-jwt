package jpabook.start.jwt.config.jwt;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jpabook.start.jwt.config.auth.PrincipalDetails;
import jpabook.start.jwt.model.User;
import lombok.RequiredArgsConstructor;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음
// /login 요청해서 username, password 전송하면 (post)
// UsernamePasswordAuthenticationFilter 동작을 함 
@RequiredArgsConstructor
public class jwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager;

	// /login 요청을 하면 로그인 시도를 위해서 실행되는 함수 
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("Jwt:로그인 시도중 ");
		
		//1.username, password 받아서 
		try {
//			BufferedReader br = request.getReader();
//			
//			String input = null;
//			
//			while((input = br.readLine()) != null) {
//				System.out.println(input);
//			}
			ObjectMapper om = new ObjectMapper();
			User user = om.readValue(request.getInputStream(), User.class);
			System.out.println(user);
			
			//토큰 생성 
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			
			//PrincipalDetailsService의 loadUserByUsername() 함수가 실행된 후 정상이면 authentication이 리턴됨
			//DB에 있는 username과 password가 일치한다.
			Authentication authentication =
					authenticationManager.authenticate(authenticationToken);
			
			// 로그인이 되었다는 뜻
			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
			
			System.out.println(principalDetails.getUser().getUsername()); //로그인 정상적으로 되었다는 뜻.
			//authentication 객체가 session영역에 저장을 해야하고 그 방법이 return 해주면 됨
			//리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고 하는것임
			//굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음. 근데 단지 권한 처리때문에 SESSION 넣어 줍니다.
			
			//JWT
			return authentication;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//2.정상인지 로그인 시도를 해보는 것 authenticationManager로 로그인 시도를 하면!! principalDetailsService가 호출 
		// loadUserByUsername() 함수 실행됨.
		
		//3.PrincipalDetails를 세션에 담고 (권한 관리를 위해서)
		
		//4.JWT 토큰을 만들어서 응답해주면 됨
		return null;
	}
	
	//attemptAuthentication실행 뒤 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행되요 
	//JWT 토큰을 만들어서 request 요청한 사용자에게 JWT토큰을 response 해주면 됨
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("인증완료");
		super.successfulAuthentication(request, response, chain, authResult);
	}
	
}
