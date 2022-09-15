<template>
  <div id="app">
    <v-app>
      <v-container class="signup-container">
        <div class="signup-title">회원가입</div>
        <div class="signup-wrap fadeInUp">
          <v-form
            ><v-text-field
              v-model="password"
              :rules="[rules.password, rules.length(6)]"
              filled
              color="deep-purple"
              counter="6"
              label="Password"
              style="min-height: 96px"
              type="password"
            ></v-text-field>
            <v-text-field
              v-model="phone"
              filled
              color="deep-purple"
              label="Phone number"
            ></v-text-field>
            <v-text-field
              v-model="email"
              :rules="[rules.email]"
              filled
              color="deep-purple"
              label="Email address"
              type="email"
            ></v-text-field>
          </v-form>
          <v-card-actions>
            <v-btn text @click="$refs.form.reset()"> Clear </v-btn>
            <v-spacer></v-spacer>
            <v-btn
              :disabled="!form"
              :loading="isLoading"
              class="white--text"
              color="deep-purple accent-4"
              depressed
            >
              Submit
            </v-btn>
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
    }
  },
}
</script>

<style scoped>
.signup-container {
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
.signup-title {
  font-size: xx-large;
  color: black;
  text-align: center;
  padding-top: 5%;
  /* padding-bottom: 15%; */
}
</style>
