package com.example.demo.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfigureSecurity extends WebSecurityConfigurerAdapter {

    @Resource(name = "UserService")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalConfigure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        // auth.inMemoryAuthentication().withUser("pinocho").password("password").roles("ADMIN");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        /*
         * http .authorizeRequests() //.anyRequest().authenticated()
         * .antMatchers("/").hasAuthority("USUARIO")
         * .antMatchers("/pruebaAdmin").hasAuthority("ADMINISTRADOR")
         * //.antMatchers("/programa").hasAuthority("ROL") .anyRequest().denyAll()
         * .and().formLogin();
         */
        http.cors().and().csrf().disable().authorizeRequests().antMatchers("/api/*").permitAll()
                /*.antMatchers("/")
                .hasAuthority("USUARIO").antMatchers("/pruebaAdmin").hasAuthority("ADMINISTRADOR")*/
                // .antMatchers("/programa").hasAuthority("ROL")
                .anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

}