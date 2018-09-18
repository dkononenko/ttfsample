package ru.dynsys.ttfsample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.ThreadFactory;

@SpringBootApplication
public class App {
    @Bean
    public TaskExecutor transactionalTaskExecutor(ThreadFactory transactionalThreadFactory) {
        final int availableProcessors = Runtime.getRuntime().availableProcessors();

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadFactory(transactionalThreadFactory);
        taskExecutor.setCorePoolSize(availableProcessors);
        taskExecutor.setMaxPoolSize(availableProcessors);
        taskExecutor.afterPropertiesSet();

        return taskExecutor;
    }

    @Bean
    public ThreadFactory transactionalThreadFactory(PlatformTransactionManager transactionManager) {
        return new TransactionalThreadFactory(transactionManager);
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(App.class, args);
    }
}
