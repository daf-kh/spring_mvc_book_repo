package ru.skillbox.app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import ru.skillbox.web.dto.Book;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class IdProvider implements InitializingBean, DisposableBean, BeanPostProcessor {

    Logger logger = LoggerFactory.getLogger(IdProvider.class);

    public String provideId (Book book) {
        return this.hashCode() + "_" + book.hashCode();
    }

    private void initIdProvider() {
        logger.info("provider INIT");
    }

    private void destroyIdProvider() {
        logger.info("provider DESTROY");
    }

    private void defaultInit() {
        logger.info("default INIT in provider");
    }

    private void defaultDestroy() {
        logger.info("default DESTROY in provider");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("provider afterPropertiesSet invoked");
    }

    @Override
    public void destroy() throws Exception {
        logger.info("disposableBean destroy invoked");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("postProcessBeforeInitialization invoked by bean " + beanName);
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("postProcessAfterInitialization invoked by bean " + beanName);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    @PostConstruct
    public void postConstructIdProvider() {
        logger.info("postConstruct annotated method called");
    }

    @PreDestroy
    public void preDestroyIdProvider() {
        logger.info("preDestroy annotated method called");
    }
}
