package kr.ac.hallym.onlinedataanalyser.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(name = "pdfServlet", value = "/pdf")
public class PDFServlet extends HttpServlet {
    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        request.getParameter("");
        InputStream in = new FileInputStream("/mnt/d_drive/University_Homeworks/2021 여름방학 프로젝트/3축가속도센서를이용한실시간걸음수검출알고리즘.pdf");
        OutputStream out = response.getOutputStream();
//        OutputStreamWriter writer = new OutputStreamWriter(out);
        byte[] buffer = new byte[1024];
        int numBytesRead;
        while ((numBytesRead = in.read(buffer)) > 0) {
            out.write(buffer, 0, numBytesRead);
        }
    }

    public void destroy() {
    }
}