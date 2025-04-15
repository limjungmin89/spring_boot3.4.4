package com.spring_boot.config;

import com.spring_boot.exception.BasicHandlerExceptionResolver;
import com.spring_boot.filter.LogFilter;
import com.spring_boot.filter.LoginCheckFilter;
import com.spring_boot.interceptor.LogInterceptor;
import com.spring_boot.interceptor.LoginCheckInterceptor;
import com.spring_boot.interceptor.LoginMemberArgumentResolver;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * packageName    : com.spring_boot.web.config
 * fileName       : WebConfig
 * author         : mzc01-jungminim
 * date           : 2025. 4. 14.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 14.        mzc01-jungminim       최초 생성
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 1. WAS(여기까지 전파) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(예외발생)
    // 2. WAS `/error-page/500` 다시 요청 -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러(/error-page/500) -> View
    // 3. 위와 같이 에러 시 다시 에러페이지 호출로 인해 서블릿 filter, 스프링 인터셉터를 다시 한번 거치게 된다.
    // 4. error 페이지 요청 인 경우 filter 에서는 dispatcherServletType 이 ERROR 로 발생 하여 필터를 적용 가능
    // 5. error 페이지 요청 인 경우 interceptor 에서는 excludePathPatterns 을 주어서 해결 가능
//    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");

        // DispatcherType.ERROR 도 필터 적용하려면 추가 필요 default 는 DispatcherType.REQUEST 만 적용
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**", "/*.ico", "/error","/error-**/**");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**", "/*.ico", "/error","/error-**/**", "/api/**", "/api2/**", "/api3/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new BasicHandlerExceptionResolver());
    }
}
