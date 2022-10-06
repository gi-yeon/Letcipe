<template>
  <div data-app>
    <v-select></v-select>
    <bar-chart
      v-bind="chartData"
      :data="chartData"
      :options="chartOptions"
    ></bar-chart>
    <line-chart
      v-bind="chartData"
      :data="chartData"
      :options="chartOptions"
    ></line-chart>
    <pie-chart
      v-bind="chartData"
      :data="chartData"
      :options="chartOptions"
    ></pie-chart>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'ChartComponent',
  data() {
    return {
      chartData: {
        labels: [],
        datasets: [
          {
            label: '',
            data: [],
            backgroundColor: [],
            borderColor: [
              // 'rgba(255, 159, 64, 1)',
            ],
            borderWidth: 1,
          },
        ],
      },
      chartOptions: {
        responsive: true,
        legend: {
          display: false,
        },
        title: {
          display: true,
          text: "Let'cipe analytics data",
          fontSize: 24,
          fontColor: '#fb7280',
        },
        tooltips: {
          backgroundColor: '#17BF62',
        },
        scales: {
          xAxes: [
            {
              gridLines: {
                display: true,
              },
            },
          ],
          yAxes: [
            {
              ticks: {
                beginAtZero: true,
                max: 50,
                min: 0,
                stepSize: 1,
              },
              gridLines: {
                display: true,
              },
            },
          ],
        },
      },
    }
  },
  // async fetch() {
  //   await this.getData()
  // },
  computed: {
    ...mapState('search', ['charts']),
  },
  created() {},
  mounted() {
    // console.log(this.chartData)
    this.chartData.labels = this.charts.recipeName
    this.chartData.datasets[0].data = this.charts.count
    // console.log(this.chartData)
  },
  methods: {
    getData() {
      this.charts.recipeName.forEach((name) => {
        this.chartData.labels.push(name)
      })
      this.charts.count.forEach((cnt) => {
        this.chartData.datasets[0].data.push(cnt)
      })
    },
  },
}
</script>
