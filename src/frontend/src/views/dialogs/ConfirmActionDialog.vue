<template>
  <v-dialog v-model="model" max-width="400">
    <v-card>
      <v-card-title>Confirm Action</v-card-title>
      <v-card-text>
        De certeza que quer realizar esta ação? Poderá perder informação crítica.
      </v-card-text>
      <v-card-actions>
        <v-spacer />
        <v-btn text @click="cancel">Cancelar</v-btn>
        <v-btn color="red" text @click="confirm">Confirmar</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
const model = defineModel<boolean>({ required: true })

const emit = defineEmits<{
  (e: 'confirm'): void
}>()

function cancel() {
  model.value = false
}

function confirm() {
  emit('confirm')
  model.value = false
}
</script>
