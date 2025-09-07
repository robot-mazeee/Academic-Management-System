<template>
  <h2 class="text-left ml-1">Lista de Professores da UC</h2>

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
    :items="teachers"
    :search="search"
    :loading="loading"
    :custom-filter="fuzzySearch"
    item-key="id"
    class="text-left"
    no-data-text="Sem professores a apresentar."
  >
    <template v-slot:[`item.type`]="{ item }">
      <v-chip :color="getColorByType(item?.type)" text-color="white">
				{{ translateType(item?.type) }}
			</v-chip>
    </template>
    <template v-slot:[`item.actions`]="{ item }" v-if="roleStore.isMainTeacher">
      <div class="d-flex align-center justify-center ga-2" v-if="item?.type !== 'MAIN_TEACHER'">
        <DeleteButton :fn="removeTeachingAssistant" :item="item" />
      </div>
    </template>
  </v-data-table>

  <AssignCurricularUnitTeachersDialog 
    v-if="roleStore.isMainTeacher"
    :curricular-unit-teachers="teachers" 
    :id="curricularUnitId" 
    @teachers-updated="getCurricularUnitTeachers" 
  />
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'
import PersonDto from '../../../models/PersonDto'
import CurricularUnitService from '../../../services/CurricularUnitService'
import { reactive, ref } from 'vue'
import { onMounted } from 'vue'
import AssignCurricularUnitTeachersDialog from '../../dialogs/curricular-unit/AssignCurricularUnitTeachersDialog.vue'
import { useRoleStore } from '../../../stores/role'
import { getColorByType, translateType } from '../../../mappings/peopleMappings'
import DeleteButton from '../../../components/buttons/DeleteButton.vue'

let search = ref('')
let loading = ref(true)

const teachers: PersonDto[] = reactive([])
const teachingAssistants: PersonDto[] = reactive([])
const mainTeacher = ref<PersonDto | null>(null)
const route = useRoute()
const curricularUnitId = parseInt(route.params.id as string, 10)

const roleStore = useRoleStore()

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
  },
  {
    title: 'Tipo',
    key: 'type',
    value: 'type',
    sortable: true,
    filterable: true
  },
  {
    title: 'Ações',
    key: 'actions',
    value: 'actions',
    sortable: true,
    filterable: true
  }
]

onMounted(async () => {
  await getMainTeacher()
	await getCurricularUnitTeachers()
})

async function getMainTeacher() {
  try {
    mainTeacher.value = await CurricularUnitService.getCurricularUnitMainTeacher(curricularUnitId)
    console.log('main teacher: ', mainTeacher.value)
  } catch (error) {
    console.error('Error fetching main teacher: ', error)
  }
}

async function getCurricularUnitTeachers() { 
	teachingAssistants.splice(0, teachers.length)
  teachers.splice(0, teachers.length)
	try {
		teachingAssistants.push(...(await CurricularUnitService.getCurricularUnitTeachers(curricularUnitId)))
    teachers.push(mainTeacher.value, ...teachingAssistants)
	} catch (error) {
		console.error("Error fetching curricular unit teachers: ", error)
	}

	loading.value = false
  console.log('curricular unit teachers: ', teachers)
}

async function removeTeachingAssistant(teacher: PersonDto) {
  console.log('Deleting teaching assistant from curricular unit')
  try {
    await CurricularUnitService.removeTeachingAssistant(curricularUnitId, teacher.id)
    console.log('Teaching assistant deleted')
    await getCurricularUnitTeachers()
  } catch (error) {
    console.error('Error deleting teaching assistant from curricular unit')
  }
}

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
