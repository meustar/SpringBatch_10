package com.koreait.exam.springbatch_10.job.withParam;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WithParamJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job WithParamJob(Step WithParamStep1) {
        return jobBuilderFactory.get("WithParamJob")
                .incrementer(new RunIdIncrementer())    // 강제로 매번 다른 ID를 실행할 때 파라미터로 부여 => 매번 다른 파라미터로 실행되서 동일하게 실행해봤자 각 실행 횟수는 별개의 다른 횟수이다.
                .start(WithParamStep1)
                .build();
    }
    @Bean
    @JobScope
    public Step WithParamStep1(Tasklet WithParamStep1Tasklet) {
        return stepBuilderFactory.get("WithParamStep1")
                .tasklet(WithParamStep1Tasklet)
                .build();
    }
    @Bean
    @StepScope
    public Tasklet WithParamStep1Tasklet(
            // @Value는 lombok이 아닌 factory.annotation.Value 이다. 주의!
            @Value("#{jobParameters['name']}") String name,
            @Value("#{jobParameters['age']}") String age
        ) {
        return (stepContribution, chunkContext) -> {
            log.debug("name : {} age : {}", name, age);
            System.out.println("WithParam111111!!!!");
            System.out.printf("%s, %s\n", name, age);
            return RepeatStatus.FINISHED;
            };
    }

}