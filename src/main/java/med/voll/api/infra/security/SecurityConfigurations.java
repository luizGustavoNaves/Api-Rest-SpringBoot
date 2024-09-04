package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static javax.management.Query.and;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;
                //O csrf() desabilita o Cross-Site Request Forgery (Ataque de Cross-Site Request Forgery)
                @Bean
                public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                    return http.csrf(csrf -> csrf.disable())
                            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                            .authorizeHttpRequests(req -> {
                                req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                                req.anyRequest().authenticated();
                            })
                            //setando a ordem dos filtros
                            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                            .build();
                }



    @Bean
    //O @Bean serve para exportar uma classe para o Spring, fazendo com que ele consiga carregar e realize a injecão de dependência en outras classes
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    //Ensinando a aplicação a criptografar as senhas
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


