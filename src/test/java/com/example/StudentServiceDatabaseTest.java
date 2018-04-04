package com.example;

import com.example.dao.StudentMapper;
import com.example.model.StudentModel;
import com.example.service.StudentService;
import com.example.service.StudentServiceDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class StudentServiceDatabaseTest {
	
//
//	private StudentService studentService = new StudentServiceDatabase();
//	
//	@Mock
//	private StudentMapper studentMapper;
//	
//	@Before
//	public void setUp() {
//		MockitoAnnotations.initMocks(this);
//		this.studentService = new StudentServiceDatabase(this.studentMapper);
//	}
//	
//	@Test
//	public void selectStudent() {
//		//Given
//		StudentModel studentModel = new StudentModel ("1506737823","Chanek",3.5);
//		StudentModel check = new StudentModel("1506737823","Chanek",3.5);
//		BDDMockito.given(studentMapper.selectStudent("1506737823")).willReturn(studentModel);
//		
//		//When
//		StudentModel test = studentService.selectStudent("1506737823");
//		
//		//Then
//		assertThat(test,notNullValue());//check if not null
//		assertThat(test.equals(check)); //check if same
//		
//	}
}
