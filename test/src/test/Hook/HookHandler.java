package test.Hook;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HookHandler{
	private static Logger logger  = LogManager.getLogger(HookHandler.class); 
	private Callable<?> requestHandler;
	private ICallback responseHandler;

	public HookHandler(Callable<?> requestHandler,ICallback responseHandler) {
		this.requestHandler = requestHandler;
		this.responseHandler = responseHandler;
	}
	
	private void consumeResult(Object data) {
		if(this.responseHandler!=null) {
			this.responseHandler.handle(data);
		}
			
	}
	public final void run() {
		new Thread(()->{
			logger.info("HookHandler called");
			FutureTask task = new FutureTask<>(this.requestHandler);
			new Thread(task).run();
    		try {
				this.consumeResult(task.get(10,TimeUnit.SECONDS));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}
}
