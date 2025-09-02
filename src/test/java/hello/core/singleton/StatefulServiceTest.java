package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService s1 = ac.getBean("statefulService",StatefulService.class);
        StatefulService s2 = ac.getBean("statefulService",StatefulService.class);

        //ThreadA: A사용자 10000원 주문
        s1.order("userA", 10000);
       //ThreadB: B사용자 20000원 주문
        s2.order("userB", 20000);

        //ThreadA: 사용자A 주문 금액 조회
        int price = s1.getPrice();
        //ThreadB: 사용자A는 10000 기대, 20000 출력
        System.out.println("price " + price);

        Assertions.assertThat(price).isEqualTo(20000);
    }

    static class TestConfig{
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}