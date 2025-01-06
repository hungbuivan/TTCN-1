<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
        prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="f"
                                                                         uri="http://java.sun.com/jsp/jstl/functions" %> <%@ taglib
        uri="http://www.springframework.org/tags/form" prefix="sf"%>

<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Bán hàng tại quầy</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
            crossorigin="anonymous"
    />

    <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css"
            integrity="sha384-b6lVK+yci+bfDmaY1u0zE8YYJt0TZxLEAFyYSLHId4xoVvsrQu3INevFKo+Xir8e"
            crossorigin="anonymous"
    />
    <link
            rel="stylesheet"
            type="text/css"
            href="../../../resources/css/BanHangTaiQuay.css"
    />
    <style>
        .form-input-check {
            outline: none;
            border: none;
            background-color: rgb(252, 250, 250);
            text-align: right;
        }
        #customer-need-to-pay {
            color: #005cbf;
            font-weight: bold;
        }
        #listSP{
            height: 530px;
            overflow: auto;
        }
        #redirect-pay-form{
            width: 100%;
        }
        .button{
            margin-top: 10px;
            margin-bottom: 10px;
        }
        #scroll-kh{
            max-height: 500px;
            overflow: auto;
        }

        #textCodeCheck{
            border: 1px solid grey;
            background-color: rgb(237, 237, 237);
            outline: none;
            margin-right: 10px;
            border-radius: 5px;
        }

        #search-input{
            border: 1px solid grey;
            outline: none;
            border-top-left-radius: 5px;
            border-bottom-left-radius: 5px;
            border-right: none;

        }
        #search-btn{
            border: 1px solid grey;
            border-top-right-radius: 5px;
            border-bottom-right-radius: 5px;
            border-left: none;
            background-color: rgb(255, 255, 255);
        }
        .card-product{
            position: relative;

        }
        .notification{
            position: absolute;
            top: 0px;
            right: 0px;
            border: 1px solid rgb(184, 184, 184);
            border-right: none;
            border-top: none;
            border-bottom-left-radius: 10px;
            width: 40px;
            height: 30px;
            background-color: red;
            color: white;
            box-shadow: -2px 2px 5px rgb(156, 156, 156);
        }
        .attribute{
            display: flex;
            justify-content: space-between;
            font-size: 12px;
        }
        .showOrno{
            cursor: pointer;
            color: #cccccc;
            font-size: smaller;
        }
		
		.nav {
		    display: flex; /* Hiển thị các phần tử theo hàng ngang */
		    align-items: center; /* Căn giữa theo chiều dọc */
		    justify-content: flex-start; /* Căn trái */
		    gap: 10px; /* Khoảng cách giữa các phần tử */
		}
    </style>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/noUiSlider/14.6.3/nouislider.min.js"></script>
</head>
<body>

<header class="p-3 mb-3 border-bottom">
    <div class="container">
        <div
                class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start"
        >
            <a href="http://localhost:8080/TrangChu/Admin/home" class="d-flex align-items-center mb-2 mb-lg-0 text-dark text-decoration-none" id="banner">5 ANH EM</a>

            <div class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">

                <input type="text"  id="textCodeCheck" readonly="true" >

                <input type="search" name="search-name" id="search-input">

                <button id="search-btn"><i class="bi bi-search"></i></button>
				
				<div class="col-4">
				    <input
				        type="text"
				        id="searchProduct"
				        class="form-control"
				        placeholder="Tìm kiếm sản phẩm theo tên..."
				        oninput="filterProducts()"
				    />
				</div>
            </div>



            <div class="dropdown text-end">
                <a
                        href="#"
                        class="d-block link-dark text-decoration-none dropdown-toggle"
                        id="dropdownUser1"
                        data-bs-toggle="dropdown"
                        aria-expanded="false"
                >
                    <img
                            src="https://github.com/mdo.png"
                            alt="mdo"
                            width="32"
                            height="32"
                            class="rounded-circle"
                    />
                </a>
                <ul
                        class="dropdown-menu text-small"
                        aria-labelledby="dropdownUser1"
                >

                    <li><a class="dropdown-item" href="/TrangChu/ThongTinCaNhan/Admin">Profile</a></li>
                    <li><hr class="dropdown-divider" /></li>
                    <li><a class="dropdown-item" href="/UserLog/logout">Sign out</a></li>
                </ul>
            </div>
        </div>
    </div>
