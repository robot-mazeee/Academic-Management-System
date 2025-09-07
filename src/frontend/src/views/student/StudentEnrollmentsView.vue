<template>
  <h2 class="text-left ml-1">Lista de Inscrições</h2>

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
    no-data-text="Sem inscrições a apresentar."
  >
    <template v-slot:[`item.curricularUnit`]="{ item }">
      {{ curricularUnitsMap[item.curricularUnitId] || 'Loading...' }}
    </template>

    <template v-slot:[`item.status`]="{ item }">
      <v-chip :color="getColorByStatus(item.status)" text-color="white">
        {{ translateStatus(item.status) }}
      </v-chip>
    </template>

    <template v-slot:[`item.finalGrade`]="{ item }">
      {{ item.finalGrade }}
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
import CurricularUnitService from '../../services/CurricularUnitService'
import { reactive, ref } from 'vue'
import { onMounted } from 'vue'
import EnrollmentDto from '../../models/EnrollmentDto'
import { getColorByStatus, translateStatus } from '../../mappings/enrollmentMappings'

let search = ref('')
let loading = ref(true)

const enrollments: EnrollmentDto[] = reactive([])
const props = defineProps<{
  studentId: number
}>()
const curricularUnitsMap = reactive<Record<number, string>>({})

const headers = [
  { title: 'ID', key: 'id', value: 'id', sortable: true, filterable: false },
  {
    title: 'Unidade Curricular',
    key: 'curricularUnit',
    sortable: true,
    filterable: true
  },
  {
    title: 'Estado Atual',
    key: 'status',
    sortable: true,
    filterable: true
  },
  {
    title: 'Nota Final Atual',
    key: 'finalGrade',
    sortable: true,
    filterable: true
  }
]

onMounted(() => {
	getStudentEnrollments()
})

async function getStudentEnrollments() { 
  enrollments.splice(0, enrollments.length)
  loading.value = true
  try {
    const data = await CurricularUnitService.getStudentEnrollments(props.studentId)
    enrollments.push(...data)

    for (const enrollment of data) {
      const cu = await CurricularUnitService.getCurricularUnit(enrollment.curricularUnitId)
      curricularUnitsMap[enrollment.curricularUnitId] = cu.name
    }
  } catch (error) {
    console.error("Error getting enrollments: ", error)
  }

  loading.value = false
}

const fuzzySearch = (value: string, search: string) => {
  if (!value) return false
  const searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
