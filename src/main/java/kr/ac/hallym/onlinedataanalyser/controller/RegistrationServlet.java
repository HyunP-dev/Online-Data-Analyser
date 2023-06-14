package kr.ac.hallym.onlinedataanalyser.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.hallym.onlinedataanalyser.model.User;
import kr.ac.hallym.onlinedataanalyser.repository.UsersRepository;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "registrationServlet", value = "/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userid = req.getParameter("userid");
        String userpw = req.getParameter("userpw");
        String nickname = req.getParameter("nickname");
        User user = new User();
        user.setUserid(userid);
        user.setUserpw(userpw);
        user.setNickname(nickname);

        UsersRepository usersRepository = new UsersRepository();
        try {
            usersRepository.save(user);
        } catch (SQLException e) {
            req.getSession().setAttribute("registration-error", true);
            resp.sendRedirect("/registration.html");
        }
        resp.sendRedirect("/");
    }
}
