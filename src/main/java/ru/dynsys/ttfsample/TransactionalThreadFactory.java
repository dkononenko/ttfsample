package ru.dynsys.ttfsample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;

@Component
public class TransactionalThreadFactory implements ThreadFactory {
    @Autowired
    public TransactionalThreadFactory(PlatformTransactionManager transactionManager) {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Objects.requireNonNull(runnable);

        return new Thread(() ->
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                        runnable.run();
                    }
                })
        );
    }

    private TransactionTemplate transactionTemplate;
}
