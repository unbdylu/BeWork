<template>
    <div>
        <div>
            <el-input v-model="posInput.name" style="width: 400px;" prefix-icon="el-icon-plus"
                      placeholder="输入添加"></el-input>
            <el-button icon="el-icon-plus" type="primary" style="margin-left: 5px" @click="addPos">添加</el-button>
        </div>
        <div style="margin-top: 6px">
            <el-table
                    :data="pos"
                    stripe
                    border
                    @selection-change="handleSelectionChange"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="id"
                        label="编号"
                        width="130">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="职位名称"
                        width="130">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        label="创建时间"
                        width="150">
                </el-table-column>
                <el-table-column
                        width="130"
                        prop="address"
                        label="是否启用">
                </el-table-column>
                <el-table-column label="操作" width="150">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button size="mini" type="danger" :disabled="multipleSelection.length==0" @click="deleteByIds">批量删除
            </el-button>
            <div>
                <el-dialog
                        title="提示"
                        :visible.sync="dialogVisible"
                        width="30%"
                        :before-close="handleClose">
                    <div>
                        <el-tag>职位名称</el-tag>
                        <el-input size="mini" style="width: 150px;margin-left: 8px" v-model="updataPos.name"></el-input>
                    </div>
                    <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="updataPosition">确 定</el-button>
  </span>
                </el-dialog>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "SysCfg",
        data() {
            return {
                posInput: {
                    name: ''
                },
                updataPos: {
                    name: ''
                },
                pos: {
                    id: '',
                    name: '',
                    creatDate: '',
                    enable: false,
                },
                // Pos: [],
                dialogVisible: false,
                multipleSelection: [],
            }
        }, mounted() {
            this.initPos();
        },
        methods: {
            deleteByIds() {
                this.$confirm('此操作将永久【' + this.multipleSelection.length + '】条, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids = '?';
                    this.multipleSelection.forEach(item => {
                        ids += 'ids=' + item.id + '&';
                    })
                    this.deleteRequest("/system/cfg/" + ids).then(resp => {
                        if (resp) {
                            this.initPos();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
                console.log(this.multipleSelection)
            },
            handleEdit(index, data) {
                // this.updataPos=data;
                Object.assign(this.updataPos, data);
                this.dialogVisible = true;
            },
            handleDelete(index, data) {
                this.$confirm('此操作将永久【' + data.name + '】员工, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/cfg/" + data.id).then(resp => {
                        if (resp) {
                            this.initPos();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            updataPosition() {
                this.putRequest("/system/cfg/position/", this.updataPos).then(resp => {
                    if (resp) {
                        this.initPos();
                        this.dialogVisible = false;
                    }
                })
            },
            addPos() {
                if (this.posInput.name) {
                    this.postRequest("/system/cfg/position/", this.posInput).then(resp => {
                        if (resp) {
                            this.initPos();
                            this.posInput.name = '';
                        }
                    })
                } else {
                    this.$message.error('输入内容不能为空');
                }
            },
            initPos() {
                this.getRequest("/system/cfg/positions/").then(resp => {
                    if (resp) {
                        this.pos = resp;
                    }
                })
            },

        }
    }
</script>

<style scoped>

</style>