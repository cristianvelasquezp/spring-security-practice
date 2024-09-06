package org.cristianvelasquezp.springsecuritypractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {


    private final AuthenticationProvider authenticationProvider;

    public ProjectSecurityConfig(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.requiresChannel(channel -> channel.anyRequest().requiresSecure())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(flc -> flc.loginPage("/login").defaultSuccessUrl("/home").failureUrl("/login?error=true"))
                .httpBasic(c -> c.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/myAccount","/home").authenticated()
                        .requestMatchers("/login/**").permitAll()
        );

        return http.build();
    }

}
