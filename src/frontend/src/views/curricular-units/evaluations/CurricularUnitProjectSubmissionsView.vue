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
      {{ formatDateTime(item.subDateTime) }}
    </template>

    <template v-slot:[`item.grade`]="{ item }">
      <div v-if="beforeSubmissionDeadline(item)">
        Período de Submissões a decorrer.
      </div>
      <div v-else-if="item.grade !== null">
        {{ item.grade }}
      </div>
    </template>

    <template v-slot:[`item.submission`]="{ item }">
      <div
        class="d-flex align-center justify-center ga-2"
        v-if="roleStore.isMainTeacher || roleStore.isTeachingAssistant"
      >
        <FileDownload v-if="item.submission" :file-name="item.submission" />
        <span v-else>Sem submissões</span>
      </div>
    </template>

    <template v-slot:[`item.editGrade`]="{ item }">
      <div>
        <v-text-field
          v-if="roleStore.isTeachingAssistant || roleStore.isMainTeacher"
          v-model="gradesDraft[item.id]"
          label="Nota"
          type="number"
        />
        <v-btn
          v-if="roleStore.isTeachingAssistant || roleStore.isMainTeacher"
          @click="gradeProject(item.id, gradesDraft[item.id])"
          color="secondary"
          class="mb-3"
        >
          Submit
        </v-btn>
      </div>
    </template>
    <template v-slot:[`item.history`]="{ item }">
			<v-btn class="mb-3" color="contrast" @click="displayProjectSubmissionsView(item.project.id, item.group.id)">
        Histórico
      </v-btn>
		</template>
  </v-data-table>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import EvaluationService from '../../../services/EvaluationService'
import ProjectSubmissionDto from '../../../models/ProjectSubmissionDto'
import { useRoleStore } from '../../../stores/role'
import ProjectDto from '../../../models/ProjectDto'
import FileDownload from '../../../components/file/FileDownload.vue'
import formatDateTime from '../../../mappings/dateMappings'

const search = ref('')
const loading = ref(true)

const route = useRoute()
const router = useRouter()
const projectId = Number(route.params.projectId)

const project = ref<ProjectDto | null>(null)
const gradesDraft: Record<number, number | null> = reactive({})
const projectSubmissions: ProjectSubmissionDto[] = reactive([])

const roleStore = useRoleStore()

const headers = [
  { title: 'ID', key: 'id', sortable: true },
  { title: 'Projeto', key: 'project', sortable: true },
  { title: 'Grupo', key: 'group', sortable: true },
  { title: 'Data de Submissão', key: 'date', sortable: true },
  { title: 'Submissão', key: 'submission', sortable: true, align: 'center' },
  { title: 'Nota', key: 'grade', sortable: true },
  { title: 'Ajustar Nota', key: 'editGrade', sortable: true, align: 'center' },
  { title: 'Histórico', key: 'history', sortable: true, align: 'center' },
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
    projectSubmissions.push(...(await EvaluationService.getLatestProjectSubmissionsByProject(projectId)))
    console.log('Fetched test project submissions: ', projectSubmissions)
  } catch (error) {
    console.error('Error getting project submissions: ', error)
  }
  loading.value = false
}

function displayProjectSubmissionsView(projectId: number, groupId: number) {
  router.push({ name: 'project-submissions-history-view', params: { projectId, groupId } })
}

async function gradeProject(submissionId: number, grade: number) {
  try {
    const response = await EvaluationService.updateProjectGrade(submissionId, grade)
    console.log('Updated project submission grade: ', response)
    await getProjectSubmissions()
  } catch (error) {
    console.error('Error adjusting project submission grade: ', error)
  }
}

function beforeSubmissionDeadline(submission: ProjectSubmissionDto) {
  const deadline = new Date(submission.subDateTime)

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

function fuzzySearch(value: string, search: string) {
  const searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>