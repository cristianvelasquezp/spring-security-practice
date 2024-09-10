package org.cristianvelasquezp.springsecuritypractice.config;

import jakarta.servlet.http.HttpServletRequest;
import org.cristianvelasquezp.springsecuritypractice.filters.JWTTokenGeneratorFilter;
import org.cristianvelasquezp.springsecuritypractice.filters.JWTTokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class ProjectSecurityConfig {


    private final AuthenticationProvider authenticationProvider;

    public ProjectSecurityConfig(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.requiresChannel(channel -> channel.anyRequest().requiresSecure())
                .sessionManagement( sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("users/login").authenticated()
                                .requestMatchers("/home","/myAccount").hasAuthority("READ")
                                .requestMatchers("/login/**", "/users/register").permitAll()
                )
                .formLogin(flc -> flc.loginPage("/login").defaultSuccessUrl("/home").failureUrl("/login?error=true"))
                .logout(logout -> logout.logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true))
                .httpBasic(c -> c.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .authenticationProvider(authenticationProvider);

        return http.build();
    }

}
