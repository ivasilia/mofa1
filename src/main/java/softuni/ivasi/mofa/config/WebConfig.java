package softuni.ivasi.mofa.config;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import softuni.ivasi.mofa.common.statistics.PathInterceptor;
import softuni.ivasi.mofa.common.statistics.StatsInterceptor;

@Component
public class WebConfig implements WebMvcConfigurer {

    private StatsInterceptor statsInterceptor;
    private PathInterceptor pathInterceptor;

    public WebConfig(StatsInterceptor statsInterceptor, PathInterceptor pathInterceptor) {
        this.statsInterceptor = statsInterceptor;
        this.pathInterceptor = pathInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(statsInterceptor);
        registry.addInterceptor(pathInterceptor);
    }
}
