package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.user.CreateUserController;
import next.dao.QuestionDao;

@Controller
public class MyController {
	 private static final Logger log = LoggerFactory.getLogger(MyController.class);
	 
    @RequestMapping("/user/findUserId")
    public ModelAndView findUserId(HttpServletRequest request, HttpServletResponse response) {
    	log.debug("findUserId");
    	return null;
    }
    
    @RequestMapping(value ="/save", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
    	log.debug("save");
    	return null;
    }
}
