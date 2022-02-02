package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v1.ControllerV1;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV2", urlPatterns = "/front-controller/v2/*")  ///front-controller/v1/아래의 어떤 파일이 접속되어도 이 서블릿이 호출됨.
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();       //url이 키이고, Controller가 값.

    public FrontControllerServletV2(){
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    } //이 서블릿이 생성될 때, 해쉬맵 값이 넣어짐.

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();     //브라우저 uri로 받은 것을 꺼내서 변수에 저장.

        ControllerV2 controller = controllerMap.get(requestURI);  // 각 구현 컨트롤러(MemberFormControllerV2,등)의 인스턴스가 저장됨.
                                                                  //실제로  ControllerV2 controller = new MemberFormControllerV2()로 되는 것이다.
                                                                  //다형성. (부모(인터페이스)는 자식(구현클래스)을 받을 수 있다.)

        //예외처리
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyView view = controller.process(request, response);//매서드 호출
        view.render(request,response);
    }
}
