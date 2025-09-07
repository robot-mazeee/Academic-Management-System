<template>
  <v-container>
    <v-row class="mb-6">
      <v-col cols="12">
        <h1 class="text-h4 text-center mb-4">Dashboard de Estatísticas</h1>
      </v-col>
    </v-row>

    <!-- Grade Reviews Progress Section -->
    <v-row class="mb-6">
      <v-col cols="12">
        <h2 class="text-h5 mb-4">Revisões de Prova</h2>
        <StatGrid :stats="reviewsProgress" caption="estudantes" :statusColors="reviewColors"/>
      </v-col>
    </v-row>
    
    <!-- Enrollments Section -->
    <v-row class="mb-6">
      <v-col cols="12">
        <h2 class="text-h5 mb-4">Inscrições em Unidades Curriculares</h2>
        <StatGrid :stats="enrollmentsStatus" caption="estudantes" :statusColors="enrollmentColors"/>
      </v-col>
    </v-row>

    <!-- Final Grades Distribution Section --> 
    <v-row class="mb-6" v-if="averageGradesByUnit.labels.length && averageGradesByUnit.datasets[0].data.length">
      <v-col cols="12">
        <h2 class="text-h5 mb-4">Média de Notas por Unidade Curricular</h2>
        <BarChart
          :chartData="averageGradesByUnit"
          :chartOptions="chartOptions"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import RevisionService from '../../services/RevisionService'
import CurricularUnitService from '../../services/CurricularUnitService'
import StatGrid from '../../components/statistics/StatGrid.vue'
import BarChart from '../../components/statistics/BarChart.vue'
import EnrollmentDto from '../../models/curricular-units/EnrollmentDto'
import CurricularUnitDto from '../../models/curricular-units/CurricularUnitDto'

import { ChartData, ChartOptions } from 'chart.js'

const reviewsProgress = ref<Record<string, number>>({})
const enrollmentsStatus = ref<Record<string, number>>({})
const enrollments = ref<EnrollmentDto[]>([])
const curricularUnits = ref<CurricularUnitDto[]>([])

const averageGradesByUnit = ref<ChartData<'bar', number[], string>>({
  labels: [],
  datasets: [{ label: 'Média de Notas', data: [], backgroundColor: 'blue' }]
})

const chartOptions = ref<ChartOptions<'bar'>>({
  responsive: true,
  plugins: { legend: { display: true } },
  scales: { y: { min: 0, max: 20 } }
})

const reviewColors = { 'Requested': 'blue lighten-4', 'In review': 'orange lighten-4', 'Accepted': 'green lighten-4', 'Rejected': 'red lighten-4' }
const enrollmentColors = { 'Enrolled': 'blue lighten-4', 'Approved': 'green lighten-4', 'Failed': 'red lighten-4' }

onMounted(async () => {
  reviewsProgress.value = {
    'Requested': await RevisionService.getRequested(),
    'In review': await RevisionService.getInReview(),
    'Accepted': await RevisionService.getApproved(),
    'Rejected': await RevisionService.getRejected()
  }

  enrollmentsStatus.value = {
    'Enrolled': await CurricularUnitService.getEnrolled(),
    'Approved': await CurricularUnitService.getApproved(),
    'Failed': await CurricularUnitService.getFailed()
  }

  await getCurricularUnits()
  await getEnrollmentsForAllUnits()
  calculateAverageGrades()
})

async function getCurricularUnits() {
  try { curricularUnits.value = await CurricularUnitService.getCurricularUnits() }
  catch (error) { console.error('Error fetching curricular units:', error) }
}

async function getEnrollmentsForAllUnits() {
  try {
    const enrollmentPromises = curricularUnits.value.map(uc =>
      CurricularUnitService.getEnrollmentsByCurricularUnit(uc.id)
    )
    const results = await Promise.all(enrollmentPromises)
    enrollments.value = results.flat()
  } catch (error) { console.error('Error fetching enrollments:', error) }
}

function calculateAverageGrades() {
  const labels: string[] = []
  const data: number[] = []

  curricularUnits.value.forEach(uc => {
    const ucEnrollments = enrollments.value.filter(e => e.curricularUnitId === uc.id)
    if (ucEnrollments.length) {
      const avg = ucEnrollments.reduce((sum, e) => sum + e.finalGrade, 0) / ucEnrollments.length
      labels.push(uc.name)
      data.push(avg)
    }
  })

  averageGradesByUnit.value = { labels, datasets: [{ label: 'Média de Notas', data, backgroundColor: 'blue' }] }
}
</script>
