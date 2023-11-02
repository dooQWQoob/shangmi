package com.example;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
class ShangmiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void FastAutoGeneratorTest(){
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/shangmi?characterEncoding=utf-8&userSSL=false", "root", "123456")
                        .globalConfig(builder -> {
                            builder.author("taozi") // 设置作者//.enableSwagger() // 开启 swagger 模式
                                    .fileOverride() // 覆盖已生成文件
                                    .outputDir("E:\\IDEA_Student\\shangmi\\src\\main\\java\\com\\example"); // 指定输出目录
                        })
                        .packageConfig(builder -> {
                            builder.parent("com.example") // 设置父包名
                                    .moduleName("example") // 设置父包模块名
                                    .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "E:\\IDEA_Student\\shangmi\\src\\main\\resources\\mapper"));// 设置mapperXml生成路径
                        })
                        .strategyConfig(builder -> {
                            builder.addInclude("t_user","t_order","t_class","t_address","t_cart","t_comment","t_goods") // 设置需要生成的表名
                                    .addTablePrefix("t_"); // 设置过滤表前缀
                        })
                        .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker 引擎模板，默认的是Velocity引擎模板
                        .execute();
    }

}
