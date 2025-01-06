package com.example.sd_41.controller.KhachHang.BanHang;

import com.example.sd_41.controller.Momo.MomoModel;
import com.example.sd_41.controller.Momo.ResultMoMo;
import com.example.sd_41.controller.Utils.Constant;
import com.example.sd_41.controller.Utils.Decode;
import com.example.sd_41.model.*;
import com.example.sd_41.repository.BanHang.GioHangChiTietRepository;
import com.example.sd_41.repository.BanHang.GioHangRepository;
import com.example.sd_41.repository.ChuongTrinhGiamGia.ChuongTrinhGiamGiaChiTietGiayTheThaoRepository;
import com.example.sd_41.repository.HoaDon.HoaDonChiTietRepository;
import com.example.sd_41.repository.HoaDon.HoaDonRepository;
import com.example.sd_41.repository.ImageRepository;
import com.example.sd_41.repository.KhachHangRepository;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.*;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoChiTietRepository;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoRepository;
import com.example.sd_41.service.GioHang.GioHangChiTietImpl;
import com.example.sd_41.service.ViTien.Impl.viTienServiceImpl;
import com.example.sd_41.service.ViTien.Impl.giaoDichViChiTietServiceImpl;
import com.example.sd_41.service.GioHang.GioHangChiTietService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class TrangChuGiayTheThaoController {

    @Autowired
    ServletContext context;

    @Autowired
    private GiayTheThaoChiTietRepository giayTheThaoChiTietRepository;

    @Autowired
    private GiayTheThaoRepository giayTheThaoRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private GioHangChiTietService gioHangChiTietService;

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private MauSacRepository mauSacRepository;

    @Autowired
    private ChuongTrinhGiamGiaChiTietGiayTheThaoRepository chuongTrinhGiamGiaChiTietGiayTheThaoRepository;

    @Autowired
    viTienServiceImpl viTienServiceImpl;

    @Autowired
    giaoDichViChiTietServiceImpl giaoDichViChiTietServiceImpl;


    //Todo code view giầy thể thao cho người dùng mua hàng phân trang cho người dùng ở luôn trang index by Giầy thể thao

    private void giaoDienTrangChuListGiayTheThao(Model model, Integer pageNum, Integer pageSize, GiayTheThaoRepository giayTheThaoRepository) {
        List<GiayTheThao> lstGiayTheThao = giayTheThaoRepository.findAll();
        model.addAttribute("lstGiayTheThao", lstGiayTheThao);

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<GiayTheThao> page = giayTheThaoRepository.findAll(pageable);

        // Hiện thông tin sản phẩm
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("listPage", page.getContent());

        // List danh sách các trang để hiện ra giao diện
        List<Integer> pageNumbers = new ArrayList<>();
        int totalPages = page.getTotalPages();
        int currentPage = pageNum;
        int startPage, endPage;

        if (totalPages <= 5) {
            startPage = 1;
            endPage = totalPages;
        } else {
            if (currentPage <= 3) {
                startPage = 1;
                endPage = 5;
            } else if (currentPage + 2 >= totalPages) {
                startPage = totalPages - 4;
                endPage = totalPages;
            } else {
                startPage = currentPage - 2;
                endPage = currentPage + 2;
            }
        }

        for (int i = startPage; i <= endPage; i++) {

            pageNumbers.add(i);

        }

        //Todo code sử lý dữ liệu size và màu sắc để lọc thông tin


        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", currentPage);

        //Todo code list các sản phẩm giầy thể thao có chương trình khuyến mãi

        List<ChuongTrinhGiamGiaChiTietGiayTheThao> listSale = chuongTrinhGiamGiaChiTietGiayTheThaoRepository.findAll();
        model.addAttribute("listSale", listSale);


    }

    private void giaoDienTrangChuListGiayTheThaoDetails(Model model, Integer pageNum, Integer pageSize, GiayTheThaoRepository giayTheThaoRepository) {
        // Số sản phẩm hiển thị trên mỗi trang
        int size = 25;

        // Lấy tất cả sản phẩm
        List<GiayTheThao> lstGiayTheThao = giayTheThaoRepository.findAll();
        model.addAttribute("lstGiayTheThao", lstGiayTheThao);

        // Thiết lập Pageable với số lượng sản phẩm trên mỗi trang
        Pageable pageable = PageRequest.of(pageNum - 1, size);

        // Lấy trang dữ liệu
        Page<GiayTheThao> page = giayTheThaoRepository.findAll(pageable);

        // Hiển thị thông tin sản phẩm
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("listPage", page.getContent());

        // Danh sách các trang để hiển thị ra giao diện
        List<Integer> pageNumbers = new ArrayList<>();
        int totalPages = page.getTotalPages();
        int currentPage = pageNum;
        int startPage, endPage;

        if (totalPages <= 5) {
            startPage = 1;
            endPage = totalPages;
        } else {
            if (currentPage <= 3) {
                startPage = 1;
                endPage = 5;
            } else if (currentPage + 2 >= totalPages) {
                startPage = totalPages - 4;
                endPage = totalPages;
            } else {
                startPage = currentPage - 2;
                endPage = currentPage + 2;
            }
        }

        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }

        // Todo code sử lý dữ liệu size và màu sắc để lọc thông tin

        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", currentPage);

    }

    //Todo code list giầy thể thao trang chủ
    @RequestMapping(value = "TrangChu/listGiayTheThao")
    public String showListViewGiayTheThao(Model model,
                                          HttpSession session,
                                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(name = "pageSize", required = false, defaultValue = "8") Integer pageSize) {

        if (session.getAttribute("khachHangLog") != null) {

            System.out.println("Tài khoản khách hàng đã được đăng nhập");
            model.addAttribute("maKH", session.getAttribute("maKH"));
            giaoDienTrangChuListGiayTheThao(model, pageNum, pageSize, giayTheThaoRepository);

            return "/templates/Users/indexLogin";

        } else {

            System.out.println("Tài khoản khách hàng chưa được đăn nhập");
            giaoDienTrangChuListGiayTheThao(model, pageNum, pageSize, giayTheThaoRepository);
            return "/templates/Users/index";

        }

    }

    //Todo code list giầy thể thao có bộ lọc
    @RequestMapping(value = "TrangChu/listGiayTheThao/Details")
    public String showListViewGiayTheThaoDetailsAndLoc(Model model,
                                                       HttpSession session,
                                                       @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                       @RequestParam(name = "pageSize", required = false, defaultValue = "25") Integer pageSize
    ) {


        if (session.getAttribute("khachHangLog") != null) {

            System.out.println("Tài khoản của khách hàng đã được đang nhập");
            model.addAttribute("maKH", session.getAttribute("maKH"));
            giaoDienTrangChuListGiayTheThaoDetails(model, pageNum, pageSize, giayTheThaoRepository);

            return "/templates/Users/indexLoginDetails";

        } else {

            System.out.println("Khách hàng chưa đăng nhập tài khoản !");
            giaoDienTrangChuListGiayTheThao(model, pageNum, pageSize, giayTheThaoRepository);

            return "/templates/Users/index";

        }

    }


    //Todo code detail thông tin sản phẩm chi tiết và thông tin sản phẩm
    //id ở đây là id của giầy thể thao
    private void detailGiayTheThaoChiTietTrangChu(Model model, UUID id, Integer pageNum, Integer pageSize) {
        GiayTheThao giayTheThao = giayTheThaoRepository.findById(id).orElse(null);
        model.addAttribute("giayTheThao", giayTheThao);

        if (giayTheThao != null) {
            List<GiayTheThaoChiTiet> giayTheThaoChiTiet = giayTheThaoChiTietRepository.findByGiayTheThao(giayTheThao);
            model.addAttribute("giayTheThaoChiTiet", giayTheThaoChiTiet);

            List<Size> uniqueSizes = new ArrayList<>();
            List<MauSac> uniqueMauSac = new ArrayList();

            for (GiayTheThaoChiTiet chiTiet : giayTheThaoChiTiet) {
                Size size = chiTiet.getSize();

                if (!uniqueSizes.contains(size)) {
                    uniqueSizes.add(size);
                }
            }

            for (GiayTheThaoChiTiet chiTiet : giayTheThaoChiTiet) {
                MauSac mauSac = chiTiet.getMauSac();
                if (!uniqueMauSac.contains(mauSac)) {
                    uniqueMauSac.add(mauSac);
                }
            }

            int totalSoLuong = 0;

            for (GiayTheThaoChiTiet chiTiet : giayTheThaoChiTiet) {

                totalSoLuong += Integer.parseInt(chiTiet.getSoLuong());

            }

            model.addAttribute("totalSoLuong", totalSoLuong);
            model.addAttribute("uniqueSizes", uniqueSizes);
            model.addAttribute("uniqueMauSac", uniqueMauSac);

            List<Image> images = imageRepository.findImageByGiayTheThao(giayTheThao);
            model.addAttribute("listImage", images);
        }

        // Phân trang dữ liệu
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<GiayTheThao> page = giayTheThaoRepository.findAll(pageable);
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("listPage", page.getContent());

        List<Integer> pageNumbers = new ArrayList<>();
        int totalPages = page.getTotalPages();
        int currentPage = pageNum;
        int startPage, endPage;

        if (totalPages <= 5) {
            startPage = 1;
            endPage = totalPages;
        } else {
            if (currentPage <= 3) {
                startPage = 1;
                endPage = 5;
            } else if (currentPage + 2 >= totalPages) {
                startPage = totalPages - 4;
                endPage = totalPages;
            } else {
                startPage = currentPage - 2;
                endPage = currentPage + 2;
            }
        }

        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }

        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", currentPage);


        try {
            double maxSale = 0.0; // Sử dụng một biến để lưu giữ giá trị lớn nhất của sale
            List<ChuongTrinhGiamGiaChiTietGiayTheThao> listSale = chuongTrinhGiamGiaChiTietGiayTheThaoRepository.findByGiayTheThao_Id(id);

            for (ChuongTrinhGiamGiaChiTietGiayTheThao sale : listSale) {
                if (sale.getTrangThai() == 1 && sale.getChuongTrinhGiamGiaGiayTheThao().getTrangThai() == 1) {
                    double currentSale = sale.getChuongTrinhGiamGiaGiayTheThao().getPhanTramGiam();
                    if (currentSale > maxSale) {
                        maxSale = currentSale;
                    }
                }
            }

            if (maxSale > 0) {
                model.addAttribute("sale", maxSale);
            } else {
                model.addAttribute("sale", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("sale", null);
            model.addAttribute("giayTheThao", giayTheThao);
        }



    }

    //Todo code list giày thể thao Deals of the Week bên trang chủ

    @RequestMapping(value = "GiayTheThao/listGiayTheThaoDealsOfTheWeek")
    public String listGiayTheThaoDealsOfTheWeek(Model model,
                                                @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "pageSize", required = false, defaultValue = "12") Integer pageSize) {

        List<GiayTheThaoChiTiet> lstGiayTheThaoDealsOfTheWeek = giayTheThaoChiTietRepository.findAll();
        model.addAttribute("lstGiayTheThaoDealsOfTheWeek", lstGiayTheThaoDealsOfTheWeek);

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<GiayTheThaoChiTiet> page = giayTheThaoChiTietRepository.findAll(pageable);

        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("listPage", page.getContent());


        return "/templates/Users/index";

    }


    //Todo code lọc
    @GetMapping("/GiayTheThao/find/{idGiayTheThao}/{idMauSac}/{idSize}")
    public ResponseEntity<?> find(
            @PathVariable UUID idGiayTheThao,
            @PathVariable UUID idMauSac,
            @PathVariable UUID idSize) {

        System.out.println("Mã giầy thể thao :" + idGiayTheThao);
        System.out.println("Mã màu sắc: " + idMauSac);
        System.out.println("Mã size" + idSize);

        GiayTheThaoChiTiet giayTheThaoChiTiet = giayTheThaoChiTietRepository.findIdByIdGiayTheThaoMsSize(idGiayTheThao, idMauSac, idSize);

        return ResponseEntity.ok(giayTheThaoChiTiet.getSoLuong());

    }


    //Todo code detail giầy thể thao chi tiết
    @GetMapping("GiayTheThao/detailThongTinGiayTheThao/{id}")
    public String detailChiTietGiayTheThao(@PathVariable UUID id, Model model,//id là id của giầy thể thao
                                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", required = false, defaultValue = "4") Integer pageSize,
                                           HttpSession session,
                                           HttpServletRequest request) {

        if (session.getAttribute("khachHangLog") != null) {

            System.out.println("Đã đăng nhập tài khoản!");

            detailGiayTheThaoChiTietTrangChu(model, id, pageNum, pageSize);
            return "/templates/Users/Layouts/Shop/detailGiayTheThaoLogin";


        } else {

            System.out.println("Chưa đăng nhập tài khoản!");
            detailGiayTheThaoChiTietTrangChu(model, id, pageNum, pageSize);
            return "/templates/Users/Layouts/Shop/detailGiayTheThao";

        }
    }

    // Todo code giỏ hàng
    //Todo code log not login chưa đăng nhập mà add to cart giỏ hàng

    @GetMapping("GiayTheThao/NguoiDungNotLoginAddToCart")
    public String nguoiDungNotLoginAddToCart() {

        return "/templates/Users/Layouts/Log/AddToCartLogNotLogin";

    }

    //Todo code log succcess login đã đăng nhập khi mà add to cart giỏ hàng
    @GetMapping("GiayTheThao/NguoiDungSuccessLoginAddToCart")
    public String nguoiDungSuccessLoginAddToCart() {

        //Trả về đường dẫn hiện swal
        return "/templates/Users/Layouts/Log/AddToCartLogLogin";

    }


    //Todo code giỏ hàng cho giầy thể thao
    @PostMapping("GiayTheThao/NguoiDung/AddToCart/{id}")
    public String showAddToCartNguoiDung(Model model,
                                         HttpSession session,
                                         RedirectAttributes attributes,
                                         HttpServletRequest request
    ) {

        if (session.getAttribute("khachHangLog") != null) {

            System.out.println("Đã đăng nhập tài khoản để thêm sản phẩm vào giỏ hàng!");

            String mauSac = request.getParameter("mauSac");
            String size = request.getParameter("size");
            String soLuong = request.getParameter("soLuong");
            String idGiayTheThao = request.getParameter("idGiayTheThao");
            String giaBan = request.getParameter("gia");
            attributes.addFlashAttribute("giaBan", giaBan);

            UUID idKhachHang = (UUID) session.getAttribute("idKhachHang");
            System.out.println("Id khách hàng đăng nhập là: " + idKhachHang);

            if (idKhachHang != null) {

                UUID giayTheThaoId = UUID.fromString(idGiayTheThao);

                GiayTheThao giayTheThao = giayTheThaoRepository.findById(giayTheThaoId).orElse(null);

                model.addAttribute("giayTheThao", giayTheThao);

                //Check size và màu sắc khi chưa chọn gì
                if (size == null || mauSac == null) {

                    attributes.addFlashAttribute("erCheckAddToCart", "Vui lòng chọn dữ liệu để có thể mua hàng!");
                    return "redirect:/GiayTheThao/detailThongTinGiayTheThao/" + giayTheThaoId;

                }
                UUID sizeId = UUID.fromString(size);
                UUID mauSacId = UUID.fromString(mauSac);
                //Tìm kiếm id của giầy thể thao chi tiếtgiayTheThao
                UUID idGiayTheThaoChiTiet = giayTheThaoChiTietRepository.findIdByGiayTheThaoAndSizeAndMauSac(giayTheThaoId, sizeId, mauSacId);

                if (idGiayTheThaoChiTiet != null) {

                    model.addAttribute("idGiayTheThaoChiTiet", idGiayTheThaoChiTiet);

                    GioHang gioHang = gioHangRepository.findByKhachHangId(idKhachHang);

                    model.addAttribute("khachHangView", session.getAttribute("khachHangLog"));

                    GiayTheThaoChiTiet giayTheThaoChiTiet = giayTheThaoChiTietRepository.findByGiayTheThaoAndSizeAndMauSac(giayTheThaoId, sizeId, mauSacId);

                    if (gioHang != null) {

                        if (soLuong == null || soLuong.trim().length() == 0 || soLuong.isEmpty()) {

                            attributes.addFlashAttribute("erCheckSoLuongAddToCart", "Không được để trống số lượng !");
                            return "redirect:/GiayTheThao/detailThongTinGiayTheThao/" + giayTheThaoId;

                        }


                        int checkSoLuongAddToCart = Integer.parseInt(soLuong);
                        int soLuongTonKho = Integer.parseInt(giayTheThaoChiTiet.getSoLuong());

                        try {


                            if (checkSoLuongAddToCart <= 0) {

                                attributes.addFlashAttribute("erSoLuongAddToCartMin", "Số lượng sản phẩm mua phải lớn hơn hoặc bằng 0!");
                                return "redirect:/GiayTheThao/detailThongTinGiayTheThao/" + giayTheThaoId;

                            }


                            if (checkSoLuongAddToCart > soLuongTonKho) {

                                attributes.addFlashAttribute("erSoLuongAddToCartMax", "Không mua được sản phẩm vì số lượng trong kho chỉ còn " + soLuongTonKho + " đối với size và màu sắc này");
                                return "redirect:/GiayTheThao/detailThongTinGiayTheThao/" + giayTheThaoId;

                            }

                            if (checkSoLuongAddToCart > 5) {

                                attributes.addFlashAttribute("erSoLuongAddToCartMaxGioiHan", "Xin lỗi quý khách! số lượng mua cho một sản phẩm không lớn hơn 5!");
                                return "redirect:/GiayTheThao/detailThongTinGiayTheThao/" + giayTheThaoId;

                            }

                        } catch (NumberFormatException e) {

                            e.printStackTrace();
                            attributes.addFlashAttribute("erCheckNumberSoLuongAddToCart", "Số lượng phải là số không được là chữ !");
                            return "redirect:/GiayTheThao/detailThongTinGiayTheThao/" + giayTheThaoId;

                        }

                        //Trường hợp check sản phẩm đã có
                        //Đây là số lượng
                        GioHangChiTiet existingItem = gioHangChiTietRepository.findByGioHangAndGiayTheThaoChiTiet(gioHang, giayTheThaoChiTiet);

                        if (existingItem != null) {

                            // Sản phẩm đã có trong giỏ hàng, cập nhật số lượng
                            int existingQuantity = Integer.parseInt(existingItem.getSoLuong());
                            int newQuantity = existingQuantity + Integer.parseInt(soLuong);

                            System.out.println("Sôs lượng trong giỏ hàng "+ soLuong);

                            if(newQuantity > Integer.parseInt(existingItem.getGiayTheThaoChiTiet().getSoLuong())){

                                System.out.println("Set lại cho số lượng ");
                                existingItem.setSoLuong(String.valueOf(existingItem.getGiayTheThaoChiTiet().getSoLuong()));
                                System.out.println("Số lượng còn lại : "+ existingItem.getGiayTheThaoChiTiet().getSoLuong());

                                int soLuongMax = Integer.parseInt(existingItem.getGiayTheThaoChiTiet().getSoLuong());

                                existingItem.setDonGia(BigDecimal.valueOf(Integer.parseInt(giayTheThao.getGiaBan())).multiply(BigDecimal.valueOf(soLuongMax)));

                                gioHangChiTietRepository.save(existingItem);

                                return "redirect:/GiayTheThao/NguoiDung/addToCartSoLuongMin";

                            }

                            if(newQuantity < Integer.parseInt(existingItem.getGiayTheThaoChiTiet().getSoLuong())){

                                System.out.println("Sản phẩm cộng dồn");
                                int soLuongMaxCongDon = 5;
                                existingItem.setSoLuong(String.valueOf(soLuongMaxCongDon));
                                existingItem.setDonGia(BigDecimal.valueOf(Integer.parseInt(giayTheThao.getGiaBan())).multiply(BigDecimal.valueOf(soLuongMaxCongDon)));

                                attributes.addFlashAttribute("soLuongMax", "Xin lỗi quý khách chỉ được thêm số lượng tối đa là 5 cho một sản phẩm !");
                                System.out.println("Làm log thông báo");
                                model.addAttribute("soLuongMaxModel", "Xin lỗi quý khách chỉ được thêm số lượng tối đa là 5 cho một sản phẩm !");
                                gioHangChiTietRepository.save(existingItem);

                                return "redirect:/GiayTheThao/NguoiDung/addToCartSoLuongMax";

                            }


                            if (newQuantity > 5) {

                                System.out.println("Sản phẩm cộng dồn");
                                int soLuongMaxCongDon = 5;
                                existingItem.setSoLuong(String.valueOf(soLuongMaxCongDon));
                                existingItem.setDonGia(BigDecimal.valueOf(Integer.parseInt(giayTheThao.getGiaBan())).multiply(BigDecimal.valueOf(soLuongMaxCongDon)));

                                attributes.addFlashAttribute("soLuongMax", "Xin lỗi quý khách chỉ được thêm số lượng tối đa là 5 cho một sản phẩm !");
                                System.out.println("Làm log thông báo");
                                model.addAttribute("soLuongMaxModel", "Xin lỗi quý khách chỉ được thêm số lượng tối đa là 5 cho một sản phẩm !");
                                gioHangChiTietRepository.save(existingItem);

                            }

                            else {

                                //Dành cho sản phẩm chưa có trong giỏ hàng
                                existingItem.setSoLuong(String.valueOf(newQuantity));
                                existingItem.setDonGia(BigDecimal.valueOf(Integer.parseInt(giayTheThao.getGiaBan())).multiply(BigDecimal.valueOf(newQuantity)));
                                gioHangChiTietRepository.save(existingItem);

                            }

                        }

                        else {

                            // Sản phẩm chưa có trong giỏ hàng, thêm mới
                            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
                            gioHangChiTiet.setGioHang(gioHang);
                            gioHangChiTiet.setGiayTheThaoChiTiet(giayTheThaoChiTiet);
                            gioHangChiTiet.setSoLuong(String.valueOf(checkSoLuongAddToCart));

                            BigDecimal giaBanBigDeimal = new BigDecimal(giaBan);

                            System.out.println("Giá bán mới là :" + giaBanBigDeimal);

                            gioHangChiTiet.setDonGia(giaBanBigDeimal.multiply(BigDecimal.valueOf(checkSoLuongAddToCart)));

                            gioHangChiTietRepository.save(gioHangChiTiet);

                        }

                        return "redirect:/GiayTheThao/NguoiDungSuccessLoginAddToCart";

                    }

                } else {

                    attributes.addFlashAttribute("erCheckSizeAndMuaSacNotFind", "Xin lỗi hiện tại màu sắc này đã hết!");
                    return "redirect:/GiayTheThao/detailThongTinGiayTheThao/" + giayTheThaoId;

                }

            }

        } else {

            System.out.println("Chưa đăng nhập tài khoản để thêm sản phẩm vào giỏ hàng!");
            return "redirect:/GiayTheThao/NguoiDungNotLoginAddToCart";

        }


        return "/templates/Users/Layouts/Log/AddToCartLogLogin";

    }



    @GetMapping("GiayTheThao/NguoiDung/ViewGioHang")
    public String nguoiDungViewShowListGioHang(Model model, RedirectAttributes attributes,
                                               HttpSession session) {

        if (session.getAttribute("khachHangLog") != null) {

            System.out.println("Đã đăng nhập tài khoản!");
            // Lấy dữ liệu từ trong db
            String giaBan = (String) attributes.getAttribute("giaBan");
            model.addAttribute("giaBan", giaBan);
            attributes.addFlashAttribute("giaBan", giaBan);

            UUID idKhachHang = (UUID) session.getAttribute("idKhachHang");
            GioHang gioHang = gioHangRepository.findByKhachHangId(idKhachHang);

            System.out.println("Id của khách hàng : " + idKhachHang);
            UUID idGioHang = gioHangRepository.findGioHangIdByKhachHangId(idKhachHang);
            System.out.println("Id của giỏ hàng có id khách hàng " + idKhachHang + " - " + " " + idGioHang);

            List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietRepository.findByGioHangId(idGioHang);

            model.addAttribute("listGioHangChiTiet", listGioHangChiTiet);

            List<Integer> saleList = new ArrayList<>(); //

            for (GioHangChiTiet gioHangChiTiet : listGioHangChiTiet) {
                UUID idGiayTheThao = gioHangChiTiet.getGiayTheThaoChiTiet().getGiayTheThao().getId();
                List<ChuongTrinhGiamGiaChiTietGiayTheThao> listChuongTrinhGiamGiaChiTietGiayTheThao = chuongTrinhGiamGiaChiTietGiayTheThaoRepository.findByGiayTheThao_Id(idGiayTheThao);

                boolean hasDiscount = false;

                for (ChuongTrinhGiamGiaChiTietGiayTheThao chuongTrinhGiamGiaChiTietGiayTheThao : listChuongTrinhGiamGiaChiTietGiayTheThao) {

                    if (chuongTrinhGiamGiaChiTietGiayTheThao.getChuongTrinhGiamGiaGiayTheThao().getTrangThai() == 1) {

                        saleList.add(chuongTrinhGiamGiaChiTietGiayTheThao.getChuongTrinhGiamGiaGiayTheThao().getPhanTramGiam());
                        hasDiscount = true;
                        break;

                    }

                }

                if (!hasDiscount) {

                    saleList.add(0);

                }
            }

            model.addAttribute("saleList", saleList);

            return "/templates/Users/Layouts/Shop/gioHangView";

        } else {

            System.out.println("Chưa đăng nhập tài khoản!");
            return "redirect:/GiayTheThao/NguoiDungNotLoginAddToCart";

        }
    }



    //Todo code add thông tin hóa đơn
    @PostMapping("/GiayTheThao/nguoiDung/addHoaDon")
    public String nguoiDungAddHoaDon(Model model,
                                     @RequestParam(value = "chon", required = false) List<String> chon,
                                     @RequestParam(value = "idGiayChiTiet", required = false) List<UUID> idGiayChiTiet,
                                     @RequestParam(value = "soLuong", required = false) List<String> soLuong,
                                     @RequestParam(value = "donGia", required = false) List<String> donGia,
                                     HttpSession session,
                                     HttpServletRequest request,
                                     RedirectAttributes attributes) {

        UUID idKhachHang = (UUID) session.getAttribute("idKhachHang");
        KhachHang khachHang = khachHangRepository.findById(idKhachHang).orElse(null);

        if (chon == null || chon.isEmpty()) {

            attributes.addFlashAttribute("erCheckNun", "Xin lỗi hãy chọn ít nhất một sản phẩm để thanh toán !");

        } else {

            HoaDon hoaDon = new HoaDon();

            // Thêm vào Hóa đơn
            LocalTime localTime = LocalTime.now();
            LocalDate ngayThanhToan = LocalDate.now();
            String ngayThanhToanToDate = ngayThanhToan.toString();

            hoaDon.setMaHoaDon("MaHD" + localTime.getHour() + localTime.getMinute() + localTime.getSecond());

            hoaDon.setKhachHang(khachHang);
            hoaDon.setTrangThai(0);
            hoaDon.setNgayThanhToan(LocalDateTime.now());
            hoaDon.setNgayTao(LocalDateTime.now());

            hoaDonRepository.save(hoaDon);

            BigDecimal tongDonGiaHoaDon = BigDecimal.ZERO;

            for (String stt : chon) {

                UUID selectedGiayChiTietId = idGiayChiTiet.get(Integer.parseInt(stt));
                Optional<GiayTheThaoChiTiet> optionalGiayTheThaoChiTiet = giayTheThaoChiTietRepository.findById(selectedGiayChiTietId);

                if (optionalGiayTheThaoChiTiet.isPresent()) {
                    GiayTheThaoChiTiet giayTheThaoChiTietNew = optionalGiayTheThaoChiTiet.get();
                    List<GioHang> gioHangList = gioHangRepository.findByKhachHang_Id(idKhachHang);

                    for (GioHang gioHang : gioHangList) {
                        Optional<GioHangChiTiet> optionalGioHangChiTiet = gioHangChiTietRepository.findByGioHang_Id_AndGiayTheThaoChiTiet_Id(gioHang.getId(), selectedGiayChiTietId);

                        if (optionalGioHangChiTiet.isPresent()) {
                            GioHangChiTiet gioHangChiTiet = optionalGioHangChiTiet.get();

                            if (gioHangChiTiet != null) {

                                GiayTheThao giayTheThao = giayTheThaoChiTietNew.getGiayTheThao();

                                if(Integer.parseInt(giayTheThaoChiTietNew.getSoLuong()) == 0){

                                    System.out.println("Báo lỗi số lượng !");
                                    attributes.addFlashAttribute("soLuongKhong","Xin lỗi số lượng không được là 0! vui lòng chọn sản phẩm khác");
                                    return "redirect:/GiayTheThao/NguoiDung/soLuongMuaKhong";

                                }

                                String giaBanNew = giayTheThao.getGiaBan();

                                List<ChuongTrinhGiamGiaChiTietGiayTheThao> listSale = chuongTrinhGiamGiaChiTietGiayTheThaoRepository.findByGiayTheThao_Id(giayTheThao.getId());

                                double phanTramGiam = 0;

                                for (ChuongTrinhGiamGiaChiTietGiayTheThao sale : listSale) {
                                    try {
                                        if (sale.getChuongTrinhGiamGiaGiayTheThao().getTrangThai() == 1) {
                                            phanTramGiam = sale.getChuongTrinhGiamGiaGiayTheThao().getPhanTramGiam();
                                            break;
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                double giaBan = Double.parseDouble(giayTheThao.getGiaBan());
                                double giaBanMoi = giaBan * (1 - phanTramGiam / 100);
                                BigDecimal giaBanMoiBigDecimal = BigDecimal.valueOf(giaBanMoi);
                                BigDecimal donGiaNew = giaBanMoiBigDecimal.multiply(BigDecimal.valueOf(Integer.parseInt(soLuong.get(Integer.parseInt(stt)))));

                                gioHangChiTiet.setSoLuong(soLuong.get(Integer.parseInt(stt)));
                                gioHangChiTiet.setDonGia(donGiaNew);

                                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                                hoaDonChiTiet.setHoaDon(hoaDon);
                                hoaDonChiTiet.setGiayTheThaoChiTiet(giayTheThaoChiTietNew);
                                hoaDonChiTiet.setSoLuong(soLuong.get(Integer.parseInt(stt)));
                                hoaDonChiTiet.setTrangThai(1);
                                BigDecimal donGiaHoaDonChiTiet = giaBanMoiBigDecimal.multiply(BigDecimal.valueOf(Integer.parseInt(soLuong.get(Integer.parseInt(stt)))));
                                hoaDonChiTiet.setDonGia(donGiaHoaDonChiTiet);

                                hoaDonChiTietRepository.save(hoaDonChiTiet);

                                // Cập nhật tổng thành tiền sau mỗi lần lặp
                                tongDonGiaHoaDon = tongDonGiaHoaDon.add(donGiaHoaDonChiTiet);

                                gioHangChiTietRepository.save(gioHangChiTiet);
                            } else {
                                System.out.println("Không có dữ liệu");
                                return "redirect:/GiayTheThao/NguoiDung/ViewGioHang";
                            }
                        }
                    }
                }
            }

            hoaDon.setThanhTien(tongDonGiaHoaDon);
            hoaDonRepository.save(hoaDon);

            model.addAttribute("hoaDon", hoaDon);

            return "redirect:/nguoiDung/HoaDon/" + hoaDon.getId();
        }

        return "redirect:/GiayTheThao/NguoiDung/ViewGioHang";
    }



    //Todo code delete sản phẩm chi tiết trong giỏ hàng
    @PostMapping("delete/{id}")
    public String deleteProduct(Model model,
                                @PathVariable("id") UUID id) {

        gioHangChiTietService.delete(id);
        model.addAttribute("viewDelete", "Xóa giỏ hàng chi tiết thành công");
        return "redirect:/GiayTheThao/NguoiDung/delete";

    }

    //Todo code thông báo xóa sản phẩm giầy thành công
    @GetMapping("GiayTheThao/NguoiDung/delete")
    public String nguoiDungXoaGiayTheThaoGioHangChiTiet() {

        //Trả về đường dẫn hiện swal
        return "/templates/Users/Layouts/Log/XoaGioHang";

    }

    //Todo code thông báo số lượng sản phẩm mua không được là 0
    @GetMapping("GiayTheThao/NguoiDung/soLuongMuaKhong")
    public String nguoiDungSoLuongMuaKhong() {

        //Trả về đường dẫn hiện swal
        return "/templates/Users/Layouts/Log/SoLuongMuaHangKhong";

    }

    //Todo code log thông báo cho giỏ hàng
    @GetMapping("GiayTheThao/NguoiDung/addToCartSoLuongMax")
    public String addToCartSoLuongMax() {

        //Trả về đường dẫn hiện swal
        return "/templates/Users/Layouts/Log/CongDonSoLuongMax";

    }

    @GetMapping("GiayTheThao/NguoiDung/addToCartSoLuongMin")
    public String addToCartSoLuongMin() {

        //Trả về đường dẫn hiện swal
        return "/templates/Users/Layouts/Log/CongDonSoLuongMin";

    }

    //Todo code view ví điện tử
    @GetMapping("/KhachHang/ViDienTu/ViewViDienTu/*")
    public String showViewViDienTuChiTiet(Model model,
                                          HttpServletRequest request,
                                          HttpSession session) {


        String url = request.getRequestURI();
        String[] parts = url.split("/KhachHang/ViDienTu/ViewViDienTu/");
        String ma = parts[1];

        try {

            KhachHang khachHang = khachHangRepository.findByMaKhachHang(ma);
            model.addAttribute("maKH", khachHang.getMaKhachHang());
            model.addAttribute("tenKhachHang", khachHang.getTenKhachHang());

            ViTien viTien = viTienServiceImpl.findByIdKhachHang(khachHang.getId());

            model.addAttribute("viTien", viTien);
            List<GiaoDichViChiTiet> giaoDichViChiTietList = giaoDichViChiTietServiceImpl.findAllByKhachHang(khachHang.getId());
            model.addAttribute("giaoDichViChiTietList", giaoDichViChiTietList);

            String addThanhCong = (String) session.getAttribute("napThanhCong");
            String addThatBai = (String) session.getAttribute("napThatBai");

            if (addThanhCong != null) {
                model.addAttribute("themThanhCong", "2");
            }
            if (addThatBai != null) {
                model.addAttribute("themThatBai", "2");
            }

            session.removeAttribute("napThanhCong");
            session.removeAttribute("napThatBai");


        } catch (Exception e) {

            model.addAttribute("maKH", "2");

        }


        return "/templates/Users/Layouts/Shop/viDienTu";

    }


    //Todo code khách hàng nạp tiền vào ví điện tử để thanh toán
    @PostMapping("/KhachHang/ViDienTu/NapTien/*")
    public String khachHangViewNapTienVaoViDienTu(

            Model model,
            HttpSession session,
            HttpServletRequest request
    ) throws JsonProcessingException {

        if (session.getAttribute("khachHangLog") != null) {
            System.out.println("Đăng nhập tài khoản thành công !");

            String url = request.getRequestURI();
            String[] p = url.split("/KhachHang/ViDienTu/NapTien/");
            String maKH = p[1];

            KhachHang khachHang = khachHangRepository.findByMaKhachHang(maKH);

            ViTien viTien = viTienServiceImpl.findByIdKhachHang(khachHang.getId());

            String soTien = request.getParameter("soTien");
            BigDecimal soTienBig = new BigDecimal(soTien);

            GiaoDichViChiTiet giaoDichViChiTiet = new GiaoDichViChiTiet();

            LocalTime localTime = LocalTime.now();
            LocalDate ngayThanhToan = LocalDate.now();
            String ngayThanhToanToDate = ngayThanhToan.toString();


            giaoDichViChiTiet.setMaGiaoDichViChiTiet("GiaoDV" + localTime.getHour() + localTime.getMinute() + localTime.getSecond());
            giaoDichViChiTiet.setViTien(viTien);
            giaoDichViChiTiet.setNgayGiaoDich(LocalDateTime.now());
            giaoDichViChiTiet.setDonGia(soTienBig);
            giaoDichViChiTiet.setHinhThuc(1);
            giaoDichViChiTiet.setTrangThai(0);

            giaoDichViChiTietServiceImpl.add(giaoDichViChiTiet);

            ObjectMapper mapper = new ObjectMapper();
            String orderId = giaoDichViChiTiet.getMaGiaoDichViChiTiet();

            MomoModel jsonRequest = new MomoModel();
            jsonRequest.setPartnerCode(Constant.IDMOMO);
            jsonRequest.setOrderId(orderId);
            jsonRequest.setStoreId(orderId);
            jsonRequest.setRedirectUrl(Constant.redirectUrl);
            jsonRequest.setIpnUrl(Constant.ipnUrl);
            jsonRequest.setAmount(soTien);
            jsonRequest.setOrderInfo("Nạp tiền vào ví");
            jsonRequest.setRequestId(orderId);
            jsonRequest.setOrderType(Constant.orderType);
            jsonRequest.setRequestType(Constant.requestType);
            jsonRequest.setTransId("1");
            jsonRequest.setResultCode("200");
            jsonRequest.setMessage("");
            jsonRequest.setPayType(Constant.payType);
            jsonRequest.setResponseTime("300000");
            jsonRequest.setExtraData("");

            String decode = "accessKey=" + Constant.accessKey + "&amount=" + jsonRequest.amount + "&extraData="
                    + jsonRequest.extraData + "&ipnUrl=" + Constant.ipnUrl + "&orderId=" + orderId + "&orderInfo="
                    + jsonRequest.orderInfo + "&partnerCode=" + jsonRequest.getPartnerCode() + "&redirectUrl="
                    + Constant.redirectUrl + "&requestId=" + jsonRequest.getRequestId() + "&requestType="
                    + Constant.requestType;


            String signature = Decode.encode(Constant.serectkey, decode);
            jsonRequest.setSignature(signature);
            String json = mapper.writeValueAsString(jsonRequest);
            HttpClient client = HttpClient.newHttpClient();
            ResultMoMo res = new ResultMoMo();

            try {
                HttpRequest requestMomo = HttpRequest.newBuilder().uri(new URI(Constant.Url))
                        .POST(HttpRequest.BodyPublishers.ofString(json)).headers("Content-Type", "application/json")
                        .build();
                HttpResponse<String> response = client.send(requestMomo, HttpResponse.BodyHandlers.ofString());
                res = mapper.readValue(response.body(), ResultMoMo.class);
            } catch (InterruptedException | URISyntaxException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (res == null) {

                System.out.println("Thanh toán thất bại !");
                session.setAttribute("error_momo", "Thanh toán thất bại");
                return "redirect:/TrangChu/listGiayTheThao";

            } else {

                System.out.println("Thanh toán thành công !");
                return "redirect:" + res.payUrl;

            }

        } else {

            System.out.println("Khách hàng chưa đăng nhập tài khoản");
            return "redirect:/TrangChu/listGiayTheThao";

        }

    }

    //Finall dữ liệu trả về

    @ModelAttribute("thuongHieu")
    public List<ThuongHieu> getListThuongHieu() {

        return thuongHieuRepository.findAll();

    }

    @ModelAttribute("form")
    public List<Form> getListForm() {

        return formRepository.findAll();

    }


    @ModelAttribute("size")
    public List<Size> getListSize() {

        return sizeRepository.findAll();
    }

    @ModelAttribute("mauSac")
    public List<MauSac> getListMauSac() {

        return mauSacRepository.findAll();

    }

}


