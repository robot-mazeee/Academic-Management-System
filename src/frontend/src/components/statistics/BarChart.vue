<template>
  <div class="bar-chart-container">
    <Bar :data="chartData" :options="chartOptions" />
  </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'
import { ChartData, ChartOptions, Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
import { Bar } from 'vue-chartjs'

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

interface Props {
  chartData: ChartData<'bar', number[], string>
  chartOptions?: ChartOptions<'bar'>
}

const props = defineProps<Props>()
</script>

<style scoped>
.bar-chart-container {
  width: 100%;
  max-width: 600px;
  margin: 0 auto; 
  height: 300px;
}
</style>
