package online.awet.springapi.configurations.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final DataSource dataSource;    // Default datasource defined in application.properties
    private final Environment env;          // Environment variables defined in application.properties

    public SecurityConfiguration(DataSource dataSource, Environment env) {
        this.dataSource = dataSource;
        this.env = env;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Authorizations are applied in controller methods annotated with @PreAuthorize
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsManager jdbcUserDetailsManager() {

        String adminUsername = env.getProperty("appRoles.admin.username", "admin");
        String adminPassword = env.getProperty("appRoles.admin.password", "password");
        String userUsername = env.getProperty("appRoles.user.username", "user");
        String userPassword = env.getProperty("appRoles.user.password", "password");

        UserDetails user = User.builder()
            .username(userUsername)
            .password(passwordEncoder().encode(userPassword))
            .roles("USER")
            .build();

        UserDetails admin = User.builder()
            .username(adminUsername)
            .password(passwordEncoder().encode(adminPassword))
            .roles("USER", "ADMIN")
            .build();

        UserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        // Add users only if they did not exist yet
        if (!manager.userExists(user.getUsername())) {
            manager.createUser(user);
        }
        if (!manager.userExists(admin.getUsername())) {
            manager.createUser(admin);
        }
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
