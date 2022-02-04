package com.ssanolja.backend.config;

import com.ssanolja.backend.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // "/user/**" 로 들어오는 주소들은 인증이 필요하다.
                .anyRequest().permitAll() // 그 외에 다른 요청들은 다 permitAll 한다.
                .and()
                .formLogin()
                .loginPage("/login") // 인증이 필요한 요청들이 들어오면 /login으로 보낸다? // 여기에 구글 로그인으로 갈 url 주면 될듯...?
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행한다.
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
    }
}
