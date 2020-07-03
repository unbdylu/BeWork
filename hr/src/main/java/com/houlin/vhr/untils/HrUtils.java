package com.houlin.vhr.untils;

import com.houlin.vhr.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @ClassName HrUtils
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/18
 * @Version V2.0
 **/
public class HrUtils{
    public static Hr getCurrentHr(){
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

    }
}
