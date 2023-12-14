package com.example.Member.config;

import com.example.Member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    @Autowired
    MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //authorization
        http
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests()

                .requestMatchers("/orders").hasAnyRole("USER")
                .requestMatchers("/list").hasAnyRole("USER")
                .requestMatchers("/pay").hasAnyRole("USER")
                .requestMatchers("/members/login").anonymous()
                .requestMatchers("/members/signup").anonymous()
                .requestMatchers("/members/logout").authenticated()

//
                .requestMatchers("/**").permitAll() //해당 경로에 대한 권한 설정
                .anyRequest().authenticated();

        http
                //로그인
                .formLogin()
                .loginPage("/members/login")            // 로그인 페이지 URL
                .defaultSuccessUrl("/")                 // 로그인 성공 시 이동할 URL
                .usernameParameter("memId")             // 로그인 시 사용할 파라미터 이름
                .failureUrl("/members/login/error")     // 로그인 실패 시 이동할 URL
                .and()

                //로그아웃
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))    // 로그아웃 URL
                .logoutSuccessUrl("/")                  // 로그아웃- 이동할 URL
        ;

        return http.build();
    }

//    //사용자 인증
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(memberService)
//                .passwordEncoder(passwordEncoder());
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(("/**"))
                        .allowedOrigins("*")
                        .allowedMethods(
                                HttpMethod.GET.name(),
                                HttpMethod.HEAD.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.DELETE.name());
            }
        };
    }
}
