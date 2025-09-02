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
            v-model="editableCurricularUnit.code"
            label="Código"
            required
            :placeholder="props.curricularUnitToEdit.code"
          />
          <v-text-field
            v-model="editableCurricularUnit.name"
            label="Nome"
            required
            :placeholder="props.curricularUnitToEdit.name"
          />
          <v-select
						:items="[1, 2]"
            v-model="editableCurricularUnit.semester"
						label="Semestre"
						required
						:placeholder="String(props.curricularUnitToEdit.semester)"
					></v-select>
					<v-text-field
            v-model="editableCurricularUnit.course"
            label="Curso"
            required
            :placeholder="props.curricularUnitToEdit.course"
          />
					<v-select
						:items="teachers"
						label="Professor Regente"
						required
						v-model="props.curricularUnitToEdit.mainTeacher"
					></v-select>
        </v-card-text>

        <v-divider />

        <v-card-actions>
          <v-spacer />
          <v-btn text="Close" variant="plain" @click="dialog = false" />
          <v-btn
              text="Save"
              color="primary"
              variant="tonal"
              @click="editCurricularUnit(), dialog = false"
            ></v-btn>
        </v-card-actions>
      </v-card>
		</v-dialog>
	</div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import CurricularUnitDto from '../../../models/CurricularUnitDto'
import CurricularUnitService from '../../../services/CurricularUnitService'
import { onMounted } from 'vue'
import PersonDto from '../../../models/PersonDto'
import PersonService from '../../../services/PersonService'

const dialog = ref(false)
const emit = defineEmits(['curricular-unit-edited'])

const props = defineProps<{
  curricularUnitToEdit: CurricularUnitDto,
}>()

const editableCurricularUnit = reactive({ ...props.curricularUnitToEdit })
const teachers: PersonDto[] = reactive([]);

onMounted(() => {
	fetchTeachers();
})

async function fetchTeachers() {
	console.log('Fetching teachers');

	try {
		teachers.push(...(await PersonService.getTeachers()))
		console.log("Teachers fetched: ", teachers);
	} catch (error) {
		console.error('Error fetching teachers: ', error);
	}
}

const editCurricularUnit = async () => {
	const curricularUnit = { ...editableCurricularUnit }
	try {
		console.log("Editing curricular unit: ", curricularUnit)
		await CurricularUnitService.editCurricularUnit(curricularUnit)
		console.log('curricular unit edited!')
	} catch (error) {
		console.error("Error deleting curricular unit: ", error)
	}

	emit('curricular-unit-edited')
	dialog.value = false
}
</script>