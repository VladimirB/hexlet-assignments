package exercise;

import exercise.calculator.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomBeanPostProcessor.class);

    private final Map<String, String> inspects = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Inspect.class)) {
            var annotation = bean.getClass().getAnnotation(Inspect.class);
            inspects.put(beanName, annotation.level());
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (inspects.containsKey(beanName)) {
            Object proxy = Proxy.newProxyInstance(
                    Calculator.class.getClassLoader(),
                    new Class[] { Calculator.class },
                    new LoggingInvocationHandler(bean)
            );
            return BeanPostProcessor.super.postProcessAfterInitialization(proxy, beanName);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private static class LoggingInvocationHandler implements InvocationHandler {

        private final Object original;

        public LoggingInvocationHandler(Object original) {
            this.original = original;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            LOGGER.info("Was called method: {}() with arguments: {}",
                    method.getName(),
                    Arrays.toString(args));
            return original.getClass()
                    .getMethod(method.getName(), method.getParameterTypes())
                    .invoke(original, args);
        }
    }
}