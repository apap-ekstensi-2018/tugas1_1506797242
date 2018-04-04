package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.One;

import com.example.model.FakultasModel;
import com.example.model.StudentModel;
import com.example.model.ProgramStudiModel;
import com.example.model.UniversitasModel;

@Mapper
public interface StudentMapper
{
	@Select ("select * from mahasiswa where npm = #{npm}")
	@Results (value= {
		@Result(property="npm", column="npm"),
		@Result(property="nama",column="nama"),
		@Result(property="tempat_lahir",column="tempat_lahir"),
		@Result(property="tanggal_lahir",column="tanggal_lahir"),
		@Result(property="jenis_kelamin",column="jenis_kelamin"),
		@Result(property="agama",column="agama"),
		@Result(property="golongan_darah",column="golongan_darah"),
		@Result(property="tahun_masuk",column="tahun_masuk"),
		@Result(property="jalur_masuk",column="jalur_masuk"),
		@Result(property="status",column="status"),
		@Result(property="id_prodi",column="id_prodi"),
		@Result(property="prodi",column="id_prodi", one = @One(select="selectProdi"))}) 
	StudentModel selectStudent (@Param("npm") String npm);
	
    @Select("select * from mahasiswa")
    List<StudentModel> selectAllStudents ();

    @Select("select max(npm) from mahasiswa where npm like CONCAT(#{npm},'%') limit 1")
    String selectNPM (@Param("npm") String npm);
    
    @Insert("INSERT INTO mahasiswa (npm, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, status, tahun_masuk, jalur_masuk, id_prodi) "
    		+ "VALUES (#{npm}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{agama}, #{golongan_darah}, #{status}, #{tahun_masuk}, #{jalur_masuk}, #{id_prodi})")
    void addStudent (StudentModel mahasiswa);
    
    @Delete("Delete from mahasiswa where npm = #{npm}")
    void deleteStudent (String npm);

    @Select("select * from program_studi where id = #{id_prodi}")
    @Results( value = {
    		@Result(property = "id", column = "id"),
            @Result(property = "kode_prodi", column = "kode_prodi"),
            @Result(property = "nama_prodi", column = "nama_prodi"),
            @Result(property = "fakultas", column = "id_fakultas", one = @One(select = "selectFak"))
    })
    ProgramStudiModel selectProdi (@Param("id_prodi") Integer id_prodi);
   
   @Select("select * from fakultas where id = #{id_fakultas}")
   @Results(value = {
		   @Result(property = "id", column ="id"),
		   @Result(property = "kode_fakultas", column ="kode_fakultas"),
		   @Result(property = "nama_fakultas", column ="nama_fakultas"),
			@Result(property="universitas",column="id_univ", one = @One(select="selectUniv"))}) 
		FakultasModel selectFak(@Param("id_fakultas") int id_fakultas);
   
   @Select("select * from universitas where id = #{id_universitas}")
   @Results( value = {
   		@Result(property = "id", column = "id"),
           @Result(property = "kode_univ", column = "kode_univ"),
           @Result(property = "nama_univ", column = "nama_univ")
   })
   UniversitasModel selectUniv (@Param("id_universitas") Integer id_universitas);
   
   @Update("UPDATE mahasiswa SET npm = #{npm}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, jenis_kelamin = #{jenis_kelamin}, agama = #{agama}"
   		+ ", golongan_darah = #{golongan_darah}, tahun_masuk = #{tahun_masuk}, jalur_masuk = #{jalur_masuk}, id_prodi = #{id_prodi}"
   		+ " where id = #{id}")
   void updateStudent(StudentModel student);
   
   @Select("Select count (student.npm) as jmlh_mhs from mahasiswa WHERE status = 'Lulus'"
   		+ "and tahun_masuk = #{tahun_masuk} and id_prodi = #{id_prodi}")
   Integer jumlahMhsLulus(@Param("tahun_masuk") String tahun_masuk, @Param("id_prodi") Integer id_prodi);
   
   @Select("SELECT COUNT(mahasiswa.npm) as jlh_mahasiswa FROM mahasiswa WHERE "
   		+ "tahun_masuk = #{tahun_masuk} and id_prodi = #{id_prodi}")
   Integer totalMahasiswa(@Param("tahun_masuk") String tahun_masuk, @Param("id_prodi") Integer id_prodi);
   
   @Select("select * from mahasiswa where id_prodi = #{id_prodi}")
   List<StudentModel> selectAllStudentsByProdi(@Param("id_prodi") String id_prodi);
   
   @Update("UPDATE mahasiswa SET npm = #{npm_baru} WHERE npm = #{npm_lama}")
   void updateStudentNpm (@Param("npm_baru") String npm_baru, @Param("npm_lama") String npm_lama);

}