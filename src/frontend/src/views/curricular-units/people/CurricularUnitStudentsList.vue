<template>
  <h2 class="text-left ml-1">Lista de Alunos da UC</h2>

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
    :items="students"
    :search="search"
    :loading="loading"
    :custom-filter="fuzzySearch"
    item-key="id"
    class="text-left"
    no-data-text="Sem alunos a apresentar."
  >
  </v-data-table>

  <AssignCurricularUnitStudentsDialog 
    :curricular-unit-students="students" 
    :id="curricularUnitId" 
    @students-updated="getCurricularUnitStudents" 
  />
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'
import PersonDto from '../../../models/PersonDto'
import CurricularUnitService from '../../../services/CurricularUnitService'
import { reactive, ref } from 'vue'
import { onMounted } from 'vue'
import AssignCurricularUnitStudentsDialog from '../../dialogs/curricular-unit/AssignCurricularUnitStudentsDialog.vue'

let search = ref('')
let loading = ref(true)

const students: PersonDto[] = reactive([])
const route = useRoute()
const curricularUnitId = parseInt(route.params.id as string, 10);

const headers = [
  { title: 'ID', key: 'id', value: 'id', sortable: true, filterable: false },
  {
    title: 'Nome',
    key: 'name',
    value: 'name',
    sortable: true,
    filterable: true
  },
  {
    title: 'IST ID',
    key: 'istId',
    value: 'istId',
    sortable: true,
    filterable: true
  },
  {
    title: 'Email',
    key: 'email',
    value: 'email',
    sortable: true,
    filterable: true
  }
]

onMounted(() => {
	getCurricularUnitStudents()
})

async function getCurricularUnitStudents() { 
	students.splice(0, students.length)
	try {
		students.push(...(await CurricularUnitService.getCurricularUnitStudents(curricularUnitId)))
	} catch (error) {
		console.error("Error getting students: ", error)
	}

	loading.value = false
  console.log(students)
}

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
