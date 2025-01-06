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
@Table(name = "MauSac")
public class MauSac {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id_MauSac")
    private UUID id;

    @Column(name = "tenMauSac")
    private String tenMauSac;

    @Column(name = "ghiChu")
    private String ghiChu;

    @Column(name = "ngayTao")
    private String ngayTao;

    @Column(name = "ngaySua")
    private String ngaySua;

    @Column(name = "trangThai")
    private int trangThai;

}
