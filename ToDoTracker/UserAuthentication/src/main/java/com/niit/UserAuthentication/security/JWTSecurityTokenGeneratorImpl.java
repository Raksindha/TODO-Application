package com.niit.UserAuthentication.security;


import com.niit.UserAuthentication.domain.UserData;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTSecurityTokenGeneratorImpl implements SecurityTokenGenerator {
//    public String createToken(User user){
//        // Write logic to create the Jwt
//        return null;
//    }

    public Map<String, String> generateToken(UserData user) {
        // Generate the token and set the user id in the claims
        String jwtToken = Jwts.builder().setIssuer("UserProduct")
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"mysecret")
                //mySecret is the key that has to be shared everytime you do encrypt and decrypt process
                .compact();
        Map<String,String> map = new HashMap<>();
        map.put("token",jwtToken);
        map.put("message","Authentication Successful");
        return map;
    }


}
