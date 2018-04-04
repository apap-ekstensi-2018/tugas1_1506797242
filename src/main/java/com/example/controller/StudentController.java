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

import com.example.service.StudentService;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class StudentController
{
    @Autowired
    StudentService studentDAO;
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
    public String addSubmit (@ModelAttribute StudentModel student)
    { 	
    	ProgramStudiModel prodi = studentDAO.selectProdi(student.getId_prodi());
    	String tahun_masuk = student.getTahun_masuk().substring(2);
    	log.info("tahun_masuk " +tahun_masuk);
    	String kode_univ = prodi.getFakultas().getUniversitas().getKode_univ();
    	log.info("kode_univ " +kode_univ);
    	String kode_prodi = prodi.getKode_prodi();
    	log.info("kode_prodi " +kode_prodi);
    	String jalur_masuk = jalurMasuk(student.getJalur_masuk());
    	log.info("jalur_masuk " +student.getJalur_masuk());
    	log.info("id jalur_masuk " +jalur_masuk);
    	String npm_sementara = tahun_masuk + kode_univ + kode_prodi + jalur_masuk;
    	log.info("npm_sementara " +npm_sementara);
    	String npm_akhir = "";
    	String nomorUrut="";
    	String max_npm = studentDAO.selectNPM(npm_sementara);
    	if (max_npm==null) {
    		npm_akhir = npm_sementara + "001";
    	}
    	else {
    		nomorUrut = nomorUrut(max_npm);
    		npm_akhir = npm_sementara + nomorUrut;
    	}
    	log.info("npm " +npm_akhir);
    	student.setNpm(npm_akhir);
    	if(studentDAO.selectStudent(npm_akhir) == null) {
    		studentDAO.addStudent(student);
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
        public String gradView(Model model,
                @RequestParam(value = "tahun_masuk", required = false) String tahun_masuk,
                @RequestParam(value = "id_prodi", required = false) Integer id_prodi)
        {
        	model.addAttribute("title", "Kelulusan Mahasiswa");
        	ProgramStudiModel prodi = studentDAO.selectProdi(id_prodi);
        	FakultasModel fakultas = studentDAO.selectFak(prodi.getId_fakultas());
        	UniversitasModel universitas = studentDAO.selectUniv(fakultas.getId_univ());
        	
        	Integer jlhMhs = studentDAO.jumlahMhsLulus(tahun_masuk, id_prodi);
        	Integer totalMhs = studentDAO.totalMahasiswa(tahun_masuk, id_prodi);
        	String presentaseKelulusan = "0";

    		if (totalMhs > 0) {
    			presentaseKelulusan = String.format("%.2f", ((float)jlhMhs/(float)totalMhs) * 100);
    		}
        	model.addAttribute("tahun_masuk", tahun_masuk);
        	model.addAttribute("program_studi", prodi.getNama_prodi());
        	model.addAttribute("fakultas", fakultas.getNama_fakultas());
        	model.addAttribute("universitas", universitas.getNama_univ());
        	model.addAttribute("jlhMahasiswa", jlhMhs);
        	model.addAttribute("totalMahasiwa", totalMhs);
        	model.addAttribute("persen", presentaseKelulusan);
        	return "viewGrad";
        }
 
}
