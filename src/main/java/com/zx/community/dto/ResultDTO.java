package com.zx.community.dto;

import com.zx.community.exception.CustomizeErrorCode;
import com.zx.community.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO <T> {
    private Integer code;
    private String message;
    private T data;
    public static ResultDTO errof(Integer code,String message){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    public static ResultDTO errof(CustomizeException e){
        return errof(e.getCode(),e.getMessage());
    }
    public static ResultDTO errof(CustomizeErrorCode errCode){
        return  errof(errCode.getCode(),errCode.getMessage());
    }
    public static ResultDTO okof(){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("成功");
        return resultDTO;
    }
    public static <T>ResultDTO okof(T t){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("成功");
        resultDTO.setData(t);
        return resultDTO;
    }

}
