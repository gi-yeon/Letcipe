<template>
  <div id="app">
    <v-app class="cart-write-page">
      <v-container class="write-container">
        <div class="write-head-wrap d-flex justify-space-between pb-3">
          <v-icon>mdi-chevron-left</v-icon>
          <div style="font-size: x-large;">레시피리스트 작성</div>
          <v-icon>mdi-blank</v-icon>
        </div>
        <div class="write-body-wrap">
          <v-textarea solo name="input-7-4" label="Solo textarea"></v-textarea>
          <v-checkbox v-model="ex4" label="공개하기" color="success" value="success" hide-details></v-checkbox>
        </div>

        <div class="ref-img-wrap">
          <div>대표사진선택</div>
          <div>사진들</div>
        </div>
      </v-container>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'WritePage',
}
</script>

<style scoped>
.cart-write-page {
  padding-top: 70px;
  padding-bottom: 70px;
}
</style>