package com.orderme.ordermebackend.service.security;

import com.orderme.ordermebackend.model.entity.User;
import com.orderme.ordermebackend.repository.UserRepository;
import com.orderme.ordermebackend.model.entity.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        userOptional.orElseThrow(() -> new UsernameNotFoundException(
                String.format("The user with email: %s was not found", email)));
        return userOptional.map(UserDetailsImpl::new).get();
    }

//    @Override
//    public UserDetails loadUserByUsername(final String userlogin)
//            throws UsernameNotFoundException {
//        com.meetup.entities.User userInfo = userDAO.findUserByLogin(userlogin);
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : userInfo.getRoles()) {
//            GrantedAuthority a = new SimpleGrantedAuthority(role.name());
//            authorities.add(a);
//        }
//
//        return new User(userInfo.getLogin(),
//                userInfo.getPassword(),
//                authorities);
//
//    }
}
