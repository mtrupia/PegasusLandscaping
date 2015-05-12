package Personal_Project.servlet;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Personal_Project.controller.Controller;
import Personal_Project.model.Customer;
import Personal_Project.model.Payment;
import Personal_Project.model.Service;

public class StatementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getSession().getAttribute("abc") == null) {
			resp.sendRedirect(req.getContextPath() + "/Login");
			return;
		}
		
		for (Customer me : Controller.getCustomes()) {
			if (me.getId() == ManageServlet.customeron) {
				for (Payment paid : Controller.statementPayments(ManageServlet.customeron)) {
					me.setPaid(me.getPaid()+paid.getPaid());
				}
				for (Service owed : Controller.statementServices(ManageServlet.customeron)) {
					me.setOwed(me.getOwed()+owed.getCost());
				}
				me.setBalance(me.getOwed()-me.getPaid());
				req.setAttribute("meonnow", me);
			}
		}
		req.setAttribute("date", new Date().toString().substring(0, 10));
		
		List<Payment> payments = new ArrayList<Payment>();
		List<Service> services = new ArrayList<Service>();
		// payments and invoices
		for (int i = Controller.statementPayments(ManageServlet.customeron).size()-1; i >= 0; i--) {
			payments.add(Controller.statementPayments(ManageServlet.customeron).get(i));
		}
		for (int i = Controller.statementServices(ManageServlet.customeron).size()-1; i >= 0; i--) {
			services.add(Controller.statementServices(ManageServlet.customeron).get(i));
		}
		req.setAttribute("payments", payments);
		req.setAttribute("services", services);
		req.getRequestDispatcher("/_view/Statement.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}