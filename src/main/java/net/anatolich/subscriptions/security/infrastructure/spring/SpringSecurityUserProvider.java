package net.anatolich.subscriptions.security.infrastructure.spring;

import net.anatolich.subscriptions.security.domain.UserId;
import net.anatolich.subscriptions.security.domain.UserProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityUserProvider implements UserProvider {

    @Override
    public UserId currentUser() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String username = (principal instanceof User) ? ((User) principal).getUsername() : (String) principal;
        return UserId.of(username);
    }
}
