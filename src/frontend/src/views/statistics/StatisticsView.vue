<template>
  <v-container>
    <v-row class="mb-6">
      <v-col cols="12">
        <h1 class="text-h4 text-center mb-4">UCs Statistics Dashboard</h1>
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
        <StatGrid :stats="enrollments" caption="estudantes" :statusColors="enrollmentColors"/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import RevisionService from '../../services/RevisionService';
import CurricularUnitService from '../../services/CurricularUnitService';
import StatGrid from '../../components/statistics/StatGrid.vue';

const reviewsProgress = ref({})
const enrollments = ref({})

// Optional: color mapping
const reviewColors = {
  'Requested': 'blue lighten-4',
  'In review': 'orange lighten-4',
  'Accepted': 'green lighten-4',
  'Rejected': 'red lighten-4'
}

const enrollmentColors = {
  'Enrolled': 'blue lighten-4',
  'Approved': 'green lighten-4',
  'Failed': 'red lighten-4'
}

onMounted(async () => {
  reviewsProgress.value = {
    'Requested': await RevisionService.getRequested(),
    'In review': await RevisionService.getInReview(),
    'Accepted': await RevisionService.getApproved(),
    'Rejected': await RevisionService.getRejected()
  }

  enrollments.value = {
    'Enrolled': await CurricularUnitService.getEnrolled(),
    'Approved': await CurricularUnitService.getApproved(),
    'Failed': await CurricularUnitService.getFailed()
  }
})
</script>