</header>

<section class="container-fluid">
    <div class="row">
        <div class="col-8">
            <div id="cart-detail">
                <table class="table table-hover">
                    <tbody id="cart-detail-product">

                    </tbody>
                </table>
            </div>
            <div id="all-product">
                <div
                        class="container-fluid"
                        style="display: flex; flex-wrap: wrap"
                        id="listProducts"
                >
                    <!-- <c:forEach items="${list}" var="gtt">
                        <div class="card card-product" style="width: 9.8rem">
                            <img
                                    src="/upload/${gtt.giayTheThao.getAnhDau()}"
                                    class="card-img-top img-product-card"
                            />

                            <div class="card-body product-card-body">
                                <p class="card-text">
                                    <a
                                            href="#"
                                            class="name-product"
                                            onclick="addToCart(`${gtt.id}`, `${gtt.giayTheThao.tenGiayTheThao}`, `${gtt.giayTheThao.giaBan}`, event)"
                                    >${gtt.giayTheThao.tenGiayTheThao} (${gtt.soLuong})</a
                                    >
                                </p>
                                <p class="card-text price-card-product">${gtt.giayTheThao.giaBan}</p>
                            </div>
                        </div>
                    </c:forEach> -->
                </div>
            </div>
        </div>
        <div class="col-4">
            <div id="listSP" >
                <table class="table table-hover" >
                    <thead>
                    <tr>
                        <th scope="col">STT</th>
                        <th scope="col">Mã hóa đơn</th>
                        <th scope="col">Nhân viên</th>
                        <th scope="col">Khách hàng</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody id="list-hdc">
                    <!-- <c:forEach items="${listHDC}" var="hdc" varStatus="status">
                  <tr onclick="getHDC(`${hdc.id}`)">
                    <td>${status.index+1}</td>
                    <td>${hdc.maHoaDon}</td>
                    <td>${hdc.user.tenUser==null?"N/A":hdc.user.tenUser}</td>
                    <td>${hdc.khachHang.tenKhachHang}</td>
                  </tr>
                </c:forEach> -->

                    </tbody>
                </table>
            </div>
            <div class="button">
                <!-- <button class="btn btn-primary" style="width: 100%;">Tạo hóa đơn mới</button> -->
                <button
                        class="btn btn-success"
                        data-bs-toggle="modal"
                        data-bs-target="#staticBackdropCheck"
                        style="width: 100%;"
                >Tạo mới hóa đơn</button>
                <div
                        class="modal fade"
                        id="staticBackdropCheck"
                        data-bs-backdrop="static"
                        data-bs-keyboard="false"
                        tabindex="-1"
                        aria-labelledby="staticBackdropLabel"
                        aria-hidden="true"
                >
                    <div class="modal-dialog modal-xl">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5" id="staticBackdropLabel">
                                    Tạo hóa đơn
                                </h1>
                                <button
                                        type="button"
                                        class="btn-close"
                                        data-bs-dismiss="modal"
                                        aria-label="Close"
                                ></button>
                            </div>
                            <div class="modal-body">
                                <div class="mb-3">
                                    <label for="search-client" class="form-label"
                                    >Tìm kiếm khách hàng</label
                                    >
                                    <input
                                            type="search"
                                            class="form-control"
                                            id="search-client"
                                            placeholder="Nhập tên khách hàng..."
                                    />
                                </div>

                                <div  id="scroll-kh">
                                    <table class="table table-hover">
                                        <thead>
                                        <th></th>
                                        <th>STT</th>
                                        <th>Tên</th>
                                        <th>Giới tính</th>
                                        <th>Ngày sinh</th>
                                        <th>SĐT</th>
                                        <th>Email</th>
                                        <th>Địa chỉ</th>
                                        </thead>
                                        <tbody id="list-kh">

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button
                                        type="button"
                                        class="btn btn-secondary"
                                        data-bs-dismiss="modal"
                                >
                                    Thoát
                                </button>
                                <button
                                        type="button"
                                        class="btn btn-primary"
                                        id="chooseButton"
                                >
                                    Tạo mới
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="button">
                    <a href="#" class="btn btn-primary" id="redirect-pay-form" onclick="redirect(event)">Thanh Toán</a>
                </div>
            </div>
        </div>
