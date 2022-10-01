import Vue from "vue";
import { Line, Bar, Pie } from "vue-chartjs/legacy";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
  LineElement,
  PointElement,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  PointElement,
  BarElement,
  CategoryScale,
  LinearScale,
  LineElement,
);

Vue.component('line-chart', {
    extends: Line,
    props: ['data', 'options'],
    mounted() {
      this.renderChart(this.data, this.options)
    },
  })
  
  Vue.component('pie-chart', {
    extends: Pie,
    props: ['data', 'options'],
    mounted() {
      this.renderChart(this.data, this.options)
    },
  })
  
  Vue.component('bar-chart', {
    extends: Bar,
    props: ['data', 'options'],
    mounted() {
      this.renderChart(this.data, this.options)
    },
  })