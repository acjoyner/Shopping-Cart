package com.acjoyner.dream_shops.security.user;

import com.acjoyner.dream_shops.model.User;
import com.acjoyner.dream_shops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ShopUserDetailsService implements UserDetailsService, UserDetailsPasswordService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(
                () -> new UsernameNotFoundException("User not found!")
        );
        return ShopUserDetails.buildUserDetails(user);
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        if (user instanceof ShopUserDetails shopUserDetails) {
            User shopUser = shopUserDetails.getUser();
            shopUser.setPassword(newPassword);
            userRepository.save(shopUser);
            return ShopUserDetails.buildUserDetails(shopUser);
        } else {
            throw new IllegalArgumentException("Expected user to be an instance of ShopUserDetails");
        }
    }
}
