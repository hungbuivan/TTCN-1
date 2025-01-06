package com.example.sd_41.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "HoaDonChiTiet")
public class HoaDonChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id_HoaDonChiTiet")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "Id_HoaDon")
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "Id_GiayTheThaoChiTiet")
    private GiayTheThaoChiTiet giayTheThaoChiTiet;

    @Column(name = "soLuong")
    private String soLuong;

    @Column(name = "donGia")
    private BigDecimal donGia;

    @Column(name = "ghiChu")
    private String ghiChu;

    @Column(name = "ngayTao")
    private LocalDateTime ngayTao;

    @Column(name = "ngaySua")
    private LocalDateTime ngaySua;

    @Column(name = "trangThai")
    private int trangThai;

}
