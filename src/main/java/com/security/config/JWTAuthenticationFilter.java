package com.security.config;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.security.entity.ApplicationUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;
	//private BCryptPasswordEncoder bCryptPasswordEncoder;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		//this.authenticationManager = authenticationManager;
		//this.bCryptPasswordEncoder = bCryptPasswordEncoder;

		// Login path
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
		
		// Set name of parameter from client for get info
		setUsernameParameter("username");
		setPasswordParameter("password");

	}

	// 認証の処理
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
		
		// get login info from request
		ApplicationUser creds = new ApplicationUser();
		creds.setUsername(req.getParameter("username"));
		creds.setPassword(req.getParameter("password"));
		
		System.out.println(creds.getUsername()+" | "+creds.getPassword());
		
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
				creds.getPassword(), new ArrayList<>()));
	}

	// 認証に成功した場合の処理
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		// loginIdからtokenを設定してヘッダにセットする
		String token = Jwts.builder().setSubject(req.getParameter("username")) // usernameだけを設定する
				.setExpiration(new Date(System.currentTimeMillis() + 26000000))
				.signWith(SignatureAlgorithm.HS512, "abc".getBytes()).compact();
		res.addHeader("Authentication", "beaer " + token);

		// ここでレスポンスを組み立てると個別のパラメータを返せるがFilterの責務の範囲内で実施しなければならない
		// auth.getPrincipal()で取得できるUserDetailsは自分で作ったEntityクラスにもできるのでカスタム属性は追加可能
	}

}