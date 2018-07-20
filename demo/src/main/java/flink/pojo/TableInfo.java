package flink.pojo;

import java.math.BigDecimal;
import java.util.Date;

public  class TableInfo{
	private String tableName;
	private String tableType;
	private long dataLength;
	private BigDecimal maxDataLength;
	private long indexLength;
	private long freeData;
	private String createDate;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public long getDataLength() {
		return dataLength;
	}
	public void setDataLength(long dataLength) {
		this.dataLength = dataLength;
	}
	public BigDecimal getMaxDataLength() {
		return maxDataLength;
	}
	public void setMaxDataLength(BigDecimal maxDataLength) {
		this.maxDataLength = maxDataLength;
	}
	public long getIndexLength() {
		return indexLength;
	}
	public void setIndexLength(long indexLength) {
		this.indexLength = indexLength;
	}
	public long getFreeData() {
		return freeData;
	}
	public void setFreeData(long freeData) {
		this.freeData = freeData;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
