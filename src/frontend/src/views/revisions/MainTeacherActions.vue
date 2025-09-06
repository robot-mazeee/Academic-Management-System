<template>
    <v-btn 
        v-if="props.revision.status === 'APPROVED_BY_TEACHING_ASSISTANT'"
        @click="updateStatus('APPROVED')"
        class="mb-3" 
        color="warning"
    >Aprovar</v-btn>
    <v-btn 
        v-if="props.revision.status === 'APPROVED_BY_TEACHING_ASSISTANT'"
        @click="updateStatus('REJECTED')"
        class="mb-3" 
        color="error"
    >Rejeitar</v-btn>
</template>

<script setup lang="ts">
import RevisionDto from '../../models/RevisionDto'
import RevisionService from '../../services/RevisionService'

const props = defineProps<{
    revision: RevisionDto
}>()

const emit = defineEmits(['revision-updated'])

async function updateStatus(status: string) {
  try {
    const updatedRevision: RevisionDto = props.revision
    updatedRevision.status = status
    const response = await RevisionService.updateStatus(updatedRevision)
    console.log('Updated revision status: ', response)
  } catch (error) {
    console.error('Failed to update status: ', error)
  }
}
</script>