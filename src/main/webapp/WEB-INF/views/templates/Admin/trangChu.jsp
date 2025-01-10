<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Home Page Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <link href="/css/Admin/trangChu.css" rel="stylesheet">
    <style>


    </style>

</head>
<body>

<%@ include file="Layouts/TrangChu/_HeaderTrangChu.jsp" %>

<%--  nội dung--%>
<div class="container">

    <div class="select_input" style="margin-top: 50px">
        <div class="row">
                <div class="col-12">
                    <marquee>

                        <div style="font-size: 50px; font-weight: bold; font-family: Brush Script MT ; color: #bac8f3;">Website bán giày thể thao SHOP 5 ANH EM</div>


                    </marquee>
                </div>
        </div>
          <div class="row" style="margin-top: 30px">
              <div class="col-4">
                      <h3 style="text-align: center">
                          <button disabled="" type="button" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 inline-flex items-center">
                             
                              <a href="/GiayTheThao/listGiayTheThao" style="text-decoration: none; font-weight: bold">
                                  Giày thể thao
                              </a>
                          </button>

                      </h3>
              </div>
              <div class="col-4">
                  <h3 style="text-align: center">
                      <button disabled="" type="button" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 inline-flex items-center">
                          
                          <a href="/TrangChu_Admin/showFormGiaoDienGiayTheThao" style="text-decoration: none; font-weight: bold">
                                    Nhân viên
                          </a>
                      </button>

                  </h3>
              </div>
              <div class="col-4">
                  <h3 style="text-align: center">
                      <button disabled="" type="button" style="color: red;">
                         
                          <a href="/TrangChu_Admin/showFormGiaoDienGiayTheThao" style="text-decoration: none; font-weight: bold">
                                    Hóa đơn
                          </a>
                      </button>
                  </h3>
              </div>

          </div>
    </div>


</div>

<%@ include file="Layouts/TrangChu/_FooterTrangChu.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
</body>
</html>