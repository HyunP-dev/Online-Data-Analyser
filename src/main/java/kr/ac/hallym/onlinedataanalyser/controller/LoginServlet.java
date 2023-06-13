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
import kr.ac.hallym.onlinedataanalyser.toolkit.Cryptography;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Optional;


@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String userid = req.getParameter("userid");
//        String userpw = Cryptography.encrypt(
//                Cryptography.SHA256,
//                req.getParameter("userpw"));
        String userpw = req.getParameter("userpw");

        UsersRepository usersRepository = new UsersRepository();
        Optional<User> loginUser = usersRepository.findByUserid(userid);
        boolean loginSuccess = loginUser
                .map(user -> user.getUserpw().equals(userpw))
                .orElse(false);
        if (loginSuccess) {
            req.getSession().setAttribute("user", loginUser.get());
            resp.sendRedirect("./");
            return;
        }
        resp.addCookie(new Cookie("login-failed", ""));
        resp.sendRedirect("./");
    }
}
