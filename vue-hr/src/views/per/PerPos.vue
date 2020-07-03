<template>
    <div>
        <div>
            <el-input
                    v-loading="loading"
                    element-loading-text="拼命加载中"
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    size="small"
                    class="posAddInput"
                    placeholder="添加职位..."
                    prefix-icon="el-icon-plus"
                    @keydown.enter.native="addPosition"
                    v-model="pos.name">
            </el-input>
            <el-button icon="el-icon-plus" type="primary" size="small" @click="addPosition">添加</el-button>
        </div>
        <div class="posManaMain">
            <el-table
                    :data="positions"
                    size="small"
                    @selection-change="handleSelectionChange"
                    border
                    stripe
                    style="width: 70%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="id"
                        label="编号"
                        width="60">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="职位名称"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        label="创建时间"
                        width="200">
                </el-table-column>
                <el-table-column
                        prop="enabled"
                        label="是否启用"
                        width="150">
                    <template slot-scope="scope">
                        <el-tag size="small" type="success" v-if="scope.row.enabled">已启用</el-tag>
                        <el-tag size="small" type="danger" v-else>未启用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                type="primary" icon="el-icon-edit"
                                @click="showEditView(scope.$index, scope.row)">编辑</el-button>
                        <el-button
                                size="mini"
                                type="danger" icon="el-icon-delete"
                                @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button type="danger" size="small" style="margin-top: 8px"
                       :disabled="multipleSelection.length==0" @click="deleteMany">批量删除</el-button>
        </div>
        <el-dialog
                title="修改职位"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <div>
                    <el-tag>职位名称</el-tag>
                    <el-input class="updatePosInput" size="small" v-model="updatePos.name">
                    </el-input>
                </div>
                <div>
                    <el-tag>是否启用</el-tag>
                    <el-switch
                            v-model="updatePos.enabled"
                            active-text="启用"
                            inactive-text="禁用">
                    </el-switch>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button size="small" @click="dialogVisible = false">取 消</el-button>
    <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "PosMana",
        data( ){
            return{
                loading:false,
                pos:{
                    name:''
                },
                dialogVisible:false,
                updatePos:{
                    name:'',
                    enabled:false,
                },
                multipleSelection: [],
                positions: []
            }
        },mounted(){
            this.initPositions();
        },

        methods:{
            deleteMany(){
                this.$confirm('此操作将永久删除【'+this.multipleSelection.length+'】条记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids = '?';
                    this.multipleSelection.forEach(item => {
                        ids += 'ids=' + item.id + '&';
                    })
                    this.deleteRequest("/system/basic/pos/"+ids).then(resp=>{
                        if (resp){
                            this.initPositions();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            handleSelectionChange(val){
                this.multipleSelection = val;

            },
            addPosition(){
                if (this.pos.name){
                    this.postRequest("/system/basic/pos/",this.pos).then(resp=>{
                        if (resp){
                            this.initPositions();
                            this.pos.name='';
                        }
                    })
                } else {
                    this.$message.error('职位名称不可以为空');
                }
            },
            showEditView(index,data){
                // this.updatePos=data;
                Object.assign(this.updatePos,data);
                this.dialogVisible=true
            },
            doUpdate(){
                this.putRequest("/system/basic/pos/",this.updatePos).then(resp=>{
                    if (resp){
                        this.initPositions();
                        this.updatePos.name='';
                        this.dialogVisible=false
                    }
                })
            },
            handleDelete(index,data){
                this.$confirm('此操作将永久删除【'+data.name+'】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/pos/"+data.id).then(resp=>{
                        if (resp){
                            this.initPositions();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            initPositions(){
                this.loading=true;
                this.getRequest("/system/basic/pos/").then(resp=>{
                    this.loading=false;
                    if (resp) {
                        this.positions=resp;
                    }
                })
            }
        }
    }
</script>

<style>
    .posManaMain{
        margin-top: 10px;
    }
    .posAddInput{
        width: 30%;
        margin-right: 8px
    }
    .updatePosInput{
        width: 200px;
        margin-left: 8px;
    }

</style>