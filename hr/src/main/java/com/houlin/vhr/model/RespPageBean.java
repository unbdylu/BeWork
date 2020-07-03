package com.houlin.vhr.model;

import java.util.List;

/**
 * @ClassName RespPageBean
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/19
 * @Version V2.0
 **/
public class RespPageBean {
    private Long total;
    private List<?> data;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
