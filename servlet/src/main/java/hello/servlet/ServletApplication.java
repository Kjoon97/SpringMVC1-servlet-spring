package hello.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ServletComponentScan //스프링이 현재 내 패키지(hello.servlet)의 하위 패키지들 다 뒤져서 서블릿을 찾아서 자동 등록
@SpringBootApplication
public class ServletApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletApplication.class, args);
	}

//	@Bean
//	ViewResolver internalResourceViewResolver(){
//		return new InternalResourceViewResolver("/WEB-INF/views/",".jsp");
//	}  -> 스프링부트가 이것을 application.properties 정보를 보고 자동으로 해줌.
}
