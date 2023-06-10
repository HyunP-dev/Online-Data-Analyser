package kr.ac.hallym.onlinedataanalyser.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.hallym.onlinedataanalyser.model.RawDataset;
import kr.ac.hallym.onlinedataanalyser.model.User;
import kr.ac.hallym.onlinedataanalyser.repository.UsersRepository;
import kr.ac.hallym.onlinedataanalyser.statistics.KNearestNeighbors;
import kr.ac.hallym.onlinedataanalyser.toolkit.Cryptography;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "analyseServlet", value = "/analyse")
public class AnalyseServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        String userid = (String) req.getSession().getAttribute("userid");
        String method = req.getParameter("method");
        String dataset = req.getParameter("dataset");
        String target = req.getParameter("target");
        String[] describe = req.getParameter("describe").split("::");
        String filename = req.getParameter("filename");
        System.out.println(target);
        System.out.println(describe);
        System.out.println(filename);
        System.out.println(method);
        switch (method) {
            case "k-nn" -> {
                System.out.println("[LOG] AnalyseServlet: " + "started kNN");
                KNearestNeighbors kNN = new KNearestNeighbors(
                        userid, new RawDataset(filename, dataset),
                        describe, target);
                kNN.generateReport();
//                System.out.println("[LOG] AnalyseServlet: " + "kNN.generateReport(); END");
            }
        }
//        PrintWriter out = resp.getWriter();
//        out.println("OK");
        resp.sendRedirect("/");
    }
}
