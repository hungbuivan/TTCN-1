package com.example.sd_41.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "GiaoDichViChiTiet")
public class GiaoDichViChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id_GiaoDichViChiTiet")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "Id_ViTien")
    private ViTien viTien;

    @ManyToOne
    @JoinColumn(name = "Id_User")
    private User user;

    @Column(name = "maGiaoDichViChiTiet")
    private String maGiaoDichViChiTiet;

    @Column(name = "donGia")
    private BigDecimal donGia;

    @Column(name = "ngayGiaoDich")
    private LocalDateTime ngayGiaoDich;

    @Column(name = "hinhThuc")
    private int hinhThuc;

    @Column(name = "trangThai")
    private int trangThai;


}
