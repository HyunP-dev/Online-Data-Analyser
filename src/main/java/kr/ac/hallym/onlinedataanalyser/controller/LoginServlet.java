package kr.ac.hallym.onlinedataanalyser.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userid = req.getParameter("userid");
        String userpw = req.getParameter("userpw");

        System.out.println(userid);
        resp.addCookie(new Cookie("token", "token"));
        resp.sendRedirect("./");
    }
}
