package com.koreait.exam.springbatch_10.job.HelloWorld;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HelloWorldConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    @Bean
    public Job helloWorldJob() {
        return jobBuilderFactory.get("helloWorldJob")
//                .incrementer(new RunIdIncrementer())    // 강제로 매번 다른 ID를 실행할 때 파라미터로 부여 => 매번 다른 파라미터로 실행되서 동일하게 실행해봤자 각 실행 횟수는 별개의 다른 횟수이다.
                .start(helloWorldStep1())
                .next(helloWorldStep2())
                .build();
    }
    @Bean
    @JobScope
    public Step helloWorldStep1() {
        return stepBuilderFactory.get("helloWorldStep1")
                .tasklet(helloWorldSept1Tasklet())
                .build();
    }
    @Bean
    @StepScope
    public Tasklet helloWorldSept1Tasklet() {
        return (stepContribution, chunkContext) -> {
            System.out.println("헬로월드11111111");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    @JobScope
    public Step helloWorldStep2() {
        return stepBuilderFactory.get("helloWorldStep2")
                .tasklet(helloWorldStep2Tasklet())
                .build();
    }
    @Bean
    @StepScope
    public Tasklet helloWorldStep2Tasklet() {
        return (stepContribution, chunkContext) -> {
            System.out.println("헬로월드22222222");

            if (false) {
                throw new Exception("실패 : 헬로월드 태스클릿 2 실패");
            }
            return RepeatStatus.FINISHED;
        };
    }
}