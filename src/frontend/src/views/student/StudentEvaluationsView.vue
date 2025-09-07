<template>
	<v-container>
		<v-row justify="center">
			<v-col cols="12" v-if="student">
        <h1>Aluno: {{ student.name }}</h1>
        <v-tabs v-model="activeTab" background-color="primary" dark>
          <v-tab value="tests">Notas de Testes</v-tab>
          <v-tab value="projects">Submissões de Projetos</v-tab>
          <v-tab value="enrollments">Unidades Currculares</v-tab>
        </v-tabs>

        <v-window v-model="activeTab" class="mt-4">
          <v-window-item value="tests">
            <div>
              <StudentTestGradesList :student="student" />
            </div>
          </v-window-item>
          <v-window-item value="projects">
            <div>
              <StudentProjectSubmissionsView :student-id="student.id" />
            </div>
          </v-window-item>
          <v-window-item value="enrollments">
            <div>
              <StudentEnrollmentsView
                :key="activeTab === 'enrollments' ? Date.now() : 'dummy'"
                :student-id="student.id"
              />
            </div>
          </v-window-item>
        </v-window>
			</v-col>
      <v-col v-else>
        <h1>Nenhum aluno registado no sistema.</h1>
      </v-col>
		</v-row>
	</v-container>
</template>

<script setup lang="ts">
import StudentTestGradesList from './StudentTestGradesList.vue';
import PersonService from '../../services/PersonService';
import PersonDto from '../../models/PersonDto';
import { onMounted, ref } from 'vue';
import StudentProjectSubmissionsView from './StudentProjectSubmissionsView.vue';
import StudentEnrollmentsView from './StudentEnrollmentsView.vue';

const activeTab = ref('tests')

const student = ref<PersonDto | null>(null)

onMounted(async () => {
  await fetchStudent()
})

async function fetchStudent() { 
	console.log('Fetching first student')
	try {
    const students = await PersonService.getStudents()
    if (students) {
      student.value = students[0]
    }
	} catch (error) {
		console.error("Error fetching first student: ", error)
	}
}
</script>