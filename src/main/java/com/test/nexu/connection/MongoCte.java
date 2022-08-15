package com.test.nexu.connection;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoCte {

	public MongoClient getConnection() {
		return MongoClients.create(new ConnectionString("mongodb://localhost:27017"));
	}

}
