<template>
  <div data-app>
    <v-container>
      <v-row>
        <v-col cols="12" lg="2">
          <v-menu
            ref="menu1"
            v-model="menu1"
            :close-on-content-click="false"
            :return-value.sync="s_date"
            transition="scale-transition"
            offset-y
            min-width="290px"
          >
            <template #activator="{ on, attrs }">
              <v-text-field
                v-model="s_date"
                prepend-icon="mdi-calendar"
                readonly
                v-bind="attrs"
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker v-model="s_date" no-title scrollable :max="e_date">
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="menu1 = false">Cancel</v-btn>
              <v-btn text color="primary" @click="s_date_search(s_date)"
                >OK</v-btn
              >
            </v-date-picker>
          </v-menu>
        </v-col>

        <v-col cols="12" lg="2">
          <v-menu
            ref="menu2"
            v-model="menu2"
            :close-on-content-click="false"
            :return-value.sync="e_date"
            transition="scale-transition"
            offset-y
            min-width="290px"
          >
            <template #activator="{ on, attrs }">
              <v-text-field
                v-model="e_date"
                prepend-icon="mdi-calendar"
                readonly
                v-bind="attrs"
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker
              v-model="e_date"
              no-title
              scrollable
              :min="s_date"
              :max="date"
            >
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="menu2 = false">Cancel</v-btn>
              <v-btn text color="primary" @click="e_date_search(e_date)"
                >OK</v-btn
              >
            </v-date-picker>
          </v-menu>
        </v-col>
      </v-row>
    </v-container>
    <v-container>
      <v-row>
        <v-col class="d-flex" cols="12" sm="2">
          <v-select
            :items="genItems"
            item-text="state"
            item-value="abbr"
            label="성별"
            @change="select_gen"
          ></v-select>
        </v-col>
        <v-col class="d-flex" cols="12" sm="2">
          <v-select
            :items="ageItems"
            item-text="state"
            item-value="abbr"
            label="나이 대"
            @change="select_age"
          ></v-select>
        </v-col>
        <v-col class="d-flex" cols="12" sm="2">
          <v-select
            :items="famItems"
            item-text="state"
            item-value="abbr"
            label="가구 수"
            @change="select_fam"
          ></v-select>
        </v-col>
        <v-col class="d-flex" cols="12" sm="3">
          <v-select
            :items="jobItems"
            item-text="state"
            item-value="abbr"
            label="직업 분류"
            @change="select_job"
          ></v-select>
        </v-col>
        <v-col class="d-flex" cols="12" sm="1">
          <v-btn x-large @click="getChartData">검색</v-btn>
        </v-col>
      </v-row>
      <v-row>
        <chart-component ref="chartChild"></chart-component>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from 'vuex'
import ChartComponent from '@/components/ChartComponent.vue'
export default {
  name: 'AdminIndex',
  components: {
    ChartComponent,
  },
  data() {
    return {
      date: new Date().toISOString().substr(0, 10),
      s_date: new Date().toISOString().substr(0, 10),
      e_date: new Date().toISOString().substr(0, 10),
      menu1: false,
      menu2: false,
      genItems: [
        { state: '선택 안 함', abbr: '-' },
        { state: '남성', abbr: 'M' },
        { state: '여성', abbr: 'F' },
      ],
      ageItems: [
        { state: '선택 안 함', abbr: '-' },
        { state: '10대', abbr: '10' },
        { state: '20대', abbr: '20' },
        { state: '30대', abbr: '30' },
        { state: '40대', abbr: '40' },
        { state: '50대', abbr: '50' },
        { state: '60대', abbr: '60' },
      ],
      famItems: [
        { state: '선택 안 함', abbr: '-' },
        { state: '1인 가구', abbr: '1' },
        { state: '2인 가구', abbr: '2' },
        { state: '3인 가구', abbr: '3' },
        { state: '4인 가구', abbr: '4' },
      ],
      jobItems: [
        { state: '선택 안 함', abbr: '-' },
        { state: '학생', abbr: 'STUDENT' },
        { state: '주부', abbr: 'JUBU' },
        { state: '직장인', abbr: 'WORKER' },
        { state: '요리사', abbr: 'COOK' },
      ],
      attr: {
        gen: '-',
        age: '-',
        fam: '-',
        job: '-',
      },
    }
  },
  computed: {
    ...mapState('search', ['charts']),
  },
  created() {
    // this.CLEAR_CHARTS()
  },
  methods: {
    ...mapActions('search', ['getCharts']),
    ...mapMutations('search', ['CLEAR_CHARTS']),

    s_date_search(v) {
      this.s_date = v
      this.menu1 = false
      this.$refs.menu1.save(v)
    },
    e_date_search(v) {
      this.e_date = v
      this.menu2 = false
      this.$refs.menu2.save(v)
    },
    async getChartData() {
      const attribute =
        this.attr.gen +
        ',' +
        this.attr.age +
        ',' +
        this.attr.fam +
        ',' +
        this.attr.job
      console.log(attribute)
      console.log(this.s_date)
      console.log(this.e_date)
      const params = {
        attr: attribute,
        begin: this.s_date,
        end: this.e_date,
        size: 2,
        page: 0,
      }
      console.log(params)
      await this.getCharts(params)
      console.log('여기')
      console.log(this.charts)
      console.log(this.$refs.chartChild)
      this.$refs.chartChild.getData()
    },
    select_gen(e) {
      console.log(e)
      this.attr.gen = e
    },
    select_age(e) {
      this.attr.age = e
    },
    select_fam(e) {
      this.attr.fam = e
    },
    select_job(e) {
      this.attr.job = e
    },
  },
}
</script>
<style lang="scss" scoped>
.row {
  justify-content: center;
}
</style>
