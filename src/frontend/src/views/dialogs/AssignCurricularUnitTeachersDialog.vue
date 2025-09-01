<template>
  <div class="pa-4 text-center">
    <v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Selecionar Professores Assistentes"
          v-bind="activatorProps"
          color="contrast"
        ></v-btn>
      </template>

      <v-card>
        <v-card-title>Selecione/Remova Professores</v-card-title>
        <v-card-text>
          <v-checkbox 
            v-for="teacher in teachers"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher"
            v-model="selectedTeachers"
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
              const success = await assignCurricularUnitTeachers();
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
import PersonDto from '../../models/PersonDto'
import CurricularUnitService from '../../services/CurricularUnitService'
import PersonService from '../../services/PersonService'

const props = defineProps<{ 
  id: number,
  curricularUnitTeachers: PersonDto[]
}>();

const dialog = ref(false);
const teachers = ref<PersonDto[]>([]);
const selectedTeachers = ref<PersonDto[]>(props.curricularUnitTeachers);

const emit = defineEmits(['teachers-updated'])

onMounted(() => {
  getAssistantTeachers();
})

async function getAssistantTeachers() {
  console.log('getting assistant teachers!')

  try {
    const response = await PersonService.getAssistantTeachers()
    teachers.value = response;
    console.log('teachers: ', teachers.value)
  } catch (error) {
    console.error('Error getting teachers: ', error)
  }
}

async function assignCurricularUnitTeachers() {
  try {
    await CurricularUnitService.assignCurricularUnitTeachers(props.id, selectedTeachers.value)
    emit('teachers-updated')
    return true;
  } catch (error) {
    console.log("Error assigning curricular unit teachers: ", error)
    return false;
  }
}
</script>