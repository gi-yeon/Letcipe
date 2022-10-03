<template>
  <div id="app">
    <v-app>
      <v-container class="modify-container">
        <div class="modify-title">회원수정</div>
        <div class="modify-wrap fadeInUp">
          <v-form ref="form" v-model="form">
            <div class="necessary-info">
              <div class="d-flex">
                <div class="modify-subtitle">필수정보</div>
              </div>
              <div class="d-flex flex-column">
                <div>아이디</div>
                <div class="d-flex">
                  <v-text-field 
                    v-model="id" 
                    :rules="rules.id_rule" 
                    placeholder="아이디" 
                    solo>
                  </v-text-field>
                  <v-dialog v-model="dialogId" persistent max-width="290">
                    <template #activator="{ on, attrs }">
                      <v-btn
                        :disabled="id === userid || id.length < 6 || !idcheck"
                        color="letcipe"
                        height="48px"
                        style="color: white;"
                        v-bind="attrs"
                        @click="idDupCheck(id)"
                        v-on="on"
                      >중복확인</v-btn>
                    </template>
                    <v-card v-if="idcheck === true">
                      <v-card-title class="text-h5">Caution</v-card-title>
                      <v-card-text
                        >중복되는 아이디가 있습니다. 다른 아이디를
                        입력해주세요.</v-card-text
                      >
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                          color="green darken-1"
                          text
                          @click="dialogId = false"
                          >확인</v-btn
                        >
                      </v-card-actions>
                    </v-card>
                    <v-card v-if="idcheck === false">
                      <v-card-title class="text-h5">Caution</v-card-title>
                      <v-card-text>사용가능한 아이디입니다.</v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                          color="green darken-1"
                          text
                          @click="dialogId = false"
                          >확인</v-btn
                        >
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </div>
              </div>
              <div class="d-flex flex-column">
                <div>이름</div>
                <v-text-field v-model="userNm" :rules="rules.nm_rule" placeholder="이름" solo></v-text-field>
              </div>
              <div class="d-flex flex-column">
                <div>생년월일</div>
                <v-menu
                  v-model="bdmenu"
                  :close-on-content-click="false"
                  :nudge-right="40"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
                >
                  <template #activator="{ on, attrs }">
                    <v-text-field
                      v-model="birthdate"
                      :rules="rules.bd_rule"
                      label="생년월일"
                      prepend-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      solo
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="birthdate" @input="bdmenu = false"></v-date-picker>
                </v-menu>
              </div>
              <div class="d-flex flex-column">
                <div>닉네임</div>
                <div class="d-flex">
                  <v-text-field
                    v-model="nickNm"
                    :rules="rules.nick_rule"
                    label="닉네임"
                    placeholder="Placeholder"
                    solo
                  ></v-text-field>
                  <v-dialog v-model="dialogNick" persistent max-width="290">
                    <template #activator="{ on, attrs }">
                      <v-btn
                        :disabled="nickNm.length < 2 || nickNm === nickname || !nickCheck"
                        color="letcipe"
                        height="48px"
                        style="color: white;"
                        v-bind="attrs"
                        @click="nicknameDupCheck(nickNm)"
                        v-on="on"
                      >중복확인</v-btn>
                    </template>
                    <v-card v-if="nickCheck === true">
                      <v-card-title class="text-h5">Caution</v-card-title>
                      <v-card-text
                        >중복되는 닉네임이 있습니다. 다른 닉네임을
                        입력해주세요.</v-card-text
                      >
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                          color="green darken-1"
                          text
                          @click="dialogNick = false"
                          >확인</v-btn
                        >
                      </v-card-actions>
                    </v-card>
                    <v-card v-if="nickCheck === false">
                      <v-card-title class="text-h5">Caution</v-card-title>
                      <v-card-text>사용가능한 닉네임입니다.</v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                          color="green darken-1"
                          text
                          @click="dialogNick = false"
                          >확인</v-btn
                        >
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </div>
              </div>
              <div class="d-flex flex-column">
                <div>프로필 이미지</div>
                <div class="d-flex">
                  <v-avatar size="36" @click="imglog">
                    <img :src="preview_profile" alt />
                  </v-avatar>
                  <v-file-input
                    v-model="file"
                    width="50%"
                    label="이미지 업로드"
                    outlined
                    dense
                    @change="previewFile(file)"
                  ></v-file-input>
                </div>
              </div>
              <div class="d-flex flex-column">
                <div>성별</div>
                <div class="d-flex justify-space-between mb-4">
                  <v-btn 
                    height="48px" 
                    width="47%" 
                    v-bind:color="gender === 'M'?'letcipe':'white'"
                    @click="setGenderM">
                    남
                  </v-btn>
                  <v-btn 
                    height="48px" 
                    width="47%" 
                    v-bind:color="gender === 'W'?'letcipe':'white'"
                    @click="setGenderW">
                    여
                  </v-btn>
                </div>
              </div>
              <div class="d-flex flex-column">
                <div>이메일</div>
                <div class="d-flex justify-space-between align-items-center">
                  <v-text-field v-model="email_id" placeholder="ssafy" solo></v-text-field>
                  <span>@</span>
                  <v-text-field
                    v-model="email_address"
                    placeholder="letcipe.com"
                    solo
                  >{{ email_address }}</v-text-field>
                  <v-select v-model="email_address" :items="emails" label="직접입력" solo></v-select>
                </div>
                <!-- <div>
                <v-row align="center">
                  <v-col>
                    <v-select :items="emails" label="직접입력" solo></v-select>
                  </v-col>
                </v-row>
                </div>-->
              </div>
              <div class="d-flex flex-column">
                <div>휴대전화번호</div>
                <div class="d-flex justify-space-between align-items-center">
                  <v-text-field :disabled="true" v-model="phoneRef" placeholder="010" solo>
                    {{
                    phoneRef
                    }}
                  </v-text-field>
                  <span>-</span>
                  <v-text-field :disabled="true" v-model="phoneFirst" placeholder="0000" solo>
                    {{
                    phoneFirst
                    }}
                  </v-text-field>
                  <span>-</span>
                  <v-text-field :disabled="true" v-model="phoneSecond" placeholder="0000" solo>
                    {{
                    phoneSecond
                    }}
                  </v-text-field>
                </div>
              </div>
            </div>
            <v-divider></v-divider>
            <div class="option-info pt-5">
              <div class="d-flex">
                <div class="modify-subtitle">선택정보</div>
              </div>
              <div class="d-flex flex-column">
                <div>직업</div>
                <v-select v-model="job" :items="jobs" label="직업" solo></v-select>
              </div>
              <div class="d-flex flex-column">
                <div>가구원 수</div>
                <v-select v-model="familymember" :items="famCnt" label="가구원 수" solo></v-select>
              </div>
            </div>
          </v-form>
          <v-card-actions>
            <v-btn text @click="init()">초기화</v-btn>
            <v-spacer></v-spacer>
            <v-dialog v-model="dialogModify" persistent max-width="290">
              <template #activator="{ on, attrs }">
                <v-btn
                  :disabled="!((gender!==userGender)||(!idcheck||id!==userid)||(!nickCheck||nickNm!==nickname)||(userNm&&userNm!==name)||(profileImage!==preview_profile)||(birth!==birthdate)||(email_id&&email_id!==real_eid)||(email_address&&email_address!==real_eaddr)||(job!==jobMap[userJob])||(familymember!==realFam))"
                  :loading="isLoading"
                  class="white--text"
                  color="letcipe"
                  depressed
                  v-bind="attrs"
                  @click="modify()"
                  v-on="on"
                >수정</v-btn>
              </template>
              <v-card>
                <v-card-title class="text-h5">Congratulations!&#127930;</v-card-title>
                <v-card-text>성공적으로 수정되었습니다!</v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                    color="green darken-1"
                    text
                    @click="[dialogModify = false]"
                  >확인</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-card-actions>
          <v-divider></v-divider>
          <div class="option-info pt-5">
              <div class="d-flex">
                <div class="modify-subtitle">비밀번호</div>
              </div>
              <div class="d-flex flex-column">
                <div>현재 비밀번호</div>
                <v-text-field
                  v-model="oldPw"
                  :append-icon="showPW ? 'mdi-eye' : 'mdi-eye-off'"
                  :type="showPW ? 'text' : 'password'"
                  placeholder="현재 비밀번호"
                  solo
                  @click:append="showPW = !showPW"
                ></v-text-field>
                <div>새 비밀번호</div>
                <v-text-field
                  v-model="pw"
                  :rules="rules.pw_rule"
                  :append-icon="showPW ? 'mdi-eye' : 'mdi-eye-off'"
                  :type="showPW ? 'text' : 'password'"
                  placeholder="새 비밀번호"
                  solo
                  @click:append="showPW = !showPW"
                ></v-text-field>
                <div>새 비밀번호 확인</div>
                <v-text-field
                  v-model="pwck"
                  :rules="rules.pwck_rule"
                  :append-icon="showPWCK ? 'mdi-eye' : 'mdi-eye-off'"
                  :type="showPWCK ? 'text' : 'password'"
                  placeholder="새 비밀번호 확인"
                  solo
                  @click:append="showPWCK = !showPWCK"
                ></v-text-field>
                <v-dialog v-model="dialogPwChange" persistent max-width="290">
                  <template #activator="{ on, attrs }">
                    <v-btn
                      :disabled="pw === oldpw || !pw || pw.length < 8 || pw.length > 20 || !/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-])/.test(pw) || !pwck || pw !== pwck"
                      color="letcipe"
                      height="48px"
                      style="color: white;"
                      v-bind="attrs"
                      @click="updatePassword()"
                      v-on="on"
                    >비밀번호변경</v-btn>
                  </template>
                  <v-card v-if="checkOldPw===true">
                    <v-card-title class="text-h5">Caution</v-card-title>
                    <v-card-text>현재 비밀번호가 맞지 않습니다.</v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn color="green darken-1" text @click="dialogPwChange = false">확인</v-btn>
                    </v-card-actions>
                  </v-card>
                  <v-card v-if="checkOldPw===false">
                    <v-card-title class="text-h5">Caution</v-card-title>
                    <v-card-text>비밀번호가 정상적으로 변경되었습니다.</v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn color="green darken-1" text @click="dialogPwChange = false">확인</v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </div>
            </div>
        </div>
      </v-container>
    </v-app>
  </div>
