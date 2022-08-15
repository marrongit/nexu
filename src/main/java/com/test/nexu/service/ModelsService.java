package com.test.nexu.service;

import java.util.ArrayList;
import java.util.List;

import com.test.nexu.entity.ModelEntity;

public interface ModelsService {
	
	public ArrayList<ModelEntity> getBrands();
	public ArrayList<ModelEntity> getModels(String brand);
	public String saveBrand(ModelEntity model);
	public String saveModel(ModelEntity model);
	public String updatePrice(ModelEntity model);
	public List<ModelEntity> getRangePrice(int g, int l);
}
