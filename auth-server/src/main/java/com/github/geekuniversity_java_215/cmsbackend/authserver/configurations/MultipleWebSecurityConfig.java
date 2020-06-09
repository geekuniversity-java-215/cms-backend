package com.github.geekuniversity_java_215.cmsbackend.authserver.configurations;

import com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.filters.BasicAuthRequestFilter;
import com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.filters.BearerRequestFilter;
import com.github.geekuniversity_java_215.cmsbackend.core.configurations.filters.CorsAllowAllFilter;
import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;




@Slf4j
@EnableWebSecurity
public class MultipleWebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    /**
     * Token operations
     * Authorisation: Basic + Bearer
     */
    @Configuration
    @Order(1)
    public static class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

        private final BearerRequestFilter bearerRequestFilter;
        private final BasicAuthRequestFilter basicAuthRequestFilter;
        private final CorsAllowAllFilter corsAllowAllFilter;


        @Autowired
        public TokenWebSecurityConfig(BearerRequestFilter bearerRequestFilter,
                                      BasicAuthRequestFilter basicAuthRequestFilter,
                                      CorsAllowAllFilter corsAllowAllFilter) {
            this.bearerRequestFilter = bearerRequestFilter;
            this.basicAuthRequestFilter = basicAuthRequestFilter;
            this.corsAllowAllFilter = corsAllowAllFilter;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.cors().and()
                .antMatcher("/oauzz/token/**").authorizeRequests().anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .addFilterBefore(corsAllowAllFilter, ChannelProcessingFilter.class)
                .addFilterAfter(bearerRequestFilter, LogoutFilter.class)
                .addFilterAfter(basicAuthRequestFilter, LogoutFilter.class);
                
        }
    }



    /**
     * Administration
     * Authorisation: Bearer
     */
    @Configuration
    @Order(2)
    public static class AdminWebSecurityConfig extends WebSecurityConfigurerAdapter {

        private final BearerRequestFilter bearerRequestFilter;
        private final CorsAllowAllFilter corsAllowAllFilter;

        @Autowired
        public AdminWebSecurityConfig(BearerRequestFilter bearerRequestFilter,
                                      CorsAllowAllFilter corsAllowAllFilter) {
            this.bearerRequestFilter = bearerRequestFilter;
            this.corsAllowAllFilter = corsAllowAllFilter;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                .antMatcher("/admin/**").authorizeRequests().anyRequest().hasAuthority(UserRole.VAL.ADMIN)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .addFilterBefore(corsAllowAllFilter, ChannelProcessingFilter.class)
                .addFilterAfter(bearerRequestFilter, LogoutFilter.class);

            //.addFilterAfter(bearerRequestFilter, LogoutFilter.class)
        }
    }


//    /**
//     * Registrar of new users
//     * Authorisation: Basic
//     */
//    @Configuration
//    @Order(3)
//    public static class RegisterNewUserSecurityConfig extends WebSecurityConfigurerAdapter {
//
//        private final BasicAuthRequestFilter basicAuthRequestFilter;
//
//        public RegisterNewUserSecurityConfig(BasicAuthRequestFilter basicAuthRequestFilter) {
//            this.basicAuthRequestFilter = basicAuthRequestFilter;
//        }
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//
//            http.antMatcher("/registration/new").authorizeRequests().anyRequest().authenticated()
//                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and().csrf().disable()
//                    .addFilterAfter(basicAuthRequestFilter, LogoutFilter.class);
//        }
//    }


    /**
     * Registrar of new users, registration confirmation
     * Authorisation: None
     */
    @Configuration
    @Order(3)
    public static class RegistrationConfirmationSecurityConfig extends WebSecurityConfigurerAdapter {

        private final CorsAllowAllFilter corsAllowAllFilter;

        public RegistrationConfirmationSecurityConfig(CorsAllowAllFilter corsAllowAllFilter) {
            this.corsAllowAllFilter = corsAllowAllFilter;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                .authorizeRequests().antMatchers("/registration/add", "/registration/confirm").permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .addFilterBefore(corsAllowAllFilter, ChannelProcessingFilter.class);
        }

    }

}

// ==========================================================================================================

//    @Autowired
//    private final ApplicationContext context;

//    public MultipleWebSecurityConfig(ApplicationContext context) {
//        this.context = context;
//    }


//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        // configure AuthenticationManager so that it knows from where to load
//        // user for matching credentials
//        // Use BCryptPasswordEncoder
//        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
//    }






//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) {
//            auth.authenticationProvider(authProvider);
//        }


//        @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//        @Override
//        public AuthenticationManager authenticationManagerBean() throws Exception {
//            return super.authenticationManagerBean();
//        }



//            http.antMatcher("/admin/**").authorizeRequests().anyRequest().authenticated()
//                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and().csrf().disable()
//                    .httpBasic();






//        @Bean
//        @Override
//        public AuthenticationManager authenticationManagerBean() throws Exception {
//            return super.authenticationManagerBean();
//        }





//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        // user/password
//        UserDetails user =
//                User.builder()
//                        .username("user")
//                        .password("{bcrypt}$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6")
//                        .roles("USER")
//                        .disabled(false)
//                        .build();
//
//
//        return new InMemoryUserDetailsManager(user);
//    }



//            String[] paths = {"/admin", "/oauzz/tokenz"};
//            http.authorizeRequests().antMatchers(paths).authenticated()
//                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and().csrf().disable()
//                .httpBasic();


//                .httpBasic()
//                .and()
//                .addFilterBefore(jwtRequestFilter, BasicAuthenticationFilter.class)
//                .addFilterAt(customBasicAuthFilter, BasicAuthenticationFilter.class);
