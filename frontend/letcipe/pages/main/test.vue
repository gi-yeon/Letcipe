<template>
  <div id="app">
    <v-app>
      <div class="test-container">
        <v-card
          style="width: 200px; height: 200px"
          class="d-flex-column justify-center align-item-center"
        >
          <div class="rec-imgs-group d-flex justify-center">
            <v-avatar size="130" tile @click="moveListDetail">
              <!-- <v-img class="ref-imgs" :src="item.recipe.repImg"></v-img> -->
              <v-img
                class="ref-imgs"
                src="https://img.icons8.com/emoji/344/orange-circle-emoji.png"
              >
                <v-icon x-large>mdi-play</v-icon>
              </v-img>
            </v-avatar>
          </div>
          <div>
            <v-card-title class="justify-center">여기는 제목</v-card-title>
          </div>
        </v-card>
      </div>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'TestPage',
}
</script>

<style scoped>
.test-container {
  padding-top: 80px;
}
</style>
