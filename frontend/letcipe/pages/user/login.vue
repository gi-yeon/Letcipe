<template>
  <div id="app"> 
    <v-app>
      <v-container class="login-container">
        <div class="login-title">로그인</div>
        <div class="login-wrap fadeInUp">
          <div>
            <div>
              <div class="login-input">
                <v-text-field
                  v-model="id"
                  label="아이디"
                  :rules="rules"
                  hide-details="auto"
                  @keyup.enter="loginTemp"
                ></v-text-field>
              </div>
              <div class="login-input">
                <v-text-field
                  v-model="pw"
                  label="비밀번호"
                  :rules="rules"
                  hide-details="auto"
                  @keyup.enter="loginTemp"
                ></v-text-field>
              </div>
            </div>
            <div class="find-wrap" @click="moveFindIdPW">
              <v-icon small color="#51600d">mdi-information</v-icon>
              <div>아이디|비밀번호 찾기</div>
            </div>
          </div>
          <div>
            <div class="btn-wrap">
              <v-btn class="btn" color="#AAC821" @click="loginTemp"
                >로그인</v-btn
              >
              <v-btn class="btn" color="#AAC821" @click="moveAgree"
                >회원가입</v-btn
              >
            </div>
          </div>
        </div>
        <v-card v-if="checkLogin === true" class="fadeInUp">
          <v-card-title class="text-h5">Caution</v-card-title>
          <v-card-text>아이디 또는 비밀번호가 일치하지 않습니다.</v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click="checkLogin = false"
              >확인</v-btn
            >
          </v-card-actions>
        </v-card>
      </v-container>
    </v-app>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
export default {
  name: 'LoginPage',
  data() {
    return {
      id: null,
      pw: null,
      checkLogin: false,
      rules: [
        (value) => !!value || 'Required.',
        (value) => (value && value.length >= 3) || 'Min 3 characters',
      ],
    }
  },
  computed: {
    ...mapState('user', ['userId', 'nickname']),
  },
  methods: {
    ...mapActions('user', ['login', 'readUser']),
    moveAgree() {
      this.$router.push('/user/agree')
    },
    async loginTemp() {
      const user = {
        userId: this.id,
        password: this.pw,
      }
      this.$cookies.remove('access-token')
      await this.login(user)
      await this.readUser()
      if (this.userId !== 0) {
        this.$router.push('/main')
      } else {
        this.checkLogin = true
      }
    },
    moveMain() {
      this.$router.push('/main')
    },
    moveFindIdPW() {
      this.$router.push('/user/pwinqury')
    },
  },
}
</script>

<style scoped>
.login-container {
  /* height: 100vh; */
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  /* background-image: url('/bg/bg_img.png'); */
  /* background-repeat: repeat; */
  /* padding-top: 70px; */
  /* padding-bottom: 70px; */
}
.login-title {
  font-size: xx-large;
  color: black;
  text-align: center;
  padding-top: 5%;
  /* padding-bottom: 15%; */
}
.login-input {
  background-color: white;
  border: 0.5px solid #aac821;
  margin-bottom: 3%;
  margin-top: 3%;
  border-radius: 10px;
}
.login-wrap {
  width: 30%;
}
.find-wrap {
  color: #51600d;
  display: flex;
  justify-content: flex-end;
  cursor: pointer;
}
.btn-wrap {
  display: flex;
  flex-direction: column;
  color: #aac821;
}
.btn {
  padding-top: 3%;
  margin-bottom: 3%;
}
.theme--dark.v-input input,
.theme--dark.v-input textarea {
  color: black;
}
.v-input {
  padding: 2%;
}
/* mobile screen */
@media (max-width: 415px) {
  .login-title {
    font-size: xx-large;
    color: black;
    text-align: center;
    padding-top: 20%;
    padding-bottom: 15%;
  }
  .login-input {
    background-color: white;
    border: 0.5px solid #73842b;
    margin-bottom: 3%;
    margin-top: 3%;
    border-radius: 10px;
  }

  .login-wrap {
    width: 60%;
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
