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


@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String userid = req.getParameter("userid");
        String userpw = Cryptography.encrypt(
                Cryptography.SHA256,
                req.getParameter("userpw"));


        UsersRepository usersRepository = new UsersRepository();
        for (User user : usersRepository.findAll()) {
            if (user.getUserid().equals(userid) && user.getUserpw().equals(userpw)) {
                System.out.println(userid);

                req.getSession().setAttribute("userid", userid);

                resp.sendRedirect("./");
                return;
            }
        }
        resp.addCookie(new Cookie("login-failed", ""));
        resp.sendRedirect("./");
    }
}