</template>

<script>
import { mapActions, mapState, mapMutations } from 'vuex'
export default {
  name: 'ModifyPage',
  data() {
    return {
      id:'',
      oldPw: '',
      pw: '',
      pwck: '',
      showPW: false,
      showPWCK: false,
      userNm: '',
      nickNm: '',
      birthdate: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
        .toISOString()
        .substr(0, 10),
      modal: false,
      bdmenu: false,
      email_id: '',
      email_address: undefined,
      real_eid:'',
      real_eaddr:'',
      form: false,
      phoneRef: undefined,
      phoneFirst: undefined,
      phoneSecond: undefined,
      validNum: undefined,
      postalcode: null,
      mainAddress: '',
      detailsAddress: '',
      familymember: '선택',
      isLoading: false,
      rules: {
        id_rule: [
          (v) => !!v || '아이디는 필수 입력사항입니다.',
          (v) =>
            /^[a-zA-Z0-9]*$/.test(v) || '아이디는 영문+숫자만 입력 가능합니다.',
          (v) =>
            !(v && v.length >= 30) || '아이디는 30자 이상 입력할 수 없습니다.',
          (v) =>
            !(v && v.length <= 5) || '아이디는 5자 이상으로 이루어져야 합니다.',
        ],
        pw_rule: [
          (v) => !!v || '비밀번호는 필수 입력사항입니다.',
          (v) => !(v && v.length < 8) || '패스워드는 8자 이상이어야합니다.',
          (v) =>
            !(v && v.length >= 30) ||
            '패스워드는 20자 이상 입력할 수 없습니다.',
          (v) =>
            /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-])/.test(
              v
            ) || '패스워드는 영문, 숫자, 특수문자를 모두 포함해야합니다.',
        ],
        pwck_rule: [
          (v) => !!v || '비밀번호는 필수 입력사항입니다.',
          (v) => v === this.pw || '비밀번호가 일치하지 않습니다.',
        ],
        nick_rule: [
          (v) => !!v || '닉네임은 필수 입력사항입니다.',
          (v) => !(v && v.length < 2) || '닉네임은 2자 이상이어야합니다.',
          (v) =>
            !(v && v.length >= 20) || '닉네임은 20자 이상 입력할 수 없습니다.',
        ],
        nm_rule: [(v) => !!v || '이름은 필수 입력사항입니다.'],

        bd_rule: [(v) => !!v || '생년월일은 필수 입력사항입니다.'],
      },
      emails: [
        '직접입력',
        'naver.com',
        'gmail.com',
        'daum.net',
        'hotmail.com',
        'outlook.com',
      ],
      job:'',
      jobs: ['주부', '학생', '직장인', '요식업 종사자'],
      jobsMap: {
        '주부': 'JUBU',
        '학생': 'STUDENT',
        '직장인': 'WORKER',
        '요식업 종사자': 'COOK'
      },
      jobMap: {
        'JUBU': '주부',
        'STUDENT': '학생',
        'WORKER': '직장인',
        'COOK': '요식업 종사자'
      },
      famCnt: ['선택',1, 2, 3, 4, '5인 이상'],
      realFam: '',
      preview_profile: null,
      dialogId: false,
      checkId: true,
      dialogNick: false,
      checkNick: true,
      dialogCode: false,
      checkCode: true,
      dialogCode2: false,
      checkValidNum: true,
      dialogModify: false,
      dialogPwChange: false,
      checkOldPw: true,
      changeProfile: false,
      gender: '',
    }
  },
  watch: {
    id(newValue, oldValue) {
      this.changeIdCheck()
    },
    nickNm(newValue, oldValue) {
      this.changeNickCheck()
    }
  },  
  computed: {
    ...mapState('user', ['userid', 'birth', 'email', 'family', 'name', 'phone', 'nickname', 'profileImage', 'userGender', 'userJob', 'userJoinCheck', 'idcheck', 'nickCheck', 'codeCheck']),
  },
  created() {
    this.init()
  },
  methods: {
    ...mapMutations('user', ['SET_IDCHECK_TRUE', 'SET_NICKCHECK_TRUE', 'SET_CODECHECK_FALSE', 'SET_USERJOINCHECK_FALSE', 'SET_CODE']),
    ...mapActions('user', ['changeNickCheck', 'modifyPassword', 'readUser', 'modifyMember', 'resetStatus', 'idCheckReset', 'idCheck', 'nicknameCheck', 'signup', 'createCode', 'checkCodeEq']),
    init(){
      this.id = this.userid
      this.nickNm = this.nickname
      this.userNm = this.name
      this.birthdate = this.birth
      this.preview_profile = this.profileImage

      const emailStr = this.email.split('@')
      this.email_id = emailStr[0]
      this.real_eid = emailStr[0]
      this.email_address = emailStr[1]
      this.real_eaddr = emailStr[1]

      this.phoneRef = this.phone.substring(0,3)
      this.phoneFirst = this.phone.substring(3,7)
      this.phoneSecond = this.phone.substring(7, this.phone.size)

      this.job = this.jobMap[this.userJob]
      if(this.family){
        if(this.family === 5) {
          this.familymember = "5인 이상"
          this.realFam = "5인 이상"
        } else {
          this.realFam = this.family
          this.familymember = this.family
        }
      }

      this.gender = this.userGender
    },
    moveMain() {
      this.$router.push('/main')
    },
    moveMypage() {
      this.$router.push('/user/mypage')
    },
    clearForm() {
      this.$refs.form.reset()
    },
    imglog() {
      console.log(this.profile_img)
    },
    previewFile(file) {
      if (file) {
        const fileData = (data) => {
          this.preview_profile = data
        }
        this.fileInfo = file
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.addEventListener(
          'load',
          function () {
            fileData(reader.result)
          },
          false
        )
        this.changeProfile = true
      } else if (file === null) {
        this.fileInfo = null
        this.preview_profile = '/banner/no-image.png'
      } else {
        file = this.fileInfo
        const fileData = (data) => {
          this.preview_profile = data
        }
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.addEventListener(
          'load',
          function () {
            fileData(reader.result)
          },
          false
        )
      }
    },
    idDupCheck(id) {
      this.idCheck(id)
    },
    nicknameDupCheck(nickNm) {
      this.nicknameCheck(nickNm)
    },
    async modify() {
      this.familymember = (this.familymember === '5인 이상')? 5: this.familymember
      if(this.familymember === '선택') {
        this.familymember = undefined
      }
      const job = (this.job !== '직업')? this.jobsMap[this.job]:undefined
      const formData = new FormData();
      formData.append('name',this.userNm)
      formData.append('userId',this.id)
      formData.append('email',this.email_id + "@" + this.email_address)
      formData.append('nickname',this.nickNm)
      formData.append('birth',this.birthdate)
      formData.append('gender',this.gender)
      if(job) {
        formData.append('job', job)
      }
      if(this.familymember){
        formData.append('family', this.familymember)
      }
      if(this.changeProfile){
        formData.append('profileImg',this.file)
      }

      await this.modifyMember(formData)
      await this.readUser()      
    },
    updatePassword() {
      this.modifyPassword({
        password: this.oldPw,
        newPassword: this.pw
      })
    },
    setGenderM(){
      this.gender = 'M'
    },
    setGenderW(){
      this.gender = 'W'
    },
    changeIdCheck(){
      console.log(this.idCheck)
      this.idCheckReset()
    }
  },
}
</script>

