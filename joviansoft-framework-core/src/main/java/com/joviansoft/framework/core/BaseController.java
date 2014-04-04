package com.joviansoft.framework.core;

import com.joviansoft.framework.core.exceptions.BusinessException;
import com.joviansoft.framework.core.exceptions.SystemServiceException;
import com.joviansoft.framework.core.exceptions.ValidateException;
import com.joviansoft.framework.core.response.error.ErrorResponse;
import com.joviansoft.framework.core.response.error.ValidateErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 所有Controller的基类，在基类中处理参数校验的异常，权限校验
 */
@Controller
public abstract class BaseController {

    @ExceptionHandler(ValidateException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Object error(ValidateException ex,
                        HttpServletResponse response){

        ValidateErrorResponse errorResponse = new ValidateErrorResponse(ex.getErrorCode().getNumber());
        errorResponse.setSubMessage(ex.getMessage());

        return errorResponse;
    }

    public void handleError(BindingResult bindingResult)  {

        if (bindingResult.hasErrors()) {
            List<String> errorCodes = new ArrayList<String>();
            String errorString = "";
            for (ObjectError error : bindingResult.getAllErrors()) {
                FieldError fieldError = (FieldError)error;
                errorString += fieldError.getField()+fieldError.getDefaultMessage() + " ";

                errorCodes.add(error.getDefaultMessage());
            }
            throw new ValidateException(errorString);
        }
    }

    /**
     * 服务级别的异常处理
     * @param ex
     * @param response
     * @return
     */

    @ExceptionHandler(SystemServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object error(SystemServiceException ex,
                        HttpServletResponse response){

        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode().getNumber());
        return errorResponse;
    }


    /**
     * 业务逻辑的异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object failExecute(BusinessException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode().getNumber());
        return errorResponse;
    }

}
