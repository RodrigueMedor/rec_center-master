package controller.admin;

import dao.CheckInDAO;
import dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users")
public class UserController extends HttpServlet {
    UserDAO userDAO=UserDAO.getInstance();
    @Override
    public void init() throws ServletException {
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // req.setAttribute("users", CheckInDAO.getInstance().getAllCheckInUsers());
        req.getSession().setAttribute("users", CheckInDAO.getInstance().getAllCheckInUsers());
//        RequestDispatcher view = req.getRequestDispatcher("views/admin/users.jsp");
//        view.forward(req, resp);
        req.getSession().setAttribute("allUsers", userDAO.getUsers());
      resp.sendRedirect(req.getContextPath()+"/views/admin/users.jsp");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
