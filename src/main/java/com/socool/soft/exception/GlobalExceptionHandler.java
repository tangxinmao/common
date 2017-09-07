package com.socool.soft.exception;

import com.socool.soft.vo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;


/**
 * 全局异常处理类
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    // 记录当前请求handler
    public static final String REQUEST_HANDLER = "_request_handler";

    // 判断当前方法是否返回string类型,是则跳转到错误页面
    private void errorPage(HttpServletRequest request, HttpServletResponse response) {
        Object handler = request.getAttribute(REQUEST_HANDLER);
        if(handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            if("String".equals(method.getReturnType().getSimpleName())) {
                // 跳转到错误页面
                try {
                    response.sendRedirect(request.getContextPath() + "/error.jsp");
                } catch (IOException e) {
                    System.out.println("##error: " + e);
                }
            }
        }
    }

    /**
     * SystemException 业务异常
     */
    @ExceptionHandler(SystemException.class)
    public Result<Object> handleSystemException(SystemException e, HttpServletRequest request, HttpServletResponse response) {
        e.printStackTrace();
        errorPage(request, response);
        return e.getResult();
    }

    /**
     * 例外：
     * 400 （错误请求）客户端请求的参数格式错误。<br>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                HttpServletRequest request, HttpServletResponse response) {
        e.printStackTrace();
        errorPage(request, response);
        return new Result<Object>(ErrorCode.PARAMETER_ERROR.getCode() + "", ErrorCode.PARAMETER_ERROR.getMessage());
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e,
                                                                HttpServletRequest request, HttpServletResponse response) {
        e.printStackTrace();
        errorPage(request, response);
        return new Result<Object>(ErrorCode.PARAMETER_ERROR.getCode() + "", ErrorCode.PARAMETER_ERROR.getMessage());
    }

    /**
     * sql error
     */
    @ExceptionHandler(SQLException.class)
    public Result<Object> handleSQLException(SQLException e, HttpServletRequest request, HttpServletResponse response) {
        e.printStackTrace();
        errorPage(request, response);
        return new Result<Object>(ErrorCode.SQL_ERROR.getCode() + "", ErrorCode.SQL_ERROR.getMessage());
    }

    /**
     * 500 - Server Error
     */
    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        e.printStackTrace();
        errorPage(request, response);
        return new Result<Object>(ErrorCode.SYSTEM_ERROR.getCode() + "", ErrorCode.SYSTEM_ERROR.getMessage());
    }
}
