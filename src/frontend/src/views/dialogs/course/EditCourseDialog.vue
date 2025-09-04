<template>
	<div class="pa-4 text-center" >
		<v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <div class="d-flex justify-center align-center">
          <v-icon v-bind="activatorProps" class="cursor-pointer">mdi-pencil</v-icon>
        </div>
      </template>

      <v-card prepend-icon="mdi-account" title="Edit">
        <v-card-text>
          <v-text-field
            v-model="editedCourse.name"
            label="Name"
            required
            :placeholder="props.courseToEdit.name"
          />
          <v-text-field
            v-model="editedCourse.code"
            label="Código"
            required
            :placeholder="props.courseToEdit.code"
          />
          <v-number-input
            :reverse="false"
            controlVariant="default"
            label="Duração (anos)"
            :hideInput="false"
            :inset="false"
            :min="1"
            :max="7"
            v-model="editedCourse.duration"
          ></v-number-input>
        </v-card-text>

        <v-divider />

        <v-card-actions>
          <v-spacer />
          <v-btn text="Close" variant="plain" @click="dialog = false" />
          <v-btn
              text="Save"
              color="primary"
              variant="tonal"
              @click="editCourse"
            ></v-btn>
        </v-card-actions>
      </v-card>
		</v-dialog>
	</div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import CourseDto from '../../../models/CourseDto'
import CourseService from '../../../services/CourseService'

const dialog = ref(false)
const emit = defineEmits(['course-edited'])

const props = defineProps<{
  courseToEdit: CourseDto
}>()

const editedCourse = reactive({ ...props.courseToEdit })

const editCourse = async () => {
	const course = { ...editedCourse }

	try {
		console.log("Editing course: ", course)
		await CourseService.editCourse(course)
		console.log('Course edited!')
	} catch (error) {
		console.error("Error deleting course: ", error)
	}

	emit('course-edited')
	dialog.value = false
}
</script>