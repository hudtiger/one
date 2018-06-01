package test;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

public class testElasticSearch {
	public static void main(String[] args) throws IOException {
//		getAll();
//		getDocsCount();
//		ESUtil.doRequestAsync("GET", "/test/note/_search?pretty&q=fdate:2017-02",result->System.err.println(result)); 
//		ESUtil.doRequestAsync("GET", "/test/note/_search?pretty","{\"query\":{\"match\":{\"fdate\":\"2017-02\"}}}",result->System.out.println(result));
		
//		ESUtil.doRequestAsync("GET", "/test/note/_search?pretty","{\"query\":{\"match\":{\"fnote\":\"a i2S\"}}}",result->System.err.println(result)); //全文模糊检索
//		ESUtil.doRequestAsync("GET", "/test/note/_search?pretty","{\"query\":{\"match_phrase\":{\"fnote\":\"a i2S\"}}}",result->System.out.println(result));//全文精确检索
//		ESUtil.doRequestAsync("GET", "/test/note/_search?pretty","{\"query\":{\"match_phrase\":{\"fnote\":\"a i2S\"}}, \"highlight\": { \"fields\" : { \"fnote\" : {} } }}",result->System.out.println(result));//全文精确检索，高亮
//		ESUtil.doRequestAsync("GET", "/test/note/_search?pretty","{\"aggs\":{\"ids\":{\"terms\":{\"field\":\"fid\"}}}}",result->System.err.println(result));
		
		//select *,sum(position) over() from test.db group by datatype order by sum(position) over() limit 10,20
//		ESUtil.doRequestAsync("GET", "test/db/_search?pretty","{\"from\":10,\"size\":20,\"aggs\":{\"group_by_datatype\":{\"terms\":{\"field\":\"datatype.keyword\",\"order\":{\"sum_position\":\"asc\"}},\"aggs\":{\"sum_position\":{\"sum\":{\"field\":\"position\"}}}}}}",result->System.out.println(result));

		//只返回100条分组聚合信息
		ESUtil.doRequestAsync("GET","test/db/_search?pretty","{\"from\":10,\"size\":10,\"aggs\":{\"group_by_tablename\":{\"terms\":{\"field\":\"tablename.keyword\",\"size\": 100},\"aggs\":{\"group_by_position\":{\"range\":{\"field\":\"position\",\"ranges\":[{\"from\":0,\"to\":10},{\"from\":10,\"to\":30},{\"from\":30,\"to\":100}]},\"aggs\":{\"max_position\":{\"max\":{\"field\":\"position\"}}}}}}}}",result->System.out.println(result));
//		ESUtil.doRequestAsync("GET", "test/db/_search?pretty","{\"from\":10,\"size\":10,\"aggs\":{\"group_by_position\":{\"range\":{\"field\":\"position\",\"ranges\":[{\"from\":0,\"to\":10},{\"from\":10,\"to\":30},{\"from\":30,\"to\":100}]},\"aggs\":{\"group_by_tablename\":{\"terms\":{\"field\":\"tablename.keyword\",\"order\":{\"max_position\":\"asc\"}},\"aggs\":{\"max_position\":{\"max\":{\"field\":\"position\"}}}}}}}}",result->System.out.println(result));
//		getByFilter();
//		String queryString = "";
	}
	
