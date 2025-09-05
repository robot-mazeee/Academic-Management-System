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
            v-model="editableProject.title"
            label="Título"
            required
            :placeholder="props.projectToEdit.title"
          />
          <v-slider 
            label="Peso*" 
            :min="5" 
            :max="100" 
            :step="1" 
            thumb-label 
            v-model="editableProject.weight"
            :placeholder="props.projectToEdit.weight"
          ></v-slider>

          <v-date-picker
            color="primary"
            v-model="editableProject.deadline"
            :min="today"
          ></v-date-picker>
        </v-card-text>

        <v-divider />

        <v-card-actions>
          <v-spacer />
          <v-btn text="Close" variant="plain" @click="dialog = false" />
          <v-btn
              text="Save"
              color="primary"
              variant="tonal"
              @click="editProject"
            ></v-btn>
        </v-card-actions>
      </v-card>
		</v-dialog>
	</div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import EvaluationService from '../../../services/EvaluationService'
import ProjectDto from '../../../models/ProjectDto';

const dialog = ref(false)
const emit = defineEmits(['project-edited'])

const props = defineProps<{
  projectToEdit: ProjectDto
}>()

const today: string = new Date().toISOString().split('T')[0]
const editableProject = reactive({ ...props.projectToEdit })

const editProject = async () => {
	const project = { ...editableProject }

	try {
		console.log("Editing project: ", project)
		await EvaluationService.updateProject(project)
		console.log('project edited!')
	} catch (error) {
		console.error("Error deleting project: ", error)
	}

	emit('project-edited')
	dialog.value = false
}
</script>