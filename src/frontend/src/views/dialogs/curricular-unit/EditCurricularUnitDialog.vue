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
            :items="availableTeachers" 
            item-title="name"
            label="Professor Regente"
            v-model="editableCurricularUnit.mainTeacher"
            return-object
          >
            <template v-slot:item="{ props: itemProps, item }">
              <v-list-item v-bind="itemProps" :subtitle="item.raw.istId" />
            </template>
          </v-select>
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
import { ref, reactive, watch } from 'vue'
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
const availableTeachers = ref<PersonDto[]>([])

onMounted(() => {
	fetchAvailableTeachers();
})

watch(dialog, async (val) => {
  if (val) {
    await fetchAvailableTeachers()
  }
})

async function fetchAvailableTeachers() {
	console.log('Fetching teachers')

	try {
    availableTeachers.value = await PersonService.getTeachers()
		console.log("Teachers fetched: ", availableTeachers)
	} catch (error) {
		console.error('Error fetching teachers: ', error)
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