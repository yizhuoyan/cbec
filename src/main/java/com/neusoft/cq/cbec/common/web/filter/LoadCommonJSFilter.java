package com.neusoft.cq.cbec.common.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

/**
 */
@WebFilter(initParams=@WebInitParam(name="targetDictionary",value="/WEB-INF/common/js/"),urlPatterns="/common.js")
public class LoadCommonJSFilter extends AbstractFilterSupport {
private String targetDictionary;

@Override
public void init(){

}

@Override
public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws Exception{
  String realDictionary = application.getRealPath(targetDictionary);
  System.out.println(realDictionary);
  File[] files = new File(realDictionary).listFiles(f->f.getName().endsWith(".js"));
  if(files==null)return;
  Arrays.sort(files, (a, b)->a.getName().compareToIgnoreCase(b.getName()));
  resp.setContentType("text/javascript;charset=utf-8");
  try(ServletOutputStream out = resp.getOutputStream()){
    byte[] buff = new byte[10240];
    int readed = 0;
    for(File f : files){
      try(FileInputStream in = new FileInputStream(f)){
        while((readed = in.read(buff))!=-1){
          out.write(buff, 0, readed);
        }
        out.println();
        out.flush();
      }
    }
  }
}

public void setTargetDictionary(String targetDictionary){
  this.targetDictionary = targetDictionary;
}
}
