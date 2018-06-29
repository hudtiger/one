package test.Spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface ISetMap {
	Dataset<Row> map(Dataset<Row> source);
}
