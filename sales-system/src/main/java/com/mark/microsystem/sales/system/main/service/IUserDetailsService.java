package com.mark.microsystem.sales.system.main.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserDetailsService {

    UserDetails loadUserByUsername(String username);
}
