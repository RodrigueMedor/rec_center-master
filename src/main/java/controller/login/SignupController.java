package controller.login;

import com.google.gson.Gson;
import dao.UserDAO;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/signup")
public class SignupController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;
    private Gson mapper = new Gson();

    @Override
    public void init() throws ServletException {
        userDAO = UserDAO.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher=req.getRequestDispatcher(req.getContextPath()+"/views/signup.jsp");
        dispatcher.forward(req,resp);
//        resp.sendRedirect("signup.jsp");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String jsonString = req.getParameter("user");
        User user = mapper.fromJson(jsonString, User.class);
        userDAO.addUser(user);

        PrintWriter out = resp.getWriter();
        out.print(mapper.toJson(user));


    }
}