	private static void getByFilter() throws IOException {
		String queryStr = new StringBuilder().append("{")
		.append("  \"query\" : {") 
		.append("    \"bool\": {" ) 
		.append("      \"must\": {" ) 
		.append("        \"match\" : {" ) 
		.append("          \"fnote\" : \"Ga a\"" )   //模糊
		.append("        }" ) 
		.append("      }," )
		.append("      \"filter\": {" ) 
		.append("        \"range\" : {" ) 
		.append("            \"fid\" : { \"gt\" : 60 }" )  //范围过滤
		.append("        }" ) 
		.append("      }" ) 
		.append("    }" ) 
		.append("  }" ) 
		.append("}").toString();
		ESUtil.doRequestAsync("GET", "/test/note/_search?pretty",queryStr,result->System.out.println(result));
	}

//	查询参数
//	http://192.168.99.100:32773/test/note/_search?pretty
	private static void getAll() throws IOException {
		Map<String,String> parms = new HashMap<>();
		parms.put("pretty", "true");
	//	ESUtil.doRequest("GET", "/test/note/_search",parms,result->System.out.println(result));
		ESUtil.doRequestAsync("GET", "/test/note/_search?pretty",result->System.err.println(result));
	}

//  查询表达式
//	curl -XGET "http://192.168.99.100:32773/test/note/_count?pretty" -H 'Content-Type: application/json' -d'
//	{
//	    "query": {
//	        "match_all": {}
//	    }
//	}'
	private static void getDocsCount() throws IOException {
		ESUtil.doRequest("GET", "/test/note/_count?pretty","{\"query\":{\"match_all\":{}}}",result->System.out.println(result));
	}
}

class ESUtil{
	static RestClientBuilder rsb=null;
	static {
		if(rsb==null) {
			rsb = RestClient.builder(new HttpHost("192.168.56.102", 32666, "http"));
		}
		rsb.setMaxRetryTimeoutMillis(10000); 
		rsb.setFailureListener( new RestClient.FailureListener() {
		    @Override
		    public void onFailure(HttpHost host) {
		        System.out.println(host);
		    }
		});
	}
	public static void doRequest(String method, String endpoint,IESCallback callback) throws IOException {
		doRequest(method,endpoint,Collections.<String, String>emptyMap(),"{}",callback);
	}
	
	public static void doRequest(String method, String endpoint,String jsondata,IESCallback callback) throws IOException {
		doRequest(method,endpoint,Collections.<String, String>emptyMap(),jsondata,callback);
	}
	
	public static void doRequest(String method, String endpoint, Map<String, String> params,IESCallback callback) throws IOException {
		doRequest(method,endpoint,params,"{}",callback);
	}
	
	public static void doRequest(String method, String endpoint, Map<String, String> params,String jsondata,IESCallback callback) throws IOException {
		RestClient restClient =rsb.build();
		Response response = restClient.performRequest(method,endpoint,params,new NStringEntity(jsondata, ContentType.APPLICATION_JSON));
		if(callback!=null)
			callback.run(EntityUtils.toString(response.getEntity()));
		restClient.close();
	}
	
	public static void doRequestAsync(String method, String endpoint, IESCallback callback) throws IOException {
		doRequestAsync(method,endpoint,Collections.<String, String>emptyMap(),"{}",callback);
	}
	public static void doRequestAsync(String method, String endpoint, String jsondata,IESCallback callback) throws IOException {
		System.err.println(jsondata);
		doRequestAsync(method,endpoint,Collections.<String, String>emptyMap(),jsondata,callback);
	}
	public static void doRequestAsync(String method, String endpoint, Map<String, String> params,IESCallback callback) throws IOException {
		doRequestAsync(method,endpoint,params,"{}",callback);
	}
	
	public static void doRequestAsync(String method, String endpoint, Map<String, String> params,String jsondata,IESCallback callback) throws IOException {
		RestClient restClient =rsb.build();
		restClient.performRequestAsync(method,endpoint,params,new NStringEntity(jsondata, ContentType.APPLICATION_JSON),new ResponseListener() {
          @Override
          public void onSuccess(Response response) {
        	  if(callback!=null)
				try {
					callback.run(EntityUtils.toString(response.getEntity()));
					restClient.close();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	  
          }

          @Override
          public void onFailure(Exception exception) {
        	  if(callback!=null)
  				try {
  					callback.run(exception.getMessage());
  					restClient.close();
  				} catch (ParseException e) {
  					e.printStackTrace();
  				} catch (IOException e) {
  					e.printStackTrace();
  				}
          }
      });	
	}
}
	
interface IESCallback{
	void run(String result);
}