<style scoped>
.modify-container {
  height: 100%;
  width: 60%;
  display: flex;
  flex-direction: column;
  align-items: center;
  /* background-image: url('/bg/bg_img.png');
  background-repeat: repeat; */
  /* padding-top: 70px;
  padding-bottom: 70px; */
}
.modify-title {
  font-size: xx-large;
  color: black;
  text-align: center;
  padding-top: 5%;
  /* padding-bottom: 15%; */
}
.modify-subtitle {
  font-size: x-large;
}

.modify-wrap {
  width: 100%;
  /* padding-bottom: 70px; */
}

.modify-input > .v-input__control {
  min-height: 36px !important;
}

@media (max-width: 415px) {
  .modify-container {
    height: 100%;
    width: 100vw;
    display: flex;
    flex-direction: column;
    align-items: center;
    /* background-image: url('/bg/bg_img.png');
  background-repeat: repeat; */
    /* padding-top: 70px;
  padding-bottom: 70px; */
  }
  .modify-title {
    font-size: xx-large;
    color: black;
    text-align: center;
    padding-top: 5%;
    /* padding-bottom: 15%; */
  }
  .modify-subtitle {
    font-size: x-large;
  }

  .modify-wrap {
    width: 100%;
    /* padding-bottom: 70px; */
  }

  .modify-input > .v-input__control {
    min-height: 36px !important;
  }
}

@media (min-width: 900px) {
  .modify-container {
    height: 100%;
    width: 30%;
    display: flex;
    flex-direction: column;
    align-items: center;
    /* background-image: url('/bg/bg_img.png');
  background-repeat: repeat; */
    /* padding-top: 70px;
  padding-bottom: 70px; */
  }
  .modify-title {
    font-size: xx-large;
    color: black;
    text-align: center;
    padding-top: 5%;
    /* padding-bottom: 15%; */
  }
  .modify-subtitle {
    font-size: x-large;
  }

  .modify-wrap {
    width: 100%;
    /* padding-bottom: 70px; */
  }

  .modify-input > .v-input__control {
    min-height: 36px !important;
  }
}
.fadeInUp {
  animation: fadeInUp 1s ease backwards;
}
@keyframes fadeInUp {
  0% {
    transform: translate(0px, 100px);
    opacity: 0;
  }
  100% {
    transform: translate(0px, 0);
    opacity: 1;
  }
}
</style>