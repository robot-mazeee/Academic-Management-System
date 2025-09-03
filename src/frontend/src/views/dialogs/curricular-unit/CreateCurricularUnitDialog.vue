<template>
  <div class="pa-4 text-center">
    <v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Adicionar Unidade Curricular"
          v-bind="activatorProps"
          color="primary"
        ></v-btn>
      </template>

      <v-card prepend-icon="mdi-account" title="Nova">
        <v-card-text>
          <v-text-field label="Código*" required v-model="newCurricularUnit.code"></v-text-field>
          <v-text-field label="Nome*" required v-model="newCurricularUnit.name"></v-text-field>
          <v-select
						:items="[1, 2]"
						label="Semestre*"
						required
						v-model="newCurricularUnit.semester"
					></v-select>
					<v-text-field label="Curso*" required v-model="newCurricularUnit.course"></v-text-field>
          <v-select 
            :items="availableTeachers" 
            item-title="name"
            label="Professor Regente*"
            v-model="newCurricularUnit.mainTeacher"
            return-object
          >
            <template v-slot:item="{ props: itemProps, item }">
              <v-list-item v-bind="itemProps" :subtitle="item.raw.istId" />
            </template>
          </v-select>
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
              createCurricularUnit()
            "
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import CurricularUnitDto from '../../../models/CurricularUnitDto'
import CurricularUnitService from '../../../services/CurricularUnitService'
import PersonDto from '../../../models/PersonDto'
import PersonService from '../../../services/PersonService'

const dialog = ref(false)

const emit = defineEmits(['curricular-unit-created'])

const availableTeachers = ref<PersonDto[]>([])

const newCurricularUnit = ref<CurricularUnitDto>({
  code: '',
  name: '',
  semester: null,
  course: '',
  mainTeacher: null
})

onMounted(() => {
  fetchAvailableTeachers()
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

const createCurricularUnit = async () => {
  console.log('Creating curricular unit: ', newCurricularUnit.value)
  try {
    await CurricularUnitService.createCurricularUnit(newCurricularUnit.value)
    emit('curricular-unit-created')
  } catch (error) {
    console.error("Error creating person: ", error)
  }

  newCurricularUnit.value = {
    code: '',
		name: '',
		semester: null,
		course: '',
		mainTeacher: null
  }
}
</script>
