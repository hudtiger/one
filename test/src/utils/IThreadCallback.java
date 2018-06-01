package utils;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public interface IThreadCallback {
	void Done(List<Future> result) throws InterruptedException, ExecutionException, TimeoutException;
}
