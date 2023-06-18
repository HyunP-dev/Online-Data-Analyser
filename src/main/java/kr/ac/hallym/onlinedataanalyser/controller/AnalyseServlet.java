package kr.ac.hallym.onlinedataanalyser.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.hallym.onlinedataanalyser.model.RawDataset;
import kr.ac.hallym.onlinedataanalyser.model.User;
import kr.ac.hallym.onlinedataanalyser.statistics.KNearestNeighbors;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet(name = "analyseServlet", value = "/analyse")
public class AnalyseServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        User user = (User) req.getSession().getAttribute("user");
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
                        user.getUserid(), new RawDataset(filename, dataset),
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
