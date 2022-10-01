<template>
  <client-only>
    <bar-chart :data="chartData"></bar-chart>
  </client-only>
</template>

<script>
export default {
  data() {
    return {
      chartData: {
        labels: ['a', 'b', 'c', 'd', 'a', 'b', 'c', 'd'],
        datasets: [
          {
            label: 'Title',
            data: [10, 30, 20, 40, 50, 20, 30, 100, 30],
            backgroundColor: ['rgba(0, 0, 0, 0)'],
            borderColor: [
              'rgba(255, 99, 132, 1)',
              'rgba(54, 162, 235, 1)',
              'rgba(255, 206, 86, 1)',
              'rgba(75, 192, 192, 1)',
              'rgba(153, 102, 255, 1)',
              'rgba(255, 159, 64, 1)',
            ],
            borderWidth: 2,
          },
        ],
      },
    }
  },
}
</script>
