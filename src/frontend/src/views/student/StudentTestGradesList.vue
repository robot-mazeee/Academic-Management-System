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
  </v-data-table>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue'
import EvaluationService from '../../services/EvaluationService'
import TestGradeDto from '../../models/TestGradeDto'
import PersonDto from '../../models/PersonDto'
import FileDownload from '../../components/file/FileDownload.vue'

let search = ref('')
let loading = ref(true)

const grades: TestGradeDto[] = reactive([])

const props = defineProps<{
  student: PersonDto
}>()

const headers = [
  { title: 'ID', key: 'id', value: 'id', sortable: true, filterable: false },
  { title: 'Teste', key: 'test', sortable: true, filterable: true },
  { title: 'Peso', key: 'weight', sortable: true, filterable: true },
  { title: 'Nota', key: 'grade', sortable: true, filterable: true },
  { title: 'Enunciado', key: 'testSheet', sortable: false, filterable: false },
  { title: 'Correção', key: 'correction', sortable: false, filterable: false }
]

onMounted(async () => {
	await getStudentTestGrades()
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

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
