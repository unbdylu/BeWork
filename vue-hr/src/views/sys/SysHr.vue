<template>
    <div>
        <div style="margin-top: 10px;justify-content: center;display: flex" >
            <el-input v-model="keywords" placeholder="请输入内容...." @keydown.enter.native="doSearch" prefix-icon="el-icon-search" style="width: 400px;margin-right:10px">
            </el-input>
            <el-button icon="el-icon-search" type="primary" @click="doSearch">搜索
            </el-button>
        </div>

        <div class="hr-contaniner">
            <el-card class="box-card" v-for="(hr,index) in hrs" :key="index">
                <div slot="header" class="clearfix">
                    <span>{{hr.name}}</span>
                    <el-button style="float: right; padding: 3px 0;color: #ff0022" icon="el-icon-delete" @click="deleteHr(hr)" type="text">
                    </el-button>
                </div>
                <div>
                  <div class="img-container">  <img class="usesrface-img" :src="hr.userface" :alt="hr.name" :title="hr.name"></div>
                   <div class="userinfo-container">
                       <div>用户名：{{hr.name}}</div>
                       <div>手机号码：{{hr.phone}}</div>
                       <div>电话号码 ：{{hr.telephone}}</div>
                       <div>地址：{{hr.address}}</div>
                       <div>用户状态：<el-switch
                               v-model="hr.enabled"
                               active-text="启用"
                               inactive-text="禁用"
                               @change="enabledChange(hr)"
                               active-color="#13ce66"
                               inactive-color="#ff4949">
                       </el-switch>
                       </div>
                       <div>用户角色：<el-tag type="success" style="margin-right: 5px" v-for="(role,indexj) in hr.roles" :key="indexj">
                           {{role.nameZh}}</el-tag>
                           <el-popover
                                   placement="right"
                                   title="标题"
                                   @show="showPop(hr)"
                                   @hide="hidePop(hr)"
                                   width="200"
                                   trigger="click">
                               <el-select v-model="selectedRoles" multiple placeholder="请选择">
                                   <el-option
                                           v-for="(r,indexk) in allroles"
                                           :key="indexk"
                                           :label="r.nameZh"
                                           :value="r.id">
                                   </el-option>
                               </el-select>
                               <el-button slot="reference" icon="el-icon-more" type="text"></el-button>
                           </el-popover>
                          </div>
                       <div>备注：{{}}</div>
                   </div>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
    export default {
        name: "SysHr",
        data(){
            return{
                keywords:'',
                hrs:[],
                allroles:[],
                selectedRoles:[]

            }
        },
        mounted(){
            this.initHrs();
        },
        methods:{
            deleteHr(hr){
                this.$confirm('此操作将永久删除【'+hr.name+'】用户 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/hr/"+hr.id).then(resp=>{
                        if (resp){
                            this.initHrs();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            doSearch(){
              this.initHrs();
            },
            hidePop(hr){
                let url = '/system/hr/role?hrid=' + hr.id;
                this.selectedRoles.forEach(sr => {
                    url += '&rids=' + sr;
                });
                this.putRequest(url).then(resp => {
                    if (resp) {
                        this.initHrs();
                    }
                });
            }  ,
            showPop(hr){
                this.initAllRoles();
                let roles=hr.roles;
                this.selectedRoles=[];
                roles.forEach(r=>{
                    this.selectedRoles.push(r.id);
                })
            },
            initAllRoles(){
                this.getRequest("/system/hr/roles/").then(resp=>{
                    if (resp){
                        this.allroles=resp;
                    }
                })
            },
            enabledChange(hr){
                delete hr.roles;
              this.putRequest("/system/hr/",hr).then(resp=>{
                  if (resp){
                  this.initHrs();
                  }
              })
            },
            initHrs(){
                this.getRequest("/system/hr/?keywords="+this.keywords).then(resp=>{
                    if (resp){
                    this.hrs=resp;
                    }
                })
            }
        }
    }
</script>

<style>
    .userinfo-container div{

    }
    .userinfo-container{
        margin-top: 20px;
    }
    .img-container{
        width: 100%;
        display: flex;
        justify-content: center;
    }
    .usesrface-img{
        width: 80px;
        height: 80px;
        border-radius: 80px;
    }
    .box-card{
        width: 350px;
        margin-bottom: 15px;
    }
    .hr-contaniner{
        margin-top: 10px;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-around;

    }

</style>