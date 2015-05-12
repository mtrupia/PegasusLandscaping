package Personal_Project.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Personal_Project.controller.Controller;
import Personal_Project.model.Customer;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getSession().getAttribute("abc") != null) {
			resp.sendRedirect(req.getContextPath() + "/Main");
			return;
		}
		req.getRequestDispatcher("/_view/Login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, NullPointerException {
		Customer me = Controller.login(req.getParameter("name"), req.getParameter("pass"));
		if (me != null && req.getParameter("log") != null) {
			req.getSession().setAttribute("abc", me);
			resp.sendRedirect(req.getContextPath() + "/Main");
			return;
		} else { req.setAttribute("errorMessage", "Try Again!"); }
		req.getRequestDispatcher("/_view/Login.jsp").forward(req, resp);
	}
}
