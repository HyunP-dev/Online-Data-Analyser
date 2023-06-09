<%@ page import="kr.ac.hallym.onlinedataanalyser.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="apple-mobile-web-app-capable" content="yes"/>

    <link rel="manifest" href="manifest.json"/>
    <title>Document</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" rel="stylesheet">


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>

    <link href="https://cdn.datatables.net/v/bs5/dt-1.13.4/datatables.min.css" rel="stylesheet"/>
    <script src="https://cdn.datatables.net/v/bs5/dt-1.13.4/datatables.min.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/danfojs@1.1.2/lib/bundle.min.js"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.6.347/pdf.min.js"></script>

    <link rel="stylesheet" href="styles/main.css"/>
    <link rel="stylesheet" href="styles/menu.css"/>
    <script>
        <%
            if (session.getAttribute("user") == null) response.sendRedirect("./login.html");
        %>

        window.onload = () => {
            // if (document.cookie.indexOf("token=") === -1) location.href = "./login.html"
            document.getElementById("ml-panel").classList.remove("collapse")
            document.querySelector("#report-menus li").classList.add("choose")
        }

        function logout() {
            if (!confirm("로그아웃하시겠습니까?")) return
            location.href = "/logout"
        }
    </script>
</head>

<body>
<main class="d-flex flex-nowrap" style="height: 100vh;">
    <div class="left-panel d-flex flex-column flex-shrink-0 p-3 text-bg-dark" style="width: 280px;">
        <a href="#" class="text-white text-decoration-none"
           style="text-align: center;">
                <span class="fs-4" style="font-weight: bold;">
                    데이터 분석기
                </span>
        </a>
        <hr>
        <ul class="nav nav-pills flex-column mb-auto">
            <li class="nav-item">
                <a data-bs-toggle="collapse" role="button" href="#ml-menus"
                   class="nav-link text-white" aria-current="page">
                    <b>머신러닝</b>
                </a>
                <div id="ml-menus" class="submenu collapse">
                    <ul class="nav nav-pills flex-column">
                        <li class="nav-item" data-algorithm-name="k-nn">최근접 알고리즘</li>
                        <li class="nav-item" data-algorithm-name="decision-tree">의사결정나무</li>
                        <span style="height: 5px;"></span>
                    </ul>
                </div>
            </li>
            <!--            <li class="nav-item">-->
            <!--                <a data-bs-toggle="collapse" role="button" href="#stat-menus"-->
            <!--                   class="nav-link text-white" aria-current="page">-->
            <!--                    <b>통계</b>-->
            <!--                </a>-->
            <!--                <div id="stat-menus" class="submenu collapse">-->
            <!--                    <ul class="nav nav-pills flex-column">-->
            <!--                        <li class="nav-item">교차분석</li>-->
            <!--                        <li class="nav-item">분산분석</li>-->
            <!--                        <li class="nav-item">상관분석</li>-->
            <!--                        <li class="nav-item">회귀분석</li>-->
            <!--                        <span style="height: 5px;"></span>-->
            <!--                    </ul>-->
            <!--                </div>-->
            <!--            </li>-->
            <li class="nav-item">
                <a data-bs-toggle="collapse" role="button" href="#report-menus"
                   class="nav-link text-white" aria-current="page">
                    <b>보고서</b>
                </a>
                <div id="report-menus" class="submenu collapse">
                    <ul class="nav nav-pills flex-column">
                        <li class="nav-item">작성된 보고서 목록</li>
                        <span style="height: 5px;"></span>
                    </ul>
                </div>
                <script>
                    const mlMenuButtons = [...document.querySelectorAll("#ml-menus li")]
                    const reportMenuButtons = [...document.querySelectorAll("#report-menus li")]

                    mlMenuButtons.map(li => {
                        li.onclick = () => {
                            [...mlMenuButtons, ...reportMenuButtons]
                                .forEach(button => button.classList.remove("choose"))
                            li.classList.add("choose")
                            document.getElementById("ml-panel").classList.remove("collapse")
                            document.getElementById("report-panel").classList.add("collapse")
                        }
                    })

                    reportMenuButtons.map(li => {
                        li.onclick = () => {
                            [...mlMenuButtons, ...reportMenuButtons]
                                .forEach(button => button.classList.remove("choose"))
                            li.classList.add("choose")
                            document.getElementById("report-panel").classList.remove("collapse")
                            document.getElementById("ml-panel").classList.add("collapse")
                        }
                    })
                </script>
            </li>
        </ul>
        <hr>
        <div class="dropdown">
        <span class="d-flex align-items-center text-white text-decoration-none" aria-expanded="false">
<%--            <img src="https://github.com/mdo.png" alt="" width="32" height="32" class="rounded-circle me-2">--%>
            <% User user = ((User) session.getAttribute("user")); %>
            <strong><%= user!=null?user.getNickname():""%></strong>
            <span style="margin-left: auto;">
                <i onclick="logout()" style="cursor: pointer;" class="fa-solid fa-arrow-right-from-bracket"></i>
            </span>
        </span>
        </div>
    </div>

    <div id="ml-panel" class="right-panel">
        <div id="dataset-loader" class="right-panel-window"></div>
        <div id="data-viewer" class="right-panel-window"></div>
        <div id="vars-selector" class="right-panel-window"></div>
        <script>
            $("#dataset-loader").load("window/dataset-loader.html")
            $("#data-viewer").load("window/data-viewer.html")
            $("#vars-selector").load("window/vars-selector.html")
        </script>
        <span style="height: 5px; display: block;"></span>
    </div>
    <div id="report-panel" class="right-panel">
        <!--        <div id="report-selector" class="right-panel-window"></div>-->
        <div id="report-viewer" class="right-panel-window"></div>
        <script>
            // $("#report-selector").load("window/report-selector.html")
            // $("#report-viewer").load("window/report-viewer.html")
            $("#report-viewer").load("window/report-viewer.jsp")
        </script>
        <style>
            #report-viewer {
                margin-bottom: 0;
                height: calc(100vh - 50px);
            }
        </style>
    </div>
</main>
<script>
    window.onload = () =>
        $(reportMenuButtons[0]).click()
</script>
</body>
</html>