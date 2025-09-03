<template>
  <h2>Notas</h2>

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
    :items="filteredUngradedStudents"
    :loading="loading"
    item-value="id"
    title="Alunos sem Nota"
    no-data-text="Sem alunos a apresentar."
  />

  <v-data-table
    :headers="headers"
    :items="grades"
    :search="search"
    :loading="loading"
    :custom-filter="fuzzySearch"
    item-key="id"
    class="text-left"
    no-data-text="Sem alunos a apresentar."
    title="Alunos com Nota"
  >
    <!-- <template v-slot:[`item.actions`]="{ item }">
			<div class="d-flex align-center justify-center ga-2" v-if="roleStore.isAdministrator">
				<EditCurricularUnitDialog :curricular-unit-to-edit="item" @curricular-unit-edited="getTestGrades" />
				<v-icon @click="deleteCurricularUnit(item)" color="red" class="cursor-pointer">
					mdi-delete
				</v-icon>
			</div>
    </template> -->
  </v-data-table>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted, computed } from 'vue'
import { useRoute } from 'vue-router'
import EvaluationService from '../../../services/EvaluationService'
import TestGradeDto from '../../../models/TestGradeDto'
import CurricularUnitService from '../../../services/CurricularUnitService'
import PersonDto from '../../../models/PersonDto'

let search = ref('')
let loading = ref(true)

const grades: TestGradeDto[] = reactive([])
const students: PersonDto[] = reactive([])

const ungradedStudents = computed(() => {
  const gradedIds = new Set(grades.map(g => g.student.id))
  return students.filter(s => !gradedIds.has(s.id))
})

const filteredUngradedStudents = computed(() => {
  const list = ungradedStudents.value

  if (!search.value) 
    return list

  return list.filter(s => fuzzySearch(s.name, search.value))
})

const route = useRoute()
const curricularUnitId = route.params.curricularUnitId;
const testId = route.params.testId;

const headers = [
  { title: 'ID', key: 'id', value: 'id', sortable: true, filterable: false },
  {
    title: 'Teste',
    key: 'test',
    value: 'test',
    sortable: true,
    filterable: true
  },
  {
    title: 'Aluno',
    key: 'student',
    value: 'student',
    sortable: true,
    filterable: true
  },
  {
    title: 'Nota',
    key: 'grade',
    value: 'grade',
    sortable: true,
    filterable: true
  },
  {
    title: 'Correção',
    key: 'testSheet',
    value: 'testSheet',
    sortable: false,
    filterable: false
  }
]

onMounted(async () => {
	await getCurricularUnitStudents()
  await getTestGrades()
})

async function getCurricularUnitStudents() {
  try {
    const response = await CurricularUnitService.getCurricularUnitStudents(Number(curricularUnitId))
    students.push(...response)
  } catch(error) {
    console.error('Error fetching curricular unit students: ', error)
  }
}

async function getTestGrades() { 
	grades.splice(0, grades.length)
	try {
		grades.push(...(await EvaluationService.getTestGrades(Number(curricularUnitId), Number(testId))))
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