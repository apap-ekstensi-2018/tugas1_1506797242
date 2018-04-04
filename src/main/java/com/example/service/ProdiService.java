package com.example.service;
import java.util.List;

import com.example.model.ProgramStudiModel;

public interface ProdiService {
	List<ProgramStudiModel> selectAllProdi(String idFakultas);
}