<%@ page import="java.io.File" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h4 style="font-weight: bold;">보고서 뷰어</h4>
<hr>
<div class="d-flex bd-highlight">

    <select class="form-select" name="reports" id="report-select" style="margin-right: 10px">
        <%
            File folder = new File(System.getProperty("user.home") + "/Online-Data-Analyser-Data");
            List<File> files = Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                    .filter(file -> file.getName().endsWith(".pdf"))
                    .toList();
            for(File file: files) { %>
                <option value="<%= file.getName()%>"><%= file.getName()%></option>
        <%  } %>
    </select>
    <button class="btn btn-primary" style="white-space:nowrap;">선택</button>
</div>

<embed type="application/pdf" src="./report" style="
    margin-top: 16px;
    width: 100%;
    height: calc(100% - 115px);
    border: 1px solid black;
">