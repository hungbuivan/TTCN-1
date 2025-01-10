<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Thống kê báo cáo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
    />

</head>
<body>
<%@ include file="../templates/Admin/Layouts/GiayTheThao/_HeaderGiayTheThao.jsp" %>

<div class="container" style="margin-bottom: 50px">
    <div class="title">
        <div class="container">
            <div class="row">
				<!--chọn khoảng thời gian-->
				<form action="/thongKeTheoKhoangThoiGian" method="get" class="mb-4">
				      <div class="form-row">
				           <div class="col-md-4">
				                <label for="startDate">Ngày bắt đầu:</label>
				                <input type="date" id="startDate" name="startDate" class="form-control" required>
				            </div>
				            <div class="col-md-4">
				                <label for="endDate">Ngày kết thúc:</label>
				                <input type="date" id="endDate" name="endDate" class="form-control" required>
				            </div>
				            <div class="col-md-4 d-flex align-items-end">
				                <button type="submit" class="btn btn-primary">Thống kê</button>
				            </div>
				      </div>
				</form>
                <!-- Earnings (Monthly) Card Example -->
                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="card border-left-primary shadow h-100 py-2">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                        Tổng doanh thu
                                    </div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800" style="margin-top: 20px">
                                        <fmt:formatNumber type="" value="${tongDoanhThu}" pattern="#,##0.###"/>
                                        <span style="color: red;font-size: 15px;">VNĐ</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="card border-left-primary shadow h-100 py-2">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                        Tổng đơn hàng đã bán
                                    </div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800"
                                         style="margin-top: 20px">${tongDonHang}</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fa fa-shopping-cart fa-2x text-gray-300"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="card border-left-primary shadow h-100 py-2">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                        Đã bán tại quầy / online
                                    </div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800"
                                         style="margin-top: 20px">
                                        ${tongHoaDonTaiQuay} / ${tongHoaDonOnline}

                                    </div>
                                </div>
                                <div class="col-auto">
                                    <i class="fa fa-shopping-cart fa-2x text-gray-300"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>



				

            </div>
            <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample"
                    aria-expanded="false" aria-controls="collapseExample">
                Biểu đồ số lượng sản phẩm
            </button>
            
            <div class="collapse" id="collapseExample">
                <div class="card card-body">
                    <div>
                        <button id="barChartButton" class="btn btn-success">Biểu đồ Bar</button>
                       
                    </div>
                    <canvas id="productChart" width="400" height="200"></canvas>
                </div>
            </div>
            
        </div>
    </div>
</div>

<%--<%@ include file="../templates/Admin/Layouts/GiayTheThao/_FooterGiayTheThao.jsp" %>--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
<script src="/vendor/jquery/jquery.min.js"></script>
<script src="/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/vendor/jquery-easing/jquery.easing.min.js"></script>
<script src="/js/sb-admin-2.min.js"></script>
<script src="/vendor/chart.js/Chart.min.js"></script>
<script src="/js/demo/chart-area-demo.js"></script>
<script src="/js/demo/chart-pie-demo.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js" integrity="sha384-fbbOQedDUMZZ5KreZpsbe1LCZPVmfTnH7ois6mU1QK+m14rQ1l2bGBq41eYeM/fS" crossorigin="anonymous"></script>

<%--Js cho biểu đồ--%>

