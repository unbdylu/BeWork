<template>
    <div>
        <div>
            <el-input
                    style="width: 260px"
                    size="small"
                    placeholder="添加职称..."
                    prefix-icon="el-icon-plus"
                    v-model="jl.name">
            </el-input>
            <el-select v-model="jl.titleLevel" placeholder="职称等级" style="width:190px;margin-left: 8px"
                       size="small" >
                <el-option
                        v-for="item in titleLevels"
                        :key="item"
                        :label="item"
                        :value="item">
                </el-option>
            </el-select>
            <el-button icon="el-icon-plus" type="primary" style="margin-left: 7px" size="small" @click="addJobLevel">添加</el-button>
        </div>
        <div style="margin-top: 8px">
            <el-table
                    :data="jls"
                    size="small"
                    v-loading="loading"
                    element-loading-text="拼命加载中"
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    border
                    stripe
                    style="width: 100%"
                @selection-change="handleSelectionChange">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="id"
                        label="编号"
                        width="60px">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="职称名称"
                        width="180px">
                </el-table-column>
                <el-table-column
                        prop="titleLevel"
                        label="职称级别"
                        width="160px">
                </el-table-column><el-table-column
                        prop="createDate"
                        label="创建时间"
                        width="160px">
                </el-table-column>
                <el-table-column
                        prop="enabled"
                        label="是否可用"
                        width="130px">
                    <template slot-scope="scope">
                        <el-tag size="small" type="success" v-if="scope.row.enabled">已启用</el-tag>
                        <el-tag size="small" type="danger" v-else>未启用</el-tag>
                    </template>
                </el-table-column><el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="small" @click="showEditView(scope.row)">编辑</el-button>
                    <el-button size="small" type="danger"  icon="el-icon-delete" @click="deleteHandler(scope.row)">删除</el-button>
                </template>
            </el-table-column>
            </el-table>
            <el-button type="danger" size="small" style="margin-top: 7px" :disabled="multipleSelection.length==0"
            @click="deleteMany">批量删除</el-button>

        </div>
        <el-dialog
                title="修改职称"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <table>
                    <tr>
                        <td>
                            <el-tag>职称名</el-tag>
                        </td>
                        <td>
                            <el-input size="small" v-model="updateJl.name"></el-input>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>职称级别</el-tag>
                        </td>
                        <td>
                            <el-select v-model="updateJl.titleLevel" placeholder="职称等级" style="width:190px;margin-left: 8px"
                                       size="small" >
                                <el-option
                                        v-for="item in titleLevels"
                                        :key="item"
                                        :label="item"
                                        :value="item">
                                </el-option>
                            </el-select>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <el-tag>职称名</el-tag>
                        </td>
                        <td>
                            <el-switch  v-model="updateJl.enabled"
                                active-text="启用"
                                inactive-text="禁用"></el-switch>
                        </td>
                    </tr>
                </table>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button size="small" @click="dialogVisible = false">取 消</el-button>
    <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
  </span>
        </el-dialog>   </div>
</template>

<script>
    export default {
        name: "JoLevelMana",
        data(){
            return{
                loading:false,
                dialogVisible:false,
                updateJl:{
                    name:'',
                    titleLevel:'',
                    enabled:false,
                },
                jl:{
                    name:'',
                    titleLevel:'',
                },
                multipleSelection: [],
                jls:[],
                titleLevels:[
                    '正高级',
                    '副高级',
                    '中级',
                    '初级',
                    '员级',
                ]
            }
        },
        mounted(){
          this.initJls();
        },
        methods:{
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            deleteMany(){
                this.$confirm('此操作将永久删除【'+this.multipleSelection.length+'】条记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids='?';
                    this.multipleSelection.forEach(item=>{
                        ids += 'ids=' + item.id + '&';
                    })
                    this.deleteRequest("/system/basic/joblevel/"+ids).then(resp=>{
                        if (resp){
                            this.initJls();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },

            //upDate后也是和add一样Request =》this.
            doUpdate(){
              this.putRequest("/system/basic/joblevel/",this.updateJl).then(resp=>{
                  this.initJls();
                  this.dialogVisible=false;
              })
            },
            //和deleteHandler一样要导入data
            showEditView(data){
                Object.assign(this.updateJl, data);
                this.dialogVisible=true;
            },
            //Request紧跟着+data.id，==所以deleteHandler后要打入（data）,
            deleteHandler(data){
                this.$confirm('此操作将永久删除【'+data.name+'】职称, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                   this.deleteRequest("/system/basic/joblevel/"+data.id).then(resp=>{
                       if (resp){
                           this.initJls();
                       }
                   })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            //Request紧跟着(，this.jl），跟新也是
            addJobLevel(){
                if (this.jl.name && this.jl.titleLevel) {
                    this.postRequest("/system/basic/joblevel/", this.jl).then(resp => {
                        if (resp) {
                            this.initJls();
                        }
                    });
                } else {
                    this.$message.error("添加字段不可以为空!");
                }
            },
            //Request紧跟着不用什么
            initJls(){
                this.loading=true
                this.getRequest("/system/basic/joblevel/").then(resp=>{
                this.loading=false
                    if (resp){
                        this.jls=resp;
                        this.jl={
                            name:'',
                            titleLevel:'',
                        };
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>