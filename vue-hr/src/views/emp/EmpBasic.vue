<template>
    <div>
        <div>
            <div style="display: flex;justify-content: space-between">
                <div>
                    <el-input placeholder="请输入员工名进行搜索..." style="width: 300px;margin-right: 10px"
                              prefix-icon="el-icon-search" v-model="keyword" clearable @clear="initEmps"
                              @keydown.enter.native="initEmps" :disabled="showAdvanceSearch"></el-input>
                    <el-button icon="el-icon-search" type="primary" @click="initEmps" :disabled="showAdvanceSearch">搜索
                    </el-button>
                    <el-button type="primary" @click="showAdvanceSearch=!showAdvanceSearch">
                        <i :class=" showAdvanceSearch?'fa fa-angle-double-up':'fa fa-angle-double-down'"
                           aria-hidden="true"></i>
                        高级搜索
                    </el-button>
                </div>
                <div>
                    <!--<el-button type="success">-->
                    <!--<i class="fa fa-level-up" aria-hidden="true"></i>-->
                    <!--导入数据-->
                    <!--</el-button>-->
                    <!--<el-button type="success">-->
                    <!--<i class="fa fa-level-down" aria-hidden="true"></i>-->
                    <!--导出数据-->
                    <!--</el-button>-->
                    <el-button type="primary" icon="el-icon-plus" @click="showAddEmp">添加用户</el-button>
                </div>
            </div>
            <transition name="slide-fade">
                <div v-show="showAdvanceSearch"
                     style="border: 1px solid #008cff;border-radius: 5px;box-sizing: border-box;  padding: 5px;margin: 10px ">
                    <el-row>
                        <el-col :span="5">政治面貌:
                            <el-select v-model="searchValue.politicId" placeholder="请选择" style="width: 130px"
                                       size="mini">
                                <el-option
                                        v-for="item in politicsstatus"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="4">职位：
                            <el-select v-model="searchValue.posId" placeholder="请选择" style="width: 130px" size="mini">
                                <el-option
                                        v-for="item in positions"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="4">
                            职称:
                            <el-select v-model="searchValue.jobLevelId" placeholder="请选择" style="width: 130px"
                                       size="mini">
                                <el-option
                                        v-for="item in joblevel"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="4">
                            民族:
                            <el-select v-model="searchValue.nationId" placeholder="请选择" style="width: 130px"
                                       size="mini">
                                <el-option
                                        v-for="item in nations"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="7">
                            聘用形式:
                            <el-radio-group v-model="searchValue.engageForm">
                                <el-radio label="劳务合同">劳务合同</el-radio>
                                <el-radio label="劳动合同">劳动合同</el-radio>
                            </el-radio-group>
                        </el-col>
                    </el-row>
                    <el-row style="margin-top: 4px">
                        <el-col :span="5">
                            所属部门:
                            <el-popover
                                    placement="right"
                                    title="请输入部门"
                                    width="200"
                                    size="mini"
                                    trigger="manual"
                                    v-model="popVisible">
                                <el-tree default-expand-all :data="allEmps" :props="defaultProps"
                                         @node-click="searvhViewHandleNodeClick"></el-tree>
                                <div slot="reference" style="width: 130px;height: 26px;border: 1px solid #dedede;display: inline-flex;align-items: center;
                                border-radius: 5px;padding-left: 8px;margin-top:3px; cursor: pointer;font-size: 13px;box-sizing: border-box;margin-left: 3px"
                                     @click="showDepView">{{inputDep}}
                                </div>
                            </el-popover>
                        </el-col>
                        <el-col :span="10">
                            入职时间：
                            <el-date-picker
                                    v-model="searchValue.beginDateScope"
                                    size="mini"
                                    unlink-panels
                                    type="daterange"
                                    value-format="yyyy-MM-dd"
                                    range-separator="至"
                                    start-placeholder="开始日期"
                                    end-placeholder="结束日期">
                            </el-date-picker>
                        </el-col>
                        <el-col :span="5" :offset="4">
                            <el-button  size="mini" >取消</el-button>
                            <el-button size="mini"  @click="initEmps('advanced')" icon="el-icon-search" type="primary">
                                搜索
                            </el-button>
                        </el-col>
                    </el-row>
                </div>
            </transition>
        </div>
        <div style="margin-top: 10px">
            <el-table
                    :data="emps"
                    stripe
                    border
                    v-loading="loading"
                    element-loading-text="拼命加载中"
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="name"
                        fixed
                        align="left"
                        label="姓名"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="id"
                        align="left"
                        label="工号"
                        width="80">
                </el-table-column>
                <el-table-column
                        prop="gender"
                        align="left"
                        width="80"
                        label="性别">
                </el-table-column>
                <el-table-column
                        prop="birthday"
                        align="left"
                        width="120"
                        label="出生日期">
                </el-table-column>
                <el-table-column
                        prop="idCard"
                        align="left"
                        width="180"
                        label="身份证号码">
                </el-table-column>
                <el-table-column
                        prop="wedlock"
                        align="left"
                        width="80"
                        label="婚姻状况">
                </el-table-column>
                <el-table-column
                        prop="nation.name"
                        align="left"
                        width="80"
                        label="民族">
                </el-table-column>
                <el-table-column
                        prop="nativePlace"
                        align="left"
                        width="80"
                        label="籍贯">
                </el-table-column>
                <el-table-column
                        prop="politicsstatus.name"
                        align="left"
                        width="80"
                        label="政治面貌">
                </el-table-column>
                <el-table-column
                        prop="email"
                        align="left"
                        width="160"
                        label="电子邮件">
                </el-table-column>
                <el-table-column
                        prop="phone"
                        align="left"
                        width="120"
                        label="电话号码">
                </el-table-column>
                <el-table-column
                        prop="address"
                        align="left"
                        width="200"
                        label="联系地址">
                </el-table-column>
                <el-table-column
                        prop="department.name"
                        align="left"
                        width="100"
                        label="所在部门">
                </el-table-column>
                <el-table-column
                        prop="position.name"
                        align="left"
                        width="120"
                        label="职位">
                </el-table-column>
                <el-table-column
                        prop="jobLevel.name"
                        align="left"
                        width="120"
                        label="职称">
                </el-table-column>
                <el-table-column
                        prop="engageForm"
                        align="left"
                        width="100"
                        label="聘用形式">
                </el-table-column>
                <el-table-column
                        prop="beginDate"
                        align="left"
                        width="120"
                        label="入职时间">
                </el-table-column>
                <el-table-column
                        prop="conversionTime"
                        align="left"
                        width="120"
                        label="转正时间">
                </el-table-column>
                <el-table-column
                        prop="beginContract"
                        align="left"
                        width="120"
                        label="合同起始时间">
                </el-table-column>
                <el-table-column
                        prop="endContract"
                        align="left"
                        width="120"
                        label="合同终止日期">
                </el-table-column>
                <el-table-column
                        prop="tiptopDegree"
                        align="left"
                        width="70"
                        label="最高学历">
                </el-table-column>
                <el-table-column
                        width="100"
                        align="left"
                        label="合同期限">
                    <template slot-scope="scope">
                        <el-tag>{{scope.row.contractTerm}}</el-tag>
                        年
                    </template>
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="150"
                        label="操作">
                    <template slot-scope="scope">
                        <el-button @click="showEditEmpView(scope.row)" icon="el-icon-edit" type="primary" style="padding:4px"  size="mini">编辑
                        </el-button>
                        <!--<el-button style="padding: 3px" type="primary" size="mini">查看高级资料</el-button>-->
                        <el-button @click="deleteEmpByEid(scope.row)" style="padding: 4px" type="danger" icon="el-icon-delete" size="mini">
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: flex-end">
                <el-pagination
                        background
                        @current-change="currentChange"
                        @size-change="sizeChange"
                        layout="sizes, prev, pager, next, jumper, ->, total, slot"
                        :total="total">
                </el-pagination>
            </div>
        </div>
        <el-dialog
                :title="title"
                :visible.sync="dialogVisible"
                width="80%">
            <div>
                <el-form ref="empForm" :model="emp" :rules="rules">
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="名字:" prop="name">
                                <el-input v-model="emp.name" size="mini" style="width: 150px;"
                                          prefix-icon="el-icon-edit" placeholder="请输入.."></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="性别:" prop="gender">
                                <el-radio-group v-model="emp.gender">
                                    <el-radio label="男">男</el-radio>
                                    <el-radio label="女">女</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="出生时间:" prop="birthday">
                                <el-date-picker
                                        v-model="emp.birthday"
                                        type="date"
                                        size="mini"
                                        style="width: 150px"
                                        placeholder="选择日期"
                                        format="yyyy-MM-dd">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="政治面貌:" prop="politicId">
                                <el-select v-model="emp.politicId" placeholder="请选择">
                                    <el-option
                                            v-for="item in politicsstatus"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="民族:" prop="nationId">
                                <el-select v-model="emp.nationId" placeholder="请选择" style="width: 150px">
                                    <el-option
                                            v-for="item in nations"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="籍贯:" prop="nativePlace">
                                <el-input v-model="emp.nativePlace" size="mini" style="width: 120px"
                                          placeholder="请输入..."></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="电子邮箱:" prop="email">
                                <el-input v-model="emp.email" size="mini" prefix-icon="el-icon-message"
                                          style="width: 150px"
                                          placeholder="请输入..."></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="联系地址:" prop="address" prefix-icon="el-icon-email">
                                <el-input v-model="emp.address" size="mini" style="width: 200px"
                                          placeholder="请输入..."></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="职位:" prop="posId">
                                <el-select v-model="emp.posId" placeholder="请选择" style="width: 150px">
                                    <el-option
                                            v-for="item in positions"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="职称:" prop="jobLevelId">
                                <el-select v-model="emp.jobLevelId" placeholder="请选择" style="width: 120px">
                                    <el-option
                                            v-for="item in joblevel"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="所属部门:" prop="departmentId">
                                <el-popover
                                        placement="right"
                                        title="请输入部门"
                                        width="200"
                                        trigger="manual"
                                        v-model="popVisible">
                                    <el-tree default-expand-all :data="allEmps" :props="defaultProps"
                                             @node-click="handleNodeClick"></el-tree>
                                    <div slot="reference" style="width: 150px;height: 26px;border: 1px solid #dedede;display: inline-flex;align-items: center;
                                border-radius: 5px;padding-left: 8px;cursor: pointer;font-size: 13px;box-sizing: border-box"
                                         @click="showDepView">{{inputDep}}
                                    </div>
                                </el-popover>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="电话号码:" prop="phone">
                                <el-input v-model="emp.phone" size="mini" style="width: 200px"
                                          placeholder="请输入..."></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="工号:" prop="workID">
                                <el-input v-model="emp.workID" disabled size="mini" style="width: 150px"
                                          placeholder="请输入..."></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="学历:" prop="tiptopDegree">
                                <el-select v-model="emp.tiptopDegree" placeholder="请选择" style="width: 120px">
                                    <el-option
                                            v-for="item in tiptopDegrees"
                                            :key="item"
                                            :label="item"
                                            :value="item">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="毕业院校" prop="school">
                                <el-input v-model="emp.school" placeholder="请输入.." size="mini" style="width: 150px"
                                          prefix-icon="el-icon-edit"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="专业名称:" prop="specialty">
                                <el-input v-model="emp.specialty" placeholder="请输入.." size="mini" style="width: 200px"
                                          prefix-icon="el-icon-edit"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="入职时间:" prop="beginDate">
                                <el-date-picker
                                        v-model="emp.beginDate"
                                        size="mini"
                                        type="date"
                                        style="width: 130px;"
                                        value-format="yyyy-MM-dd"
                                        placeholder="选择日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="转正时间:" prop="conversionTime">
                                <el-date-picker
                                        v-model="emp.conversionTime"
                                        size="mini"
                                        type="date"
                                        style="width: 130px;"
                                        value-format="yyyy-MM-dd"
                                        placeholder="选择日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="合同起始时间:" prop="beginContract">
                                <el-date-picker
                                        v-model="emp.beginContract"
                                        size="mini"
                                        type="date"
                                        style="width: 130px;"
                                        value-format="yyyy-MM-dd"
                                        placeholder="选择日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="合同终止时间:" prop="endContract">
                                <el-date-picker
                                        v-model="emp.endContract"
                                        size="mini"
                                        type="date"
                                        style="width: 130px;"
                                        value-format="yyyy-MM-dd"
                                        placeholder="选择日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="8">
                            <el-form-item label="身份证号码" prop="idCard">
                                <el-input v-model="emp.idCard" placeholder="请输入.." size="mini" style="width: 200px"
                                          prefix-icon="el-icon-edit"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="聘用形式:" prop="engageForm">
                                <el-radio-group v-model="emp.engageForm">
                                    <el-radio label="劳务合同">劳务合同</el-radio>
                                    <el-radio label="劳动合同">劳动合同</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="婚姻状况:" prop="wedlock">
                                <el-radio-group v-model="emp.wedlock">
                                    <el-radio label="未婚">未婚</el-radio>
                                    <el-radio label="已婚">已婚</el-radio>
                                    <el-radio label="离异">离异</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doAddEmps">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "EmpBasic",
        data() {
            return {
                searchValue: {
                    politicId: null,
                    nationId: null,
                    jobLevelId: null,
                    posId: null,
                    engageForm: null,
                    departmentId: null,
                    beginDateScope: null,
                },
                title: '增加用户',
                emps: [],
                allEmps: [],
                loading: false,
                popVisible: false,
                showAdvanceSearch: false,
                total: 0,
                size: 10,
                page: 1,
                keyword: '',
                dialogVisible: false,
                tiptopDegrees: ['本科', '大专', '硕士', '博士', '高中', '初中', '小学', '其他'],
                nations: [],
                joblevel: [],
                politicsstatus: [],
                positions: [],
                inputDep: '所属部门',
                emp: {
                    name: "javaboy",
                    gender: "男",
                    birthday: "1989-12-31",
                    idCard: "610122199001011256",
                    wedlock: "已婚",
                    nationId: 1,
                    nativePlace: "陕西",
                    politicId: 13,
                    email: "laowang@qq.com",
                    phone: "18565558897",
                    address: "深圳市南山区",
                    departmentId: null,
                    jobLevelId: 9,
                    posId: 29,
                    engageForm: "劳务合同",
                    tiptopDegree: "本科",
                    specialty: "信息管理与信息系统",
                    school: "深圳大学",
                    beginDate: "2017-12-31",

                    workID: "00000057",
                    contractTerm: 2,
                    conversionTime: "2018-03-31",
                    notworkDate: null,
                    beginContract: "2017-12-31",
                    endContract: "2019-12-31",
                    workAge: null
                },
                rules: {
                    name: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                    gender: [{required: true, message: '请输入性别', trigger: 'blur'}],
                    birthday: [{required: true, message: '请输入出生日期', trigger: 'blur'}],
                    idCard: [{required: true, message: '请输入身份证', trigger: 'blur'}, {
                        pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
                        message: '身份证号码格式不正确',
                        trigger: 'blur'
                    }],
                    wedlock: [{required: true, message: '请输入', trigger: 'blur'}],
                    nationId: [{required: true, message: '请输入籍贯', trigger: 'blur'}],
                    nativePlace: [{required: true, message: '请输入地址', trigger: 'blur'}],
                    politicId: [{required: true, message: '请输入政治面貌', trigger: 'blur'}],
                    email: [{required: true, message: '请输入邮箱', trigger: 'blur'}, {
                        type: 'email',
                        message: '邮箱格式不正确',
                        trigger: 'blur'
                    }],
                    phone: [{required: true, message: '请输入电话', trigger: 'blur'}],
                    address: [{required: true, message: '请输入地址', trigger: 'blur'}],
                    departmentId: [{required: true, message: '请输入部门', trigger: 'blur'}],
                    jobLevelId: [{required: true, message: '请输入职称', trigger: 'blur'}],
                    posId: [{required: true, message: '请输入..', trigger: 'blur'}],
                    engageForm: [{required: true, message: '请输入聘用形式', trigger: 'blur'}],
                    tiptopDegree: [{required: true, message: '请输入最高学历', trigger: 'blur'}],
                    specialty: [{required: true, message: '请输入专业', trigger: 'blur'}],
                    workState: [{required: true, message: '请输入工作状态', trigger: 'blur'}],
                    beginDate: [{required: true, message: '请输入合同起始时间', trigger: 'blur'}],
                    workID: [{required: true, message: '请输入...', trigger: 'blur'}],
                    contractTerm: [{required: true, message: '请输入...', trigger: 'blur'}],
                    conversionTime: [{required: true, message: '请输入......', trigger: 'blur'}],
                    notworkDate: [{required: true, message: '请输入...', trigger: 'blur'}],
                    beginContract: [{required: true, message: '请输入...', trigger: 'blur'}],
                    endContract: [{required: true, message: '请输入...', trigger: 'blur'}],
                    school: [{required: true, message: '请输入...', trigger: 'blur'}],
                    workAge: [{required: true, message: '请输入...', trigger: 'blur'}],
                },
                defaultProps: {
                    children: 'children',
                    label: 'name'
                }
            }
        },
        mounted() {
            this.initEmps();
            this.initData();
            this.initPositions();
        },
        methods: {
            emptyEmp() {
                this.emp = {
                    name: "",
                    gender: "",
                    birthday: "",
                    idCard: "",
                    wedlock: "",
                    nationId: null,
                    nativePlace: "",
                    politicId: null,
                    email: "",
                    phone: "",
                    address: "",
                    departmentId: null,
                    jobLevelId: null,
                    posId: null,
                    engageForm: "",
                    tiptopDegree: "",
                    specialty: "",
                    school: "",
                    beginDate: "",
                    workID: "",
                    contractTerm: null,
                    conversionTime: "",
                    notworkDate: null,
                    beginContract: "",
                    endContract: "",
                    workAge: null
                },
                    this.inputDep = ''
            },
            showEditEmpView(data) {
                this.title = "编辑员工资料"
                this.emp = data;
                // this.initPositions();
                this.inputDep = data.department.name;
                this.dialogVisible = true;
            },
            showAddEmp() {
                this.emptyEmp();
                // this.initPositions();
                this.initMaxId();
                this.dialogVisible = true;
            },
            handleNodeClick(data) {
                this.inputDep = data.name;
                this.emp.departmentId = data.id;
                this.popVisible = !this.popVisible
            },
            searvhViewHandleNodeClick(data) {
                this.inputDep = data.name;
                this.searchValue.departmentId = data.id;
                this.popVisible = !this.popVisible
            },
            showDepView() {
                this.popVisible = !this.popVisible
            },
            deleteEmpByEid(data) {
                this.$confirm('此操作将永久删除【' + data.name + '】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/employee/basic/" + data.id).then(resp => {
                        this.initEmps();
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            initMaxId() {
                this.getRequest("/employee/basic/maxid").then(resp => {
                    if (resp) {
                        this.emp.workID = resp.obj;
                    }
                })
            },
            doAddEmps() {
                if (this.emp.id) {
                    this.$refs['empForm'].validate(valid => {
                        if (valid) {
                            this.putRequest("/employee/basic/", this.emp).then(resp => {
                                if (resp) {
                                    this.dialogVisible = false;
                                    this.initEmps();
                                }
                            })
                        }
                    })
                } else {
                    this.$refs['empForm'].validate(valid => {
                        if (valid) {
                            this.postRequest("/employee/basic/", this.emp).then(resp => {
                                if (resp) {
                                    this.dialogVisible = false;
                                    this.initEmps();
                                }
                            })
                        }
                    })
                }
            },
            initPositions() {
                this.getRequest("/employee/basic/positions").then(resp => {
                    if (resp) {
                        this.positions = resp;
                    }
                })
            },
            initData() {
                if (!window.sessionStorage.getItem("nation")) {
                    this.getRequest("/employee/basic/nations").then(resp => {
                        if (resp) {
                            this.nations = resp;
                            window.sessionStorage.setItem("nations", JSON.stringify(resp))
                        }
                    })
                }
                else {
                    this.nations = JSON.parse(window.sessionStorage.getItem("nations"));
                }
                if (!window.sessionStorage.getItem("joblevel")) {
                    this.getRequest("/employee/basic/joblevel").then(resp => {
                        if (resp) {
                            this.joblevel = resp;
                            window.sessionStorage.setItem("joblevel", JSON.stringify(resp))
                        }
                    })
                }
                else {
                    this.joblevel = JSON.parse(window.sessionStorage.getItem("joblevel"));
                }
                if (!window.sessionStorage.getItem("politicsstatus")) {
                    this.getRequest("/employee/basic/politicsstatus").then(resp => {
                        if (resp) {
                            this.politicsstatus = resp;
                            window.sessionStorage.setItem("politicsstatus", JSON.stringify(resp))
                        }
                    })
                }
                else {
                    this.politicsstatus = JSON.parse(window.sessionStorage.getItem("politicsstatus"));
                }
                if (!window.sessionStorage.getItem("deps")) {
                    this.getRequest("/employee/basic/deps").then(resp => {
                        if (resp) {
                            this.allEmps = resp;
                            window.sessionStorage.setItem("deps", JSON.stringify(resp))
                        }
                    })
                }
                else {
                    this.allEmps = JSON.parse(window.sessionStorage.getItem("deps"));
                }
            },
            sizeChange(currentsize) {
                this.size = currentsize;
                this.initEmps();
            }
            ,
            currentChange(currentpage) {
                this.page = currentpage;
                this.initEmps();
            }
            ,
            initEmps(type) {
                this.loading = true;
                let url = '/employee/basic/?page=' + this.page + '&size=' + this.size;
                if (type && type == 'advanced') {
                    if (this.searchValue.politicId) {
                        url += '&politicId=' + this.searchValue.politicId;
                    }
                    if (this.searchValue.nationId) {
                        url += '&nationId=' + this.searchValue.nationId;
                    }
                    if (this.searchValue.jobLevelId) {
                        url += '&jobLevelId=' + this.searchValue.jobLevelId;
                    }
                    if (this.searchValue.posId) {
                        url += '&posId=' + this.searchValue.posId;
                    }
                    if (this.searchValue.engageForm) {
                        url += '&engageForm=' + this.searchValue.engageForm;
                    }
                    if (this.searchValue.departmentId) {
                        url += '&departmentId=' + this.searchValue.departmentId;
                    }
                    if (this.searchValue.beginDateScope) {
                        url += '&beginDateScope=' + this.searchValue.beginDateScope;
                    }
                } else {
                    url += "&name=" + this.keyword;
                }
                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.emps = resp.data;
                        this.total = resp.total;
                    }
                });
            }
        }
    }
</script>

<style>
    /* 可以设置不同的进入和离开动画 */
    /* 设置持续时间和动画函数 */
    .slide-fade-enter-active {
        transition: all .8s ease;
    }

    .slide-fade-leave-active {
        transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
    }

    .slide-fade-enter, .slide-fade-leave-to
        /* .slide-fade-leave-active for below version 2.1.8 */
    {
        transform: translateX(10px);
        opacity: 0;
    }


</style>