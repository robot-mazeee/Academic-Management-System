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
    :items="combinedSubmissions"
    :search="search"
    :loading="loading"
    :custom-filter="fuzzySearch"
    item-key="project.id"
    class="text-left"
    no-data-text="Sem submissões a apresentar."
  >
    <template v-slot:[`item.project`]="{ item }">
      {{ item.project?.title }}
    </template>

    <template v-slot:[`item.deadline`]="{ item }">
      <span v-if="item.project?.deadline">
        {{ formatDate(item.project.deadline) }}
      </span>
      <span v-else>Sem prazo</span>
    </template>

    <template v-slot:[`item.date`]="{ item }">
      <span v-if="item.submission?.subDateTime">
        {{ formatDateTime(item.submission.subDateTime) }}
      </span>
      <span v-else>—</span>
    </template>

    <template v-slot:[`item.grade`]="{ item }">
      <div v-if="item.submission && beforeSubmissionDeadline(item.project)">
        Período de submissões a decorrer.
      </div>
      <div v-else-if="item.submission?.grade != null">
        {{ item.submission.grade }}
      </div>
      <div v-else>
        Sem submissão
      </div>
    </template>

    <template v-slot:[`item.projectSheet`]="{ item }">
      <FileDownload v-if="item.project.projectSheet" :file-name="item.project.projectSheet" />
      <span v-else>Não disponível</span>
    </template>

    <template v-slot:[`item.submit`]="{ item }">
      <FileUpload v-if="roleStore.isStudent"
        @file-uploaded="(fileName) => createProjectSubmission(fileName, item.project)"
        :disabled="!beforeSubmissionDeadline(item.project)"
      />
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue'
import EvaluationService from '../../services/EvaluationService'
import ProjectSubmissionDto from '../../models/ProjectSubmissionDto'
import ProjectDto from '../../models/ProjectDto'
import { useRoleStore } from '../../stores/role'
import FileUpload from '../../components/file/FileUpload.vue'
import FileDownload from '../../components/file/FileDownload.vue'
import FileService from '../../services/FileService'
import formatDateTime, { formatDate } from '../../mappings/dateMappings'

const search = ref('')
const loading = ref(true)

const combinedSubmissions = ref<{ project: ProjectDto, submission?: ProjectSubmissionDto }[]>([])
const projects = ref<ProjectDto[]>([])

const roleStore = useRoleStore()

const props = defineProps<{ studentId: number }>()

const headers = [
  { title: 'Projeto', key: 'project', sortable: true },
  { title: 'Prazo de Submissão', key: 'deadline', sortable: true },
  { title: 'Data de Submissão', key: 'date', sortable: true },
  { title: 'Enunciado', key: 'projectSheet', sortable: true },
  { title: 'Submeter', key: 'submit', sortable: true },
  { title: 'Nota', key: 'grade', sortable: true }
]

onMounted(async () => {
  await getProjectsAndSubmissions()
})

async function getProjectsAndSubmissions() {
  loading.value = true
  try {
    projects.value = await EvaluationService.getProjects()
    const submissions = await EvaluationService.getProjectSubmissionsByStudent(props.studentId)

    const submissionMap = new Map<number, ProjectSubmissionDto>()
    submissions.forEach((sub: ProjectSubmissionDto) => {
      if (sub.project?.id != null) {
        submissionMap.set(sub.project.id, sub)
      }
    })

    combinedSubmissions.value = projects.value.map((project) => ({
      project,
      submission: submissionMap.get(project.id!)
    }))

    console.log('Projects and submissions:', combinedSubmissions)
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

function beforeSubmissionDeadline(project: ProjectDto) {
  const deadline = new Date(project.deadline)

  const today = new Date()
  today.setHours(0, 0, 0, 0)

  if (deadline < today) {
    return false;
  } else {
    return true;
  }
}

async function createProjectSubmission(fileName: string, project: ProjectDto) {
  console.log('creating project submission')
  try {
    console.log('submission name: ', fileName)
    const response = await EvaluationService.createProjectSubmission(project.id, props.studentId, fileName)
    console.log('Created submission: ', response)
    await getProjectsAndSubmissions()
  } catch (error) {
    console.error('Error creating project submission: ', error)
  }
}

function fuzzySearch(value: string, search: string) {
  const searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>