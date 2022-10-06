<template>
  <div id="app">
    <v-app>
      <v-container class="signup-container">
        <div class="signup-title">회원가입</div>
        <div class="signup-wrap fadeInUp">
          <v-form ref="form" v-model="form">
            <div class="necessary-info">
              <div class="d-flex">
                <div class="signup-subtitle">필수정보</div>
              </div>
              <div class="d-flex flex-column">
                <div>아이디</div>
                <div class="d-flex">
                  <v-text-field
                    v-model="id"
                    :disabled="!idcheck"
                    :rules="rules.id_rule"
                    placeholder="아이디"
                    solo
                  ></v-text-field>
                  <v-dialog v-model="dialogId" persistent max-width="290">
                    <template #activator="{ on, attrs }">
                      <v-btn
                        :disabled="id.length < 6 || !idcheck"
                        color="letcipe"
                        height="48px"
                        style="color: white"
                        v-bind="attrs"
                        @click="idDupCheck(id)"
                        v-on="on"
                      >중복확인</v-btn>
                    </template>
                    <v-card v-if="idcheck === true">
                      <v-card-title class="text-h5">Caution</v-card-title>
                      <v-card-text>
                        중복되는 아이디가 있습니다. 다른 아이디를
                        입력해주세요.
                      </v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="green darken-1" text @click="dialogId = false">확인</v-btn>
                      </v-card-actions>
                    </v-card>
                    <v-card v-if="idcheck === false">
                      <v-card-title class="text-h5">Caution</v-card-title>
                      <v-card-text>사용가능한 아이디입니다.</v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="green darken-1" text @click="dialogId = false">확인</v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </div>
              </div>
              <div class="d-flex flex-column">
                <div>비밀번호</div>
                <v-text-field
                  v-model="pw"
                  :rules="rules.pw_rule"
                  :append-icon="showPW ? 'mdi-eye' : 'mdi-eye-off'"
                  :type="showPW ? 'text' : 'password'"
                  placeholder="비밀번호"
                  solo
                  @click:append="showPW = !showPW"
                ></v-text-field>
                <div>비밀번호 확인</div>
                <v-text-field
                  v-model="pwck"
                  :rules="rules.pwck_rule"
                  :append-icon="showPWCK ? 'mdi-eye' : 'mdi-eye-off'"
                  :type="showPWCK ? 'text' : 'password'"
                  placeholder="비밀번호 확인"
                  solo
                  @click:append="showPWCK = !showPWCK"
                ></v-text-field>
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
                    :disabled="!nickCheck"
                    :rules="rules.nick_rule"
                    label="닉네임"
                    placeholder="Placeholder"
                    solo
                  ></v-text-field>
                  <v-dialog v-model="dialogNick" persistent max-width="290">
                    <template #activator="{ on, attrs }">
                      <v-btn
                        :disabled="nickNm.length < 2 || !nickCheck"
                        color="letcipe"
                        height="48px"
                        style="color: white"
                        v-bind="attrs"
                        @click="nicknameDupCheck(nickNm)"
                        v-on="on"
                      >중복확인</v-btn>
                    </template>
                    <v-card v-if="nickCheck === true">
                      <v-card-title class="text-h5">Caution</v-card-title>
                      <v-card-text>
                        중복되는 닉네임이 있습니다. 다른 닉네임을
                        입력해주세요.
                      </v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="green darken-1" text @click="dialogNick = false">확인</v-btn>
                      </v-card-actions>
                    </v-card>
                    <v-card v-if="nickCheck === false">
                      <v-card-title class="text-h5">Caution</v-card-title>
                      <v-card-text>사용가능한 닉네임입니다.</v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="green darken-1" text @click="dialogNick = false">확인</v-btn>
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
                    :color="gender === 'M'?'letcipe':'white'"
                    @click="setGenderM"
                  >남</v-btn>
                  <v-btn
                    height="48px"
                    width="47%"
                    :color="gender === 'F'?'letcipe':'white'"
                    @click="setGenderW"
                  >여</v-btn>
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
                  <v-text-field
                    v-model="phoneRef"
                    :disabled="codeCheck"
                    placeholder="010"
                    solo
                  >{{ phoneRef }}</v-text-field>
                  <span>-</span>
                  <v-text-field
                    v-model="phoneFirst"
                    :disabled="codeCheck"
                    placeholder="0000"
                    solo
                  >{{ phoneFirst }}</v-text-field>
                  <span>-</span>
                  <v-text-field
                    v-model="phoneSecond"
                    :disabled="codeCheck"
                    placeholder="0000"
                    solo
                  >{{ phoneSecond }}</v-text-field>
                  <v-dialog v-model="dialogCode" persistent max-width="290">
                    <template #activator="{ on, attrs }">
                      <v-btn
                        :disabled="codeCheck"
                        color="letcipe"
                        class="pl-2"
                        height="48px"
                        style="color: white"
                        v-bind="attrs"
                        @click="setCode(phoneRef, phoneFirst, phoneSecond)"
                        v-on="on"
                      >인증</v-btn>
                    </template>
                    <v-card>
                      <v-card-title class="text-h5">Caution</v-card-title>
                      <v-card-text>인증번호가 발송되었습니다.</v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="green darken-1" text @click="dialogCode = false">확인</v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </div>
              </div>
              <div class="d-flex">
                <v-text-field
                  v-model="validNum"
                  :disabled="codeCheck"
                  placeholder="SK123LDk"
                  solo
                >{{ validNum }}</v-text-field>
                <v-dialog v-model="dialogCode2" persistent max-width="290">
                  <template #activator="{ on, attrs }">
                    <v-btn
                      :disabled="codeCheck"
                      color="letcipe"
                      height="48px"
                      style="color: white"
                      v-bind="attrs"
                      @click="varification(validNum)"
                      v-on="on"
                    >확인</v-btn>
                  </template>
                  <v-card v-if="codeCheck === true">
                    <v-card-title class="text-h5">Caution</v-card-title>
                    <v-card-text>인증되었습니다.</v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn color="green darken-1" text @click="dialogCode2 = false">확인</v-btn>
                    </v-card-actions>
                  </v-card>
                  <v-card v-if="codeCheck === false">
                    <v-card-title class="text-h5">Caution</v-card-title>
                    <v-card-text>잘못된 코드입니다. 다시 입력해주십시오</v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn color="green darken-1" text @click="dialogCode2 = false">확인</v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </div>
            </div>
            <v-divider></v-divider>
            <div class="option-info pt-5">
              <div class="d-flex">
                <div class="signup-subtitle">선택정보</div>
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
            <v-btn text @click="clearForm">지우기</v-btn>
            <v-spacer></v-spacer>
            <v-dialog v-model="dialogSignup" persistent max-width="290">
              <template #activator="{ on, attrs }">
                <v-btn
                  :disabled="!(gender&&(!idcheck)&&(!nickCheck)&&codeCheck&&userNm&&(pw===pwck)&&email_id&&email_address)"
                  :loading="isLoading"
                  class="white--text"
                  color="letcipe"
                  depressed
                  v-bind="attrs"
                  @click="userJoin()"
                  v-on="on"
                >가입</v-btn>
              </template>
              <v-card v-if="userJoinCheck === true">
                <v-card-title class="text-h5">Congratulations!&#127930;</v-card-title>
                <v-card-text>성공적으로 가입되었습니다!</v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="green darken-1" text @click="signupSuccess()">확인</v-btn>
                </v-card-actions>
              </v-card>
              <v-card v-if="userJoinCheck === false">
                <v-card-title class="text-h5">Congratulations!&#127930;</v-card-title>
                <v-card-text>다시 시도해주십시오</v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="green darken-1" text @click="resetStat()">확인</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-card-actions>
        </div>
      </v-container>
    </v-app>
  </div>
