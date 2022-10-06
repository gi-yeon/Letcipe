<template>
  <!-- 한줄에 하고 싶은데 방법 모름! -->
  <!-- 여기서의 레시피 리스트는 히스토리에서 가져오는건지,,!  -->
  <div id="app">
    <v-app id="inspire">
      <div class="progressrecipelist-page">
        <v-container class="progressrecipelist-container d-flex-row">
          <div class="progressrecipelist-head-wrap">
            <div class="d-flex justify-space-between pb-3">
              <div>
                <v-icon @click="moveBack">mdi-window-close</v-icon>
              </div>
              <div style="font-size: x-large">진행중인 레시피리스트</div>
              <div>
                <v-icon>mdi-blank</v-icon>
              </div>
            </div>
          </div>
          <v-divider></v-divider>
          <div v-if="status != '종료'">
            <!-- <div v-for="(recipeList, i) in letcipeList" :key="i"> -->
            <v-list-item three-line style="background-color: white">
              <v-list-item-avatar tile size="100">
                <v-img :src="historyRepImg"></v-img>
              </v-list-item-avatar>
              <v-list-item-content>
                <!-- <v-list-item-title
                style="display: inline; float: left"
                :color="statusColor"
                >{{ historyInfo }}-->
                <v-list-item-title style="display: inline; float: left">
                  {{ historyInfo.regTime }}
                  <span style="font-size: 1rem; float: right; color: green">
                    {{ status }}
                  </span>
                </v-list-item-title>

                <br />

                <!-- <v-list-item-subtitle
                >생성일 : {{ letcipeList.reg_time }}</v-list-item-subtitle
                >-->
                <v-list-item-subtitle style="text-align: right">
                  총&nbsp;
                  <span style="color: black; font-size: 1rem">
                    {{ containsNum }} </span
                  >&nbsp;개의 레시피를 포함&nbsp;
                  <v-icon v-if="!isShow" @click="showRecipes"
                    >mdi-chevron-down</v-icon
                  >
                  <v-icon v-else @click="hideRecipes">mdi-chevron-up</v-icon>
                </v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
            <v-divider></v-divider>
            <!-- </div> -->
            <div v-if="isShow">
              <v-card-title
                class="justify-center"
                style="background-color: white; color: #7cb342"
                >포함된 레시피 목록</v-card-title
              >
              <v-divider></v-divider>
              <div v-for="(recipeInfo, i) in historyInfo.historyItems" :key="i">
                <v-list-item three-line style="background-color: white" @click="moveDetail(recipeInfo.recipe)">
                  <v-list-item-avatar tile size="100">
                    <v-img :src="recipeInfo.recipe.repImg"></v-img>
                  </v-list-item-avatar>
                  <v-list-item-content>
                    <v-list-item-title>{{
                      recipeInfo.recipe.title
                    }}</v-list-item-title>

                    <v-list-item-subtitle>{{
                      recipeInfo.recipe.content
                    }}</v-list-item-subtitle>
                    <div class="d-flex justify-space-between">
                      <v-list-item-subtitle style="margin: auto 0">
                        <v-icon small color="pink lighten-1"
                          >mdi-cards-heart</v-icon
                        >
                        {{ recipeInfo.recipe.recipeLike }}
                      </v-list-item-subtitle>

                      <v-list-item-subtitle style="text-align: right">
                        수량&nbsp;&nbsp;:&nbsp;&nbsp;
                        <span style="color: black; font-size: 1.2rem">{{
                          recipeInfo.amount
                        }}</span
                        >&nbsp;&nbsp;개
                      </v-list-item-subtitle>

                      <!-- <v-list-item-title style="text-align: right">
                      11개</v-list-item-title
                      >-->
                    </div>
                  </v-list-item-content>
                </v-list-item>
                <v-divider></v-divider>
              </div>
              <v-divider></v-divider>
            </div>
            <div style="text-align: center; padding-top: 5%">
              <v-btn
                v-if="status == '진행예정'"
                large
                color="lime darken-1"
                style="width: 50%"
                @click="startReadyHistory"
                >시작</v-btn
              >
              <v-btn
                v-else-if="status == '진행중'"
                large
                color="lime darken-1"
                style="width: 50%"
                @click="endEatingHistory"
                >종료</v-btn
              >
            </div>
          </div>
          <div v-else align="center" class="pt-3 pb-3">
            진행중인 레시피리스트가 없습니다.
          </div>

          <v-dialog v-model="dialog" persistent max-width="290">
            <v-card v-if="startDialog">
              <v-card-title class="text-h5">Caution</v-card-title>
              <v-card-text>레시피 리스트가 시작되었습니다.</v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  color="green darken-1"
                  text
                  @click="
                    {
                      dialog = false
                      $router.go()
                    }
                  "
                  >확인</v-btn
                >
              </v-card-actions>
            </v-card>
            <v-card v-if="endDialog">
              <v-card-title class="text-h5">Caution</v-card-title>
              <v-card-text>레시피 리스트가 종료되었습니다.</v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  color="green darken-1"
                  text
                  @click="
                    {
                      dialog = false
                      $router.go()
                    }
                  "
                  >확인</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-container>
      </div>
    </v-app>
  </div>
