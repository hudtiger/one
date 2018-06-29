package test.Spark;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class GroupConcat extends UserDefinedAggregateFunction {

	  private StructType inputSchema;
	  private StructType bufferSchema;

	  public GroupConcat() {
	    List<StructField> inputFields = new ArrayList<>();
	    inputFields.add(DataTypes.createStructField("inputColumn", DataTypes.StringType, true));
	    inputSchema = DataTypes.createStructType(inputFields);

	    List<StructField> bufferFields = new ArrayList<>();
	    bufferFields.add(DataTypes.createStructField("tmpConcatColumn", DataTypes.StringType, true));
	    bufferSchema = DataTypes.createStructType(bufferFields);
	  }
	  // Data types of input arguments of this aggregate function
	  public StructType inputSchema() {
	    return inputSchema;
	  }
	  // Data types of values in the aggregation buffer
	  public StructType bufferSchema() {
	    return bufferSchema;
	  }
	  // The data type of the returned value
	  public DataType dataType() {
	    return DataTypes.StringType;
	  }
	  // Whether this function always returns the same output on the identical input
	  public boolean deterministic() {
	    return true;
	  }
	  // Initializes the given aggregation buffer. The buffer itself is a `Row` that in addition to
	  // standard methods like retrieving a value at an index (e.g., get(), getBoolean()), provides
	  // the opportunity to update its values. Note that arrays and maps inside the buffer are still
	  // immutable.
	  public void initialize(MutableAggregationBuffer buffer) {
	    buffer.update(0, "");
	  }
	  // Updates the given aggregation buffer `buffer` with new input data from `input`
	  public void update(MutableAggregationBuffer buffer, Row input) {
	    if (!input.isNullAt(0)) {
	    	buffer.update(0, "".equals(buffer.getString(0))?input.getString(0): buffer.getString(0).concat(",".concat(input.getString(0))));
	    }
	  }
	  // Merges two aggregation buffers and stores the updated buffer values back to `buffer1`
	  public void merge(MutableAggregationBuffer buffer1, Row buffer2) {
	    buffer1.update(0,  "".equals(buffer1.getString(0))?buffer2.getString(0):buffer1.getString(0).concat(",").concat(buffer2.getString(0)));
	  }
	  // Calculates the final result
	  public String evaluate(Row buffer) {
	    return buffer.getString(0);
	  }
	}
