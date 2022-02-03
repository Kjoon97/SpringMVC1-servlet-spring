package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        //MemberFormControllerV3가 handler로 들어감.
        return (handler instanceof ControllerV3);   //handler에 ControllerV3의 구현체가 넘어오면 true를 반환함.
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        //프론트 컨트롤러에서 supports()를 통해 ControllerV3인지 걸러지고 그다음에 handle로 들어가기 때문에 캐스팅해도 된다.
        //MemberFormControllerV3()가 handler로 들어감.
        ControllerV3 controller = (ControllerV3) handler;

        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        return mv;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()     //모든 파라미터들의 이름들을 다 가지고 와서 돌리면서, paramMap에 키 값과 데이터 값을 다 집어 넣음.
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        return paramMap;
    }

}
