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
    :items="allStudentsWithGrades"
    :search="search"
    :loading="loading"
    :custom-filter="fuzzySearch"
    item-key="id"
    class="text-left"
    no-data-text="Sem alunos a apresentar."
    title="Notas"
  >
    <template v-slot:[`item.test`]="{ item }">
      {{ item.test?.title }}
    </template>

    <template v-slot:[`item.student`]="{ item }">
      {{ item.student.name }} ({{ item.student.istId }})
    </template>

    <template v-slot:[`item.grade`]="{ item }">
      <div v-if="item.grade !== null">
        {{ item.grade }}
      </div>
      <div v-else>
        <v-text-field
          v-model="gradesDraft[item.id]"
          label="Nota"
          type="number"
        />
        <v-btn
          @click="gradeTest(item.student, gradesDraft[item.id])"
          color="secondary"
          class="mb-3"
        >
          Submit
        </v-btn>
      </div>
    </template>

    <template v-slot:[`item.correction`]="{ item }">
      <div class="d-flex align-center ga-2">
        <template v-if="item.correction">
          <FileDownload :file-name="item.correction" />
          <FileUpload
            v-if="roleStore.isMainTeacher || roleStore.isTeachingAssistant"
            @file-uploaded="(fileName) => assignCorrection(item.id, fileName)"
          />
        </template>

        <template v-else>
          <FileUpload
            v-if="roleStore.isMainTeacher || roleStore.isTeachingAssistant"
            @file-uploaded="(fileName) => assignCorrection(item.id, fileName)"
          />
        </template>
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
import FileService from '../../../services/FileService'
import FileDownload from '../../../components/file/FileDownload.vue'

const search = ref('')
const loading = ref(true)

const route = useRoute()
const curricularUnitId = Number(route.params.curricularUnitId)
const testId = Number(route.params.testId)

const grades: TestGradeDto[] = reactive([])
const students: PersonDto[] = reactive([])
const test = ref<TestDto | null>(null)
const gradesDraft: Record<number, number | null> = reactive({})

const roleStore = useRoleStore()

const allStudentsWithGrades = computed(() => {
  return students.map(student => {
    const grade = grades.find(g => g.student.id === student.id) || null
    return {
      id: student.id,
      student,
      grade: grade?.grade ?? null,
      test: test.value,
      correction: grade?.correction ?? null
    }
  })
})

const headers = [
  { title: 'ID', key: 'id', sortable: true },
  { title: 'Teste', key: 'test', sortable: true },
  { title: 'Aluno', key: 'student', sortable: true },
  { title: 'Nota', key: 'grade', sortable: true },
  { title: 'Correção', key: 'correction', sortable: false }
]

onMounted(async () => {
  await getTest()
  await getCurricularUnitStudents()
  await getTestGrades()
})

async function getTest() {
  try {
    console.log('Fetching test: ', testId)
    test.value = await EvaluationService.getTest(testId)
    console.log('Fetched test: ', test.value)
  } catch (error) {
    console.error('Error fetching test: ', error)
  }
}

async function getCurricularUnitStudents() {
  try {
    const response = await CurricularUnitService.getCurricularUnitStudents(curricularUnitId)
    students.push(...response)
    console.log('Fetched curricular unit students: ', students)
  } catch (error) {
    console.error('Error fetching curricular unit students: ', error)
  }
}

async function getTestGrades() {
  grades.splice(0, grades.length)
  try {
    grades.push(...(await EvaluationService.getTestGrades(curricularUnitId, testId)))
    console.log('Fetched test grades: ', grades)
  } catch (error) {
    console.error('Error getting grades: ', error)
  }
  loading.value = false
}

async function gradeTest(student: PersonDto, grade: number | null) {
  if (!test.value || grade == null) 
    return
  const testGrade: TestGradeDto = {
    test: test.value,
    student,
    grade
  }
  try {
    const response = await EvaluationService.createTestGrade(testGrade)
    console.log('created test grade: ', response)
    await getTestGrades()
  } catch (error) {
    console.error('Error creating test grade: ', error)
  }
}

async function assignCorrection(testGradeId: number, fileName: string) {
  try {
    console.log('file: ', fileName)
    const response = await FileService.assignTestGradeCorrection(fileName, testGradeId)
    console.log('Assigned correction: ', response)
    await getTestGrades()
  } catch (error) {
    console.error("Error assigning correction:", error)
  }
}

function fuzzySearch(value: string, search: string) {
  const searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>