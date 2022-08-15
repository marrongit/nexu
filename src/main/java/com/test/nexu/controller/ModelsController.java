package com.test.nexu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.nexu.entity.ModelEntity;
import com.test.nexu.service.ModelsService;

@RestController
@RequestMapping("brands")
public class ModelsController {
	
	@Autowired
	private ModelsService modelService;

	@GetMapping("/")
	public ResponseEntity<ArrayList<ModelEntity>> getBrands(){
		return new ResponseEntity<ArrayList<ModelEntity>>(modelService.getBrands(),HttpStatus.OK);
	}
	
	@GetMapping("/models")
	public ResponseEntity<ArrayList<ModelEntity>> getModels(@RequestParam("brand") String brand){
		return new ResponseEntity<ArrayList<ModelEntity>>(modelService.getModels(brand),HttpStatus.OK);
	}
	
	@PostMapping("/saveBrand")
	public ResponseEntity<String> saveBrand(@RequestBody ModelEntity model){
		return new ResponseEntity<String>(modelService.saveBrand(model),HttpStatus.OK);
	}
	
	@PostMapping("/saveModel")
	public ResponseEntity<String> saveModel(@RequestBody ModelEntity model){
		if((model.getName() == null || model.getName().equals(""))) {
			return new ResponseEntity<String>("Debe contener marca",HttpStatus.BAD_REQUEST);
		} else if(model.getAverage_price() == 0) {
			return new ResponseEntity<String>("Debe contener precio y ser diferente de 0",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(modelService.saveModel(model),HttpStatus.OK);
	}
	
	@PutMapping("/updatePrice")
	public ResponseEntity<String> updatePrice(@RequestBody ModelEntity model){
		if(model.getId() == 0 || model.getAverage_price() == 0) {
			return new ResponseEntity<String>("Debe contener id y el precio diferente de 0",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(modelService.updatePrice(model),HttpStatus.OK);
	}
	
	@GetMapping("/rangePrice")
	public ResponseEntity<List<ModelEntity>> getRangePrice(@RequestParam("g") int g,
			@RequestParam("l") int l){
		if(g == 0 && l == 0) {
			return new ResponseEntity<List<ModelEntity>>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<ModelEntity>>(modelService.getRangePrice(g, l), HttpStatus.OK);
	}
}
