<template>
  <div id="app">
    <v-app>
      <v-container class="signup-container">
        <div class="signup-title">회원가입</div>
        <div class="signup-wrap fadeInUp">
          <v-form>
            <div class="necessary-info">
              <div class="d-flex">
                <div class="signup-subtitle">필수정보</div>
              </div>
              <div class="d-flex flex-column">
                <div>아이디</div>
                <div class="d-flex">
                  <v-text-field
                    v-model="id"
                    :rules="rules.id_rule"
                    placeholder="아이디"
                    solo
                  ></v-text-field>
                  <v-btn height="48px" color="letcipe">중복확인</v-btn>
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
                <v-text-field
                  v-model="user_nm"
                  :rules="rules.nm_rule"
                  placeholder="이름"
                  solo
                ></v-text-field>
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
                  <v-date-picker
                    v-model="birthdate"
                    @input="bdmenu = false"
                  ></v-date-picker>
                </v-menu>
              </div>
              <div class="d-flex flex-column">
                <div>닉네임</div>
                <div class="d-flex">
                  <v-text-field
                    v-model="nick_nm"
                    :rules="rules.nick_rule"
                    label="닉네임"
                    placeholder="Placeholder"
                    solo
                  ></v-text-field>
                  <v-btn height="48px" color="letcipe">중복확인</v-btn>
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
                  <v-btn height="48px" width="47%" color="white">남</v-btn>
                  <v-btn height="48px" width="47%" color="white">여</v-btn>
                </div>
              </div>
              <div class="d-flex flex-column">
                <div>이메일</div>
                <div class="d-flex justify-space-between align-items-center">
                  <v-text-field
                    v-model="email_id"
                    placeholder="ssafy"
                    solo
                  ></v-text-field>
                  <span>@</span>
                  <v-text-field
                    v-model="email_address"
                    placeholder="letcipe.com"
                    solo
                    >{{ email_address }}</v-text-field
                  >
                  <v-select
                    v-model="email_address"
                    :items="emails"
                    label="직접입력"
                    solo
                  ></v-select>
                </div>
                <!-- <div>
                <v-row align="center">
                  <v-col>
                    <v-select :items="emails" label="직접입력" solo></v-select>
                  </v-col>
                </v-row>
              </div> -->
              </div>
              <div class="d-flex flex-column">
                <div>휴대전화번호</div>
                <div class="d-flex justify-space-between align-items-center">
                  <v-text-field v-model="phoneRef" placeholder="010" solo>{{
                    phoneRef
                  }}</v-text-field>
                  <span>-</span>
                  <v-text-field v-model="phoneFirst" placeholder="0000" solo>{{
                    phoneFirst
                  }}</v-text-field>
                  <span>-</span>
                  <v-text-field v-model="phoneSecond" placeholder="0000" solo>{{
                    phoneSecond
                  }}</v-text-field>
                  <v-btn height="48px" class="pl-2">인증</v-btn>
                </div>
              </div>
              <div class="d-flex">
                <v-text-field v-model="validNum" placeholder="SK123LDk" solo>{{
                  validNum
                }}</v-text-field>
                <v-btn height="48px" class="pl-2">확인</v-btn>
              </div>
            </div>
            <v-divider></v-divider>
            <div class="option-info pt-5">
              <div class="d-flex">
                <div class="signup-subtitle">선택정보</div>
              </div>
              <div class="d-flex flex-column">
                <div>주소</div>
                <div class="d-flex">
                  <v-text-field
                    id="postal-code"
                    v-model="postalcode"
                    placeholder="우편번호"
                    solo
                    style="width: 20px"
                    @click="find_Postcode()"
                  ></v-text-field>
                  <v-btn height="48px" class="ml-3" @click="find_Postcode()"
                    >주소검색</v-btn
                  >
                </div>
                <v-text-field
                  id="address"
                  v-model="mainAddress"
                  placeholder="주소"
                  solo
                ></v-text-field>
                <v-text-field
                  id="address-detail"
                  v-model="detailsAddress"
                  placeholder="상세주소"
                  solo
                ></v-text-field>
              </div>
              <div class="d-flex flex-column">
                <div>직업</div>
                <v-select
                  v-model="job"
                  :items="jobs"
                  label="직업"
                  solo
                ></v-select>
              </div>
              <div class="d-flex flex-column">
                <div>가구원 수</div>
                <v-select
                  v-model="familymember"
                  :items="famCnt"
                  label="가구원 수"
                  solo
                ></v-select>
              </div>
            </div>
          </v-form>
          <v-card-actions>
            <v-btn text @click="$refs.form.reset()">Clear</v-btn>
            <v-spacer></v-spacer>
            <v-btn
              :disabled="!form"
              :loading="isLoading"
              class="white--text"
              color="deep-purple accent-4"
              depressed
              >Submit</v-btn
            >
          </v-card-actions>
        </div>
      </v-container>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'SignupPage',
  data() {
    return {
      id: '',
      pw: '',
      pwck: '',
      showPW: false,
      showPWCK: false,
      user_nm: '',
      nick_nm: '',
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
      job: '',
      familymember: 1,
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
      jobs: ['주부', '학생', '직장인', '요식업 종사자'],
      famCnt: [1, 2, 3, 4, '5인 이상'],
      preview_profile: null,
    }
  },
  methods: {
    find_Postcode() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
          // 각 주소의 노출 규칙에 따라 주소를 조합한다.
          // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
          // let addr = '' // 주소 변수
          // let extraAddr = '' // 참고항목 변수
          // vue라서 위 data에 변수로 추가해줬음.
          // //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
          this.mainAddress = ''
          this.detailAddress = ''
          if (data.userSelectedType === 'R') {
            // 사용자가 도로명 주소를 선택했을 경우
            this.mainAddress = data.roadAddress
          } else {
            // 사용자가 지번 주소를 선택했을 경우(J)
            this.mainAddress = data.jibunAddress
          }
          // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
          if (data.userSelectedType === 'R') {
            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
              this.detailAddress += data.bname
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if (data.buildingName !== '' && data.apartment === 'Y') {
              this.detailAddress +=
                this.detailAddress !== ''
                  ? ', ' + data.buildingName
                  : data.buildingName
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if (this.detailAddress !== '') {
              this.detailAddress = ' (' + this.detailAddress + ')'
            }
            // 조합된 참고항목을 해당 필드에 넣는다.
            document.getElementById('address-detail').value = this.extraAddr
          } else {
            document.getElementById('address-detail').value = ''
          }
          // 우편번호와 주소 정보를 해당 필드에 넣는다.
          document.getElementById('postal-code').value = data.zonecode
          document.getElementById('address').value = this.addr
          // 커서를 상세주소 필드로 이동한다.
          document.getElementById('address-detail').focus()
        },
      }).open()
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
  },
}
</script>

<style scoped>
#app {
  background-image: url('/bg/bg_img.png');
  background-repeat: repeat;
}
.signup-container {
  height: 100%;
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-image: url('/bg/bg_img.png');
  background-repeat: repeat;
  padding-top: 70px;
  padding-bottom: 70px;
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
  padding-bottom: 70px;
}
.signup-input > .v-input__control {
  min-height: 36px !important;
}
</style>
