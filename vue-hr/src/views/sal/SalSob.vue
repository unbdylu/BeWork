<template>
    <div>
        <div>
            <el-button size="mini" icon="el-icon-plus" type="primary" @click=ShowAddSalaryView>添加账套</el-button>
            <el-button size="mini" icon="el-icon-refresh" type="success" @click="initAllSalaries">刷新</el-button>
        </div>
        <div style="margin-top: 10px;">
            <el-table
                    v-loading="loading"
                    element-loading-text="拼命加载中"
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    :data="AllSalaries"
                    border
                    stripe
                    align="center"
                    size="mini">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="账套名称"
                        width="110">
                </el-table-column>
                <el-table-column
                        prop="basicSalary"
                        label="基本工资"
                        width="70">
                </el-table-column>
                <el-table-column
                        prop="trafficSalary"
                        label="交通补助"
                        width="70">
                </el-table-column>
                <el-table-column
                        prop="lunchSalary"
                        label="午餐补助"
                        width="70">
                </el-table-column>
                <el-table-column
                        prop="bonus"
                        label="奖金"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        label="启用时间"
                        width="120">
                </el-table-column>

                <el-table-column label="养老金" align="center">
                    <el-table-column
                            prop="pensionPer"
                            label="比率"
                            width="70">
                    </el-table-column>
                    <el-table-column
                            prop="pensionBase"
                            label="基数"
                            width="70">
                    </el-table-column>
                </el-table-column>
                <el-table-column label="医疗保险" align="center">
                    <el-table-column
                            prop="medicalPer"
                            label="比率"
                            width="70">
                    </el-table-column>
                    <el-table-column
                            prop="medicalBase"
                            label="基数"
                            width="70">
                    </el-table-column>
                </el-table-column>
                <el-table-column label="公积金" align="center">
                    <el-table-column
                            prop="accumulationFundPer"
                            label="比率"
                            width="70">
                    </el-table-column>
                    <el-table-column
                            prop="accumulationFundBase"
                            label="基数"
                            width="70">
                    </el-table-column>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope" style="display: flex;justify-self: self-start">
                        <el-button size="mini" type="primary" icon="el-icon-edit" @click="showEditSalary(scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" icon="el-icon-delete" @click="deleteSalary(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <el-dialog
                :title="diaVlogTitle"
                :visible.sync="dialogVisible"
                width="50%">
            <div style="display: flex;justify-content: space-around;align-items: center">
                <el-steps direction="vertical" :active="activeItemIndex">
                    <el-step :title="itemName" v-for="(itemName,index) in SalaryItemName"
                             :key="index"></el-step>
                </el-steps>
                <el-input v-model="salaries[title]" :placeholder="'请输入'+SalaryItemName[index]+'...'"
                          v-for="(value,title,index) in salaries"
                          :key="index" v-show="activeItemIndex==index"
                          style="width: 180px"></el-input>
            </div>
            <span slot="footer" class="dialog-footer">

    <el-button @click="cancelSalary">取消</el-button>
    <el-button @click="preStep" type="primary"> 上一步</el-button>
    <el-button type="primary" @click="nextStep">{{activeItemIndex==10?'完成':'下一步'}}</el-button>
  </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "SalSob",
        data() {
            return {
                AllSalaries: [],
                SalaryItemName: [
                    '基本工资',
                    '交通补助',
                    '午餐补助',
                    '奖金',
                    '养老金比率',
                    '养老金基数',
                    '医疗保险比率',
                    '医疗保险基数',
                    '公积金比率',
                    '公积金基数',
                    '账套名称'
                ],
                salaries: {
                    basicSalary: null,
                    trafficSalary: null,
                    lunchSalary: null,
                    bonus: null,
                    pensionPer: null,
                    pensionBase: null,
                    medicalPer: null,
                    medicalBase: null,
                    accumulationFundPer: null,
                    accumulationFundBase: 0,
                    name: ''
                },
                activeItemIndex: 0,
                dialogVisible: false,
                diaVlogTitle: "添加工资账套",
            }
        }, mounted() {
            this.initAllSalaries();
        }
        , methods: {
            showEditSalary(data) {
                this.diaVlogTitle="修改工资账套";
                this.dialogVisible = true;
                this.salaries.basicSalary=data.basicSalary
                this.salaries.trafficSalary =data.trafficSalary
                this.salaries.lunchSalary =data.lunchSalary
                this.salaries.bonus =data.bonus
                this.salaries.pensionPer =data.pensionPer
                this.salaries.pensionBase =data.pensionBase
                this.salaries.medicalPer =data.medicalPer
                this.salaries. medicalBase=data.medicalBase
                this.salaries. accumulationFundPer=data.accumulationFundPer
                this.salaries.accumulationFundBase =data.accumulationFundBase
                this.salaries.name =data.name
                this.salaries.id =data.id
            },
            deleteSalary(data) {
                this.$confirm('此操作将永久删除【' + data.name + '】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/salary/sob/" + data.id).then(resp => {
                        if (resp) {
                            this.initAllSalaries();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            cancelSalary() {
                this.dialogVisible = !this.dialogVisible;
                this.activeItemIndex = 0
            },
            preStep() {
                // if (this.activeItemIndex == 10) {
                //     this.salaries = {
                //         basicSalary: null,
                //         trafficSalary: null,
                //         lunchSalary: null,
                //         bonus: null,
                //         pensionPer: null,
                //         pensionBase: null,
                //         medicalPer: null,
                //         medicalBase: null,
                //         accumulationFundPer: null,
                //         accumulationFundBase: 0,
                //         name: ''
                //     };
                //     this.dialogVisible = false;
                //     this.activeItemIndex == 0
                //     return;
                // } else if (this.activeItemIndex == 0) {
                //     return;
                // }
                this.activeItemIndex--;
            },
            nextStep() {
                if (this.activeItemIndex == 10) {
                    if (this.salaries.id){
                        this.putRequest("/salary/sob/", this.salaries).then(resp => {
                            if (resp) {
                                this.initAllSalaries();
                                this.dialogVisible = false;
                            }
                        })
                    } else {

                    this.diaVlogTitle="添加工资账套";
                    this.postRequest("/salary/sob/", this.salaries).then(resp => {
                        if (resp) {
                            this.initAllSalaries();
                            this.dialogVisible = false;
                        }
                    })
                    }

                    return;
                }
                this.activeItemIndex++;
            },
            ShowAddSalaryView() {
                this.dialogVisible = true;
            },
            initAllSalaries() {
                    this.loading = true;
                this.getRequest("/salary/sob/").then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.AllSalaries = resp;
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>