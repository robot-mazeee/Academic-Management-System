<template>
  <Calendar
    v-if="loaded"
    :events="events"
    :weekStart="1"
    :dayHighlights="dayHighlights"
    class="full-width"
  />
  <div v-else class="loading">Loading…</div>

  <div class="legend">
    <span class="swatch" style="background:#facc15"></span> Projeto
    <span class="swatch" style="background:#10b981; margin-left: 12px;"></span> Teste
    <span class="swatch" style="background:#fee2e2; margin-left: 12px;"></span> Conflito
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import Calendar from '../../components/Calendar.vue'
import TestDto from '../../models/TestDto'
import EvaluationService from '../../services/EvaluationService'
import ProjectDto from '../../models/ProjectDto'

const events = ref<any[]>([])
const loaded = ref(false)
const dayHighlights = ref<Record<string,string>>({})

onMounted(async () => {
  await getEvents()
  showConflicts()
  loaded.value = true
})

function showConflicts() {
  const grouped: Record<string, any[]> = {}

  for (const e of events.value) {
    const key = new Date(e.date).toISOString().slice(0,10)
    if (!grouped[key]) grouped[key] = []
    grouped[key].push(e)
  }

  const highlights: Record<string,string> = {}
  for (const key in grouped) {
    if (grouped[key].length > 1) {
      highlights[key] = '#fee2e2'
    }
  }

  dayHighlights.value = highlights
}

async function getEvents() {
  try {
    const projects = await EvaluationService.getProjects()
    const tests = await EvaluationService.getTests()

    const tempEvents: any[] = []

    for (let i = 0; i < tests.length; i++) {
      const test: TestDto = tests[i]
      tempEvents.push({
        title: 'Teste: ' + test.title,
        date: test.testDate,
        color: '#10b981',
      })
    }

    for (let i = 0; i < projects.length; i++) {
      const project: ProjectDto = projects[i]
      tempEvents.push({
        title: 'Projeto: ' + project.title,
        date: project.deadline,
        color: '#facc15'
      })
    }

    events.value = tempEvents  
  } catch (error) {
    console.error('Error fetching tests and projects: ', error)
  }
}
</script>

<style scoped>
v-btn {
  margin-top: 50px;
}

.legend { margin-top: 12px; font-size: 14px; display: flex; align-items: center; }
.swatch { width: 12px; height: 12px; display: inline-block; border-radius: 3px; margin-right: 4px; }
</style>
