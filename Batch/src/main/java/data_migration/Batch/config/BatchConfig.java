package data_migration.Batch.config;

import data_migration.Batch.entities.sourceEntities.SourceEntity;
import data_migration.Batch.entities.targetEntities.TargetEntity;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    @StepScope
    public JpaPagingItemReader<SourceEntity> reader(@Qualifier("sourceEntityManagerFactory") EntityManagerFactory sourceEmf) {
        JpaPagingItemReader<SourceEntity> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(sourceEmf);
        reader.setQueryString("SELECT e FROM SourceEntity e");
        reader.setPageSize(100); // Set batch size
        return reader;
    }


    @Bean
    public ItemProcessor<SourceEntity, TargetEntity> processor() {
        return item -> {
            TargetEntity entity = new TargetEntity();
            entity.setActorId(item.getActorId());
            entity.setFirstName(item.getFirstName());
            entity.setLastName(item.getLastName());
            entity.setLastUpdated(item.getLastUpdated());
            return entity;
        };
    }

    @Bean
    public JpaItemWriter<TargetEntity> writer(@Qualifier("entityManagerFactory") EntityManagerFactory targetEmf) {
        JpaItemWriter<TargetEntity> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(targetEmf);
        return writer;
    }

    @Bean
    public Step step(JobRepository jobRepository,
                     @Qualifier("transactionManager") PlatformTransactionManager transactionManager,
                     JpaPagingItemReader<SourceEntity> reader,
                     ItemProcessor<SourceEntity, TargetEntity> processor,
                     JpaItemWriter<TargetEntity> writer,
                     TaskExecutor taskExecutor) {
        return new StepBuilder("step", jobRepository)
                .<SourceEntity, TargetEntity>chunk(100, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer).allowStartIfComplete(false)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("dataMigrationJob", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }
}
