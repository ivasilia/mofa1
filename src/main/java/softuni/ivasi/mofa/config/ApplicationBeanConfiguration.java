package softuni.ivasi.mofa.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import softuni.ivasi.mofa.security.CustomSuccessHandler;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CustomSuccessHandler authSuccessHandler(){
        return new CustomSuccessHandler();
    }
}
