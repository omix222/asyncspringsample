package com.example.asyncsample;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
/**
 * 
 * @see https://qiita.com/kazuki43zoo/items/ce88dea403c596249e8a#async%E3%81%AE%E5%88%A9%E7%94%A8
 */
@Configuration
@EnableAsync // @Asyncの有効化
public class AsyncConfig {
    @Bean
    public AsyncTaskExecutor taskExecutor() { // デフォルトだと"taskExecutor"という名前のBeanが利用される
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10);
        return executor;
    }
}
