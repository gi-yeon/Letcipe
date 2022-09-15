<template>
  <div id="app">
    <v-app>
      <v-container class="login-container">
        <div class="login-title">로그인</div>
        <div class="login-wrap">
          <div>
            <div class="login-input">
              <v-text-field
                label="아이디"
                :rules="rules"
                hide-details="auto"
              ></v-text-field>
            </div>
            <div class="login-input">
              <v-text-field
                label="비밀번호"
                :rules="rules"
                hide-details="auto"
              ></v-text-field>
            </div>
            <div class="find-wrap">
              <v-icon small color="#51600d">mdi-information</v-icon>
              <div>아이디|비밀번호 찾기</div>
            </div>
          </div>
          <div>
            <div class="btn-wrap">
              <v-btn class="btn" color="#AAC821" @click="moveMain"
                >로그인</v-btn
              >
              <v-btn class="btn" color="#AAC821" @click="moveAgree"
                >회원가입</v-btn
              >
            </div>
          </div>
        </div>
      </v-container>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'LoginPage',
  data() {
    return {
      rules: [
        (value) => !!value || 'Required.',
        (value) => (value && value.length >= 3) || 'Min 3 characters',
      ],
    }
  },
  methods: {
    moveAgree() {
      this.$router.push('/user/agree')
    },
    moveMain() {
      this.$router.push('/main')
    },
  },
}
</script>

<style>
.login-container {
  height: 100vh;
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-image: url('/bg/bg_img.png');
  background-repeat: repeat;
  padding-top: 70px;
  padding-bottom: 70px;
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
}
.btn-wrap {
  display: flex;
  flex-direction: column;
  color: #aac821;
}
.btn {
  margin-top: 3%;
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
</style>
