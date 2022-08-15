package com.test.nexu.impl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import com.test.nexu.connection.MongoCte;
import com.test.nexu.dao.ModelsDaoService;
import com.test.nexu.entity.ModelEntity;

@Component
public class ModelsDaoImpl implements ModelsDaoService {

	@Override
	public ArrayList<ModelEntity> getBrands() {
		MongoClient client = new MongoCte().getConnection();
		FindIterable<Document> iterable = client.getDatabase("nexus").getCollection("models").find();
		Iterator<Document> it = iterable.cursor();
		ArrayList<ModelEntity> arrlist = new ArrayList<>();
		ModelEntity model = new ModelEntity();
		while (it.hasNext()) {
	          Document doc = it.next();
	          if (!arrlist.stream()
	        		  .filter(e -> e.getBrand_name().equals(doc.getString("brand_name")))
	        		  .findFirst()
	        		  .isPresent()) {
	        	  model.setId(doc.getInteger("id"));
		          model.setAverage_price(doc.getInteger("average_price"));
		          model.setBrand_name(doc.getString("brand_name"));
	        	  arrlist.add(model);
	        	  model = new ModelEntity();
	          }
	      }
		client.close();
		return arrlist;
	}

	@Override
	public ArrayList<ModelEntity> getModels(String brand) {
		MongoClient client = new MongoCte().getConnection();
		MongoCollection<Document> collection = client.getDatabase("nexus").getCollection("models");
		Bson equalComparison = eq("brand_name", brand);
		ArrayList<ModelEntity> list = new ArrayList<ModelEntity>();
		ModelEntity model = new ModelEntity();
		for (Document doc : collection.find(equalComparison)) {
			  model.setId(doc.getInteger("id"));
	          model.setAverage_price(doc.getInteger("average_price"));
	          model.setName(doc.getString("name"));
	          list.add(model);
	          model = new ModelEntity();
		}
		return list;
	}

	@Override
	public String saveBrand(ModelEntity model) {
		String stringResponse = "";
		MongoClient client = new MongoCte().getConnection();
		MongoCollection<Document> collection = client.getDatabase("nexus").getCollection("models");
		Bson equalComparison = eq("brand_name", model.getBrand_name());
		if (collection.find(equalComparison).first() == null) {
			Document doc = new Document("_id", new ObjectId());
			doc.append("brand_name", model.getBrand_name());
			Document lastDoc = collection.find().sort(new BasicDBObject("id",-1)).first();
			int id = lastDoc.getInteger("id") + 1;
			doc.append("id", id);
			collection.insertOne(doc);
			stringResponse = "Se guardo con exito la marca";
		} else {
			stringResponse = "Ya existe la marca";
		}
		return stringResponse;
	}

	@Override
	public String saveModel(ModelEntity model) {
		String stringResponse = "No se guardo el modelo";
		MongoClient client = new MongoCte().getConnection();
		MongoCollection<Document> collection = client.getDatabase("nexus").getCollection("models");
		Bson equalComparison = eq("brand_name", model.getBrand_name());
		for (Document doc : collection.find(equalComparison)) {
			if (doc.getString("name") == null) {
				BasicDBObject updateFields = new BasicDBObject();
				updateFields.append("name", model.getName());
				updateFields.append("average_price", model.getAverage_price());
				BasicDBObject setQuery = new BasicDBObject();
				setQuery.append("$set", updateFields);
				//Bson updateOperation = set("name", model.getName());
				//Bson filter = new BasicDBObject("_id",new ObjectId(doc.getString("_id")));
				Bson filter = eq("id", doc.getInteger("id"));
				UpdateResult result = collection.updateOne(filter, setQuery);
				if (result.getModifiedCount() == 1) stringResponse = "Se guardo el modelo";
			}
		}
		return stringResponse;
	}

	@Override
	public String updatePrice(ModelEntity model) {
		String stringResponse = "No se actualizo el modelo";
		MongoClient client = new MongoCte().getConnection();
		MongoCollection<Document> collection = client.getDatabase("nexus").getCollection("models");
		Bson equalComparison = eq("id", model.getId());
		if (collection.find(equalComparison).first() != null) {
			Bson updateOperation = set("average_price", model.getAverage_price());
			Bson filter = new BasicDBObject("id", model.getId());
			UpdateResult result = collection.updateOne(filter, updateOperation);
			if (result.getModifiedCount() == 1) stringResponse = "Se actualizo el modelo con el precio";
		}
		return stringResponse;
	}

	@Override
	public List<ModelEntity> getRangePrice(int g, int l) {
		MongoClient client = new MongoCte().getConnection();
		MongoCollection<Document> collection = client.getDatabase("nexus").getCollection("models");
		FindIterable<Document> iterable = collection.find(new Document(
			"average_price", new Document("$gte", g).append("$lte", l)
			));
		MongoCursor<Document> cursor = iterable.cursor();
		List<ModelEntity> list = new ArrayList<>();
		ModelEntity model = new ModelEntity();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			model.setAverage_price(doc.getInteger("average_price"));
			model.setId(doc.getInteger("id"));
			model.setBrand_name(doc.getString("brand_name"));
			model.setName(doc.getString("name"));
			list.add(model);
			model = new ModelEntity();
		}
		return list;
	}
}
