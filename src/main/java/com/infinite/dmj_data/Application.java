package com.infinite.dmj_data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@ImportResource(locations= {"classpath:person.xml"})
@SpringBootApplication
@EnableTransactionManagement//开启事务支持
@MapperScan("com.infinite.dmj_data.dao") //扫描mybatis的Mapper类所在目录
@EnableScheduling  //添加定时任务注解
public class Application {
	
    public static void main( String[] args ) {
        SpringApplication.run(Application.class, args);
    }
}