</template>

<script>
import { mapActions,mapMutations, mapState } from 'vuex'
export default {
  name: 'ProgressPage',
  data() {
    return {
      status: '종료',
      // statusColor: 'purple darken-1',
      historyListInfo: [],
      historyRepImg: '',
      historyInfo: {},
      containsNum: 0,
      isShow: false,
      dialog: false,
      startDialog: false,
      endDialog: false,
    }
  },
  // async fetch() {
  //   await this.getHistoryList()
  //   console.log(this.historyList)
  // },
  computed: {
    ...mapState('history', ['historyList']),
  },

  created() {
    // this.getHistoryList()
    const promise = new Promise((resolve, reject) => {
      resolve()
    })
    promise.then(async () => {
      await this.getHistoryList()
      console.log(this.historyList)
      this.historyListInfo = this.historyList
      let index = -1
      for (let i = 0; i < this.historyListInfo.length; i++) {
        console.log('22222222222222')
        if (this.historyListInfo[i].process === 'READY') {
          this.status = '진행예정'
          index = i
          break
        } else if (this.historyListInfo[i].process === 'EATING') {
          this.status = '진행중'
          index = i
          break
        } else {
          this.status = '종료'
        }
      }

      if (index >= 0) {
        this.historyInfo = {
          historyItems: this.historyList[index].historyItems,
          id: this.historyList[index].id,
          progress: this.historyList[index].progress,
          regTime:
            this.historyList[index].regTime.split('T')[0] +
            ' ' +
            this.historyList[index].regTime.split('T')[1],
          review: this.historyList[index].review,
        }
        this.containsNum = this.historyInfo.historyItems.length

        this.historyRepImg = this.historyInfo.historyItems[0].recipe.repImg
      }
    })
  },
  methods: {
        ...mapMutations('recipe', ['SET_RECIPE_ID', 'CLEAR_RECIPE_ID']),
    ...mapActions('history', ['getHistoryList', 'updateHistory']),
    moveBack() {
      this.$router.go(-1)
    },
    showRecipes() {
      this.isShow = true
    },
    hideRecipes() {
      this.isShow = false
    },
    startReadyHistory() {
      const history = {
        id: this.historyInfo.id,
        process: 'EATING',
      }
      this.updateHistory(history)
      this.startDialog = true
      this.setDialog()
    },
    endEatingHistory() {
      const history = {
        id: this.historyInfo.id,
        process: 'END',
      }
      this.updateHistory(history)
      this.endDialog = true
      this.setDialog()
    },
    setDialog() {
      this.dialog = true
    },
    moveDetail(data) {
      this.CLEAR_RECIPE_ID()
      this.SET_RECIPE_ID(data.id)
      this.$router.push('/recipe/detail')
    },
  },
}
</script>

<style scoped>
.progressrecipelist-page {
  /* padding-top: 70px; */
  padding-bottom: 70px;
  padding: 4%;
}
.progressrecipelist-page-head {
  padding: 4%;
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
  /* border: 1px solid gray; */
}
.progressrecipelist-container {
  position: sticky;
  height: 100%;
  color: black;

  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.2);
}
</style>
