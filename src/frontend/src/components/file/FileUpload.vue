<template>
	<v-file-input
    multiple
    show-size
    @change="onFileSelect"
    class="upload-btn"
  >
		<template v-if="selectedFile" v-slot:prepend>
			<v-btn icon color="primary" elevation="2" class="rounded-circle" @click="uploadFile">
				<v-icon>mdi-upload</v-icon>
			</v-btn>
		</template>
	</v-file-input>
</template>

<script setup lang="ts">
import { ref } from "vue"
import FileService from "../../services/FileService"

const selectedFile = ref<File | null>(null)
const emit = defineEmits(['file-uploaded'])

const onFileSelect = async (event: Event) => {
	const input = event.target as HTMLInputElement
	if (input.files && input.files[0])
		selectedFile.value = input.files[0]
}

const uploadFile = async () => {
  if (!selectedFile.value) return

  try {
    console.log(selectedFile.value)
    const storedFileName = await FileService.uploadFile(selectedFile.value)
    console.log('Uploaded file:', storedFileName.fileName)

    emit('file-uploaded', storedFileName.fileName)
    selectedFile.value = null
  } catch (error) {
    console.error("Upload failed:", error)
  }
}
</script>