<%--Done--%>
<script>
    $(document).ready(function () {
        var currentChart;

        function changeChartType(chartType) {
            if (currentChart) {
                currentChart.destroy();
            }

            var ctx = document.getElementById('productChart').getContext('2d');
            var chartData = {
                labels: productNames,
                datasets: [{
                    label: 'Số lượng sản phẩm',
                    data: productQuantities,
                    backgroundColor: 'rgba(255,0,116,0.2)',
                    borderColor: 'rgb(238,36,107)',
                    borderWidth: 1
                }]
            };

            if (chartType === 'bar') {
                currentChart = new Chart(ctx, {
                    type: 'bar',
                    data: chartData,
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true,
                                max: 50
                            }
                        },
                        plugins: {
                            legend: {
                                display: false
                            },
                            datalabels: {
                                anchor: 'end',
                                align: 'top',
                            }
                        },

                        tooltips: {
                            enabled: true,
                            mode: 'index',
                            intersect: false,
                            callbacks: {
                                title: function (tooltipItems, data) {
                                    var index = tooltipItems[0].dataIndex;
                                    return data.labels[index];
                                },
                                label: function (tooltipItem, data) {
                                    var datasetLabel = data.datasets[tooltipItem.datasetIndex].label || '';
                                    return datasetLabel + ': ' + tooltipItem.yLabel;
                                },
                                afterLabel: function (tooltipItem, data) {
                                    var index = tooltipItem.index;
                                    return 'Size: ' + size[index] + '\nMàu sắc: ' + mauSac[index];
                                }
                            }
                        },
                        barPercentage: 0.5,
                        categoryPercentage: 0.7,
                    }
                });
            } else if (chartType === 'line') {
                currentChart = new Chart(ctx, {
                    type: 'line',
                    data: chartData,
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true,
                                max: 50
                            }
                        },
                        plugins: {
                            legend: {
                                display: false
                            },
                            datalabels: {
                                anchor: 'end',
                                align: 'top',
                            }
                        },
                        elements: {
                            line: {
                                tension: 0.4, // Điều chỉnh độ đoạn của đường
                                borderWidth: 2, // Độ dày của đường
                                fill: true,
                            }
                        }
                    }
                });
            } else if (chartType === 'pie') {
                currentChart = new Chart(ctx, {
                    type: 'pie',
                    data: {
                        labels: productNames,
                        datasets: [{
                            label: "Số lượng sản phẩm",
                            data: productQuantities,
                            backgroundColor: [
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)',
                                'rgba(255, 0, 0, 0.2)',
                                'rgba(0, 255, 0, 0.2)',
                                'rgba(0, 0, 255, 0.2)',
                                'rgba(128, 128, 0, 0.2)',
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)',
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                            ],
                            borderColor: [
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)',
                                'rgba(255, 0, 0, 0.2)',
                                'rgba(0, 255, 0, 0.2)',
                                'rgba(0, 0, 255, 0.2)',
                                'rgba(128, 128, 0, 0.2)',
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)',
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                            ],
                            borderWidth: 1
                        }]
                    },
                    options: {
                        plugins: {
                            legend: {
                                display: true,
                                position: 'right'
                            }
                        },

                        aspectRatio: 3
                    }
                });
            }
        }

        var productNames = [];
        var productQuantities = [];
        var size = [];
        var mauSac = [];


        $.getJSON('/thongke-data', function (data) {

            data.forEach(function (product) {

                //product đại diện cho giầy thể thao chi tiết

                productNames.push(product.giayTheThao.tenGiayTheThao);
                productQuantities.push(product.soLuong);
                size.push(product.size.size);
                mauSac.push(product.mauSac.tenMauSac);

            });

            changeChartType('bar'); // Hiển thị biểu đồ bar mặc định khi trang được tải

            $('#barChartButton').click(function () {
                changeChartType('bar');
            });

            $('#lineChartButton').click(function () {
                changeChartType('line');
            });

            $('#pieChartButton').click(function () {
                changeChartType('pie');
            });
        });
    });

    function createSolidWaveBackgroundGradient(ctx) {
        const gradient = ctx.createLinearGradient(0, 0, 0, 300);
        gradient.addColorStop(0, 'rgba(0, 0, 255, 0.8)'); // Màu sắc ở đỉnh sóng
        gradient.addColorStop(0.5, 'rgba(255, 255, 255, 0)'); // Màu sắc ở giữa sóng (trong suốt)
        gradient.addColorStop(1, 'rgba(0, 0, 255, 0.8)'); // Màu sắc ở đáy sóng
        return gradient;
    }

    // Thiết lập màu nền cho biểu đồ
    currentChart.options.plugins.background = {
        color: createSolidWaveBackgroundGradient(ctx), // Sử dụng gradient màu nền sóng đặc
    };

</script>

<%--Thống kê hóa đơn--%>


<script>
    $(document).ready(function() {
        var currentChart;

        function changeChartType(chartType) {
            if (currentChart) {
                currentChart.destroy();
            }

            var ctx = document.getElementById('productChart1').getContext('2d');
            var chartData = {
                labels: productNames.map(formatDate),
                datasets: [{
                    label: 'Tổng tiền',
                    data: productTongTien,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            };

            if (chartType === 'bar') {
                currentChart = new Chart(ctx, {
                    type: 'bar',
                    data: chartData,
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true,
                            }
                        },
                        plugins: {
                            legend: {
                                display: false
                            },
                            datalabels: {
                                anchor: 'end',
                                align: 'top',
                            }
                        },
                        barPercentage: 0.5,
                        categoryPercentage: 0.7,
                    }
                });
            } else if (chartType === 'line') {
                currentChart = new Chart(ctx, {
                    type: 'line',
                    data: chartData,
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true,
                            }
                        },
                        plugins: {
                            legend: {
                                display: false
                            },
                            datalabels: {
                                anchor: 'end',
                                align: 'top',
                            }
                        },
                        elements: {
                            line: {
                                tension: 0.4,
                                borderWidth: 2,
                                fill: true,
                            }
                        }
                    }
                });
            } else if (chartType === 'pie') {
                currentChart = new Chart(ctx, {
                    type: 'pie',
                    data: {
                        labels: productNames.map(formatDate),
                        datasets: [{
                            data: productTongTien,
                            backgroundColor: [
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)',
                                'rgba(255, 0, 0, 0.2)',
                                'rgba(0, 255, 0, 0.2)',
                                'rgba(0, 0, 255, 0.2)',
                                'rgba(128, 128, 0, 0.2)',
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)',
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                            ],
                            borderColor: [
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)',
                                'rgba(255, 0, 0, 0.2)',
                                'rgba(0, 255, 0, 0.2)',
                                'rgba(0, 0, 255, 0.2)',
                                'rgba(128, 128, 0, 0.2)',
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)',
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                            ],
                            borderWidth: 1
                        }]
                    },
                    options: {
                        plugins: {
                            legend: {
                                display: true,
                                position: 'right'
                            }
                        },
                        aspectRatio: 3
                    }
                });
            }
        }

        function formatDate(dateString) {
            var date = moment(dateString, 'YYYY-MM-DDTHH:mm:ss');
            if (date.isValid()) {
                return date.format('DD/MM/YYYY');
            } else {
                return 'Invalid date';
            }
        }

        var productNames = [];
        var productTongTien = [];

        $.getJSON('/thongke-data-hoadon', function(data) {
            data.forEach(function(hoaDon) {
                if (hoaDon.trangThai === 4) {
                    productNames.push(hoaDon.ngayThanhToan);
                    productTongTien.push(hoaDon.thanhTien);
                }
            });

            changeChartType('bar');

            $('#barChartButton1').click(function() {
                changeChartType('bar');
            });

            $('#lineChartButton1').click(function() {
                changeChartType('line');
            });

            $('#pieChartButton1').click(function() {
                changeChartType('pie');
            });
        });
    });
</script>


</body>
</html>

