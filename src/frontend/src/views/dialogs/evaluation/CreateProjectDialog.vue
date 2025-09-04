<template>
  <div class="pa-4 text-center">
    <v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Adicionar Projetos"
          v-bind="activatorProps"
          color="primary"
        ></v-btn>
      </template>

      <v-card prepend-icon="mdi-account" title="Novo Projeto">
        <v-card-text>
          <v-text-field label="Título*" required v-model="newProject.title"></v-text-field>
          <v-slider label="Peso*" :min="5" :max="100" :step="1" thumb-label required v-model="newProject.weight"></v-slider>
          <v-number-input
            :reverse="false"
            controlVariant="default"
            label="Tamanho Máximo dos Grupos*"
            :hideInput="false"
            :inset="false"
            :min="1"
            :max="7"
            v-model="newProject.maxGroupSize"
          ></v-number-input>
          <v-date-picker
              color="primary"
              v-model="newProject.deadline"
              :min="today"
          ></v-date-picker>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn text="Close" variant="plain" @click="dialog = false"></v-btn>

          <v-btn
            color="primary"
            text="Save"
            variant="tonal"
            @click="
              dialog = false,
              createProject()
            "
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import ProjectDto from '../../../models/ProjectDto'
import EvaluationService from '../../../services/EvaluationService'

const dialog = ref(false)

const today: string = new Date().toISOString().split('T')[0]

const emit = defineEmits(['evaluation-created'])
const props = defineProps<{
  id: number
}>()

const newProject = ref<ProjectDto>({
  title: '',
  weight: 0,
  deadline: '',
  curricularUnitId: props.id,
  maxGroupSize: null
})

const createProject = async () => {
  try {
    console.log('Creating project: ', newProject.value)
    await EvaluationService.createProject(newProject.value)
    emit('evaluation-created')
  } catch (error) {
    console.error("Error creating project: ", error)
  }

  newProject.value = {
    title: '',
    weight: 0,
    deadline: '',
    curricularUnitId: props.id,
    maxGroupSize: null
  }
}
</script>
