<template>
  <div class="pa-4 text-center">
    <v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Selecionar Alunos"
          v-bind="activatorProps"
          color="contrast"
        ></v-btn>
      </template>

      <v-card>
        <v-card-title>Selecione/Remova Alunos</v-card-title>
        <v-card-text>
          <v-checkbox 
            v-for="student in students"
            :key="student.id"
            :label="student.name"
            :value="student"
            v-model="selectedStudents"
            hide-details
            density="compact"
          ></v-checkbox>
        </v-card-text>
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
              const success = await assignCurricularUnitStudents();
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
import { onMounted, ref } from 'vue'
import PersonDto from '../../../models/PersonDto'
import CurricularUnitService from '../../../services/CurricularUnitService'
import PersonService from '../../../services/PersonService'

const props = defineProps<{ 
  id: number,
  curricularUnitStudents: PersonDto[]
}>();

const dialog = ref(false);
const students = ref<PersonDto[]>([]);
const selectedStudents = ref<PersonDto[]>(props.curricularUnitStudents);

const emit = defineEmits(['students-updated'])

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

async function assignCurricularUnitStudents() {
  try {
    await CurricularUnitService.assignCurricularUnitStudents(props.id, selectedStudents.value)
    emit('students-updated')
    return true;
  } catch (error) {
    console.log("Error assigning curricular unit students: ", error)
    return false;
  }
}
</script>