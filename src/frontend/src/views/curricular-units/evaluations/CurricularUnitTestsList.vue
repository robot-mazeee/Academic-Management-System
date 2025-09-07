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
    <template v-slot:[`item.testSheet`]="{ item }">
      <div class="d-flex align-center ga-2">
        <template v-if="item.testSheet">
          <FileDownload :file-name="item.testSheet" />
          <FileUpload
            v-if="roleStore.isMainTeacher || roleStore.isTeachingAssistant"
            @file-uploaded="(fileName) => assignTestSheet(item.id, fileName)"
          />
        </template>

        <template v-else>
          <FileUpload
            v-if="roleStore.isMainTeacher || roleStore.isTeachingAssistant"
            @file-uploaded="(fileName) => assignTestSheet(item.id, fileName)"
          />
        </template>
      </div>
    </template>
    <template v-slot:[`item.grades`]="{ item }">
      <v-btn @click="openTestGradesManagementView(curricularUnitId, item.id)" class="mb-3" color="secondary">
        Notas
      </v-btn>
    </template>
    <template v-slot:[`item.testDate`]="{ item }" v-if="roleStore.isTeachingAssistant || roleStore.isMainTeacher">
      {{ formatDate(item.testDate) }}
    </template>
    <template v-slot:[`item.actions`]="{ item }" v-if="roleStore.isMainTeacher">
			<div class="d-flex align-center justify-center ga-2">
				<EditTestDialog :test-to-edit="item" @test-edited="getCurricularUnitTests" />
				<DeleteButton :fn="deleteTest" :item="item" />
			</div>
    </template>
  </v-data-table>

  <CreateTestDialog 
    v-if="roleStore.isMainTeacher"
    :id="curricularUnitId" 
    @evaluation-created="getCurricularUnitTests" 
  />
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { reactive, ref, onMounted } from 'vue'
import TestDto from '../../../models/TestDto'
import CurricularUnitService from '../../../services/CurricularUnitService'
import CreateTestDialog from '../../dialogs/evaluation/CreateTestDialog.vue'
import { useRoleStore } from '../../../stores/role'
import FileUpload from '../../../components/file/FileUpload.vue'
import EvaluationService from '../../../services/EvaluationService'
import FileService from '../../../services/FileService'
import EditTestDialog from '../../dialogs/evaluation/EditTestDialog.vue'
import FileDownload from '../../../components/file/FileDownload.vue'
import { formatDate } from '../../../mappings/dateMappings'
import DeleteButton from '../../../components/buttons/DeleteButton.vue'

let search = ref('')
let loading = ref(true)

const tests: TestDto[] = reactive([])
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
    title: 'Data',
    key: 'testDate',
    value: 'testDate',
    sortable: true,
    filterable: true
  },
  {
    title: 'Enunciado',
    key: 'testSheet',
    value: 'testSheet',
    sortable: false,
    filterable: false
  },
  {
    title: 'Gerir Notas',
    key: 'grades',
    value: 'grades',
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
	getCurricularUnitTests()
})

async function getCurricularUnitTests() { 
	tests.splice(0, tests.length)
	try {
		tests.push(...(await CurricularUnitService.getCurricularUnitTests(curricularUnitId)))
    console.log('tests: ', tests)
	} catch (error) {
		console.error("Error getting tests: ", error)
	}

	loading.value = false
  console.log(tests)
}

async function deleteTest(testId: number) {
  console.log('Deleting test: ', testId)
  try {
    const response = await EvaluationService.deleteTest(testId)
    await getCurricularUnitTests()
    console.log('Test deleted: ', response)
  } catch (error) {
    console.log('Error deleting test: ', error)
  }
}

async function assignTestSheet(testId: number, fileName: string) {
  try {
    console.log('file: ', fileName)
    const response = await FileService.assignTestSheet(fileName, testId)
    console.log('Assigned teste sheet: ', response)
    await getCurricularUnitTests()
  } catch (error) {
    console.error("Error assigning test sheet:", error)
  }
}

function openTestGradesManagementView(curricularUnitId: number, testId: number) {
  router.push({ name: 'curricular-unit-test-grades-management', params: { curricularUnitId, testId } })
}

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
