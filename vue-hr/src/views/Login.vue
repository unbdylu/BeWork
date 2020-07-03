<template>
    <div class="login">
        <div class="login-box">

            <div class="login-head">
                <img src="../assets/V.jpeg" alt="">
            </div>

            <el-form :model="loginForm"
                     v-loading="loading"
                     element-loading-text="正在登陆中...."
                     element-loading-spinner="el-icon-loading"
                     element-loading-background="rgba(0, 0, 0, 0.8)"
                     :rules="loginRules" ref="loginForm" class="login-form">
                <h3 class="loginTitle">BeWork人员管理平台登陆</h3>
                <el-form-item prop="username">
                    <el-input prefix-icon="iconfont icon-user"
                              placeholder="请输入工号"
                              v-model="loginForm.username"></el-input>
                </el-form-item>

                <el-form-item prop="password">
                    <el-input prefix-icon="iconfont icon-3702mima"
                              placeholder="请输入密码"
                              v-model="loginForm.password"
                              type="password"></el-input>
                </el-form-item>

                <el-form-item class="btns">
                    <el-button type="primary" @click="submitLogin">登录</el-button>
                    <el-button type="info" @click="restLogin">重置</el-button>
                </el-form-item>

            </el-form>
        </div>
    </div>
</template>


<script>

    export default {
        name: "Login",
        data() {
            return {
                // note: {
                //     backgroundImage: "url(" + require("../assets/logo.png") + ")",
                //     // backgroundRepeat: "no-repeat",
                    // backgroundSize: "25px auto",
                    // marginTop: "5px",
                // },
                loading: false,
                loginForm: {
                    username: 'admin',
                    password: '123'

                },
                // checked: true,
                rules: {
                    username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'}],
                }
            }
        },
        methods: {
            restLogin(){
                this.loginForm= {
                    username: '',
                    password: ''}
            },
            submitLogin() {
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                        this.loading = true;
                        this.postKeyValueRequest('/doLogin', this.loginForm).then(resp => {
                            this.loading = false;
                            if (resp)
                                this.$store.commit('INIT_CURRENTHR', resp.obj);
                            window.sessionStorage.setItem("user", JSON.stringify(resp.obj))
                            let path = this.$route.query.redirect;
                            this.$router.replace((path == '/' || path == undefined) ? '/home' : path);
                        })
                    } else {
                        console.log('error submit!!');
                        this.$message.error('错了哦，请输入字段');
                        return false;
                    }
                });
            }
        }
    }
</script>


<style lang="less" scoped>

    .loginTitle {
        margin: 20px 20px 15px 15px;
        text-align: center;
        color: black;
    }
    .login {
        background-color: #156b56 !important;

        height: 100%;
    }
    .login-box {
        height: 300px;
        width: 450px;
        background-color: #fff;
        border-radius: 5px;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
    }
    .login-head {
        width: 130px;
        height: 130px;
        border-radius: 50%;
        border: 1px solid #ddd;
        padding: 10px;
        box-shadow: 0 0 10px #ddd; /*线条阴影*/
        position: absolute;
        left: 50%;
        transform: translate(-50%,-50%);
        background-color: #eee;
        > img {
            width: 100%;
            height: 100%;
            border-radius: 50%;
        }
    }
    .login-form {
        position: absolute;
        bottom: 0;
        width: 100%;
        padding: 0 20px;
        box-sizing: border-box;
    }
    .btns {
        display: flex;
        justify-content: flex-end;
    }
</style>
