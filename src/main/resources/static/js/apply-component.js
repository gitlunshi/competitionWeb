const tabTyp = {};
//模板编写
//首页
tabTyp.homePage = {
    template:
        '<div id="homePage" >' +
        '  <div style="line-height:50px;font-size:16px;height: 50px;width: 100%;background-color: #F2F2F2;color: #222222"> <div style="width: 5px;height: 100%;background-color: #417dff;margin-right: 20px;float: left"></div> 欢迎同学：<span style="color: #417dff">{{realName}}</span>！&nbsp;当前时间：<span>{{currentTime}}</span></div>' +
        '<div class="team-box team-title-box">' +
        '    <div class="label">数据统计</div>' +
        '  <div class="homePage-info-box">' +
        '<div class="homePage-info-title-box">战队名称：</div>' +
        '<div v-if="!teamName" class="homePage-info-content-box">暂未加入战队</div>' +
        '<div class="homePage-info-content-box">{{teamName}}</div>' +
        '</div>' +
        '  <div class="homePage-info-box">' +
        '<div class="homePage-info-title-box">战队成员：</div>' +
        '<div v-if="!teamMember" class="homePage-info-content-box">暂未加入战队</div>' +
        '<div class="homePage-info-content-box">{{teamMember}}</div>' +
        '</div>' +
        '  <div class="homePage-info-box">' +
        '<div class="homePage-info-title-box">战队选题：</div>' +
        '<div v-if="!teamProject" class="homePage-info-content-box">暂未加入战队</div>' +
        '<div class="homePage-info-content-box">{{teamProject}}</div>' +
        '</div>' +
        '  <div style="clear :both;"></div>' +
        '</div>' +
        '<div class="team-box team-title-box">' +
        '    <div class="label">账号信息</div>' +
        '  <el-input class="user-info-input" disabled placeholder="" v-model="userInfo.nickname">' +
        '    <template slot="prepend">用户名</template>' +
        '  </el-input>' +
        '  <el-input class="user-info-input" disabled placeholder="" v-model="userInfo.realName">' +
        '    <template slot="prepend">真实姓名</template>' +
        '  </el-input>' +
        '  <el-input class="user-info-input" disabled placeholder="" v-model="userInfo.schoolName">' +
        '    <template slot="prepend">所在学校</template>' +
        '  </el-input>' +
        '  <el-input class="user-info-input" disabled placeholder="" v-model="userInfo.address">' +
        '    <template slot="prepend">地址</template>' +
        '  </el-input>' +
        '</div>' +
        '</div>'
    ,
    data() {
        let homePage = getHomePage();
        var etime = this.formatDateTime(new Date(),'yyyy年MM月dd日 HH:mm:ss');
        setInterval(this.getCurrentTime, 1000)
        return {
            realName: homePage.user.realName,
            currentTime: etime,
            teamName: homePage.name,
            teamMember: homePage.members,
            teamProject: homePage.project,
            userInfo: {
                nickname: homePage.user.name,
                realName: homePage.user.realName,
                schoolName: homePage.user.schoolId,
                address: homePage.user.address
            }
        }
    },
    methods: {
        getCurrentTime() {
            var etime = this.formatDateTime(new Date(),'yyyy年MM月dd日 HH:mm:ss');
            this.currentTime = etime;
        },
        formatDateTime(date, format) {
            const o = {
                'M+': date.getMonth() + 1, // 月份
                'd+': date.getDate(), // 日
                'h+': date.getHours() % 12 === 0 ? 12 : date.getHours() % 12, // 小时
                'H+': date.getHours(), // 小时
                'm+': date.getMinutes(), // 分
                's+': date.getSeconds(), // 秒
                'q+': Math.floor((date.getMonth() + 3) / 3), // 季度
                S: date.getMilliseconds(), // 毫秒
                a: date.getHours() < 12 ? '上午' : '下午', // 上午/下午
                A: date.getHours() < 12 ? 'AM' : 'PM', // AM/PM
            };
            if (/(y+)/.test(format)) {
                format = format.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
            }
            for (let k in o) {
                if (new RegExp('(' + k + ')').test(format)) {
                    format = format.replace(
                        RegExp.$1,
                        RegExp.$1.length === 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length)
                    );
                }
            }
            return format;
        }
    }
};
//战队信息
tabTyp.teamInfo = {
    template:
        '<div class="userInfo" id="teamInfo">' +
        ' <div class="team-box team-title-box">' +
        '    <div class="label">战队信息</div>' +
        '<div  class="no-team-box" v-if="!teamTag">' +
        '    <div >暂为加入任何战队</div>' +
        '      <el-button type="primary" @click="creatTeam()">创建战队</el-button>' +
        '      <el-button type="primary" @click="joinTeam()">加入战队</el-button>' +
        '</div>' +
        '<div  class="has-team-box" v-if="teamTag">' +
        '  <el-input class="user-info-input" disabled placeholder="" v-model="teamID">' +
        '    <template slot="prepend">战队ID</template>' +
        '  </el-input>' +
        '  <el-input class="user-info-input" disabled placeholder="" v-model="teamName">' +
        '    <template slot="prepend">战队名称</template>' +
        '  </el-input>' +
        '  <el-input class="user-info-input" disabled placeholder="" v-model="teamPoject">' +
        '    <template slot="prepend">战队选题</template>' +
        '  </el-input>' +
        '      <el-button v-if="leadTag"type="primary"  @click="modifyTeam()" >修改战队</el-button>' +
        '<el-popconfirm' +
        '  confirm-button-text="确定"' +
        '  cancel-button-text="取消"' +
        '  icon="el-icon-info"' +
        '  icon-color="red"' +
        '  title="确定退出战队吗？"' +
        '  @confirm="outTeam"' +
        '>' +
        '      <el-button slot="reference" v-if="!leadTag" type="primary">退出战队</el-button>' +
        '</el-popconfirm>' +
        '<el-popconfirm' +
        '  confirm-button-text="确定"' +
        '  cancel-button-text="取消"' +
        '  icon="el-icon-info"' +
        '  icon-color="red"' +
        '  title="确定解散战队吗？"' +
        '  @confirm="dissolveTeam"' +
        '>' +
        '      <el-button slot="reference"  v-if="leadTag" type="primary"">解散战队</el-button>' +
        '</el-popconfirm>' +
        '</div>' +
        '</div>' +
        ' <div class="team-box team-member-box">' +
        '    <div class="label">战队成员</div>' +
        '<div class="no-team-box" v-if="!teamTag">' +
        '    <div >暂为加入任何战队</div>' +
        '</div>' +
        '<div class="has-team-box" v-if="teamTag">' +
        '        <el-table' +
        '      :data="teamMembers"' +
        '      style="width: 100%">' +
        '      <el-table-column' +
        '        prop="snumber"' +
        '        label="学号"' +
        '        width="180">' +
        '      </el-table-column>' +
        '      <el-table-column' +
        '        prop="realName"' +
        '        label="姓名"' +
        '        width="180">' +
        '      </el-table-column>' +
        '      <el-table-column' +
        '        prop="schoolId"' +
        '        label="学校">' +
        '      </el-table-column>' +
        '      <el-table-column' +
        '        label="队长"' +
        'width="180">' +
        '<template slot-scope="scope">' +
        '<span v-if="scope.row.id==capId">是</span>' +
        '      </template>' +
        '      </el-table-column>' +
        '      <el-table-column' +
        '        label="操作"' +
        'width="180">' +
        '<template slot-scope="scope">' +
        '<el-popconfirm' +
        '  confirm-button-text="确定"' +
        '  cancel-button-text="取消"' +
        '  icon="el-icon-info"' +
        '  icon-color="red"' +
        '  title="确定将此用户更换为队长吗？"' +
        '  @confirm="modifyCap(scope.row.id)"' +
        '>' +
        '<el-button slot="reference" v-if="scope.row.id!=capId&&leadTag" type="text" size="small">更换队长</el-button>' +
        '      </template>' +
        '</el-popconfirm>' +
        '      </el-table-column>' +
        '    </el-table>' +
        '</div>' +
        '</div>' +
        '</div>'
    ,
    data() {
        let teamInfo = getTeamInfo();
        if (teamInfo) {
            return {
                teamTag: true,
                leadTag: teamInfo.leadTag,
                teamID: teamInfo.id,
                teamName: teamInfo.name,
                teamPoject: teamInfo.project,
                teamPojectId: teamInfo.projectId,
                capId: teamInfo.capId,
                teamMembers: teamInfo.member,
            }
        } else {
            return {
                teamTag: false,
                leadTag: false,
                teamID: "",
                teamName: "",
                teamPoject: "",
                teamPojectId: "",
                teamMembers: [],

            }
        }

    },
    methods: {
        creatTeam() {
            app.teamOperat = 1;
            app.infoComponentValue = {
                name: 'creatTeam',
                content: creatTeam
            }
            app.infoShow = true;
        },
        joinTeam() {
            app.infoComponentValue = {
                name: 'joinTeam',
                content: joinTeam
            }
            app.infoShow = true;
        },
        dissolveTeam() {
            let b = deleteSquadronById(this.teamID);
            if (b) {
                app.$message.success("解散成功！！");
                location.reload();
            }

        },
        modifyTeam() {
            app.teamOperat = 2;
            app.teamInfo = {
                teamID: this.teamID,
                teamName: this.teamName,
                teamPojectId: this.teamPojectId,
            }
            app.infoComponentValue = {
                name: 'creatTeam',
                content: creatTeam
            }
            app.infoShow = true;
        },
        outTeam() {
            let b = outTeamByid(this.teamID);
            if (b) {
                app.$message.success("退出成功！！");
                app.infoShow = false;
                location.reload();
            }
        },
        modifyCap(id) {
            let modifyCapReq = {
                squadronId: this.teamID,
                newCapId: id
            };
            let b = modifyCapInfo(modifyCapReq);
            if (b) {
                app.$message.success("修改成功！！");
                app.infoShow = false;
                location.reload();
            }

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
        '<div class="user-info-input-sex user-info-input">' +
        '    <div class="sex-title el-input-group__prepend"><span class="input-important-tag">*</span>性别</div>' +
        '<el-select v-model="sexSelect"  placeholder="请选择性别">' +
        '      <el-option label="男" value="1"></el-option>' +
        '      <el-option label="女" value="2"></el-option>' +
        '    </el-select>' +
        '    </div>' +
        '<div class="user-info-input-sex user-info-input">' +
        '    <div class="sex-title el-input-group__prepend"><span class="input-important-tag">*</span>学历</div>' +
        '<el-select v-model="educationSelect"  placeholder="请选择学历">' +
        '      <el-option label="博士研究生" value="1"></el-option>' +
        '      <el-option label="硕士研究生" value="2"></el-option>' +
        '      <el-option label="本科" value="3"></el-option>' +
        '      <el-option label="专科" value="4"></el-option>' +
        '    </el-select>' +
        '    </div>' +
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
        '<div class="user-info-input-sex user-info-input">' +
        '    <div class="sex-title el-input-group__prepend"><span class="input-important-tag">*</span>学校</div>' +
        '<el-select v-model="schoolSelect"  filterable placeholder="请选择学校">' +
        '                       <el-option' +
        '                           v-for="item in schoolOptions"' +
        '                           :key="item.value"' +
        '                           :label="item.label"' +
        '                           :value="item.value">' +
        '                   </el-option>' +
        '    </el-select>' +
        '    </div>' +
        '  <el-input class="user-info-input" placeholder="" v-model="address">' +
        '    <template slot="prepend">地址</template>' +
        '  </el-input>' +
        '<div class="user-info-input-remark user-info-input">' +
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
        '<div class="user-info-input-remark user-info-input">' +
        '    <div class="remark-title">备注</div>' +
        '<el-input' +
        '  type="textarea"' +
        '  :autosize="{ minRows: 4, maxRows: 6}"' +
        '  placeholder="请输入内容"' +
        '    maxlength="200"' +
        '    show-word-limit' +
        '  v-model="remark">' +
        '</el-input>' +
        '</div>' +
        '  <el-button @click="modifyUserInfo" type="primary">提交修改</el-button>' +
        '</div>'
    ,
    data() {
        let userInfo = getUserInfo();
        return {
            nickname: userInfo.name,
            realName: userInfo.realName,
            statentId: userInfo.snumber,
            nation: userInfo.nation,
            phone: userInfo.phone,
            email: userInfo.email,
            address: userInfo.address,
            sexSelect: userInfo.sex,
            educationSelect: userInfo.education,
            schoolSelect: userInfo.schoolId,
            remark: userInfo.remark,
            imageUrl: userInfo.sicImage,
            schoolOptions: school,
            updateFile: {
                fileType: '2'
            }
        }
    },
    methods: {
        handleAvatarSuccess(res, file) {
            if (res.code == 200) {
                this.imageUrl = res.data;
            } else {
                this.imageUrl = '';
                app.$message.error(res.msg);
            }
        },
        beforeAvatarUpload(file) {
            var isJPG = (file.type === 'image/jpeg') || (file.type === 'image/png');
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
        modifyUserInfo() {
            if (!this.statentId || !this.realName || !this.sexSelect || !this.nation || !this.phone || !this.email || !this.schoolSelect || !this.imageUrl|| !this.educationSelect) {
                app.$message.error("请将必填项填写完整！！");
                return;
            }
            let userInfo = {
                "snumber": this.statentId,
                "realName": this.realName,
                "sex": this.sexSelect,
                "nation": this.nation,
                "phone": this.phone,
                "email": this.email,
                "education": this.educationSelect,
                "schoolId": this.schoolSelect,
                "address": this.address,
                "sicImage": this.imageUrl,
                "remark": this.remark
            };
            let b = modifyUserInfo(userInfo);
            if (b) {
                app.$message.success("提交保存成功！！");
            }
        }
    }

};
//作品信息
tabTyp.worksInfo = {
    template:
        '<div class="worksInfo">' +
        '  <el-input class="user-info-input" disabled placeholder="" v-model="teamName">' +
        '    <template slot="prepend">战队名称</template>' +
        '  </el-input>' +
        '  <el-input class="user-info-input" disabled placeholder="" v-model="teamPoject">' +
        '    <template slot="prepend">战队选题</template>' +
        '  </el-input>' +
        '<el-upload' +
        '  class="upload-demo"' +
        '  ref="upload"' +
        '  action="https://jsonplaceholder.typicode.com/posts/"' +
        '  :on-preview="handlePreview"' +
        '  :on-remove="handleRemove"' +
        '  :on-success="handleAvatarSuccess"' +
        '  :before-upload="beforeAvatarUpload"'+
        '  :file-list="fileList"' +
        '  :auto-upload="false">' +
        '  <el-button slot="trigger" size="small" type="primary">选取文件</el-button>' +
        '  <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">提交作品</el-button>' +
        '  <div slot="tip" class="el-upload__tip">只能上传zip文件，且不超过200MB。</div>' +
        '</el-upload>'+
        '</div>'
    ,
    data() {
        let teamInfo = getTeamInfo();
        return {
            teamID: teamInfo.id,
            teamName: teamInfo.name,
            teamPoject: teamInfo.project,
            fileList: []
        }
    },
    methods: {
        submitUpload() {
            this.$refs.upload.submit();
        },
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePreview(file) {
            console.log(file);
        },
        handleAvatarSuccess(res, file) {
            this.imageUrl = URL.createObjectURL(file.raw);
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'application/x-zip-compressed';
            const isLt2M = file.size / 1024 / 1024 < 200;

            if (!isJPG) {
                this.$message.error('只能上传zip格式的压缩文件!');
            }
            if (!isLt2M) {
                this.$message.error('上传的文件大小不能超过200MB!');
            }
            return isJPG && isLt2M;
        }

    }

};
//账号管理
tabTyp.accountManagement = {
    template:
        '<div class="accountManagement">' +
        '  <div style="margin-bottom: 15px">修改密码</div>' +
        '  <el-input class="user-info-input" placeholder="" v-model="oldPasswprd">' +
        '    <template slot="prepend"><span class="input-important-tag">*</span>原密码</template>' +
        '  </el-input>' +
        '  <el-input class="user-info-input" placeholder="" v-model="newPasswprd">' +
        '    <template slot="prepend"><span class="input-important-tag">*</span>新密码</template>' +
        '  </el-input>' +
        '  <el-input class="user-info-input" placeholder="" v-model="conNewPasswprd">' +
        '    <template slot="prepend"><span class="input-important-tag">*</span>确认密码</template>' +
        '  </el-input>' +
        '  <el-button @click="modifyUserInfo" type="primary">提交修改</el-button>' +
        '</div>'
    ,
    data() {
        return {
            oldPasswprd: "",
            newPasswprd: "",
            conNewPasswprd: "",
        }
    },
    methods: {
        modifyUserInfo() {
            if (!this.oldPasswprd) {
                app.$message.error("请输入老密码！！");
                return;
            }
            if (!this.newPasswprd) {
                app.$message.error("请输入新密码！！");
                return;
            }
            if (!this.conNewPasswprd) {
                app.$message.error("请输入确认密码！！");
                return;
            }
            if (this.conNewPasswprd != this.newPasswprd) {
                app.$message.error("两次新密码不一致！！");
                return;
            }
            let passwordInfo = {
                "oldPasswprd": this.oldPasswprd,
                "newPasswprd": this.newPasswprd,
            };
            let b = modifyPassword(passwordInfo);
            if (b) {
                app.$message.success("修改成功，请重新登录！！");
                //间隔性定时器
                // 每过1秒，向文本框中显示系统时间。
                setTimeout(clock(), 3000);

                function clock() {
                    location.href = "/apply";
                }
            }
        }

    }

};


var creatTeam = {
    template: '<div>' +
        '  <el-input class="user-info-input" maxlength="12" minlength="2"  placeholder="" v-model="teamName">' +
        '    <template slot="prepend"><span class="input-important-tag">*</span>战队名称</template>' +
        '  </el-input>' +
        '<div class="user-info-input-sex user-info-input">' +
        '    <div class="sex-title el-input-group__prepend"><span class="input-important-tag">*</span>战队选题</div>' +
        '<el-select v-model="teamPoject"  filterable placeholder="请选择题目">' +
        '                       <el-option' +
        '                           v-for="item in projectOptions"' +
        '                           :key="item.id"' +
        '                           :label="item.name"' +
        '                           :value="item.id">' +
        '                   </el-option>' +
        '    </el-select>' +
        '    </div>' +
        '<div style="text-align: center">' +
        '      <el-button type="primary" @click="onSubmitTeam">提交</el-button>' +
        '      <el-button type="primary" @click="cancelSubmit">取消</el-button>' +
        '</div>' +
        '</div>'
    ,
    data() {
        let projectInfoList = getProjectInfoList();
        if (app.teamOperat == 2) {
            return {
                teamID: app.teamInfo.teamID,
                teamName: app.teamInfo.teamName,
                teamPoject: app.teamInfo.teamPojectId,
                projectOptions: projectInfoList
            }
        } else {
            return {
                teamID: "",
                teamName: "",
                teamPoject: "",
                projectOptions: projectInfoList
            }
        }

    },
    methods: {
        onSubmitTeam() {
            let squadronReq = {
                id: this.teamID,
                name: this.teamName,
                projectId: this.teamPoject
            };
            if (app.teamOperat == 1) {
                let creatTeam = creatTeamPost(squadronReq);
                if (creatTeam) {
                    app.$message.success("创建成功");
                    app.infoShow = false;
                    location.reload();
                }
            } else {
                let updateSquadron1 = updateSquadron(squadronReq);
                if (updateSquadron1) {
                    app.$message.success("修改成功！！");
                    app.infoShow = false;
                    location.reload();
                }
            }
        },
        cancelSubmit() {
            app.infoShow = false;
        },
    }
}

var joinTeam = {
    template: '<div>' +
        '  <el-input class="user-info-input"  placeholder="" v-model="teamId">' +
        '    <template slot="prepend"><span class="input-important-tag">*</span>战队ID</template>' +
        '  </el-input>' +
        '<div style="text-align: center">' +
        '      <el-button type="primary" @click="onSubmitTeam">提交</el-button>' +
        '      <el-button type="primary" @click="cancelSubmit">取消</el-button>' +
        '</div>' +
        '</div>'
    ,
    data() {
        return {
            teamId: "",
        }
    },
    methods: {
        onSubmitTeam() {
            if (!this.teamId) {
                app.$message.error("战队id不能为空！！");
                return;
            }
            let b = joinTeamByid(this.teamId);
            if (b) {
                app.$message.success("修改成功！！");
                app.infoShow = false;
                location.reload();
            }
        },
        cancelSubmit() {
            app.infoShow = false;
        },
    }
}