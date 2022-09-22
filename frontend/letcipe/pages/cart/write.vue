<template>
  <div id="app">
    <v-app class="cart-write-page">
      <v-container class="write-container">
        <div class="write-head-wrap d-flex justify-space-between pb-3">
          <v-icon>mdi-chevron-left</v-icon>
          <div style="font-size: x-large">레시피리스트 작성</div>
          <v-icon>mdi-blank</v-icon>
        </div>
        <div class="write-body-wrap">
          <div>
            <div>리스트 이름</div>
            <v-textarea
              placeholder="이름을 지어주세요."
              auto-grow
              solo
              rows="1"
              row-height="15"
            ></v-textarea>
          </div>
          <div>
            <div>내용</div>
            <v-textarea
              solo
              name="input-7-4"
              placeholder="내용을 입력해주세요"
            ></v-textarea>
            <v-checkbox
              v-model="share"
              label="공개하기"
              color="success"
              value="success"
              style="margin-top: 0"
              hide-details
            ></v-checkbox>
          </div>
        </div>

        <div class="ref-img-wrap">
          <div>대표사진선택</div>
          <div class="ref-images d-flex">
            <v-avatar
              v-for="(ref, i) in stepImg"
              :key="i"
              size="130"
              tile
              class="mr-2 ref-imgs"
              :class="{ active: ref.isChoiced }"
              @click="choiceImg(ref)"
            >
              <v-img :src="'https://2bob.co.kr/' + ref.url">
                <div
                  class="ref-wrap d-flex-column justify-center align-items-center"
                >
                  <v-card-title class="ref-title" style="font-size: xxx-large"
                    >{{ ref.stepNum }}
                    <v-icon
                      v-if="ref.isChoiced"
                      :class="{ choiced }"
                      large
                      color="success"
                      >mdi-tag-faces</v-icon
                    >
                  </v-card-title>
                </div>
              </v-img></v-avatar
            >
          </div>
        </div>
        <div class="d-flex justify-center pt-8">
          <v-btn color="#aac821" style="color: white">레시피리스트 생성</v-btn>
        </div>
      </v-container>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'WritePage',
  data() {
    return {
      stepImg: [
        {
          stepNum: 1,
          url: 'data/recipe/20210810142007-EYPBD.jpg',
          isChoiced: false,
        },
        {
          stepNum: 2,
          url: 'data/recipe/20210810142007-EYPBD.jpg',
          isChoiced: false,
        },
        {
          stepNum: 3,
          url: 'data/recipe/20210810142007-EYPBD.jpg',
          isChoiced: false,
        },
        {
          stepNum: 4,
          url: 'data/recipe/20210810142007-EYPBD.jpg',
          isChoiced: false,
        },
        {
          stepNum: 5,
          url: 'data/recipe/20210810142007-EYPBD.jpg',
          isChoiced: false,
        },
      ],
    }
  },
  methods: {
    choiceImg(ref) {
      this.stepImg.forEach((img) => {
        if (img.stepNum !== ref.stepNum) {
          img.isChoiced = false
        }
      })
      ref.isChoiced = !ref.isChoiced
    },
  },
}
</script>

<style scoped>
.cart-write-page {
  padding-top: 70px;
  padding-bottom: 70px;
}
.ref-images {
  overflow: scroll;
}
.ref-imgs {
  box-shadow: 0 3px 3px 0.4px rgba(0, 0, 0, 0.263);
}
.ref-img-wrap {
  padding-top: 4%;
  padding-bottom: 2%;
}
.active {
  /* border: 4px solid #f9ff41; */
  /* filter: brightness(70%); */
  /* -webkit-filter: drop-shadow(50%); */
}

.choiced {
  font-size: xx-large;
  /* background-color: rgb(255, 255, 255); */
  color: #cde856;
}
</style>
