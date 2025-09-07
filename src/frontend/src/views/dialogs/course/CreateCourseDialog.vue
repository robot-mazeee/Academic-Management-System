<template>
  <div class="pa-4 text-center">
    <v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Adicionar Curso"
          v-bind="activatorProps"
          color="primary"
        ></v-btn>
      </template>

      <v-card prepend-icon="mdi-account" title="Novo Curso">
        <v-card-text>
          <v-text-field label="Nome*" required v-model="newCourse.name"></v-text-field>
          <v-text-field label="Código*" required v-model="newCourse.code"></v-text-field>
          <v-number-input
            :reverse="false"
            controlVariant="default"
            label="Duração (anos)"
            :hideInput="false"
            :inset="false"
            :min="1"
            :max="7"
            v-model="newCourse.duration"
          ></v-number-input>
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
              saveCourse()
            "
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import CourseDto from '../../../models/course/CourseDto'
import CourseService from '../../../services/CourseService'

const dialog = ref(false)

const emit = defineEmits(['course-created'])

const newCourse = ref<CourseDto>({
  name: '',
  code: '',
  duration: null
})

const saveCourse = async () => {
  try {
    await CourseService.createCourse(newCourse.value)
    emit('course-created')
  } catch (error) {
    console.error("Error creating course: ", error)
  }

  newCourse.value = {
    name: '',
    code: '',
    duration: null
  }
}
</script>
