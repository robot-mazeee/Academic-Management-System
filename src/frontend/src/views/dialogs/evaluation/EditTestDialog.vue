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
            v-model="editableTest.title"
            label="Título"
            required
            :placeholder="props.testToEdit.title"
          />
          <v-slider 
            label="Peso*" 
            :min="5" 
            :max="100" 
            :step="1" 
            thumb-label 
            v-model="editableTest.weight"
            :placeholder="props.testToEdit.weight"
          ></v-slider>
          <v-date-picker
            color="primary"
            v-model="editableTest.testDate"
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
              @click="editTest"
            ></v-btn>
        </v-card-actions>
      </v-card>
		</v-dialog>
	</div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import TestDto from '../../../models/TestDto'
import EvaluationService from '../../../services/EvaluationService'

const dialog = ref(false)
const emit = defineEmits(['test-edited'])

const props = defineProps<{
  testToEdit: TestDto
}>()

const today: string = new Date().toISOString().split('T')[0]
const editableTest = reactive({ ...props.testToEdit })

const editTest = async () => {
	const test = { ...editableTest }

	try {
		console.log("Editing test: ", test)
		await EvaluationService.updateTest(test)
		console.log('test edited!')
	} catch (error) {
		console.error("Error editing test: ", error)
	}

	emit('test-edited')
	dialog.value = false
}
</script>