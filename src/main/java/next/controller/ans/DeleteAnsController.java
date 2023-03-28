package next.controller.ans;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import next.controller.UserSessionUtils;
import next.controller.qna.ShowController;
import next.dao.AnsDao;
import next.model.Ans;
import next.model.User;

public class DeleteAnsController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(ShowController.class);
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		logger.debug("answerId : {} ", req.getParameter("answerId"));
		
		AnsDao ansDao = new AnsDao();
		Ans ans = ansDao.select(Long.parseLong(req.getParameter("answerId")));
		if( ans == null ) {
			throw new IllegalStateException("존재하지 않는 댓글입니다.");
		}
		String originalWriter = ans.getWriter();
		HttpSession session = req.getSession();
		if( !UserSessionUtils.isLogined(session) ) {
			return "redirect:/users/loginForm";
		}
		User user = (User) session.getAttribute("user");
		if( !originalWriter.equals(user.getUserId()) ) {
			throw new IllegalStateException("다른 사용자의 글을 삭제할 수 없습니다.");
		}
		ansDao.delete(ans);
		return "redirect:/";
	}

}
