package com.example.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.StudentModel;
import com.example.model.ProgramStudiModel;
import com.example.model.FakultasModel;
import com.example.model.UniversitasModel;
import com.example.service.FakultasService;
import com.example.service.ProdiService;
import com.example.service.StudentService;
import com.example.service.UniversitasService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class StudentController
{
    StudentService studentDAO;
    @Autowired
    UniversitasService universitasDAO;
    @Autowired
    FakultasService fakultasDAO;
    @Autowired
    ProdiService prodiDAO;
    
    @RequestMapping("/")
    public String index ()
    {
        return "home";
    }
//    }
    @RequestMapping("/student/add")
    public String addMhs(Model model)
    {
    	StudentModel student = new StudentModel();
    	model.addAttribute ("student", student);
        return "tambahStudent";
    }
    
    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
        StudentModel student = studentDAO.selectStudent(npm);
        if (student != null) {
            model.addAttribute ("student", student);
            return "viewStudent";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }
    
    @RequestMapping("/student/add/submit")
    public String addSubmit (@ModelAttribute StudentModel mahasiswa)
    { 	
    	ProgramStudiModel prodi = studentDAO.selectProdi(mahasiswa.getId_prodi());
    	String tahun_masuk = mahasiswa.getTahun_masuk().substring(2);
    	log.info("tahun_masuk " +tahun_masuk);
    	String kode_univ = prodi.getFakultas().getUniversitas().getKode_univ();
    	log.info("kode_univ " +kode_univ);
    	String kode_prodi = prodi.getKode_prodi();
    	log.info("kode_prodi " +kode_prodi);
    	String jalur_masuk = jalurMasuk(mahasiswa.getJalur_masuk());
    	log.info("jalur_masuk " +mahasiswa.getJalur_masuk());
    	log.info("id jalur_masuk " +jalur_masuk);

    	String npm_sementara = tahun_masuk + kode_univ + kode_prodi + jalur_masuk;
    	log.info("npm_sementara " +npm_sementara);
    	
    	String max_npm = studentDAO.selectNPM(npm_sementara);
    	log.info("max_npm " +max_npm);
    	String nomor_urut = "001";
    	if(max_npm != null) {
    		nomor_urut = nomorUrut(max_npm);
        	log.info("nomor_urut " +nomor_urut);	
    	}
    	
    	String npm = npm_sementara + nomor_urut;
    	log.info("npm " +npm);
    	
    	mahasiswa.setNpm(npm);
    	
    	if(studentDAO.selectStudent(npm) == null) {
    		studentDAO.addStudent (mahasiswa);
    		return "addSuccess";
    	}
    	else {
    		return"not-successAdd";
    	}
    }
    
    public String nomorUrut(String npm) {
    	String hasil = "0";
    	if(npm ==null){
    		hasil = npm +"01";
    	}
    	else {
    		String nomorUrut =npm.substring(9);
    		String nomorUrut1 = String.valueOf(Integer.parseInt(nomorUrut) + 1) ;
    			if(nomorUrut1.length()==1) {
    				hasil = "00"+nomorUrut1;
    			}else if(nomorUrut1.length()==2) {
    				hasil = "0"+nomorUrut1;
    			}else {
    				hasil = nomorUrut1;
    			}
    	}
    		return hasil;
    	}
    
    public String jalurMasuk(String jalur_masuk) {
    	String kode = "0";
    	if(jalur_masuk.equalsIgnoreCase("Undangan Olimpiade")) {
    		jalur_masuk = "53";
    	}else if(jalur_masuk.equalsIgnoreCase("Undangan Reguler/SNMPTN")) {
    		kode = "54";
    	}else if(jalur_masuk.equalsIgnoreCase("Undangan Paralel/PPKB")){
    		kode = "55";
    	}else if(jalur_masuk.equalsIgnoreCase("Ujian Tulis Bersama/SBMPTN")) {
    		kode = "57";
    	}else if(jalur_masuk.equalsIgnoreCase("Ujian Tulis Mandiri")) {
    		kode = "62";
    	}
    	return kode;
    }


    @RequestMapping("/student/update/{npm}")
    public String UpdateSubmit (Model model, @PathVariable(value="npm")String npm){
    	StudentModel student = studentDAO.selectStudent(npm);
    	if (student != null) {
    		model.addAttribute("student", student);
    		return "updateStudent";
    		}else {
    			model.addAttribute("npm", npm);
    			return "not-successUpdate";
    		}
    }
    
    @RequestMapping(value="/student/update/submit", method=RequestMethod.POST)
    public String updateSubmit( @ModelAttribute StudentModel student) {
    	ProgramStudiModel prodi = studentDAO.selectProdi(student.getId_prodi());
		String npm= student.getNpm().substring(9,12);
		String tahun_masuk = student.getTahun_masuk().substring(2,4);
    	log.info("tahun_masuk " +tahun_masuk);
    	String kode_univ = prodi.getFakultas().getUniversitas().getKode_univ();
    	log.info("kode_univ " +kode_univ);
    	String kode_prodi = prodi.getKode_prodi();
    	log.info("kode_prodi " +kode_prodi);
    	String jalur_masuk = jalurMasuk(student.getJalur_masuk());
    	log.info("jalur_masuk " +student.getJalur_masuk());
    	log.info("id jalur_masuk " +jalur_masuk);
    	String npm_sementara = tahun_masuk + kode_univ + kode_prodi + jalur_masuk;
    	log.info("npm_sementara" +npm_sementara);
    	String npm_baru = npm_sementara + npm;
    	log.info("npm " +npm_baru);
    	student.setNpm(npm_baru);
    	if(studentDAO.selectStudent(npm_baru) == null) {
    		studentDAO.updateStudent(student);
    		return "addSuccess";
    	}
    	else {
    		return"not-successUpdate";
    	}
    }
    
    
    
    @RequestMapping("/kelulusan")
    public String kelulusan () 
    {
    	return "searchGrad";
    }
//    
//    @RequestMapping("/grad/view")
//    public String viewGraduation (Model model,
//            @RequestParam(value = "tahun_masuk", required = false) String tahun_masuk,
//            @RequestParam(value = "id_prodi", required = false) Integer id_prodi)
//    {
//        StudentModel student = studentDAO.selectStudent(tahun_masuk);
//        StudentModel student = studentDAO.selectStudent(id_prodi);
//        if (student != null) {
//            model.addAttribute ("student", student);
//            return "viewStudent";
//        } else {
//            model.addAttribute ("npm", npm);
//            return "not-found";
//        }
//    }
        @RequestMapping("/graduation/view")
    	public String viewLulus(Model model, @RequestParam(value="thn_masuk", required=true)String thn_masuk,
				 @RequestParam(value="id_prodi", required=true)int id_prodi) {
          Integer jumlah_mahasiswa = studentDAO.totalMahasiswa(thn_masuk, id_prodi);
          if(jumlah_mahasiswa != 0) {
        	  Integer total_lulus = studentDAO.jumlahMhsLulus(thn_masuk, id_prodi);
        	  Integer persentase = (total_lulus * 100) / jumlah_mahasiswa;
        	  log.info("test");
        	  ProgramStudiModel prodi = studentDAO.selectProdi(id_prodi);

        	  model.addAttribute ("prodi", prodi);
        	  model.addAttribute ("persentase", persentase);
        	  model.addAttribute ("total_lulus", total_lulus);
          	  model.addAttribute ("jumlah_mahasiswa", jumlah_mahasiswa);
          	  model.addAttribute ("thn_masuk", thn_masuk);
              return "viewGrad";
      	}else {
      		return "not-found";
      	}	
  	}
        @RequestMapping("/mahasiswa/cariUniv")
    	public String cariMahasiswa(Model model)
        {
        	log.info("start cari mahasiswa");
        	List<UniversitasModel> allUniversitas = universitasDAO.selectAllUniversitas();	
        	model.addAttribute("allUniversitas", allUniversitas);
    		return "search-universitas";
    	}
        
        @RequestMapping("/mahasiswa/cariFakultas")
      	public String cariBasedUniversitas(Model model, @RequestParam(value = "universitas", required = false) String idUniversitas)
        {
          	List<UniversitasModel> allUniversitas = universitasDAO.selectAllUniversitas();
          	for(UniversitasModel universitasSelected: allUniversitas) {
          		if (universitasSelected.getId().equals(idUniversitas)){
          			model.addAttribute("universitasSelected", universitasSelected);
          		}
          	}
          	model.addAttribute("idUniversitas", idUniversitas);
          	
          	List<FakultasModel> allFakultas = fakultasDAO.selectAllFakultas(idUniversitas);
          	model.addAttribute("allFakultas", allFakultas);
    		return "search-fakultas";
      	}
        
        @RequestMapping("/mahasiswa/cariProdi")
      	public String cariBasedFakultas(Model model, 
      				@RequestParam(value = "universitas", required = false) String idUniversitas,
      				@RequestParam(value = "fakultas", required = false) String idFakultas)
          {
          	List<UniversitasModel> allUniversitas = universitasDAO.selectAllUniversitas();
          	for(UniversitasModel universitasSelected: allUniversitas) {
          		if (universitasSelected.getId().equals(idUniversitas)){
          			model.addAttribute("universitasSelected", universitasSelected);
          		}
          	}
          	model.addAttribute("idUniversitas", idUniversitas);
          	
          	List<FakultasModel> allFakultas = fakultasDAO.selectAllFakultas(idUniversitas);
          	for(FakultasModel fakultasSelected: allFakultas) {
          		if (fakultasSelected.getId().equals(idFakultas)){
          			model.addAttribute("fakultasSelected", fakultasSelected);
          		}
          	}
          	model.addAttribute("idFakultas", idFakultas);
          	
          	List<ProgramStudiModel> allProdi = prodiDAO.selectAllProdi(idFakultas);
          	model.addAttribute("allProdi", allProdi);
    		return "search-prodi";
      	}
        
        @RequestMapping("/mahasiswa/cari")
      	public String cariMahasiswa(Model model, 
      				@RequestParam(value = "universitas", required = false) String idUniversitas,
      				@RequestParam(value = "fakultas", required = false) String idFakultas,
      				@RequestParam(value = "prodi", required = false) String idProdi)
          {
        	List<UniversitasModel> allUniversitas = universitasDAO.selectAllUniversitas();
          	for(UniversitasModel universitasSelected: allUniversitas) {
          		if (universitasSelected.getId().equals(idUniversitas)){
          			model.addAttribute("universitasSelected", universitasSelected);
          		}
          	}
          	model.addAttribute("idUniversitas", idUniversitas);
          	
          	List<FakultasModel> allFakultas = fakultasDAO.selectAllFakultas(idUniversitas);
          	for(FakultasModel fakultasSelected: allFakultas) {
          		if (fakultasSelected.getId().equals(idFakultas)){
          			model.addAttribute("fakultasSelected", fakultasSelected);
          		}
          	}
          	model.addAttribute("idFakultas", idFakultas);
          	
          	List<ProgramStudiModel> allProdi = prodiDAO.selectAllProdi(idFakultas);
          	for(ProgramStudiModel prodiSelected: allProdi) {
          		if (prodiSelected.getId().equals(idProdi)){
          			model.addAttribute("prodiSelected", prodiSelected);
          		}
          	}
          	model.addAttribute("idProdi", idProdi);
          	
          	List<StudentModel> mahasiswa = studentDAO.selectAllStudentsByProdi(idProdi);
          	String size = String.valueOf(mahasiswa.size());
          	log.info("size "+size);
          	model.addAttribute("mahasiswa", mahasiswa);
    		return "search-mahasiswa";
      	}
 
}
