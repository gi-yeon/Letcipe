<template>
  <div id="app">
    <v-app> 
      <v-container class="find-user">
        <div class="find-title">아이디/비밀번호 찾기</div>
        <div class="find-wrap">
          <v-toolbar flat color="letcipe">
            <template #extension>
              <v-tabs v-model="tabs" fixed-tabs>
                <v-tabs-slider color="black"></v-tabs-slider>
                <v-tab href="#mobile-tabs-5-1" class="white--text">
                  <v-icon>mdi-account-box-outline</v-icon>
                </v-tab>

                <v-tab href="#mobile-tabs-5-2" class="white--text">
                  <v-icon>mdi-key-variant</v-icon>
                </v-tab>
              </v-tabs>
            </template>
          </v-toolbar>

          <v-tabs-items v-model="tabs">
            <v-tab-item v-for="i in 2" :key="i" :value="'mobile-tabs-5-' + i">
              <v-card flat>
                <v-card-text v-if="i === 1">
                  <div>아이디</div>
                  <v-text-field v-model="userId" placeholder="아이디를 입력해주세요." solo>{{userId}}</v-text-field>
                  <div>휴대폰번호</div>
                  <v-text-field v-model="phoneNum" placeholder="휴대전화번호를 입력해주세요." solo>{{phoneNum}}</v-text-field>
                  <v-dialog v-model="dialog1" persistent max-width="290">
                    <template #activator="{ on, attrs }">
                      <v-btn
                        color="letcipe"
                        style="color: white; width: 100%"
                        v-bind="attrs"
                        @click="sendCode(phoneNum)"
                        v-on="on"
                      >인증번호받기</v-btn>
                    </template>
                    <v-card v-if="check===false">
                      <v-card-title class="text-h5">Caution</v-card-title>
                      <v-card-text>인증번호가 발송되었습니다.</v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="green darken-1" text @click="dialog1 = false">확인</v-btn>
                      </v-card-actions>
                    </v-card>
                    <v-card v-if="check===true">
                      <v-card-title class="text-h5">Caution</v-card-title>
                      <v-card-text>가입시 입력하신 회원정보가 맞는지 다시 한 번 확인해주세요.</v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="green darken-1" text @click="dialog1 = false">확인</v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </v-card-text>
                <v-card-text v-else-if="i === 2">
                  <div>이름</div>
                  <v-text-field v-model="userName" placeholder="이름을 입력해주세요." solo>{{userName}}</v-text-field>
                  <div>휴대폰번호</div>
                  <v-text-field v-model="phoneNum" placeholder="휴대전화번호를 입력해주세요." solo>{{phoneNum}}</v-text-field>
                  <v-dialog v-model="dialog2" persistent max-width="290">
                    <template #activator="{ on, attrs }">
                      <v-btn
                        color="letcipe"
                        style="color: white; width: 100%"
                        v-bind="attrs"
                        @click="sendCode(phoneNum)"
                        v-on="on"
                      >인증번호받기</v-btn>
                    </template>
                    <v-card v-if="check===false">
                      <v-card-title class="text-h5">Caution</v-card-title>
                      <v-card-text>인증번호가 발송되었습니다.</v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="green darken-1" text @click="dialog1 = false">확인</v-btn>
                      </v-card-actions>
                    </v-card>
                    <v-card v-if="check===true">
                      <v-card-title class="text-h5">Caution</v-card-title>
                      <v-card-text>가입시 입력하신 회원정보가 맞는지 다시 한 번 확인해주세요.</v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="green darken-1" text @click="dialog1 = false">확인</v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </v-card-text>
              </v-card>
            </v-tab-item>
          </v-tabs-items>
        </div>
      </v-container>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'FindPage',
  data() {
    return {
      tabs: null,
      userName: '',
      userId: '',
      phoneNum: '',
      dialog1: false,
      dialog2: false,
      check: true,
    }
  },
  methods: {
    sendCode(phoneNum) {
      console.log(phoneNum)
    },
  },
}
</script>

<style scoped>
.find-user {
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  /* background-image: url('/bg/bg_img.png'); */
  /* background-repeat: repeat; */
  padding-top: 70px;
  padding-bottom: 70px;
}
.find-title {
  font-size: x-large;
  padding: 3%;
}
.find-wrap {
  width: 70%;
}
</style>