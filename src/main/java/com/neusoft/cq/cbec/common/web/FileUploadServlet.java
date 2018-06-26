package com.neusoft.cq.cbec.common.web;

import com.alibaba.fastjson.JSON;
import com.neusoft.cq.cbec.common.dto.JsonResponse;
import com.neusoft.cq.cbec.common.dto.UserContext;
import com.neusoft.cq.cbec.common.util.KeyValueMap;
import com.neusoft.cq.cbec.common.util.PlatformUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 */
public class FileUploadServlet extends HttpServlet {
    private String saveLocation;

    @Override
    public void init(ServletConfig config) throws ServletException {
        saveLocation=config.getInitParameter("save-location");
        if(saveLocation==null)return;

        if(!new File(saveLocation).isAbsolute()){
            saveLocation=config.getServletContext().getRealPath(saveLocation);
        }
        try {
            Files.createDirectories(Paths.get(saveLocation));
        } catch (IOException e) {
            throw  new ServletException(e);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserContext uc=UserContext.getCurrentUser();
            String userId=uc.getUserId();
            Collection<Part> parts = req.getParts();
            List<Object> uploadRespList=new ArrayList<>(parts.size());
            String fileName;
            for (Part part:parts){
                fileName = part.getSubmittedFileName();
                part.write(userId+fileName);
                uploadRespList.add(KeyValueMap.of(4)
                        .put("name",part.getName())
                        .put("path","/upload/"+fileName));
            }
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            try(PrintWriter out = resp.getWriter()){
                out.print(JSON.toJSONString(JsonResponse.ok(uploadRespList).toJSON()));
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

}
