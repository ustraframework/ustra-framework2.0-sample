package com.gstim.ustra.java.sample.custom.config;

import com.gsitm.ustra.java.core.exception.UstraResponseCode;
import com.gsitm.ustra.java.mvc.rest.error.RestBaseExceptionHandler;
import com.gsitm.ustra.java.mvc.rest.utils.DefaultBodyWriteHandler;
import com.gstim.ustra.java.sample.custom.response.RestApiResponseVo;
import com.gstim.ustra.java.sample.custom.response.RestResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class CustomRestResponseConfiguration
{
    @Bean
    public CustomRestResponseBodyWriteHandler restResponseBodyWriteHandler()
    {
        return new CustomRestResponseBodyWriteHandler();
    }

    @Bean
    public CustomRestBaseExceptionHandler customRestBaseExceptionHandler()
    {
        return new CustomRestBaseExceptionHandler();
    }

    public static class CustomRestResponseBodyWriteHandler extends DefaultBodyWriteHandler
    {
        @Override
        public Object getSuccessResult(Object body)
        {
            RestApiResponseVo resVo = null;
            if(body instanceof RestApiResponseVo)
            {
                resVo = (RestApiResponseVo)body;
            }
            else
            {
                resVo = new RestApiResponseVo();
                resVo.setBody(body);
            }

            this.mapResCode(resVo, UstraResponseCode.SUCCESS.getCode(), null);
            this.storeResCode(resVo.getResultCode().toString());

            return resVo;
        }

        @Override
        public Object getFailureResult(String code, String message)
        {
            RestApiResponseVo resVo = new RestApiResponseVo();
            this.mapResCode(resVo, code, message);
            this.storeResCode(resVo.getResultCode().toString());
            return resVo;
        }

        private void mapResCode(RestApiResponseVo resVo, String code, String message)
        {
            if(resVo.getResultCode() != null)
            {
                return;
            }

            RestResponseCode resCode = RestResponseCode.mapResponseCode(code);
            resVo.setResultCode(resCode.getNumberCode());
            resVo.setResultMsg(StringUtils.defaultString(message, resCode.getMessage()));
        }
    }

    public static class CustomRestBaseExceptionHandler extends RestBaseExceptionHandler
    {
        //  getExceptionResult?? getExcetionResult??
        @Override
        public Object getExcetionResult(HttpServletRequest request, Exception e)
        {
            RestApiResponseVo result = (RestApiResponseVo)super.getExcetionResult(request, e);
            this.storeResCode(result.getResultCode().toString());

            return result;
        }

        @Override
        protected Object getFailureResult(ConstraintViolationException e)
        {
            String message = e.getConstraintViolations().stream()
                    .map(fe->"[" + fe.getPropertyPath() + "] " + fe.getMessage())
                    .collect(Collectors.joining(","));

            RestApiResponseVo result = new RestApiResponseVo();
            result.setResultCode(RestResponseCode.INVALID_REQUEST_VALUE.getNumberCode());
            result.setResultMsg(message);

            return result;
        }

        @Override
        protected Object getFailureResult(MethodArgumentNotValidException e)
        {
            return getFailureResult(e.getBindingResult().getFieldErrors());
        }

        @Override
        protected Object getFailureResult(List<FieldError> e)
        {
            String message = e.stream()
                    .map(fe->"[" + fe.getField() + "] " + fe.getDefaultMessage())
                    .collect(Collectors.joining(","));

            RestApiResponseVo result = new RestApiResponseVo();
            result.setResultCode(RestResponseCode.INVALID_REQUEST_VALUE.getNumberCode());
            result.setResultMsg(message);

            return result;
        }

        @Override
        protected Object getFailureResult(String code, String message)
        {
            RestApiResponseVo result = new RestApiResponseVo();
            this.mapResCode(result, code, message);
            return result;
        }

        @Override
        protected Object getFailureResult(String code, String message, Exception e)
        {
            return getFailureResult(code, message);
        }

        private void mapResCode(RestApiResponseVo resVo, String code, String message)
        {
            if(resVo.getResultCode() != null)
            {
                return;
            }

            RestResponseCode resCode = RestResponseCode.mapResponseCode(code);
            resVo.setResultCode(resCode.getNumberCode());
            resVo.setResultMsg(StringUtils.defaultString(message, resCode.getMessage()));
        }

    }
}
