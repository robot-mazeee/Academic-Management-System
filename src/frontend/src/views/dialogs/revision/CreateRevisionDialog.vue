<template>
  <div class="pa-4 text-center">
    <v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Pedir Revisão"
          v-bind="activatorProps"
          color="primary"
        ></v-btn>
      </template>

      <v-card prepend-icon="mdi-account" title="Revisão">
        <v-card-text>
          <v-text-field label="Justificação*" required v-model="newRevision.reason"></v-text-field>
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
              saveRevision()
            "
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import RevisionDto from '../../../models/RevisionDto'
import RevisionService from '../../../services/RevisionService'
import TestDto from '../../../models/TestDto'
import PersonDto from '../../../models/PersonDto'

const dialog = ref(false)

const emit = defineEmits(['revision-created'])

const props = defineProps<{
  test: TestDto,
  student: PersonDto
}>()

const newRevision = ref<RevisionDto>({
  status: 'REQUESTED_BY_STUDENT',
  test: props.test,
  student: props.student,
  reason: ''
})

const saveRevision = async () => {
  try {
    const revision = await RevisionService.createRevision(newRevision.value)
    console.log('Revision created: ', revision)
    emit('revision-created')
  } catch (error) {
    console.error("Error creating revision: ", error)
  }

  newRevision.value = {
    status: 'REQUESTED_BY_STUDENT',
    test: props.test,
    student: props.student,
    reason: ''
  }
}
</script>
