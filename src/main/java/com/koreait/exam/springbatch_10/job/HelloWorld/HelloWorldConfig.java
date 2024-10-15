package com.koreait.exam.springbatch_10.job.HelloWorld;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HelloWorldConfig {
    // job을 빌드해줄 빌더를 만드는.
    private final JobBuilderFactory jobBuilderFactory;
    // step을 빌드해줄 빌더를 만드는.
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloWorldJob() {
        return jobBuilderFactory.get("HelloWorldJob").start(helloWorldStep1()).build();
    }

    @Bean
    public Step helloWorldStep1() {
        return stepBuilderFactory.get("helloWorldStep1").tasklet(hellWorldTasklet()).build();
    }

    @Bean
    public Tasklet hellWorldTasklet() {
        return ((stepContribution, chunkContext) -> {
            System.out.println("helloWorld!!!");
            return RepeatStatus.FINISHED;
        });
    }
}
