package ru.dynsys.ttfsample;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.TransactionRequiredException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {
    @Test
    public void testShouldError() throws InterruptedException {
        Observable.range(1, 100)
                .map(n -> catService.create(String.format("Cat #%d", n)))
                .test()
                .assertError(TransactionRequiredException.class);
    }

    @Test
    public void testShouldOk() throws InterruptedException {
        TestObserver<Cat> observer = Observable.range(1, 100)
                .observeOn(Schedulers.from(transactionalTaskExecutor))
                .map(n -> catService.create(String.format("Cat #%d", n)))
                .test();

        Thread.sleep(500);

        observer.assertValueCount(100);
    }

    @Autowired
    private CatService catService;
    @Autowired
    private TaskExecutor transactionalTaskExecutor;
}
