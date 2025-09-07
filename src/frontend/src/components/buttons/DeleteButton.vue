<template>
  <v-icon @click="openDialog" color="red" class="cursor-pointer">
    mdi-delete
  </v-icon>

  <ConfirmActionDialog v-model="showDialog" @confirm="doAction" />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import ConfirmActionDialog from '../../views/dialogs/ConfirmActionDialog.vue'

const props = defineProps<{
  fn: (arg: any) => void
  item: any
}>()

const showDialog = ref(false)

function openDialog() {
  showDialog.value = true
}

function doAction() {
  props.fn(props.item)
}
</script>
