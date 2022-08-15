package com.test.nexu.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.nexu.dao.ModelsDaoService;
import com.test.nexu.entity.ModelEntity;
import com.test.nexu.service.ModelsService;

@Service
public class ModelsServiceImpl implements ModelsService {

	@Autowired
	private ModelsDaoService modelsDaoService;

	@Override
	public ArrayList<ModelEntity> getBrands() {
		// TODO Auto-generated method stub
		return modelsDaoService.getBrands();
	}

	@Override
	public ArrayList<ModelEntity> getModels(String brand) {
		// TODO Auto-generated method stub
		return modelsDaoService.getModels(brand);
	}

	@Override
	public String saveBrand(ModelEntity model) {
		// TODO Auto-generated method stub
		return modelsDaoService.saveBrand(model);
	}

	@Override
	public String saveModel(ModelEntity model) {
		// TODO Auto-generated method stub
		return modelsDaoService.saveModel(model);
	}

	@Override
	public String updatePrice(ModelEntity model) {
		// TODO Auto-generated method stub
		return modelsDaoService.updatePrice(model);
	}

	@Override
	public List<ModelEntity> getRangePrice(int g, int l) {
		// TODO Auto-generated method stub
		return modelsDaoService.getRangePrice(g, l);
	}

	

}
