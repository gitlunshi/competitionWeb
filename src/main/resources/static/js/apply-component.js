const tabTyp = {};
//模板编写
//首页
tabTyp.homePage = {
    template:
        '<div>' +
        '  <el-button>用户首页</el-button>'+
        '</div>'
    ,
    data() {
        return {
            value:""
        }
    },
    methods: {
        onSubmit(row) {
        }

    }

};
//战队信息
tabTyp.teamInfo = {
    template:
        '<div>' +
        '  <el-button>战队信息</el-button>'+
        '</div>'
    ,
    data() {
        return {
            value:""
        }
    },
    methods: {
        onSubmit(row) {
        }

    }

};

//用户信息
tabTyp.userInfo = {
    template:
        '<div class="userInfo">' +
        '  <el-input class="user-info-input" disabled placeholder="" v-model="nickname">' +
        '    <template slot="prepend"><span class="input-important-tag">*</span>昵称</template>' +
        '  </el-input>' +
        '  <el-input class="user-info-input" placeholder="" v-model="realName">' +
        '    <template slot="prepend"><span class="input-important-tag">*</span>姓名</template>' +
        '  </el-input>' +
        '<div class="user-info-input-sex user-info-input">'+
        '    <div class="sex-title el-input-group__prepend"><span class="input-important-tag">*</span>性别</div>' +
        '<el-select v-model="sexSelect"  placeholder="请选择性别">' +
        '      <el-option label="男" value="1"></el-option>' +
        '      <el-option label="女" value="2"></el-option>' +
        '    </el-select>'+
        '    </div>'+
        '  <el-input class="user-info-input" placeholder="" v-model="nation">' +
        '    <template slot="prepend"><span class="input-important-tag">*</span>民族</template>' +
        '  </el-input>' +
        '  <el-input class="user-info-input" placeholder="" v-model="phone">' +
        '    <template slot="prepend"><span class="input-important-tag">*</span>电话</template>' +
        '  </el-input>' +
        '  <el-input type="email" class="user-info-input" placeholder="" v-model="email">' +
        '    <template slot="prepend"><span class="input-important-tag">*</span>邮箱</template>' +
        '  </el-input>' +
        '  <el-input class="user-info-input" placeholder="" v-model="statentId">' +
        '    <template slot="prepend"><span class="input-important-tag">*</span>学号</template>' +
        '  </el-input>' +
        '<div class="user-info-input-sex user-info-input">'+
        '    <div class="sex-title el-input-group__prepend"><span class="input-important-tag">*</span>学校</div>' +
        '<el-select v-model="schoolSelect"  filterable placeholder="请选择学校">' +
        '                       <el-option' +
        '                           v-for="item in schoolOptions"' +
        '                           :key="item.value"' +
        '                           :label="item.label"' +
        '                           :value="item.value">' +
        '                   </el-option>'+
        '    </el-select>'+
        '    </div>'+
        '  <el-input class="user-info-input" placeholder="" v-model="address">' +
        '    <template slot="prepend">地址</template>' +
        '  </el-input>' +
        '<div class="user-info-input-remark user-info-input">'+
        '    <div class="remark-title student-card-title"><span class="input-important-tag">*</span>学生证</div>' +
        '    <div class="student-card-image-box">' +
        '<el-upload' +
        '  class="avatar-uploader"' +
        '  action="/common/uploadResources"' +
        '  name="data"' +
        '  :data="updateFile"' +
        '  :show-file-list="false"' +
        '  :on-success="handleAvatarSuccess"' +
        '  :before-upload="beforeAvatarUpload">' +
        '  <img v-if="imageUrl" :src="imageUrl" class="avatar">' +
        '  <i v-else class="el-icon-plus avatar-uploader-icon"></i>' +
        '</el-upload>' +
        '</div>' +
        '</div>' +
        '<div class="user-info-input-remark user-info-input">'+
        '    <div class="remark-title">备注</div>' +
        '<el-input' +
        '  type="textarea"' +
        '  :autosize="{ minRows: 4, maxRows: 6}"' +
        '  placeholder="请输入内容"' +
        '    maxlength="200"' +
        '    show-word-limit' +
        '  v-model="remark">' +
        '</el-input>'+
        '</div>'+
        '  <el-button @click="modifyUserInfo" type="primary">提交修改</el-button>'+
        '</div>'
    ,
    data() {
        let userInfo = getUserInfo();
        return {
            nickname:userInfo.name,
            realName:userInfo.realName,
            statentId:userInfo.snumber,
            nation:userInfo.nation,
            phone:userInfo.phone,
            email:userInfo.email,
            address:userInfo.address,
            sexSelect:userInfo.sex,
            schoolSelect:userInfo.schoolId,
            remark:userInfo.remark,
            imageUrl:userInfo.sicImage,
            schoolOptions:school,
            updateFile:{
                fileType:'2'
            }
        }
    },
    methods: {
        handleAvatarSuccess(res, file) {
            if (res.code==200){
                this.imageUrl=res.data;
            }else{
                this.imageUrl='';
                app.$message.error(res.msg);
            }
        },
        beforeAvatarUpload(file) {
            var isJPG = (file.type === 'image/jpeg')||(file.type === 'image/png');
            const isLt2M = file.size / 1024 / 1024 < 2;
            console.log(file.type)
            if (!isJPG) {
                this.$message.error('上传的学生证图片只能是 JPG和PNG格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传的学生证图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        modifyUserInfo(){
            let userInfo={
                "snumber": this.statentId,
                "realName": this.realName,
                "sex": this.sexSelect,
                "nation": this.nation,
                "phone": this.phone,
                "email": this.email,
                "schoolId": this.schoolSelect,
                "address": this.address,
                "sicImage": this.imageUrl,
                "remark": this.remark
            };
            let b = modifyUserInfo(userInfo);
            app.$message.success("提交保存成功！！");
        }
    }

};
//作品信息
tabTyp.worksInfo = {
    template:
        '<div>' +
        '  <el-button>作品信息</el-button>'+
        '</div>'
    ,
    data() {
        return {
            value:""
        }
    },
    methods: {
        onSubmit(row) {
        }

    }

};
//账号管理
tabTyp.accountManagement = {
    template:
        '<div class="accountManagement">' +
        '  <span>修改密码</span>'+
        '  <el-input class="user-info-input" placeholder="" v-model="oldPasswprd">' +
        '    <template slot="prepend"><span class="input-important-tag">*</span>原密码</template>' +
        '  </el-input>' +
        '  <el-input class="user-info-input" placeholder="" v-model="newPasswprd">' +
        '    <template slot="prepend"><span class="input-important-tag">*</span>新密码</template>' +
        '  </el-input>' +
        '  <el-input class="user-info-input" placeholder="" v-model="conNewPasswprd">' +
        '    <template slot="prepend"><span class="input-important-tag">*</span>确认密码</template>' +
        '  </el-input>' +
        '  <el-button @click="modifyUserInfo" type="primary">提交修改</el-button>'+
        '</div>'
    ,
    data() {
        return {
            oldPasswprd:"",
            newPasswprd:"",
            conNewPasswprd:"",
        }
    },
    methods: {
        modifyUserInfo(){
            let passwordInfo={
                "snumber": this.statentId,
                "realName": this.realName,
                "sex": this.sexSelect,
            };
            let b = modifyUserInfo(userInfo);
            app.$message.success("提交保存成功！！");
        }

    }

};


var cityInfoTemplate = {
    template: '<div>' +
        '<div class="add-info-title">添加城市</div>' +
        '<el-form ref="form"  label-width="80px">' +
        '            <el-form-item label="城市名：">' +
        '                <el-input v-model="cityName"></el-input>' +
        '            </el-form-item>' +
        '            <el-form-item>' +
        '                <el-button type="primary" @click="onSubmitCity">提交</el-button>' +
        '                <el-button type="primary" @click="cancleSubmit">取消</el-button>' +
        '            </el-form-item>' +
        '        </el-form>' +
        '</div>'
    ,
    data() {
        return {
            cityName: "",
        }
    },
    methods: {
        onSubmitCity() {
            let b = addCity(this.cityName);
            if (b) {
                app.$message.success("成功！！")
                let data = getCityList(this.pageSize, this.currentPage);
                app.$emit("refreshCityTable", data)
                this.cityName = "";
                $("#info-mask-layer").css("display", "none");
            }
        },
        cancleSubmit() {
            $("#info-mask-layer").css("display", "none");
        },
    }
}
