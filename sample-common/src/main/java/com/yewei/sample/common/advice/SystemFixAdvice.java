package com.yewei.sample.common.advice;

import com.yewei.common.error.BusinessException;
import com.yewei.common.error.GeneralCode;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SystemFixAdvice {

    @Value("${system.in.fix:false}")
    private Boolean systemInFix;

    @Value("${system.api.in.fix:false}")
    private Boolean systemApiInFix;

    @Before("@annotation(systemFix)")
    public void systemStatusCheck(SystemFix systemFix){
        if(systemInFix){
            throw new BusinessException(GeneralCode.SYS_MAINTENANCE);
        }

        if(systemFix.isApi()){
            if(systemApiInFix){
                throw new BusinessException(GeneralCode.SYS_API_MAINTENANCE);
            }
        }
    }
}
