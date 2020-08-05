package softuni.ivasi.mofa.common.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/error")
public class ExceptionController {

    @GetMapping("/not-found")
    public ModelAndView notFound() {
        throw new EntityNotFoundException("How annoying! The object you are referring to could not be found! Error code 404.");
    }

    @GetMapping("/bad-request")
    public ModelAndView badRequest() {
        throw new EntityNotFoundException("Bad request! You messed up something here! Error code 400.");
    }
}
