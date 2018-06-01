package test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import utils.UThread;

public class testDynamic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		/*
		UThread.RunMultiASync(
				Arrays.asList(new TCaller(),new TCaller(),new TCaller()),
				new IThreadCallback(){
					StopWatcher watcher = new StopWatcher();
					@Override
					public  void Done(List<Future> result)
							throws InterruptedException, ExecutionException, TimeoutException {
						int total =0;
						for(Future res : result){
							//total =total+(int)res.get();
							total =total+(int)res.get(10000,TimeUnit.MILLISECONDS);
							watch(watcher.watch());
						}
						print("Total:"+total);;
						watch(watcher.watch());
						print("OK");
					}
				});
		*/
		
		UThread.RunMultiASync(
				Arrays.asList(new TCaller(),new TCaller(),new TCaller()),
				result -> {
						int total =0;
						for(Future res : result){
							//total =total+(int)res.get();
							total =total+(int)res.get(10000,TimeUnit.MILLISECONDS);
						}
						print("Total:"+total);;
						print("OK");
					
				});



		Integer res = UThread.Run(()->1, (msg)->print(msg));
		print(res.toString());
		
		try {
			print(UThread.RunASync(()->"121").get().toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void print(String msg){
		System.out.println(msg);
	}
	
	private static void watch(long delta){
		print("[Now]:"+delta);
	}	

}


class TCaller implements Callable{
	public Integer call() throws InterruptedException{
		int rdm= (int)(Math.random()*5*1000)+1;
		System.out.println(rdm);
		Thread.sleep(rdm);
		return rdm;
	}
}
