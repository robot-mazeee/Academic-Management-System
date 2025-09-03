<template>
  <div class="pa-4 text-center">
    <v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Adicionar Alunos"
          v-bind="activatorProps"
          color="contrast"
        ></v-btn>
      </template>

      <v-card>
        <v-card-title>Adicionar Alunos</v-card-title>
        <v-card-text v-if="unenrolledStudents.length">
          <v-checkbox 
            v-for="student in unenrolledStudents"
            :key="student.id"
            :label="`${student.name} (${student.istId})`"
            :value="student"
            v-model="selectedStudents"
            no-data-text="Sem alunos."
          ></v-checkbox>
        </v-card-text>
        <v-card-text v-else>Sem alunos.</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn 
            text="Close" 
            variant="plain" 
            @click="dialog = false">
          </v-btn>

          <v-btn
            text="Save"
            color="primary"
            variant="tonal"
            @click="async () => {
              const success = await createEnrollments();
              if (success) 
                dialog = false;
            }"
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, computed } from 'vue'
import PersonDto from '../../../models/PersonDto'
import CurricularUnitService from '../../../services/CurricularUnitService'
import PersonService from '../../../services/PersonService'
import EnrollmentDto from '../../../models/EnrollmentDto';

const props = defineProps<{ 
  curricularUnitId: number
  enrollments: EnrollmentDto[]
}>();

const dialog = ref(false);
const students = ref<PersonDto[]>([]);
const selectedStudents = ref<PersonDto[]>([])

const unenrolledStudents = computed(() => {
  const enrolledStudentIds = new Set(props.enrollments.map(e => e.student.id));
  return students.value.filter(student => !enrolledStudentIds.has(student.id));
})

const emit = defineEmits(['enrollments-updated'])

onMounted(() => {
  getStudents();
})

async function getStudents() {
  console.log('getting students!')

  try {
    const response = await PersonService.getStudents()
    students.value = response;
    console.log('Students: ', students.value)
  } catch (error) {
    console.error('Error getting students: ', error)
  }
}

async function createEnrollments() {
  try {
    const promises = selectedStudents.value.map(student => {
      const enrollmentDto: EnrollmentDto = {
        student,
        curricularUnitId: props.curricularUnitId,
        status: 'ENROLLED'
      }
      return CurricularUnitService.createEnrollment(enrollmentDto)
    })

    await Promise.all(promises)
  
    emit('enrollments-updated')
    selectedStudents.value = []
    return true
  } catch (error) {
    console.log("Error assigning curricular unit students: ", error)
    return false
  }
}

</script>