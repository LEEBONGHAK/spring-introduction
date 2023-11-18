package management.membermanagement.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
//@Component    // component scan을 통해 aop를 빈에 등록하여 사용 방법
public class TimeTraceAop {

    @Around("execution(* management.membermanagement..*(..))")  // 어디에 적용할 지 타켓팅
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());   // 실행되는 메소드 이름 출력
        try {
            return joinPoint.proceed();    // 다음 메소드로 진행
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = start - finish;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
