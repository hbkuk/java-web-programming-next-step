package core.mvc;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.controller.CreateUserController;
import next.controller.HomeController;
import next.controller.ListUserController;
import next.controller.LoginController;
import next.controller.LogoutController;
import next.controller.ProfileController;
import next.controller.UpdateUserController;
import next.controller.UpdateUserFormController;
import next.controller.ans.DeleteAnswerController;
import next.controller.ans.addAnswerController;
import next.controller.qna.CreateQnaController;
import next.controller.qna.CreateQnaFormController;
import next.controller.qna.DeleteQnaController;
import next.controller.qna.ShowController;
import next.controller.qna.UpdateQnaController;
import next.controller.qna.UpdateQnaFormController;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        mappings.put("/", new HomeController());
        mappings.put("/users/form", new ForwardController("/user/form.jsp"));
        mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        mappings.put("/users", new ListUserController());
        mappings.put("/users/login", new LoginController());
        mappings.put("/users/profile", new ProfileController());
        mappings.put("/users/logout", new LogoutController());
        mappings.put("/users/create", new CreateUserController());
        mappings.put("/users/updateForm", new UpdateUserFormController());
        mappings.put("/users/update", new UpdateUserController());
        
        mappings.put("/qna/create", new CreateQnaController());
        mappings.put("/qna/form", new CreateQnaFormController());
        mappings.put("/qna/show", new ShowController());
        mappings.put("/qna/updateForm", new UpdateQnaFormController());
        mappings.put("/qna/update", new UpdateQnaController());
        mappings.put("/qna/delete", new DeleteQnaController());
        
        mappings.put("/api/qna/addAnswer", new addAnswerController());
        mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController());
        mappings.put("/ans/updateForm", null);
        mappings.put("/ans/update", null);
        mappings.put("/ans/delete", null);

        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }
}

