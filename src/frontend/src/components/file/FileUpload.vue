<template>
	<v-file-input
		accept=".pdf"
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

    const formData = new FormData();
    formData.append("file", selectedFile.value);

    try {
        const response = await FileService.uploadFile(selectedFile.value)
		console.log('Uploaded file')
        emit('file-uploaded', selectedFile.value.name);
        selectedFile.value = null;
    } catch (error) {
        console.error("Upload failed:", error);
    }
}
</script>