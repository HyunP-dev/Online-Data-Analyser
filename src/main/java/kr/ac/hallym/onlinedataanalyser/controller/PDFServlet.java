package kr.ac.hallym.onlinedataanalyser.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(name = "pdfServlet", value = "/pdf")
public class PDFServlet extends HttpServlet {
    public void init() {
    }

    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/pdf");
        String filename = request.getParameter("filename");
        if (!filename.split("-")[0].equals(request.getSession().getAttribute("userid")))
            throw new Exception("공격을 감지하였습니다.");
        if (filename.contains("..")) throw new Exception("공격을 감지하였습니다.");
        InputStream in = new FileInputStream(System.getProperty("user.home") + "/Online-Data-Analyser-Data/" + filename);
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int numBytesRead;
        while ((numBytesRead = in.read(buffer)) > 0) {
            out.write(buffer, 0, numBytesRead);
        }
    }

    public void destroy() {
    }
}