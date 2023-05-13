package kr.ac.hallym.onlinedataanalyser.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.Arrays;

@WebServlet(name = "reportServlet", value = "/report-viewer")
public class ReportServlet extends HttpServlet {
    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        File folder = new File(System.getProperty("user.home") + "/Online-Data-Analyser-Data");

        request.setAttribute("pdfs", folder.listFiles());
        System.out.println(Arrays.toString(folder.listFiles()));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/window/report-viewer.jsp");
        dispatcher.forward(request, response);
    }

    public void destroy() {
    }
}