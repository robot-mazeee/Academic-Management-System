<template>
  <v-btn
    color="primary"
    variant="outlined"
    @click="downloadFile(props.fileName)"
  >
    Download
  </v-btn>
</template>

<script setup lang="ts">
import FileService from '../../services/FileService'

const props = defineProps<{
  fileName: string
}>()

async function downloadFile(fileName: string) {
  const cleanFileName = fileName.replace(/^"(.*)"$/, '$1')

  console.log(cleanFileName)

  try {
    const blob: Blob = await FileService.downloadFile(cleanFileName)
    console.log(blob)

    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.setAttribute('download', fileName)
    document.body.appendChild(link)
    link.click()
    link.remove()
    window.URL.revokeObjectURL(url)
  } catch (error) {
    console.error('Error downloading file:', error)
  }
}
</script>