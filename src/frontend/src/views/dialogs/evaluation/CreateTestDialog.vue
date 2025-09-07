<template>
  <div class="pa-4 text-center">
    <v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Adicionar Testes"
          v-bind="activatorProps"
          color="primary"
        ></v-btn>
      </template>

      <v-card prepend-icon="mdi-account" title="Novo Teste">
        <v-card-text>
          <v-text-field label="Título*" required v-model="newTest.title"></v-text-field>
          <v-slider label="Peso*" :min="5" :max="100" :step="1" thumb-label required v-model="newTest.weight"></v-slider>
          <v-date-picker
            color="primary"
            v-model="newTest.testDate"
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
              createTest()
            "
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import TestDto from '../../../models/evaluations/TestDto'
import EvaluationService from '../../../services/EvaluationService';

const dialog = ref(false)

const today: string = new Date().toISOString().split('T')[0];

const emit = defineEmits(['evaluation-created'])
const props = defineProps<{
  id: number
}>()

const newTest = ref<TestDto>({
  title: '',
  weight: 0,
  testDate: '',
  curricularUnitId: props.id
})

const createTest = async () => {
  try {
    await EvaluationService.createTest(newTest.value)
    emit('evaluation-created')
  } catch (error) {
    console.error("Error creating test: ", error)
  }

  newTest.value = {
    title: '',
    weight: 0,
    testDate: '',
    curricularUnitId: props.id
  }
}
</script>
