package softuni.ivasi.mofa.common.statistics;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PathInterceptor implements HandlerInterceptor {
    private PathService pathService;

    public PathInterceptor(PathService pathService) {
        this.pathService = pathService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object object) throws Exception {
        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        switch (path) {
            case "/projects":
                pathService.incRequestsToProjectsCount();
                System.out.println(path);
                break;
            case "/items/show/{id}":
                pathService.incRequestsToItemsCount();
                System.out.println(path);
                break;
        }

        return true;
    }
}
