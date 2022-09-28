<template>
  <div>
    <v-btn @click="testReadDetail()"> testReadDetail </v-btn>
    <v-btn @click="testHadoop()"> HadoopTest </v-btn>
    <v-btn @click="test()"> testIngredient </v-btn>
    <v-file-input
      v-model="fileImg"
      width="50%"
      label="이미지 업로드"
      outlined
      dense
    ></v-file-input>
  </div>
</template>

<script>
import axios from 'axios'
import { mapActions, mapState } from 'vuex'
export default {
  name: 'DataTest',
  data() {
    return {
      fileImg: '',
    }
  },
  computed: {
    ...mapState('recipe', ['recipeDetail']),
    ...mapState('ingredients', ['ingredientsList']),
  },
  methods: {
    ...mapActions('recipe', ['RecipeDetail', 'selectRecipeDetail']),
    ...mapActions('ingredients', ['searchIngredient']),
    testReadDetail() {
      this.RecipeDetail(2)
      console.log(this.recipeDetail)
    },
    testCreate() {
      this.changeForm()

      //   console.log(formData.entries())
      // this.selectRecipeDetail(formData)
    },
    changeForm() {
      console.log(this.fileImg)
      const formData = new FormData()
      formData.append('title', '고르곤졸라피자')
      formData.append('content', '만만치 않은 가격')
      formData.append('category', 'R0001')
      formData.append('cookingTime', 30)
      formData.append('serving', 4)
      formData.append('repImg', this.fileImg)
      formData.append('stepDtoList[0].step', 1)
      formData.append('stepDtoList[0].img', this.fileImg)
      formData.append('stepDtoList[0].content', 'asd')
      formData.append('ingredients[0].id', 1)
      formData.append('ingredients[1].id', 3)
      formData.append('ingredients[2].id', 6)
      formData.append('ingredients[0].amount', 5)
      formData.append('ingredients[1].amount', 1)
      formData.append('ingredients[2].amount', 2)

      formData.append('tagList', ['피자', '꿀'])
      for (const p of formData.entries()) {
        console.log(p[0] + ',' + p[1])
      }
      console.log(formData)
      this.selectRecipeDetail(formData)
    },
    test() {
      const keyword = '양파'
      this.searchIngredient(keyword)
      console.log(this.ingredientsList)
    },
    testHadoop() {
      axios.get('https://j7a705.q.ssafy.io/hadoop/test').then((res) => {
        console.log(res.data)
      })
    },
  },
}
</script>

<style></style>
