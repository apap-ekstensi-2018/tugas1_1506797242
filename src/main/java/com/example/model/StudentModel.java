package com.example.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentModel
{
	private String id;
    private String npm;
    private String nama;
    private String tempat_lahir;
    private String tanggal_lahir;
    private Integer jenis_kelamin;
    private String agama;
    private String golongan_darah;
    private String status = "Aktif";
    private String tahun_masuk;
    private String jalur_masuk;
    private Integer id_prodi;
    private ProgramStudiModel prodi;
    private FakultasModel fakultas;
}
