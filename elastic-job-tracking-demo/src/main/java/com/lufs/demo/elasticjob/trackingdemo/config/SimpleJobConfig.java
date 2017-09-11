package com.lufs.demo.elasticjob.trackingdemo.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.lufs.demo.elasticjob.trackingdemo.job.MyISimpleJob;
import com.lufs.demo.elasticjob.trackingdemo.job.MySimpleJob;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class SimpleJobConfig {

    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Resource
    private JobEventConfiguration jobEventConfiguration;

    @Bean
    public SimpleJob mySimpleJob() {
        return new MySimpleJob();
    }

    @Bean(initMethod = "init")
    public JobScheduler mySimpleJobScheduler(@Qualifier("mySimpleJob") final SimpleJob mySimpleJob, @Value("${mySimpleJob.cron}") final String cron,
                                                             @Value("${mySimpleJob.shardingTotalCount}") final int shardingTotalCount,
                                                             @Value("${mySimpleJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(mySimpleJob, regCenter, getLiteJobConfiguration(mySimpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration);
    }


    @Bean
    public SimpleJob myISimpleJob() {
        return new MyISimpleJob();
    }

    @Bean(initMethod = "init")
    public JobScheduler myISimpleJobScheduler(@Qualifier("myISimpleJob") final SimpleJob myISimpleJob, @Value("${myISimpleJob.cron}") final String cron,
                                                             @Value("${myISimpleJob.shardingTotalCount}") final int shardingTotalCount,
                                                             @Value("${myISimpleJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(myISimpleJob, regCenter, getLiteJobConfiguration(myISimpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration);
    }

    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();
    }
}
