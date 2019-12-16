package com.zx.community.advice;

import com.alibaba.fastjson.JSON;
import com.zx.community.dto.ResultDTO;
import com.zx.community.exception.CustomizeErrorCode;
import com.zx.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handler(Throwable e, Model model,
                         HttpServletResponse response,
                         HttpServletRequest request)  {
        String contntType=request.getContentType();
        if("application/json".equals(contntType)){
            if(e instanceof CustomizeException){
                ResultDTO resultDTO=ResultDTO.errof((CustomizeException) e);
                try {
                    response.setContentType("application/json");
                    response.setStatus(200);
                    response.setCharacterEncoding("utf-8");
                    PrintWriter writer=response.getWriter();
                    writer.print(JSON.toJSONString(resultDTO));
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }
        else {
            if(e instanceof  CustomizeException){
                model.addAttribute("code",((CustomizeException) e).getCode());
                model.addAttribute("message",e.getMessage());
            }
            else{
               ResultDTO resultDTO=ResultDTO.errof(CustomizeErrorCode.SYS_ERROR);
                model.addAttribute("code",resultDTO.getCode());
                model.addAttribute("message",resultDTO.getMessage());
            }
        }
        return new ModelAndView("error");

    }
}
