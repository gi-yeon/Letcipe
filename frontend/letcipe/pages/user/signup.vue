<template>
  <div id="app">
    <v-app>
      <v-container class="signup-container">
        <div class="signup-title">회원가입</div>
        <div class="signup-wrap fadeInUp">
          <v-form>
            <div class="d-flex">
              <v-text-field label="아이디" placeholder="Placeholder" solo></v-text-field>
              <v-btn height="48px" color="letcipe">중복확인</v-btn>
            </div>
            <v-text-field label="비밀번호" placeholder="Placeholder" solo></v-text-field>

            <v-text-field label="비밀번호 확인" placeholder="Placeholder" solo></v-text-field>
            <v-text-field label="이름" placeholder="Placeholder" solo></v-text-field>
            <v-text-field label="생년월일" placeholder="Placeholder" solo></v-text-field>
            <div class="d-flex">
              <v-text-field label="닉네임" placeholder="Placeholder" solo></v-text-field>
              <v-btn height="48px" color="letcipe">중복확인</v-btn>
            </div>
            <div class="d-flex">
              <v-avatar size="36">
                <img src="/icon/페페.jpg" alt />
              </v-avatar>
              <v-file-input width="50%" label="File input" outlined dense></v-file-input>
            </div>
            <div class="d-flex justify-space-between mb-4">
              <v-btn height="48px" width="47%" color="white">남</v-btn>
              <v-btn height="48px" width="47%" color="white">여</v-btn>
            </div>
            <div class="d-flex justify-space-between align-items-center">
              <div class="d-flex">
                <v-text-field label="ssafy" placeholder="Placeholder" solo></v-text-field>
                <span>@</span>
                <v-text-field label="letcipe.com" placeholder="Placeholder" solo></v-text-field>
              </div>
              <div>
                <v-overflow-btn
                  :items="dropdown_edit"
                  editable
                  label="Select size"
                  hide-details
                  overflow
                ></v-overflow-btn>
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
            >Submit</v-btn>
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
      email: undefined,
      form: false,
      phone: undefined,
      isLoading: false,
      rules: {
        email: (v) => !!(v || '').match(/@/) || 'Please enter a valid email',
        length: (len) => (v) =>
          (v || '').length >= len ||
          `Invalid character length, required ${len}`,
        password: (v) =>
          !!(v || '').match(
            /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(_|[^\w])).+$/
          ) ||
          'Password must contain an upper case letter, a numeric character, and a special character',
        required: (v) => !!v || 'This field is required',
      },
      dropdown_edit: [
        { text: '100%' },
        { text: '75%' },
        { text: '50%' },
        { text: '25%' },
        { text: '0%' },
      ],
    }
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

.signup-wrap {
  width: 100%;
  padding-bottom: 70px;
}
.signup-input > .v-input__control {
  min-height: 36px !important;
}
</style>
