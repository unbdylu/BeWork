package com.houlin.vhr.model;

/**
 * @ClassName Meta
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/4
 * @Version V2.0
 **/
public class Meta {
    private Boolean keepAlive;

    private Boolean requireAuth;

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(Boolean requireAuth) {
        this.requireAuth = requireAuth;
    }
}
