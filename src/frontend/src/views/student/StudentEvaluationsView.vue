<template>
  <template v-if="student">
    <h1>Testes</h1>
    <StudentGradesList :student="student" />
  </template>
  <h1 v-else>Nao há nenhum aluno registado no sistema.</h1>
</template>

<script setup lang="ts">
import StudentGradesList from './StudentGradesList.vue';
import PersonService from '../../services/PersonService';
import PersonDto from '../../models/PersonDto';
import { onMounted, ref } from 'vue';

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