package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.mvc.ModelAndView;
import core.nmvc.AbstractNewController;
import next.dao.QuestionRepository;

@Controller
public class HomeController extends AbstractNewController {
    private QuestionRepository questionRepository;
	
	@Inject
    public HomeController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    
    @RequestMapping("/")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jspView("home.jsp").addObject("questions", questionRepository.findAll());
    }
}
