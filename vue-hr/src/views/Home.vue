<template>
    <div>
        <el-container>
            <el-header class="homeHeader">
                <div class="title">	BeWork人员管理平台</div>
                <div align="right">
                    <el-button icon="el-icon-message-solid" type="text" style="margin-right: 8px" @click="goChat"></el-button>
   <el-dropdown class="userInfo" @command="commandHandler">
  <span class="el-dropdown-link">
    {{user.name}}<i><img :src="user.userface" alt=""/></i>
  </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
                        <el-dropdown-item command="setting">设置</el-dropdown-item>
                        <el-dropdown-item divided command="logout">注销登陆</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
                </div>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <el-menu router unique-opened>
                        <el-submenu :index="index + ''" v-for="(item,index) in routes" v-if="!item.hidden" :key="index">
                            <template slot="title">
                                <i style="color: #b300ff;margin-right: 4px" :class="item.iconCls"></i>
                                <span>
                                    {{item.name}}
                                </span>
                            </template>
                            <el-menu-item-group>
                                <el-menu-item :index="child.path" v-for="(child,indexj) in item.children" :key="indexj">{{child.name}}</el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>

                    </el-menu>
                </el-aside>
                <el-main>
                    <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path!='/home'">
                        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
                    </el-breadcrumb>
                    <div class="homeWel" v-if="this.$router.currentRoute.path=='/home'">
                        欢迎来到人事管理首页！！！
                    </div>
                    <router-view class="homeRouterView"/>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    export default {
        name: "Home",
        data(){
            return{
                user:JSON.parse(window.sessionStorage.getItem("user"))
            }
        },
    computed:{
            routes(){
                return this.$store.state.routes;
            }
    },
        methods:{
            goChat(){
                this.$router.push("/chat")
            },
            // menuClick(index){
            //     this.$router.push(index);
            //
            // },
            commandHandler(command){
                if (command == 'logout'){
                this.$confirm('此操作将注销登陆, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.getRequest("/logout");
                    window.sessionStorage.removeItem("user")
                    this.$store.commit('initRoutes',[]);
                    this.$router.replace("/")
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
                });}
            }
        }
    }
</script>

<style>
    .homeRouterView{
        margin-top: 11px;
    }

    .homeHeader {
        background-color: #ff5c43;
        color: #333;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0px 15px;
        box-sizing: border-box;
    }
    .homeHeader .title{

    }
    .homeHeader .userInfo{
        cursor: pointer;
    }
    .homeWel{
        text-align: center;
        font-size: 30px;
        font-family: 宋体;
        color: #009fff;
        padding-top: 60px;

    }

    .el-dropdown-link{
        display: flex;
        align-items: center;
    }
    .el-dropdown-link img{
        width: 48px;
        height: 48px;
        border-radius: 24px;
        margin-left: 8px;
    }



</style>