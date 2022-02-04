package com.bridgelabz.userregistration.utility;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;

@Component
public class TokenUtil {
	
	private final String TOKEN_SECRET = "Nikhil";
	
	/*** Creating token. ***/
	public String createToken(Long id) {
		Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
		String token = JWT.create()
				          .withClaim("id", id)
				          .sign(algorithm);
		return token;
	}
	
	/*** Decoding token and return id. ***/
	public Long decodeToken(String token) {
		Verification verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
		JWTVerifier jwtVerifier = verification.build();
		DecodedJWT decodedJWT = jwtVerifier.verify(token);
		Claim claim = decodedJWT.getClaim("id");
		Long employee_id = claim.asLong();
		return employee_id;
	}
}