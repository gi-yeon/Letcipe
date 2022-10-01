<template>
  <div id="app">
    <Bar :chart-data="chartData" :chart-options="chartOptions" />
    <Line :chart-data="chartData" :chart-options="chartOptions" />
    <Pie :chart-data="chartData" :chart-options="chartOptions" />
  </div>
</template>

<script>
// DataPage.vue
import { Bar, Line, Pie } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, ArcElement, LineElement, CategoryScale, LinearScale } from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, BarElement, ArcElement, LineElement, CategoryScale, LinearScale)

export default {
  name: 'BarChart',
  components: { Bar, Line, Pie },
  data() {
      return {
        chartData: {
          labels: [
            "a","b","c","d"
          ],
          datasets: [{
            label: 'Title',
            data: [15, 23,47, 55]
          }]
        },
        chartOptions: {
        responsive: true,
        legend: {
          display: false,
        },
        title: {
          display: true,
          text: "Google analytics data",
          fontSize: 24,
          fontColor: "#6b7280",
        },
        tooltips: {
          backgroundColor: "#17BF62",
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
                max: 7,
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
      };
    }
}
</script>