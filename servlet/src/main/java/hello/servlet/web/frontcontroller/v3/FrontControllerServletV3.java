package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV3", urlPatterns = "/front-controller/v3/*")  ///front-controller/v1/아래의 어떤 파일이 접속되어도 이 서블릿이 호출됨.
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();       //url이 키이고, Controller가 값.

    public FrontControllerServletV3(){
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    } //이 서블릿이 생성될 때, 해쉬맵 값이 넣어짐.

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();     //브라우저 uri로 받은 것을 꺼내서 변수에 저장.

        ControllerV3 controller = controllerMap.get(requestURI);  // 각 구현 컨트롤러(MemberFormControllerV2,등)의 인스턴스가 저장됨.
                                                                  //실제로  ControllerV2 controller = new MemberFormControllerV2()로 되는 것이다.
                                                                  //다형성. (부모(인터페이스)는 자식(구현클래스)을 받을 수 있다.)

        //예외처리
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);  //해시맵 생성.
        ModelView mv = controller.process(paramMap);

        //new-form
        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);


        view.render(mv.getModel(), request,response);   //model에 담긴 데이터를 꺼내서 나중에 jsp로 꺼내기 위해서 request.SetAttribute()로 넣는다.
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()     //모든 파라미터들의 이름들을 다 가지고 와서 돌리면서, paramMap에 키 값과 데이터 값을 다 집어 넣음.
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        return paramMap;
    }
}
