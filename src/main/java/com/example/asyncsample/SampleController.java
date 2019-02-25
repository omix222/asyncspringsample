package com.example.asyncsample;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class SampleController {
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
    
	@Autowired
	private AsyncServiceA serviceA;
	
	@Autowired
	private AsyncServiceB serviceB;
	
	@Autowired
	private AsyncServiceException serviceEx;
	
	/**
	 * DeferredResultを使って待ち合わせるサンプル.
	 * このやり方だと、レスポンス返すタイミングでしか
	 * @return
	 */
	@RequestMapping("/")
	public DeferredResult<String>  hello() {
		logger.info("start SampleController.hello");
		// 非同期処理を呼び出す。DeferredResultを非同期処理に引き渡すのがポイント。
        DeferredResult<String> deferredResult = new DeferredResult<>();
		serviceA.asyncService(deferredResult);
		serviceB.asyncService(deferredResult);
		logger.info("end SampleController.hello");
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
		logger.info("start SampleController.doCompletableFuture");
		CompletableFuture<String> resultA = serviceA.asyncService();
		CompletableFuture<String> resultB = serviceB.asyncService();
		CompletableFuture.allOf(resultA, resultB).join();
		logger.info("end SampleController.doCompletableFuture");
		return resultA.get() + " " + resultB.get() + " " + LocalDateTime.now().toString();
	}
	
	@RequestMapping("/completableFutureError")
	public String  doCompletableFutureError() throws InterruptedException, ExecutionException{
		logger.info("start SampleController.doCompletableFuture");

		CompletableFuture<String> resultEx = serviceEx.asyncService();

		//例外発生時の非同期スレッド側の例外取得方法
		// http://pppurple.hatenablog.com/entry/2017/06/23/220109
   String errorStr  =		resultEx.handle((t, error) -> {
			if (error != null) {
				System.out.println("cause : " + error);
				return "fallback value"; // エラー発生時の返却値の指定
			} else {
				return t;
			}
		}).get();

		// 一軒ずつ例外が出ていないかを確認するパターン
//		boolean result = resultEx.isCompletedExceptionally();
//		if (!result) {
//			logger.info("isCompletedExceptionally : "+result);
//			throw new RuntimeException("Error");
//		}
		CompletableFuture<String> resultA = serviceA.asyncService();
		CompletableFuture<String> resultB = serviceB.asyncService();
		//CompletableFuture.allOf(resultA, resultB, resultEx).join();
		logger.info("end SampleController.doCompletableFuture");
		return resultA.get() + " " + resultB.get() + " " + LocalDateTime.now().toString();
	}

}
