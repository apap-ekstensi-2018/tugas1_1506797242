package com.example.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.ProgramStudiMapper;
import com.example.model.ProgramStudiModel;

import lombok.extern.slf4j.Slf4j;;

@Slf4j
@Service
public class ProdiServiceDatabase implements ProdiService{
	@Autowired
    private ProgramStudiMapper prodiMapper;
    
    @Override
    public List<ProgramStudiModel> selectAllProdi(String idFakultas)
    {
        log.info ("select all fakultas");
        return prodiMapper.selectAllProdi(idFakultas);
    }

}