package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "mytodo" ,variant = Lumo.LIGHT)
@JsModule("@vaadin/vaadin-lumo-styles/presets/compact.js")//压缩模式
@NpmPackage(value = "line-awesome", version = "1.3.0")
@Push
@PreserveOnRefresh
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        System.out.println("success!");
        
//        RedisUtil redisUtil = run.getBean(RedisUtil.class);
//        redisUtil.set("user1", new User("sdjaksdsa", "uasernamespa", "1231231", "123@234wee", "depart部门2", "机构1"));
//        Object object = redisUtil.get("user1");
//        System.out.println(object);
//        
//        redisUtil.hset("hashtest", "list1", new User("sdjaksdsa", "uasernamespa", "1231231", "123@234wee", "depart部门2", "机构1"));
//        Object hget = redisUtil.hget("hashtest", "list1");
//        System.out.println(hget);
//        redisUtil.hset("hashtest", "list2", new User("sdjaksdsa2", "uasernamespa2", "1231231", "123@234wee", "depart部门2", "机构1"));
//        Object hget2 = redisUtil.hget("hashtest", "list2");
//        System.out.println(hget2);
        
    }

}