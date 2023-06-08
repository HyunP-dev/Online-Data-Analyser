package kr.ac.hallym.onlinedataanalyser.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.hallym.onlinedataanalyser.database.Database;
import kr.ac.hallym.onlinedataanalyser.model.User;
import kr.ac.hallym.onlinedataanalyser.repository.UsersRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userid = req.getParameter("userid");
        String userpw = req.getParameter("userpw");



        UsersRepository usersRepository = new UsersRepository();
        try {
            for (User user : usersRepository.findAll()) {
                if (user.getUserid().equals(userid) &&
                    user.getUserpw().equals(userpw)) {
                    System.out.println(userid);

                    req.getSession().setAttribute("userid", userid);
                    req.getSession().setAttribute("userpw", userpw);

                    resp.sendRedirect("./");
                    return;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("./");
    }
}
