<template>
  <v-row align="center">
    <v-col>
      <h2 class="text-left ml-1">Lista de Unidades Curriculares</h2>
    </v-col>
    <v-col cols="auto">
      <CreateCurricularUnitDialog v-if="roleStore.isAdministrator" @curricular-unit-created="getCurricularUnits" />
    </v-col>
  </v-row>

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
    :items="curricularUnits"
    :search="search"
    :loading="loading"
    :custom-filter="fuzzySearch"
    item-key="id"
    class="text-left"
    no-data-text="Sem unidades curriculares a apresentar."
  >
    <template v-slot:[`item.actions`]="{ item }">
			<div class="d-flex align-center justify-center ga-2" v-if="roleStore.isAdministrator">
				<EditCurricularUnitDialog :curricular-unit-to-edit="item" @curricular-unit-edited="getCurricularUnits" />
				<v-icon @click="deleteCurricularUnit(item)" color="red" class="cursor-pointer">
					mdi-delete
				</v-icon>
			</div>

      <div v-if="roleStore.isMainTeacher">
        <v-btn @click="openPeopleManagementView(item.id)" class="mb-3" color="primary">
          Gerir Pessoas
        </v-btn>
      </div>

      <div>
        <v-btn @click="openEvaluationManagementView(item.id)" class="mb-3" color="contrast">
          Gerir Avaliações
        </v-btn>
      </div>
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
import CreateCurricularUnitDialog from '../dialogs/curricular-unit/CreateCurricularUnitDialog.vue'
import EditCurricularUnitDialog from '../dialogs/curricular-unit/EditCurricularUnitDialog.vue'
import CurricularUnitDto from '../../models/CurricularUnitDto'
import { reactive, ref } from 'vue'
import { onMounted } from 'vue'
import CurricularUnitService from '../../services/CurricularUnitService'
import { useRoleStore } from '../../stores/role'
import { useRouter } from 'vue-router'

let search = ref('')
let loading = ref(true)

const curricularUnits: CurricularUnitDto[] = reactive([])
const roleStore = useRoleStore();
const router = useRouter()

const headers = [
  { title: 'ID', key: 'id', value: 'id', sortable: true, filterable: false },
  {
    title: 'Código',
    key: 'code',
    value: 'code',
    sortable: true,
    filterable: true
  },
  {
    title: 'Nome',
    key: 'name',
    value: 'name',
    sortable: true,
    filterable: true
  },
  {
    title: 'Semestre',
    key: 'semester',
    value: 'semester',
    sortable: true,
    filterable: true
  },
  {
    title: 'Curso',
    key: 'course',
    value: 'course',
    sortable: true,
    filterable: true
  },
  {
    title: 'Professor Regente',
    key: 'mainTeacher',
    value: 'mainTeacher',
    sortable: true,
    filterable: true
  },
  {
    title: 'Ações',
    key: 'actions',
    value: 'actions',
    sortable: false,
    filterable: false,
    align: 'center'
  }
]

onMounted(() => {
	getCurricularUnits()
})

async function getCurricularUnits() { 
	curricularUnits.splice(0, curricularUnits.length)
	try {
		curricularUnits.push(...(await CurricularUnitService.getCurricularUnits()))
	} catch (error) {
		console.error("Error getting curricularunits: ", error)
	}

	loading.value = false
  console.log(curricularUnits)
}

const deleteCurricularUnit = async (curricularUnit: CurricularUnitDto) => {
	console.log("Deleting curricular unit:", curricularUnit)
	try {
		await CurricularUnitService.deleteCurricularUnit(curricularUnit)
		await getCurricularUnits()
	} catch (error) {
		console.error("Error deleting curricular unit: ", error)
	}
}

function openPeopleManagementView(id: number) {
  router.push({ name: 'curricular-unit-people-management', params: { id } })
}

function openEvaluationManagementView(id: number) {
  router.push({ name: 'curricular-unit-evaluations-management', params: { id } })
}

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
