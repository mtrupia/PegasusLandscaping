package Personal_Project.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Personal_Project.controller.Controller;
import Personal_Project.model.*;

public class ManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	int pos = 0;
	String searchTerm = "";
	public static int customeron = 1;
	int sectionOn = 0;
	
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
		if (param("searcher", req) != null) {
				if (!param("searchString", req).isEmpty() && !param("searchString", req).equals("")) {
					searchTerm = param("searchString", req);
				} else if (param("searcherSelect", req) != null){
					searchTerm = Controller.getCustomes().get(Integer.parseInt(param("searcherSelect", req))-1).getAddress();
				}
				pos = 3;
		}
		if (pos == 0) {
			req.setAttribute("customers", Controller.getCustomes());
		} else if (pos == 1) {
			req.setAttribute("customers", Controller.getActiveCustomers());
		} else if (pos == 2) {
			req.setAttribute("customers", Controller.getDeactiveCustomers());
		} else {
			req.setAttribute("customers", Controller.search(searchTerm));
		}
		req.setAttribute("date", new Date().toString().substring(0, 10));
		if (Controller.search(searchTerm).size() == 1) {
			customeron = Controller.search(searchTerm).get(0).getId();
			searchTerm = "";
		}
		for (Customer me : Controller.getCustomes()) {
			if (me.getId() == customeron) {
				for (Payment paid : Controller.statementPayments(customeron)) {
					me.setPaid(me.getPaid()+paid.getPaid());
				}
				for (Service owed : Controller.statementServices(customeron)) {
					me.setOwed(me.getOwed()+owed.getCost());
				}
				me.setBalance(me.getOwed()-me.getPaid());
				req.setAttribute("meonnow", me);
			}
		}
		req.setAttribute("sectionOn", sectionOn);
		if (sectionOn == 0) {
			req.setAttribute("sectionList", Controller.statementServices(customeron));
		} else {
			req.setAttribute("sectionList", Controller.statementPayments(customeron));
		}
		req.getRequestDispatcher("/_view/Manage.jsp").forward(req, resp);
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
		if (param("schedule", req) != null) {
			resp.sendRedirect(req.getContextPath() + "/Main");
			return;
		}
		else if (param("active", req) != null) {
			pos = 1;
		}
		else if (param("deactive", req) != null) {
			pos = 2;
		}
		else if (param("all", req) != null) {
			pos = 0;
		}
		else if (param("search", req) != null) {
			searchTerm = param("searchText", req);
			if (searchTerm != null && searchTerm != "") {
				pos = 3;
			}
		}
		else if (param("toStatement", req) != null) {
			resp.sendRedirect(req.getContextPath() + "/Statement");
			return;
		}
		else if (param("notactive", req) != null) {
			Controller.setCustomerActive(customeron, 0);
		}
		else if (param("isactive", req) != null) {
			Controller.setCustomerActive(customeron, 1);
		} else if (param("addlawn", req) != null) {
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
			customeron = Controller.getCustomes().size();
		} else if (param("deleteme", req) != null) {
			Controller.deleteCustomer(customeron);
		} else if (param("newPaymentAdd", req) != null) {
			int bad = 0;
			int paid = 0;
			try {
				paid = Integer.parseInt(param("newPaymentAmount", req));
			} catch (NumberFormatException e) {
				bad++;
			}
			String memo = param("newPaymentMemo", req);
			if (bad == 0) {
				Controller.addPayment(customeron, paid, memo);
			}
		}  else if (param("newInvoiceAdd", req) != null) {
			int bad = 0;
			int paid = 0;
			try {
				paid = Integer.parseInt(param("newInvoiceAmount", req));
			} catch (NumberFormatException e) {
				bad++;
			}
			String memo = param("newInvoiceMemo", req);
			if (bad == 0) {
				Controller.addService(customeron, paid, memo);
			}
		} else if (param("invoices", req) != null) {
			sectionOn = 0;
		} else if (param("payments", req) != null) {
			sectionOn = 1;
		} else if (param("cutty", req) != null) {
			Controller.setCustomerDaily(customeron, Integer.parseInt(param("cuttyVal", req)));
		} else if (param("customerwant", req) != null) {
			customeron = Integer.parseInt(param("customerwant", req));
		} else if (param("sectionDelete", req) != null) {
			Controller.removeSection(Integer.parseInt(param("sectionDelete", req)), sectionOn, customeron);
		}
		resp.sendRedirect(req.getContextPath() + "/Manage");
	}
	
}