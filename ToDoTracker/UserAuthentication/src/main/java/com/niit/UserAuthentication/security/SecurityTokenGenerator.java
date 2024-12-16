package com.niit.UserAuthentication.security;



import com.niit.UserAuthentication.domain.UserData;

import java.util.Map;


public interface SecurityTokenGenerator {

    Map<String,String> generateToken(UserData user);
}