</template>

<script>
import { mapActions, mapState, mapMutations } from 'vuex'
export default {
  name: 'SignupPage',
  data() {
    return {
      id: '',
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
      form: false,
      phone: undefined,
      phoneRef: undefined,
      phoneFirst: undefined,
      phoneSecond: undefined,
      validNum: undefined,
      postalcode: null,
      mainAddress: '',
      detailsAddress: '',
      job: '직업',
      familymember: '선택',
      isLoading: false,
      file: '',
      gender: '',
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
      jobs: ['직업', '주부', '학생', '직장인', '요식업 종사자'],
      jobsMap: {
        주부: 'JUBU',
        학생: 'STUDENT',
        직장인: 'WORKER',
        '요식업 종사자': 'COOK',
      },
      famCnt: ['선택', 1, 2, 3, 4, '5인 이상'],
      preview_profile: null,
      dialogId: false,
      dialogNick: false,
      dialogCode: false,
      checkCode: true,
      dialogCode2: false,
      checkValidNum: true,
      dialogSignup: false,
    }
  },
  computed: {
    ...mapState('user', ['userJoinCheck', 'idcheck', 'nickCheck', 'codeCheck']),
  },
  created() {
    this.SET_IDCHECK_TRUE()
    this.SET_NICKCHECK_TRUE()
    this.SET_CODECHECK_FALSE()
    this.SET_CODE('')
    this.SET_USERJOINCHECK_FALSE()
  },
  methods: {
    ...mapMutations('user', [
      'SET_IDCHECK_TRUE',
      'SET_NICKCHECK_TRUE',
      'SET_CODECHECK_FALSE',
      'SET_USERJOINCHECK_FALSE',
      'SET_CODE',
    ]),
    ...mapActions('user', [
      'resetStatus',
      'idCheckReset',
      'idCheck',
      'nicknameCheck',
      'signup',
      'createCode',
      'checkCodeEq',
    ]),
    clearForm() {
      this.$refs.form.reset()
    },
    imglog() {
      console.log(this.profile_img)
    },
    previewFile(file) {
      console.log(file)
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
    setCode(phoneRef, phoneFirst, phoneSecond) {
      const phone = phoneRef + phoneFirst + phoneSecond
      this.createCode(phone)
    },
    varification(validNum) {
      this.checkCodeEq(validNum)
    },
    userJoin() {
      this.familymember =
        this.familymember === '5인 이상' ? 5 : this.familymember
      if (this.familymember === '선택') {
        this.familymember = undefined
      }
      const job = this.job !== '직업' ? this.jobsMap[this.job] : undefined
      const formData = new FormData()
      formData.append('name', this.userNm)
      formData.append('userId', this.id)
      formData.append('password', this.pw)
      formData.append('email', this.email_id + '@' + this.email_address)
      formData.append('nickname', this.nickNm)
      formData.append(
        'phone',
        this.phoneRef + this.phoneFirst + this.phoneSecond
      )
      formData.append('birth', this.birthdate)
      formData.append('gender', this.gender)
      if (job) {
        formData.append('job', job)
      }
      if (this.familymember) {
        formData.append('family', this.familymember)
      }
      formData.append('profileImg', this.file)
      this.signup(formData)
    },
    setGenderM() {
      this.gender = 'M'
    },
    setGenderW() {
      this.gender = 'F'
    },
    signupSuccess() {
      this.resetStat()
      this.$router.push('/main')
    },
    resetStat() {
      this.resetStatus()
      this.dialogSignup = false
    },
  },
}
</script>

<style scoped>
/* #app {
  background-image: url('/bg/bg_img.png');
  background-repeat: repeat;
} */
.signup-container {
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
.signup-title {
  font-size: xx-large;
  color: black;
  text-align: center;
  padding-top: 5%;
  /* padding-bottom: 15%; */
}
.signup-subtitle {
  font-size: x-large;
}

.signup-wrap {
  width: 100%;
  /* padding-bottom: 70px; */
}

.signup-input > .v-input__control {
  min-height: 36px !important;
}

@media (max-width: 415px) {
  .signup-container {
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
  .signup-title {
    font-size: xx-large;
    color: black;
    text-align: center;
    padding-top: 5%;
    /* padding-bottom: 15%; */
  }
  .signup-subtitle {
    font-size: x-large;
  }

  .signup-wrap {
    width: 100%;
    /* padding-bottom: 70px; */
  }

  .signup-input > .v-input__control {
    min-height: 36px !important;
  }
}

@media (min-width: 900px) {
  .signup-container {
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
  .signup-title {
    font-size: xx-large;
    color: black;
    text-align: center;
    padding-top: 5%;
    /* padding-bottom: 15%; */
  }
  .signup-subtitle {
    font-size: x-large;
  }

  .signup-wrap {
    width: 100%;
    /* padding-bottom: 70px; */
  }

  .signup-input > .v-input__control {
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

.active {
  background-color: aqua;
}
</style>
