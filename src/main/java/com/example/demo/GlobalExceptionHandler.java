package com.example.demo;

import com.example.demo.html.domian.vo.Msg;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: lingshipu
 * @description:
 * @author: QWS
 * @create: 2021-05-16 20:15
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, InvalidFormatException.class})
    @ResponseBody
    public Msg illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return Msg.statu400().add("error",e.getMessage());
    }

}
