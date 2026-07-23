package com.shop.pavushop.config.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.shop.pavushop.repository.UserInfoRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInfoManagerConfig implements UserDetailsService {

    private final UserInfoRepo userInfoRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userInfoRepo
                .findByUsername(username)
                .map(UserInfoConfig::new)
                .orElseThrow(()-> new UsernameNotFoundException("UserName: "+username+" does not exist"));
    }
}
