<template>
  <div>
    <bar-chart :data="chartData" :options="chartOptions"></bar-chart>
    <line-chart :data="chartData" :options="chartOptions"></line-chart>
    <pie-chart :data="chartData" :options="chartOptions"></pie-chart>
  </div>
</template>

<script>
export default {
  name: 'ChartComponent',
  data() {
    return {
      chartData: {
        labels: ['a', 'b'],
        datasets: [
          {
            label: '',
            data: [10, 20],
            backgroundColor: [],
            borderColor: [
              // 'rgba(255, 159, 64, 1)',
            ],
            borderWidth: 2,
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
                max: 100,
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
}
</script>
