<template>
  <h2 class="text-left ml-1">Lista de Projetos da UC</h2>

  <v-text-field
    v-model="search"
    label="Search"
    prepend-inner-icon="mdi-magnify"
    variant="outlined"
    hide-details
    single-line
  ></v-text-field>

  <v-data-table
    :headers="headers"
    :items="projects"
    :search="search"
    :loading="loading"
    :custom-filter="fuzzySearch"
    item-key="id"
    class="text-left"
    no-data-text="Sem projetos a apresentar."
  >
    <template v-slot:[`item.projectSheet`]="{ item }">
      <div class="d-flex align-center ga-2">
        <template v-if="item.projectSheet">
          <FileDownload :file-name="item.projectSheet" />
          <FileUpload
            v-if="roleStore.isMainTeacher || roleStore.isTeachingAssistant"
            @file-uploaded="(fileName) => assignProjectSheet(item.id, fileName)"
          />
        </template>

        <template v-else>
          <FileUpload
            v-if="roleStore.isMainTeacher || roleStore.isTeachingAssistant"
            @file-uploaded="(fileName) => assignProjectSheet(item.id, fileName)"
          />
        </template>
      </div>
    </template>
    <template v-slot:[`item.submissions`]="{ item }" v-if="roleStore.isTeachingAssistant || roleStore.isMainTeacher">
      <v-btn @click="openProjectSubmissionsManagementView(curricularUnitId, item.id)" class="mb-3" color="secondary">
        Submissões
      </v-btn>
    </template>

    <template v-slot:[`item.actions`]="{ item }" v-if="roleStore.isMainTeacher">
			<div class="d-flex align-center justify-center ga-2">
				<EditProjectDialog :project-to-edit="item" @project-edited="getCurricularUnitProjects" />
				<v-icon @click="deleteProject(item.id)" color="red" class="cursor-pointer">
					mdi-delete
				</v-icon>
			</div>
    </template>
  </v-data-table>

  <CreateProjectDialog 
    v-if="roleStore.isMainTeacher"
    :id="curricularUnitId" 
    @evaluation-created="getCurricularUnitProjects" 
  />
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { reactive, ref, onMounted } from 'vue'
import CurricularUnitService from '../../../services/CurricularUnitService'
import { useRoleStore } from '../../../stores/role'
import FileUpload from '../../../components/file/FileUpload.vue'
import CreateProjectDialog from '../../dialogs/evaluation/CreateProjectDialog.vue'
import EvaluationService from '../../../services/EvaluationService'
import EditProjectDialog from '../../dialogs/evaluation/EditProjectDialog.vue'
import ProjectDto from '../../../models/ProjectDto'
import FileService from '../../../services/FileService'
import FileDownload from '../../../components/file/FileDownload.vue'

let search = ref('')
let loading = ref(true)

const projects: ProjectDto[] = reactive([])
const route = useRoute()
const router = useRouter()
const curricularUnitId = parseInt(route.params.id as string, 10)

const roleStore = useRoleStore()

const headers = [
  { title: 'ID', key: 'id', value: 'id', sortable: true, filterable: false },
  {
    title: 'Título',
    key: 'title',
    value: 'title',
    sortable: true,
    filterable: true
  },
  {
    title: 'Peso',
    key: 'weight',
    value: 'weight',
    sortable: true,
    filterable: true
  },
  {
    title: 'Prazo de Submissão',
    key: 'deadline',
    value: 'deadline',
    sortable: true,
    filterable: true
  },
  {
    title: 'Enunciado',
    key: 'projectSheet',
    value: 'projectSheet',
    sortable: false,
    filterable: false
  },
  {
    title: 'Ver Submissões',
    key: 'submissions',
    value: 'submissions',
    sortable: false,
    filterable: false
  },
  {
    title: 'Ações',
    key: 'actions',
    value: 'actions',
    sortable: false,
    filterable: false
  }
]

onMounted(() => {
	getCurricularUnitProjects()
})

async function getCurricularUnitProjects() { 
	projects.splice(0, projects.length)
	try {
		projects.push(...(await CurricularUnitService.getCurricularUnitProjects(curricularUnitId)))
    console.log('projects: ', projects)
	} catch (error) {
		console.error("Error getting projects: ", error)
	}

	loading.value = false
  console.log(projects)
}

async function deleteProject(projectId: number) {
  console.log('Deleting project: ', projectId)
  try {
    const response = await EvaluationService.deleteProject(projectId)
    await getCurricularUnitProjects()
    console.log('project deleted: ', response)
  } catch (error) {
    console.log('Error deleting project: ', error)
  }
}

async function assignProjectSheet(projectId: number, fileName: string) {
  try {
    console.log('file: ', fileName)
    const response = await FileService.assignProjectSheet(fileName, projectId)
    console.log('Assigned project sheet: ', response)
    await getCurricularUnitProjects()
  } catch (error) {
    console.error("Error assigning project sheet:", error)
  }
}

function openProjectSubmissionsManagementView(curricularUnitId: number, projectId: number) {
  router.push({ name: 'curricular-unit-project-submissions-management', params: { curricularUnitId, projectId } })
}

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
