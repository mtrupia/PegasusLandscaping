package Personal_Project.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Personal_Project.controller.Controller;
import Personal_Project.model.Customer;
import Personal_Project.model.Payment;
import Personal_Project.model.Service;

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String param(String param, HttpServletRequest req) {
		return req.getParameter(param);
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getSession().getAttribute("abc") == null) {
			resp.sendRedirect(req.getContextPath() + "/Login");
			return;
		}
		req.setAttribute("customers", Controller.getActiveCustomers());
		req.setAttribute("memes", Controller.schedule(new Date().getTime()));
		req.setAttribute("date", new Date().toString().substring(0, 10));
		List<Customer> pastDuey = new ArrayList<Customer>();
		for (Customer me : Controller.getCustomes()) {
			for (Payment paid : Controller.statementPayments(me.getId())) {
				me.setPaid(me.getPaid()+paid.getPaid());
			}
			for (Service owed : Controller.statementServices(me.getId())) {
				me.setOwed(me.getOwed()+owed.getCost());
			}
			me.setBalance(me.getOwed()-me.getPaid());

			if (me.getBalance() > 50) {
				pastDuey.add(me);
			}
		}
		req.setAttribute("pastDuey", pastDuey);
		req.getRequestDispatcher("/_view/Main.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("date", new Date().toString().substring(0, 10));
		if (req.getSession().getAttribute("abc") == null) {
			resp.sendRedirect(req.getContextPath() + "/Login");
			return;
		}
		if (param("logout", req) != null) {
			req.getSession().removeAttribute("abc");
			resp.sendRedirect(req.getContextPath() + "/Login");
			return;
		}
		if (param("manage", req) != null) {
			resp.sendRedirect(req.getContextPath() + "/Manage");
			return;
		}
		if (param("addlawn", req) != null) {
			int bad = 0;
			String fname = param("fname", req);
			String lname = param("lname", req);
			String address = param("address", req);
			String city = param("city", req);
			String state = param("state", req);
			int zip = 0, daily = 0;
			try {
				zip = Integer.parseInt(param("zip", req));
				daily = Integer.parseInt(param("daily", req));
			} catch (NumberFormatException e) {
				bad++;
			}
			if (bad == 0) {
				Controller.addLawn(fname, lname, address, city, state, zip, daily);
			}
		} else if (param("addToSchedule", req) !=null) {
			if (param("addme", req)!=null) {
				Controller.addToSchedule(Integer.parseInt(param("addme", req)));
			}
		} else if (param("addpayment", req) != null) {
			int bad = 0;
			int paid = 0;
			try {
				paid = Integer.parseInt(param("paid", req));
			} catch (NumberFormatException e) {
				bad++;
			}
			String memo = param("comment", req);
			if (param("addme", req)!=null && bad == 0) {
				Controller.addPayment(Integer.parseInt(param("addme", req)), paid, memo);
			}
		} else if (param("scheduleupdate", req) != null){
			for (int i = Controller.schedule(new Date().getTime()).size()-1; i >= 0 ; i--) {
				String amount = param("amount" + Controller.schedule(new Date().getTime()).get(i).getId(), req);
				if (param("amount" + Controller.schedule(new Date().getTime()).get(i).getId(), req) != null) {
					if (Integer.parseInt(amount) != 0) {
						Controller.addService(Controller.schedule(new Date().getTime()).get(i).getId(), Integer.parseInt(param("amount" + Controller.schedule(new Date().getTime()).get(i).getId(), req)), param("memo" + Controller.schedule(new Date().getTime()).get(i).getId(), req));
					}
				}
			}
		} else {
			for (int i = 0; i < Controller.schedule(new Date().getTime()).size(); i++) {
				String amount = param("amount" + Controller.schedule(new Date().getTime()).get(i).getId(), req);
				if (param("cut" + Controller.schedule(new Date().getTime()).get(i).getId(), req) != null) {
					if (Integer.parseInt(amount) != 0) {
						Controller.addService(Controller.schedule(new Date().getTime()).get(i).getId(), Integer.parseInt(param("amount" + Controller.schedule(new Date().getTime()).get(i).getId(), req)), param("memo" + Controller.schedule(new Date().getTime()).get(i).getId(), req));
					}
				}
			}
		}
		resp.sendRedirect(req.getContextPath() + "/Main");
	}
	
}
