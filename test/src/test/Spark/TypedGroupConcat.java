package test.Spark;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.expressions.Aggregator;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import test.model.CounterDetail;

public class TypedGroupConcat  extends Aggregator<Row, CounterDetail, String> {
	@Override
	public CounterDetail zero() {
		return new CounterDetail("","");
	}
	
	@Override
	public Encoder<CounterDetail> bufferEncoder() {
		return Encoders.bean(CounterDetail.class);
	}
	
	@Override
	public Encoder<String> outputEncoder() {
		return Encoders.STRING();
	}

	@Override
	public CounterDetail merge(CounterDetail arg0, CounterDetail arg1) {
		arg0.setSubject(arg1.getSubject());
		if("".equals(arg0.getCounter()))
			arg0.setCounter(arg1.getCounter());
		else 
			arg0.setCounter(arg0.getCounter().concat(",").concat(arg1.getCounter()));
		return arg0;
	}

	@Override
	public CounterDetail reduce(CounterDetail arg0, Row arg1) {
		arg0.setSubject(arg1.<String>getAs("name"));
		if("".equals(arg0.getCounter()))
			arg0.setCounter(arg1.<Integer>getAs("id").toString());
		else 
			arg0.setCounter(arg0.getCounter().concat(",").concat(arg1.<Integer>getAs("id").toString()));
		return arg0;
	}

	@Override
	public String finish(CounterDetail arg0) {
		return arg0.getCounter();
	}
}


