package com.example.asyncsample;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class SampleController {
	@Autowired
	private AsyncServiceA serviceA;
	
	@Autowired
	private AsyncServiceB serviceB;
	/**
	 * DeferredResultを使って待ち合わせるサンプル.
	 * @return
	 */
	@RequestMapping("/")
	public DeferredResult<String>  hello() {
		String result =LocalDateTime.now().toString();
		// 非同期処理を呼び出す。DeferredResultを非同期処理に引き渡すのがポイント。
        DeferredResult<String> deferredResult = new DeferredResult<>();
		serviceA.asyncService(deferredResult);
		result = serviceB.asyncService(deferredResult);
		return deferredResult;
	}
	/**
	 * JDK8標準のCompletableFutureを使って待ち合わせるサンプル.
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@RequestMapping("/completableFuture")
	public String  doCompletableFuture() throws InterruptedException, ExecutionException{
		CompletableFuture<String> resultA = serviceA.asyncService();
		CompletableFuture<String> resultB = serviceB.asyncService();
		CompletableFuture.allOf(resultA, resultB).join();
		return resultA.get() + " " + resultB.get() + " " + LocalDateTime.now().toString();
	}

}