</section>

<div class="container-fluid">
    <footer
            class="d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top"
    >
        <div class="col-md-4 d-flex align-items-center">
            <a
                    href="/"
                    class="mb-3 me-2 mb-md-0 text-body-secondary text-decoration-none lh-1"
            >
                <svg class="bi" width="30" height="24">
                    <use xlink:href="#bootstrap"></use>
                </svg>
            </a>
            <span class="mb-3 mb-md-0 text-body-secondary"
            >© 2023 Company, Inc</span
            >
        </div>

        <ul class="nav col-md-4 justify-content-end list-unstyled d-flex">
            <li class="ms-3">
                <a class="text-body-secondary" href="#"
                ><i class="bi bi-twitter" width="24" height="24"></i
                ></a>
            </li>
            <li class="ms-3">
                <a class="text-body-secondary" href="#"
                ><i class="bi bi-instagram" width="24" height="24"></i
                ></a>
            </li>
            <li class="ms-3">
                <a class="text-body-secondary" href="#"
                ><i class="bi bi-facebook" width="24" height="24"></i
                ></a>
            </li>
        </ul>
    </footer>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"
></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="../../../resources/js/BanHangTaiQuay.js"></script>
<script>




    function start() {
        getAllProducts(renderProducts);
        getAllHDCho(renderHDCho);
        getAllKH(renderKH);
        search();
    }
    start();

    function getAllProducts(callback) {
        fetch("http://localhost:8080/api/gttct" )
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(callback)
            .catch((error) => {
                console.error(
                    "There was a problem with the fetch operation:",
                    error
                );
            });
    }

    function renderProducts(products) {

        var htmls = products.map((gtt) =>{
            if(gtt.giayTheThao.tenGiayTheThao.length>20){
                return `
                <div class="card card-product" style="width: 9.8rem; ">
                            <img
                                    src="/upload/`+gtt.giayTheThao.anhDau+`"
                                    class="card-img-top img-product-card"
                            />

                            <div class="card-body product-card-body">
                                <p class="card-text">
                                    <a
                                            href="#"
                                            class="name-product"
                                            onclick="addToCart(\``+gtt.id+`\`, \``+gtt.giayTheThao.tenGiayTheThao+`\`, \``+gtt.giayTheThao.giaBan+`\`, event)"
                                    >`+gtt.giayTheThao.tenGiayTheThao+`</a
                                    >
                                    <span class="showOrno" onclick="showName(this, \``+gtt.giayTheThao.tenGiayTheThao+`\`)">Hiện</span>
                                </p>
                                <p class="attribute">
                                    <span>Màu: `+gtt.mauSac.tenMauSac+`</span>
                                    <span>Size: `+gtt.size.size+`</span>
                                </p>
                                <p class="card-text price-card-product">`+gtt.giayTheThao.giaBan+`</p>
                            </div>
                            <div class="notification"><p style="text-align: center;">`+gtt.soLuong+`</p></div>
                        </div>


          `;
            } else {
                return `
                <div class="card card-product" style="width: 9.8rem; ">
                            <img
                                    src="/upload/`+gtt.giayTheThao.anhDau+`"
                                    class="card-img-top img-product-card"
                            />

                            <div class="card-body product-card-body">
                                <p class="card-text">
                                    <a
                                            href="#"
                                            class="name-product"
                                            onclick="addToCart(\``+gtt.id+`\`, \``+gtt.giayTheThao.tenGiayTheThao+`\`, \``+gtt.giayTheThao.giaBan+`\`, event)"
                                    >`+gtt.giayTheThao.tenGiayTheThao+`</a
                                    >

                                </p>
                                <p class="attribute">
                                    <span>Màu: `+gtt.mauSac.tenMauSac+`</span>
                                    <span>Size: `+gtt.size.size+`</span>
                                </p>
                                <p class="card-text price-card-product">`+gtt.giayTheThao.giaBan+`</p>
                            </div>
                            <div class="notification"><p style="text-align: center;">`+gtt.soLuong+`</p></div>
                        </div>


          `;
            }

        });
        var html = htmls.join("");
        document.getElementById("listProducts").innerHTML = html;
        var priceProduct = document.getElementsByClassName("price-card-product");
        var priceArray = Array.from(priceProduct);
        priceArray.forEach((element) => {
            var price = parseInt(element.textContent).toLocaleString("en-US");
            element.textContent = price + " đ";
        });

        var longTextElements = document.querySelectorAll(".name-product");
        var maxLength = 20;

        longTextElements.forEach(function (element) {
            var originalText = element.innerText;

            if (originalText.length > maxLength) {
                var truncatedText = originalText.substring(0, maxLength) + "...";
                element.innerText = truncatedText;
            }
        });


    }

    function showName(params, name) {
        var values = params.innerText;
        var previousElementSibling = params.previousElementSibling;
        if(values=="Hiện"){
            previousElementSibling.innerText=name;
            params.innerText="Ẩn";
        } else {
            var maxLength = 20;
            var originalText =  previousElementSibling.innerText;
            if (originalText.length > maxLength) {
                var truncatedText = originalText.substring(0, maxLength) + "...";
                previousElementSibling.innerText = truncatedText;
            }
            params.innerText="Hiện";
        }

    }



    function search() {

        var btn_search = document.getElementById("search-btn");
        btn_search.addEventListener('click', ()=>{
            var name = document.getElementById("search-input").value;

            fetch('http://localhost:8080/api/gttct/search', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify([name])
            })
                .then(response => response.json())
                .then(data => {

                    if(data.length > 0){

                        renderProducts(data);
                    } else {
                        alert("Không tìm thấy");
                    }

                })
                .catch(error => {
                    console.error('Error:', error);
                });
        });
    }

    function getAllHDCho(callback) {
        fetch("http://localhost:8080/api/hd/unpaid" )
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(callback)
            .catch((error) => {
                console.error(
                    "There was a problem with the fetch operation:",
                    error
                );
            });
    }

    function renderHDCho(checks) {
        var htmls = checks.map((check, key) =>{
            return `
                <tr id="`+check.id+`" onclick="getHDC(\``+check.id+`\`)">
                    <td>`+(key+1)+`</td>
                    <td >`+check.maHoaDon+`</td>
                    <td>`+(check.user==null?"N/A":check.user.tenUser)+`</td>
                    <td>`+(check.khachHang==null?"Khách hàng lẻ":check.khachHang.tenKhachHang)+`</td>
                    <td style="text-align: center">
                      <i
                        class="bi bi-x-lg cancel"
                        onclick="removeCheck(\``+check.id+`\`)"
                      ></i>
                    </td>
                  </tr>

          `;
        });
        var html = htmls.join("");
        document.getElementById("list-hdc").innerHTML = html;

    }

    function removeCheck(id) {

        fetch("http://localhost:8080/api/hd/" + id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }

            })
            .then(()=>{
                getAllHDCho(renderHDCho);
                localStorage.setItem("idHD","");
                document.getElementById("textCodeCheck").value="";

            })
            .catch((error) => {
                console.error(
                    "There was a problem with the fetch operation:",
                    error
                );
            });


    }





    function createCheck() {
        $("#chooseButton").click(function () {
            var idKH = localStorage.getItem("idKH");
            if(idKH.trim().length==0){
                idKH = "";
            }
            var formData = ["", idKH];
            fetch("http://localhost:8080/api/hd", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(()=>{
                    getAllHDCho(renderHDCho);
                    alert("Tạo mới hóa đơn thành công");
                    localStorage.setItem("idKH", "");
                    var radios = document.getElementsByClassName("rdo-check")
                    Array.from(radios).forEach(function(radio) {
                        radio.checked = false;
                    });
                })
                .catch(error => {
                    console.error('Error during POST request:', error);
                });

            $("#staticBackdropCheck").modal("hide");
        });
    }



    createCheck();

    function getAllKH(callback) {
        fetch("http://localhost:8080/api/kh" )
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(callback)
            .catch((error) => {
                console.error(
                    "There was a problem with the fetch operation:",
                    error
                );
            });
    }

    function renderKH(customers) {
        var htmls = customers.map((customer, key) =>{
            return `
                <tr id="`+customer.id+`" onclick="setLocalStorageKH(\``+customer.id+`\`, `+(key+1)+`)" >
                  <td>
                    <input class="form-check-input rdo-check" type="radio" name="kh" id="check-`+(key+1)+`">
                  </td>
                  <td>`+(key+1)+`</td>
                  <td class="name">`+customer.tenKhachHang+`</td>
                  <td>`+customer.gioiTinh+`</td>
                  <td>`+customer.ngaySinh+`</td>
                  <td>`+customer.soDienThoai+`</td>
                  <td>`+customer.email+`</td>
                  <td>`+customer.diaChi+`</td>
                </tr>

          `;
        });
        var html = htmls.join("");
        document.getElementById("list-kh").innerHTML = html;
    }

    function setLocalStorageKH(idKH, key) {
        document.getElementById("check-"+key).checked = true;

        localStorage.setItem("idKH", idKH);
    }



    function addToCart(idGTTCT,name, price, event) {
        event.preventDefault();
        var idHD = localStorage.getItem("idHD");
        console.log(idHD);

        if (idHD == null || idHD.length <= 0) {
            alert("Hãy chọn 1 hóa đơn");
            return;
        }
        //xử lý request
        var gttct = {id: idGTTCT};
        var hd = {id: idHD};
        var formData = {
            hoaDon : hd,
            giayTheThaoChiTiet: gttct,
            donGia: price
        };
        fetch("http://localhost:8080/api/hdct", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                return response.text();
            })
            .then(datas => {
                //xử lý giao diện

                if(datas.trim() != "") {
                    var data = JSON.parse(datas);

                    if (data.trangThai == 2){
                        var id = data.id;
                        var tr = document.getElementById(id);
                        var inputElement = tr.querySelector(".qty");
                        if (inputElement) {
                            inputElement.value = parseInt(inputElement.value)+1;
                            updateTotal(inputElement);
                        } else {
                            console.error('Không tìm thấy ô input');
                        }
                    } else {
                        var table = document.querySelector("#cart-detail-product");
                        var newRow = document.createElement("tr");
                        newRow.id = data.id;

                        newRow.innerHTML = `

                              <td style="text-align: center">
                                <i
                                  class="bi bi-x-lg cancel"
                                  onclick="removeCartDetail(`+`\``+data.id+`\``+`,this)"
                                ></i>
                              </td>
                              <td>1</td>
                              <td>`+name+`</td>
                              <td class="unit-price">`+price+`</td>
                              <td class="col-qty">
                                <div class="quantity-box">
                                  <a
                                    class="btn btn-subtract-qty"
                                    onclick="subtractQty(`+`\``+data.id+`\``+`,this)"
                                    ><i class="bi bi-dash"></i
                                  ></a>
                                  <input
                                    type="text"
                                    class="qty"
                                    oninput="validateNumber(`+`\``+data.id+`\``+`,this)"
                                    onblur="validateQty(`+`\``+data.id+`\``+`,this)"
                                    value="1"
                                  />
                                  <a class="btn btn-plus-qty" onclick="addQty(`+`\``+data.id+`\``+`,this)"
                                    ><i class="bi bi-plus-lg"></i
                                  ></a>
                                </div>
                              </td>
                              <td class="total-price">`+price+`</td>

                `;
                        table.appendChild(newRow);
                    }} else {
                    alert("Sản phẩm trong kho đã hết");
                    console.log(data);
                }



            })
            .catch(error => {
                console.error('Error during POST request:', error);
            });


    }

    function getHDC(id) {
        localStorage.setItem("idHD",id);

        fetch("http://localhost:8080/api/hdct/" + id)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                getAllHDCho((checks)=>{
                    checks.forEach(check => {
                        if(check.id==id){
                            document.getElementById("textCodeCheck").value = check.maHoaDon;
                            return;
                        }
                    });
                })
                render(data);
            })
            .catch((error) => {
                console.error(
                    "There was a problem with the fetch operation:",
                    error
                );
            });
    }
    function render(listHDCT) {

        var htmls = listHDCT.map((hdct, key) => {
            var ten = hdct.giayTheThaoChiTiet.giayTheThao.tenGiayTheThao;

            var giaoDien = `
            <tr id="`+hdct.id+`">
                  <td style="text-align: center">
                    <i
                      class="bi bi-x-lg cancel"
                      onclick="removeCartDetail(`+`\``+hdct.id+`\``+`,this)"
                    ></i>
                  </td>
                  <td>`+(key+1)+`</td>
                  <td>`+ten+`</td>
                  <td class="unit-price">`+hdct.donGia+`</td>
                  <td class="col-qty">
                    <div class="quantity-box">
                      <a
                        class="btn btn-subtract-qty"
                        onclick="subtractQty(`+`\``+hdct.id+`\``+`,this)"
                        ><i class="bi bi-dash"></i
                      ></a>
                      <input
                        type="text"
                        class="qty"
                        oninput="validateNumber(`+`\``+hdct.id+`\``+`,this)"
                        onblur="validateQty(`+`\``+hdct.id+`\``+`,this)"
                        value="`+hdct.soLuong+`"
                      />
                      <a class="btn btn-plus-qty" onclick="addQty(`+`\``+hdct.id+`\``+`,this)"
                        ><i class="bi bi-plus-lg"></i
                      ></a>
                    </div>
                  </td>
                  <td class="total-price">`+(hdct.soLuong*hdct.donGia)+`</td>
                </tr>
          `;
            return giaoDien;
        });
        var html = htmls.join('');
        document.getElementById('cart-detail-product').innerHTML=html;
    }

    function addQty(id, element) {
        var qty = element.previousElementSibling;

        var number = Number(qty.value);
        if (qty.value <= 999) {
            number += 1;
        }


        check(id, number, (check)=>{
            if(check=="false") {
                qty.value = number;
                updateQuantity(id, number);
                updateTotal(qty);
            } else {
                alert("Sản phẩm trong kho đã hết");
            }
        });

    }

    function validateQty(id, element) {
        var qty = element;
        if (qty.value > 999) {
            qty.value = 999;
            updateQuantity(id, 999, (datas)=>{
                var data = JSON.parse(datas);
                if(data.id == null){
                    qty.value = data.soLuong;
                    updateQuantity(id, data.soLuong,()=>{});
                    updateTotal(qty);
                }
            });
        }
        if (!qty.value) {
            qty.value = 1;
            updateQuantity(id, 1, (datas)=>{

            });
            updateTotal(qty);
        }

        updateQuantity(id, qty.value, (datas)=>{
            var data = JSON.parse(datas);
            if(data.id == null){
                qty.value = data.soLuong;
                updateQuantity(id, data.soLuong,()=>{});
                updateTotal(qty);
            }
        });
        updateTotal(qty);
    }

    function subtractQty(id, element) {
        var qty = element.nextElementSibling;

        var number = Number(qty.value);
        if (qty.value > 1) {
            number -= 1;
        }

        updateQuantity(id, number, ()=> {
            qty.value = number;
            updateTotal(qty);
        });

    }

    function validateNumber(id, input) {
        input.value = input.value.replace(/[^\d]+/g, "");
        // input.value = input.value.replace(/[]/g, "");

    }

    function updateTotal(inputElement) {
        var unitPriceElement = inputElement
            .closest("tr")
            .querySelector(".unit-price");
        var totalElement = inputElement.closest("tr").querySelector(".total-price");

        var unitPrice = parseFloat(unitPriceElement.textContent, 10);
        var quantity = parseFloat(inputElement.value, 10);
        var total = unitPrice * quantity;

        totalElement.textContent = total;
    }

    function updateQuantity(id, quantity, callback) {
        fetch("http://localhost:8080/api/hdct/"+id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(quantity),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(callback)
            .catch(error => {
                console.error('Error during POST request:', error);
            });
    }

    function check(id, quantity, callback){
        fetch("http://localhost:8080/api/hdct/check/"+id, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(quantity),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(callback)
            .catch(error => {
                console.error('Error during POST request:', error);
            });
    }

    function redirect(event) {
        event.preventDefault();
        var idHD = localStorage.getItem("idHD");
        if(idHD.length > 0) {
            window.location.href = "${pageContext.request.contextPath}/BanHangTaiQuay/thanhToan/"+idHD;
        } else {
            alert("Hãy chọn 1 hóa đơn");
        }
    }

    function removeCartDetail(id, element) {
        fetch("http://localhost:8080/api/hdct/" + id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }

            })
            .catch((error) => {
                console.error(
                    "There was a problem with the fetch operation:",
                    error
                );
            });
        var parentElement = element.closest("tr").parentNode;
        parentElement.removeChild(element.closest("tr"));

    }

    localStorage.setItem("idHD", "");
    localStorage.setItem("idKH", "");
</script>
</body>
</html>
