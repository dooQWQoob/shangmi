package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class swaggerConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }

    //配置swagger的Docket的Bean对象
    @Bean
    public Docket docket(Environment environment){
        //设置要显示的swagger环境
        Profiles profiles = Profiles.of("dev", "pro");
        //通过environment.acceptsProfiles() 判断是否处在自己设定的环境当中
        boolean flag = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                // 添加 apiInfo 信息
                .apiInfo(apiInfo())
                //enable() 是否启用 swagger  若为false 则swagger 不能在浏览器访问 默认为true(不用配置)
                .enable(true)
                .select()
                //RequestHandlerSelectors 配置要扫描接口的方式
                //withClassAnnotation() 扫描类上的注解，参数是一个注解的反射对象  例如 GetMapper.class
                //withMethodAnnotation() 扫描方法上的注解  例如：RestController.class  一般在控制层的类上面
                //basePackage() 指定要扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
                //paths() 过滤什么路径
                //ant() 扫描全部
                //nome() 不扫描
//              .paths(PathSelectors.ant("/taozi/**"))  //只扫描 /taozi 下面的所有路径
                .build()
                ;
    }
    //配置 apiInfo 信息
    private ApiInfo apiInfo(){
        //作者信息
        Contact contact = new Contact("桃子","http://localhost:8080","3488676408@qq.com");
        //返回api接口文档
        return new ApiInfo("桃子的API文档",
                "尚米网购项目Api文档",
                "1.0",
                "http://localhost:8080",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
