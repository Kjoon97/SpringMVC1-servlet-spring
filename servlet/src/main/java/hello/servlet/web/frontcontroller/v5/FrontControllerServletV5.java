package hello.servlet.web.frontcontroller.v5;


import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();  //이전과 다르게 ControllerV1,V2,V3,, 등 다 들어갈 수 있어야하므로 Object로 선언.
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();  // 어댑터가 여러개 있고 그중에 하나를 꺼내서 쓰기때문에 arraylist로.

    //생성자 초기화
    public FrontControllerServletV5(){
        initHandlerMapping();
        initHandlerAdapters();
    }

    //handlerMappingMap초기화
    private void initHandlerMapping() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        //v4추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    //handlerAdapter초기화
    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //MemberFormControllerV3()
        Object handler = getHandler(request); //1. 핸들러 조회 후 반환

        //예외처리
        if(handler == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //ControllerV3HandlerAdapter가 adpater로 들어감
        MyHandlerAdapter adapter = getHandlerAdapter(handler);       // 2. 해당 핸들러를 처리할 수 있는 어댑터 조회, 반환.

        ModelView mv = adapter.handle(request, response, handler);   // 3. 어댑터가 핸들 호출 -> 4. 각 핸들러의 handle호출 -> 5. 각 로직 처리후 모델 뷰 반환.

        //new-form
        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);  //6. 뷰리졸버 호출 , 7. MyView 반환

        view.render(mv.getModel(), request,response);   //model에 담긴 데이터를 꺼내서 나중에 jsp로 꺼내기 위해서 request.SetAttribute()로 넣는다.

    }

    //핸들러를 처리할 수 있는 어댑터 찾기
    private MyHandlerAdapter getHandlerAdapter(Object handler) {

        //ControllerV3HandlerAdapter가 adapter로 들어감.
        for (MyHandlerAdapter adapter : handlerAdapters) {  //handlerAdapters에서 핸들러들을 다 뒤진다.
            if(adapter.supports(handler)){
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler" +handler);
    }

    //핸들러 객체 반환
    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();     //브라우저 uri로 받은 것을 꺼내서 변수에 저장.
        return handlerMappingMap.get(requestURI);  // 각 구현 컨트롤러(MemberFormControllerV2,등)의 인스턴스가 저장됨.
    }

    //jsp파일 뷰 반환
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
