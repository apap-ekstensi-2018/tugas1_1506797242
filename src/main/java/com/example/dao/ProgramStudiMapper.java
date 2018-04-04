package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

import com.example.model.StudentModel;
import com.example.model.ProgramStudiModel;
import com.example.model.UniversitasModel;

@Mapper
public interface ProgramStudiMapper {
	@Select("select nama_prodi from program_studi where id = #{id_prodi}")
    ProgramStudiModel selectProdiNama (@Param("id_prodi") Integer id_prodi);
}
