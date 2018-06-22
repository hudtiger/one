package test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import test.Hook.HookHandler;
import test.Hook.UserRequestHandler;

public class testWebHook {
	
    
	public static void main(String[] args) {
		int port = 4567;
        if (args.length > 0) {
            // port was set
            port = Integer.valueOf(args[0]);
        }
        new SparkWebhookReceiver().setupWebhookListener(port);
	}

}

/*************************************************************
 * 可以用作动态端口映射，请求分发，自定义为服务框架等等
 * ref：https://www.cnblogs.com/davidwang456/p/4655090.html
 *************************************************************/
class SparkWebhookReceiver {
	private static Logger logger  = LogManager.getLogger(SparkWebhookReceiver.class);  

	public void setupWebhookListener(int port) {
        Spark.port(port);// Optionally set a port (defaults to 4567)
        
        //前置过滤器
        Spark.before((req,res)->{
        	if(false) {
        		Spark.halt(); //终止处理
        		System.err.println("stop server");
        	}
        });
        
        //异常处理
        Spark.exception(Exception.class, (ex,req,res)->{
        	String errMsg = String.format("Path:s%;Message:s%",req.pathInfo(), ex.getMessage());
        	logger.error(errMsg);
        	res.body(errMsg);
        });
        
        Spark.get("/", (req,res)->"Index Page");
        
        Spark.post("/baidu", (req,res)->{res.redirect("http://www.baidu.com",301);return "re-send";});
 
        Spark.post("/direct", (req,res)->{res.redirect("/");return "re-send";});
        
        Spark.post("/webhook", new Route() {
                @Override
                public Object handle(Request request, Response response) throws Exception {              	
                	//模拟去执行其他程序
                //	new HookHandler(new TCaller(),data->logger.error(data)).run();
                	new HookHandler(UserRequestHandler.fromJson(request.body()),data->logger.error(data)).run();
                    return "OK";
                }
            }
        );
        
        //后置过滤器
        Spark.after((req,res)->res.body(res.body()+":>>"));
    }
}




