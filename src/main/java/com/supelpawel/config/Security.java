package com.supelpawel.config;

import com.supelpawel.account.repository.AccountRepository;
import com.supelpawel.role.repository.RoleRepository;
import com.supelpawel.transfer.repository.TransferRepository;
import com.supelpawel.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.supelpawel.deposit.repository.DepositRepository;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = {UserRepository.class, RoleRepository.class,
    AccountRepository.class, DepositRepository.class, TransferRepository.class})
public class Security {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .authorizeHttpRequests((requests) -> {
              try {
                requests
                    .antMatchers("/login", "/user/add").permitAll()
                    .antMatchers("/account/**").authenticated()
                    .antMatchers("/user/list").hasRole("ADMIN")
                    .and()
                    .exceptionHandling().accessDeniedPage("/403");
              } catch (Exception e) {
                throw new RuntimeException(e);
              }
            }
        )
        .formLogin((form) -> form
            .loginPage("/login")
            .defaultSuccessUrl("/account/list")
            .permitAll()
        )
        .logout((logout) -> logout
            .logoutSuccessUrl("/")
            .permitAll()
        )
        .csrf().disable();

    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
