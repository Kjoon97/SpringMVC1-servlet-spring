package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="helloServlet", urlPatterns = "/hello") //name은 원하는 대로, 주소창에 '/hello'로 들어오면 이것이 실행되는 것.
public class HelloServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        //request
        String username = request.getParameter("username");  //http 쿼리 파라미터를 읽기.(/hello?username='kim')
        System.out.println("username 은 " + username);

        //response
        response.setContentType("text/plain");  //단순 문자형식  (이 정보는 http 헤더에 들어감)
        response.setCharacterEncoding("utf-8");  //문자 인코딩   (이 정보는 http 헤더에 들어감)
        response.getWriter().write("hello "+username);   //http 메세지 바디에 데이터를 넣기
    }
}
