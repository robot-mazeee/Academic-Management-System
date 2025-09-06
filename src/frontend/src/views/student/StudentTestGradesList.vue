<template>
  <h2 class="text-left ml-1">Notas</h2>

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
    :items="grades"
    :search="search"
    :loading="loading"
    :custom-filter="fuzzySearch"
    item-key="id"
    class="text-left"
    no-data-text="Sem notas a apresentar."
  >
    <template v-slot:[`item.test`]="{ item }">
      {{ item.test.title }}
    </template>
    <template v-slot:[`item.weight`]="{ item }">
      {{ item.test.weight }}
    </template>
    <template v-slot:[`item.grade`]="{ item }">
      {{ item.grade }}
    </template>
    <template v-slot:[`item.testSheet`]="{ item }">
      <FileDownload v-if="item.test.testSheet" :file-name="item.test.testSheet" />
      <span v-else>Não disponível</span>
    </template>
    <template v-slot:[`item.correction`]="{ item }">
      <FileDownload v-if="item.correction" :file-name="item.correction" />
      <span v-else>Não disponível</span>
    </template>
    <template v-slot:[`item.revision`]="{ item }">
      <v-chip
        v-if="roleStore.isStudent && revisions[`${item.student.id}-${item.test.id}`]"
        :color="getColorByStatus(revisions[`${item.student.id}-${item.test.id}`].status)"
        text-color="white"
      >
        {{ revisions[`${item.student.id}-${item.test.id}`].status }}
      </v-chip>
      <CreateRevisionDialog
        v-else-if="roleStore.isStudent && item.grade" 
        :test="item.test" 
        :student="item.student"
        @revision-created="reloadData"
      />
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue'
import EvaluationService from '../../services/EvaluationService'
import TestGradeDto from '../../models/TestGradeDto'
import PersonDto from '../../models/PersonDto'
import FileDownload from '../../components/file/FileDownload.vue'
import CreateRevisionDialog from '../dialogs/revision/CreateRevisionDialog.vue'
import { useRoleStore } from '../../stores/role'
import RevisionService from '../../services/RevisionService'
import { getColorByStatus } from '../../mappings/revisionMappings'

let search = ref('')
let loading = ref(true)

const grades: TestGradeDto[] = reactive([])
const revisions = ref<Record<string, any>>({})

const roleStore = useRoleStore()

const props = defineProps<{
  student: PersonDto
}>()

const headers = [
  { title: 'ID', key: 'id', value: 'id', sortable: true, filterable: false },
  { title: 'Teste', key: 'test', sortable: true, filterable: true },
  { title: 'Peso', key: 'weight', sortable: true, filterable: true },
  { title: 'Nota', key: 'grade', sortable: true, filterable: true },
  { title: 'Enunciado', key: 'testSheet', sortable: false, filterable: false },
  { title: 'Correção', key: 'correction', sortable: false, filterable: false },
  { title: 'Revisão', key: 'revision', sortable: false }
]

onMounted(async () => {
	await getStudentTestGrades()
  await getAllRevisions()
})

async function getStudentTestGrades() { 
	grades.splice(0, grades.length)
	try {
		grades.push(...(await EvaluationService.getStudentTestGrades(props.student.id)))
    console.log('grades: ', grades)
	} catch (error) {
		console.error("Error getting grades: ", error)
	}

	loading.value = false
  console.log(grades)
}

async function getAllRevisions() {
  try {
    const studentRevisions = await RevisionService.getRevisionsByStudent(props.student.id)
    console.log('Student revisions: ', studentRevisions)
    for (const r of studentRevisions) {
      const testId = r.test.id
      const key = `${props.student.id}-${testId}`
      if (!revisions.value[key]) {
        if (r) revisions.value[key] = r
      }
    }
  } catch (error) {
    console.error('Error getting revisions: ', error)
  }
}

async function reloadData() {
  loading.value = true
  await getStudentTestGrades()
  await getAllRevisions()
  loading.value = false
}

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
