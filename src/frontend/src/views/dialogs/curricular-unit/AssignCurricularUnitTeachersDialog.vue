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
        <v-card-title>Adicionar Professores</v-card-title>

        <v-card-text v-if="availableTeachers.length">
          <v-checkbox 
            v-for="teacher in availableTeachers"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher"
            v-model="selectedTeachers"
          ></v-checkbox>
        </v-card-text>

        <v-card-text v-else>
          Sem professores.
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
            @click="saveTeachers"
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'
import PersonDto from '../../../models/PersonDto'
import CurricularUnitService from '../../../services/CurricularUnitService'
import PersonService from '../../../services/PersonService'

const props = defineProps<{ 
  id: number,
  curricularUnitTeachers: PersonDto[]
}>()

const dialog = ref(false)
const availableTeachers = ref<PersonDto[]>([])
const selectedTeachers = ref<PersonDto[]>([])

const emit = defineEmits(['teachers-updated'])


onMounted(() => {
  getAvailableTeachers()
})

watch(dialog, async (val) => {
  if (val) {
    await getAvailableTeachers()
  }
})

async function getAvailableTeachers() {
  try {
    const response = await PersonService.getTeachers()
    availableTeachers.value = response
    console.log('available teachers: ', availableTeachers.value)
  } catch (error) {
    console.error('Error getting available teachers: ', error)
  }
}

async function assignCurricularUnitTeachers() {
  try {
    await CurricularUnitService.assignCurricularUnitTeachers(props.id, selectedTeachers.value)
    selectedTeachers.value = []
    emit('teachers-updated')
    return true
  } catch (error) {
    console.error("Error assigning curricular unit teachers: ", error)
    return false
  }
}

async function saveTeachers() {
  const success = await assignCurricularUnitTeachers()
  if (success) {
    await getAvailableTeachers()
    dialog.value = false
  }
}
</script>