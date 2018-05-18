package com.gls.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gls.demo.entity.Inventory;

public interface InventoryRepository extends MongoRepository<Inventory, String> {

	public List<Inventory> findByStatus(String status);

}
