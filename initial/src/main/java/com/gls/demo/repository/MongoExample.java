package com.gls.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.gls.demo.entity.Inventory;

@Component
public class MongoExample {
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void simpleQuery() {
		Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("status").is("A"));
		Query query =new Query(criteria);
		mongoTemplate.find(query, Inventory.class).forEach(inventory->{System.out.println(JSON.toJSONString(inventory));});
	}	
	
	public void mapreduce() {
		String mapFunction = "function() {emit(this.status,this.qty);}";
		String reduceFunction = "function(key,values) {return Array.sum(values);}";
		MapReduceResults<Result> result = mongoTemplate.mapReduce("inventory", mapFunction, reduceFunction, Result.class);
		result.forEach(res->System.out.println(JSON.toJSONString(res)));
	}

	public void aggregate() {
        AggregationOperation matchOp = Aggregation.match(Criteria.where("size.uom").is("cm"));
        AggregationOperation groupOp  =Aggregation.group("status")
        		.sum("qty").as("value")
        		.count().as("count")
        		.addToSet("tags").as("parms");
        AggregationOperation sortOp = Aggregation.sort(Direction.DESC,"value");
		Aggregation aggregation = Aggregation.newAggregation(matchOp,groupOp,sortOp);
		AggregationResults<ResultExt> result =mongoTemplate.aggregate(aggregation, Inventory.class, ResultExt.class);
		result.forEach(res->System.out.println(JSON.toJSONString(res)));
	}
}
class ResultExt extends Result{
	Object[][] parms;

	public Object[][] getParms() {
		return parms;
	}
	
	public void setParms(Object[][] parms) {
		this.parms = parms;
	}

	public ResultExt(String id, Integer value, Integer count, Object[][] parms) {
		super(id, value, count);
		this.parms = parms;
	}
	
}

class Result{
	@Id
	String id;
	Integer value;
	Integer count;
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer count) {
		this.value = count;
	}
	
	public Result(String id, Integer value, Integer count) {
		super();
		this.id = id;
		this.value = value;
		this.count = count;
	}

	
	
//	{"id":"A","value":120}
//	{"id":"D","value":175}
	
}