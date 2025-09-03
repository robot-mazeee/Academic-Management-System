<template>
  <h2>Notas</h2>

  <v-text-field
    v-model="search"
    label="Search"
    prepend-inner-icon="mdi-magnify"
    variant="outlined"
    hide-details
    single-line
  />

  <v-data-table
    :headers="headers"
    :items="filteredUngradedStudents"
    :loading="loading"
    item-value="id"
    title="Alunos sem Nota"
    no-data-text="Sem alunos a apresentar."
  >
    <template v-slot:[`item.test`]="{ item }">
      {{ test.title }}
    </template>

    <template v-slot:[`item.student`]="{ item }">
      {{ item.name }} ({{ item.istId }})
    </template>

    <template v-slot:[`item.grade`]="{ item }">
      <v-text-field
        v-model="gradesDraft[item.id]"
        label="Nota"
        type="number"
      />
      <v-btn
        @click="gradeTest(item, gradesDraft[item.id])"
        color="secondary"
        class="mb-3"
      >
        Submit
      </v-btn>
    </template>

    <template v-slot:[`item.testCorrection`]="{ item }">
      <div
        class="d-flex align-center justify-center ga-2"
        v-if="roleStore.isMainTeacher || roleStore.isTeachingAssistant"
      >
        <FileUpload :test="test"/>
      </div>
    </template>
  </v-data-table>

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
    <template v-slot:[`item.test`]="{ item }">
      {{ item.test.title }}
    </template>

    <template v-slot:[`item.student`]="{ item }">
      {{ item.student.name }} ({{ item.student.istId }})
    </template>

    <template v-slot:[`item.grade`]="{ item }">
      {{ item.grade }}
    </template>

    <template v-slot:[`item.testCorrection`]="{ item }">
      <div
        class="d-flex align-center justify-center ga-2"
        v-if="roleStore.isMainTeacher || roleStore.isTeachingAssistant"
      >
        <FileUpload :test="test" />
      </div>
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted, computed } from 'vue'
import { useRoute } from 'vue-router'
import EvaluationService from '../../../services/EvaluationService'
import TestGradeDto from '../../../models/TestGradeDto'
import CurricularUnitService from '../../../services/CurricularUnitService'
import PersonDto from '../../../models/PersonDto'
import TestDto from '../../../models/TestDto'
import { useRoleStore } from '../../../stores/role'
import FileUpload from '../../../components/file/FileUpload.vue'

const search = ref('')
const loading = ref(true)

const grades: TestGradeDto[] = reactive([])
const students: PersonDto[] = reactive([])

// store draft grades per student id
const gradesDraft: Record<number, number | null> = reactive({})

const roleStore = useRoleStore()

const ungradedStudents = computed(() => {
  const gradedIds = new Set(grades.map(g => g.student.id))
  return students.filter(s => !gradedIds.has(s.id))
})

const filteredUngradedStudents = computed(() => {
  if (!search.value) return ungradedStudents.value
  return ungradedStudents.value.filter(s => fuzzySearch(s.name, search.value))
})

const route = useRoute()
const curricularUnitId = Number(route.params.curricularUnitId)
const testId = Number(route.params.testId)
const test = ref<TestDto | null>(null)

const headers = [
  { title: 'ID', key: 'id', sortable: true },
  { title: 'Teste', key: 'test', sortable: true },
  { title: 'Aluno', key: 'student', sortable: true },
  { title: 'Nota', key: 'grade', sortable: true },
  { title: 'Correção', key: 'testCorrection', sortable: false }
]

onMounted(async () => {
  await getTest()
  await getCurricularUnitStudents()
  await getTestGrades()
})

async function getTest() {
  try {
    test.value = await EvaluationService.getTest(testId)
  } catch (error) {
    console.error('Error fetching test: ', error)
  }
}

async function getCurricularUnitStudents() {
  try {
    const response = await CurricularUnitService.getCurricularUnitStudents(curricularUnitId)
    students.push(...response)
  } catch (error) {
    console.error('Error fetching curricular unit students: ', error)
  }
}

async function getTestGrades() {
  grades.splice(0, grades.length)
  try {
    grades.push(...(await EvaluationService.getTestGrades(curricularUnitId, testId)))
  } catch (error) {
    console.error('Error getting grades: ', error)
  }
  loading.value = false
}

async function gradeTest(student: PersonDto, grade: number | null) {
  if (!test.value || grade == null) return
  const testGrade: TestGradeDto = {
    test: test.value,
    student,
    grade
  }
  try {
    const response = await EvaluationService.createTestGrade(testGrade)
    console.log('created test grade: ', response)
    await getTestGrades() // refresh after submit
  } catch (error) {
    console.error('Error creating test grade: ', error)
  }
}

function fuzzySearch(value: string, search: string) {
  const searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>