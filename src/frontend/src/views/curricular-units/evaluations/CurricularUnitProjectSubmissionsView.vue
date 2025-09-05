<template>
  <h2>Submissões</h2>

  <v-text-field
    v-model="search"
    label="Search"
    prepend-inner-icon="mdi-magnify"
    variant="outlined"
    hide-details
    single-line
  />

  <v-data-table
    :headers="headers"
    :items="projectSubmissions"
    :search="search"
    :loading="loading"
    :custom-filter="fuzzySearch"
    item-key="id"
    class="text-left"
    no-data-text="Sem submissões a apresentar."
    title="Submissões"
  >
    <template v-slot:[`item.project`]="{ item }">
      {{ item.project?.title }}
    </template>

    <template v-slot:[`item.group`]="{ item }">
      <div v-for="student in item.group.members">
        {{ student.name }} ({{ student.istId }})
      </div>
    </template>

    <template v-slot:[`item.date`]="{ item }">
      {{ formatDate(item.subDateTime) }}
    </template>

    <template v-slot:[`item.grade`]="{ item }">
      <div v-if="beforeSubmissionDeadline(item)">
        Período de Submissões a decorrer.
      </div>
      <div v-else-if="item.grade !== null">
        {{ item.grade }}
      </div>
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import EvaluationService from '../../../services/EvaluationService'
import ProjectSubmissionDto from '../../../models/ProjectSubmissionDto'
import { useRoleStore } from '../../../stores/role'
import ProjectDto from '../../../models/ProjectDto'

const search = ref('')
const loading = ref(true)

const route = useRoute()
const projectId = Number(route.params.projectId)

const project = ref<ProjectDto | null>(null)
const projectSubmissions: ProjectSubmissionDto[] = reactive([])

const roleStore = useRoleStore()

const headers = [
  { title: 'ID', key: 'id', sortable: true },
  { title: 'Projeto', key: 'project', sortable: true },
  { title: 'Grupo', key: 'group', sortable: true },
  { title: 'Data de Submissão', key: 'date', sortable: true },
  { title: 'Nota', key: 'grade', sortable: true }
]

onMounted(async () => {
  await getProject()
  await getProjectSubmissions()
})

async function getProject() {
  try {
    console.log('Fetching project: ', projectId)
    project.value = await EvaluationService.getProject(projectId)
    console.log('Fetched project: ', project.value)
  } catch (error) {
    console.error('Error fetching project: ', error)
  }
}

async function getProjectSubmissions() {
  projectSubmissions.splice(0, projectSubmissions.length)
  try {
    projectSubmissions.push(...(await EvaluationService.getProjectSubmissionsByProject(projectId)))
    console.log('Fetched test project submissions: ', projectSubmissions)
  } catch (error) {
    console.error('Error getting project submissions: ', error)
  }
  loading.value = false
}

function beforeSubmissionDeadline(submission: ProjectSubmissionDto) {
  const deadline = new Date(submission.date)

  const today = new Date()
  today.setHours(0, 0, 0, 0)

  if (deadline < today) {
    console.log("Deadline has passed")
    return false
  } else {
    console.log("Deadline has not passed yet")
    return true
  }
}

function formatDate(dateStr: string) {
  const date = new Date(dateStr)
  return date.toLocaleString('pt-PT', { 
    day: '2-digit', month: '2-digit', year: 'numeric', 
    hour: '2-digit', minute: '2-digit' 
  })
}

function fuzzySearch(value: string, search: string) {
  const searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>