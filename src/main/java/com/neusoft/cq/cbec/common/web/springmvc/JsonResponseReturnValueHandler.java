package com.neusoft.cq.cbec.common.web.springmvc;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.neusoft.cq.cbec.common.dto.JsonResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 */
public class JsonResponseReturnValueHandler implements HandlerMethodReturnValueHandler{

@Override
public boolean supportsReturnType(MethodParameter mp){
  return mp.getParameterType()==JsonResponse.class;
}

@Override
public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception{
  modelAndViewContainer.setRequestHandled(true);
  if(o!=null){
    JsonResponse result = (JsonResponse)o;
    HttpServletResponse resp = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
    resp.setContentType("application/json;charset=utf-8");
    try(PrintWriter writer = resp.getWriter()){
      writer.write(result.toString());
    }
  }

}
}
