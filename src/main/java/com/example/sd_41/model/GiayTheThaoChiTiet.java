package com.example.sd_41.model;

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
@Table(name = "GiayTheThaoChiTiet")
public class GiayTheThaoChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id_GiayTheThaoChiTiet")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "Id_GiayTheThao")
    private GiayTheThao giayTheThao;

    @ManyToOne
    @JoinColumn(name = "Id_Size")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "Id_MauSac")
    private MauSac mauSac;

    @Column(name = "soLuong")
    private String soLuong;

    @Column(name = "ghiChu")
    private String ghiChu;

    @Column(name = "ngayTao")
    private String ngayTao;

    @Column(name = "ngaySua")
    private String ngaySua;

    @Column(name = "trangThai")
    private int trangThai;

}
