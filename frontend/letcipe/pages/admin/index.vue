<template>
  <div>
    <v-container>
      <v-row>
        <chart-component></chart-component>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import ChartComponent from '@/components/ChartComponent.vue'
export default {
  name: 'AdminIndex',
  components: { ChartComponent },
  data() {
    return {}
  },

  mounted() {},

  methods: {},
}
</script>

<style lang="scss" scoped></style>
