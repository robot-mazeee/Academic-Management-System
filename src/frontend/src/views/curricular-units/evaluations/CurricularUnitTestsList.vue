<template>
  <h2 class="text-left ml-1">Lista de Testes da UC</h2>

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
    :items="tests"
    :search="search"
    :loading="loading"
    :custom-filter="fuzzySearch"
    item-key="id"
    class="text-left"
    no-data-text="Sem testes a apresentar."
  >
  </v-data-table>

  <CreateTestDialog 
    v-if="roleStore.isMainTeacher"
    :id="curricularUnitId" 
    @evaluation-created="getCurricularUnitTests" 
  />
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'
import { reactive, ref, onMounted } from 'vue'
import TestDto from '../../../models/TestDto'
import CurricularUnitService from '../../../services/CurricularUnitService'
import CreateTestDialog from '../../dialogs/evaluation/CreateTestDialog.vue'
import { useRoleStore } from '../../../stores/role'

let search = ref('')
let loading = ref(true)

const tests: TestDto[] = reactive([])
const route = useRoute()
const curricularUnitId = parseInt(route.params.id as string, 10);

const roleStore = useRoleStore();

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
    title: 'Data',
    key: 'date',
    value: 'date',
    sortable: true,
    filterable: true
  }
]

onMounted(() => {
	getCurricularUnitTests()
})

async function getCurricularUnitTests() { 
	tests.splice(0, tests.length)
	try {
		tests.push(...(await CurricularUnitService.getCurricularUnitTests(curricularUnitId)))
    console.log('tests: ', tests);
	} catch (error) {
		console.error("Error getting tests: ", error)
	}

	loading.value = false
  console.log(tests)
}

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
