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
    :items="enrollments"
    :search="search"
    :loading="loading"
    :custom-filter="fuzzySearch"
    item-key="id"
    class="text-left"
    no-data-text="Sem alunos a apresentar."
  >
    <template v-slot:[`item.name`]="{ item }">
      {{ item.student.name }}
    </template>

    <template v-slot:[`item.istId`]="{ item }">
      {{ item.student.istId }}
    </template>

    <template v-slot:[`item.email`]="{ item }">
      {{ item.student.email }}
    </template>

    <template v-slot:[`item.status`]="{ item }">
      <v-chip :color="getColorByStatus(item.status)" text-color="white">
				{{ translateStatus(item.status) }}
			</v-chip>
    </template>

    <template v-slot:[`item.finalGrade`]="{ item }">
      <div v-if="item.status === 'APPROVED'">
        {{ item.finalGrade }}
      </div>
    </template>

    <template v-slot:[`item.actions`]="{ item }" v-if="roleStore.isMainTeacher">
      <div class="d-flex align-center justify-center ga-2"></div>
				<v-icon @click="deleteEnrollment(item)" color="red" class="cursor-pointer">
					mdi-delete
				</v-icon>
    </template>
  </v-data-table>

  <CreateEnrollmentsDialog 
    v-if="roleStore.isMainTeacher"
    :enrollments="enrollments"
    :curricular-unit-id="curricularUnitId" 
    @enrollments-updated="getCurricularUnitEnrollments" 
  />
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'
import CurricularUnitService from '../../../services/CurricularUnitService'
import { reactive, ref } from 'vue'
import { onMounted } from 'vue'
import { useRoleStore } from '../../../stores/role'
import EnrollmentDto from '../../../models/EnrollmentDto'
import { translateStatus } from '../../../mappings/enrollmentMappings'
import { getColorByStatus } from '../../../mappings/enrollmentMappings'
import CreateEnrollmentsDialog from '../../dialogs/curricular-unit/CreateEnrollmentsDialog.vue'

let search = ref('')
let loading = ref(true)

const enrollments: EnrollmentDto[] = reactive([])
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
    title: 'Estado',
    key: 'status',
    value: 'status',
    sortable: true,
    filterable: true
  },
  {
    title: 'Nota Final',
    key: 'finalGrade',
    value: 'finalGrade',
    sortable: true,
    filterable: true
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
	getCurricularUnitEnrollments()
})

async function getCurricularUnitEnrollments() { 
	enrollments.splice(0, enrollments.length)
	try {
		enrollments.push(...(await CurricularUnitService.getCurricularUnitEnrollments(curricularUnitId)))
	} catch (error) {
		console.error("Error getting enrollments: ", error)
	}

	loading.value = false
  console.log('enrollments: ', enrollments)
}

const deleteEnrollment = async (enrollment: EnrollmentDto) => {
	console.log("Deleting enrollment:", enrollment)
	try {
		await CurricularUnitService.deleteEnrollment(enrollment.id)
		await getCurricularUnitEnrollments()
	} catch (error) {
		console.error("Error deleting enrollment: ", error)
	}
}


const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
