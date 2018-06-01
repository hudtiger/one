package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UThread {

	public static  Future RunASync(Callable caller){
		FutureTask task = new FutureTask<>(caller);
		new Thread(task).run();
		return task;
	}
	
	public static  void RunMultiASync(List<Callable> callers,IThreadCallback callback){
		List<Future> taskResult = new ArrayList<>();
		for(Callable caller : callers){
			FutureTask task = new FutureTask<>(caller);
			taskResult.add(task);
			new Thread(task).run();
		}
		try {
		     callback.Done(taskResult);			
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
	}
	
	public static  <T1> T1 Run(Callable caller,IMonitor monitor){
		//开启时间监听
		StopWatcher watcher = new StopWatcher();
		monitor.Trace(Thread.currentThread()+" Start at:"+watcher.watch());
		
		T1 res = null;
		FutureTask task = new FutureTask<>(caller);
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(!task.isDone());
				//结束时间监听
				monitor.Trace(Thread.currentThread()+" end at:"+watcher.watch());
			}}).start();;
		new Thread(task).run();
		
		try {
			res = (T1) task.get(10000,TimeUnit.MILLISECONDS);			
			
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
		
		return res;
	}
}






