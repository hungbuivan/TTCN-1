package com.example.sd_41.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "Form")
public class Form{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id_Form")
    private UUID id;

    @NotBlank(message = "Không được để trống")
    @Column(name = "tenForm")
    private  String tenForm	;

    @NotBlank(message = "Không được để trống")
    @Column(name = "ghiChu")
    private String ghiChu;

    @NotBlank(message = "Không được để trống")
    @Column(name = "ngayTao")
    private String ngayTao;

    @NotBlank(message = "Không được để trống")
    @Column(name = "ngaySua")
    private String ngaySua;

    @Column(name = "trangThai")
    private int trangThai;

}
