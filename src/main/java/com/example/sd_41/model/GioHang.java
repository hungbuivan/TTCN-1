package com.example.sd_41.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "GioHang")
public class GioHang {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id_GioHang")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "Id_User")
    private User user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "Id_KhachHang")
    private KhachHang khachHang;


    @Column(name = "ghiChu")
    private String ghiChu;

    @Column(name = "ngayTao")
    private String ngayTao;

    @Column(name = "ngaySua")
    private String ngaySua;

    @Column(name = "trangThai")
    private int trangThai;

}
