<template>
  <div>
    <div class="slick-wrapper home-insta">
      <div class="slider card-slider slick-slider">
        <!-- 1 -->
        <div v-for="(ref, i) in stepImg" :key="i" class="card-slider-item">
          <img :src="'https://2bob.co.kr/' + ref.url" alt="" />
          <div class="insta-post">
            <p class="">
              @Deborah.A<br /><strong>5 Star sim</strong><br />I used my sim in
              12 countries for 30 days. It worked perfectly and was very cost
              effective. Thankyou.
            </p>
          </div>
        </div>
        <!-- 2 -->

        <!-- 3 -->

        <!-- 5 -->

        <!-- 6 -->

        <!-- 7 -->

        <!--8 -->

        <!-- 9 -->

        <!-- 10 -->

        <!-- 11 -->
        <div class="card-slider-item">
          <img
            src="https://cdn.shopify.com/s/files/1/2979/3338/files/10.jpg"
            alt=""
          />
          <div class="insta-post">
            <p class="">
              @Jun.Y<br /><strong>Works just like it should</strong
              ><br />Excellent experience overall, the activation process is
              super easy and speed is quick, will use it on my next travel!
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TestPage',
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
}
</script>
<script></script>

<style></style>
