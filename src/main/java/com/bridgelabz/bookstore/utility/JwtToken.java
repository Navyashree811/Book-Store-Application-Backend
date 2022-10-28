package com.bridgelabz.bookstore.utility;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JwtToken {
	private static final String SECRET = "Navya";

	public static String createJWT(long empId) {

		String token = JWT.create().withClaim("id", empId).sign(Algorithm.HMAC256(SECRET));
		return token;
	}

	public static long decodeJWT(String jwt) {

		int id = 0;
		if (jwt != null) {

			id = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(jwt).getClaim("id").asInt();
		}
		return id;
	}

}
