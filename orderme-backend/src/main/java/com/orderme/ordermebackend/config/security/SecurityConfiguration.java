package com.orderme.ordermebackend.config.security;

import com.orderme.ordermebackend.controller.utils.PathRoutes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final UserDetailsService userDetailsService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final ExceptionHandlerChainFilter exceptionHandlerChainFilter;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter,
                                 @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                                 ExceptionHandlerChainFilter exceptionHandlerChainFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
        this.exceptionHandlerChainFilter = exceptionHandlerChainFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors()
                .and().exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                    .authorizeRequests()
                        .antMatchers(PathRoutes.PATH_AUTH + PathRoutes.CHILD_PATH_ADMIN_REGISTER).hasAuthority("SUPER_ADMIN")
                        .antMatchers(PathRoutes.PATH_ORDERS).hasAnyAuthority("SUPER_ADMIN", "ADMIN", "USER")
                        .antMatchers(PathRoutes.PATH_ORDER_LINES).hasAnyAuthority("SUPER_ADMIN", "ADMIN", "USER")
                        .antMatchers(HttpMethod.POST,   PathRoutes.PATH_GOODS_AVAILABILITIES + "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                        .antMatchers(HttpMethod.PATCH,  PathRoutes.PATH_GOODS_AVAILABILITIES + "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                        .antMatchers(HttpMethod.DELETE, PathRoutes.PATH_GOODS_AVAILABILITIES + "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                        .antMatchers(HttpMethod.POST,   PathRoutes.PATH_GOODS + "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                        .antMatchers(HttpMethod.PATCH,  PathRoutes.PATH_GOODS + "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                        .antMatchers(HttpMethod.DELETE, PathRoutes.PATH_GOODS + "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                        .antMatchers(HttpMethod.POST,   PathRoutes.PATH_GOODS_TYPES + "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                        .antMatchers(HttpMethod.PATCH,  PathRoutes.PATH_GOODS_TYPES + "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                        .antMatchers(HttpMethod.DELETE, PathRoutes.PATH_GOODS_TYPES + "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                        .antMatchers(HttpMethod.POST,   PathRoutes.PATH_SHOPS + "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                        .antMatchers(HttpMethod.PATCH,  PathRoutes.PATH_SHOPS + "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                        .antMatchers(HttpMethod.DELETE, PathRoutes.PATH_SHOPS + "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                    .anyRequest().permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerChainFilter, JwtAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
