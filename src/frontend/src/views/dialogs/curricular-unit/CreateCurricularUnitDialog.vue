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
						:items="teachers"
						label="Professor Regente*"
						required
						v-model="newCurricularUnit.mainTeacher"
					></v-select>
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
import { ref, reactive, onMounted } from 'vue'
import CurricularUnitDto from '../../../models/CurricularUnitDto'
import CurricularUnitService from '../../../services/CurricularUnitService'
import PersonDto from '../../../models/PersonDto'
import PersonService from '../../../services/PersonService'

const dialog = ref(false)

const emit = defineEmits(['curricular-unit-created'])

const teachers: PersonDto[] = reactive([]);

const newCurricularUnit = ref<CurricularUnitDto>({
  code: '',
  name: '',
  semester: 1,
  course: '',
  mainTeacher: {}
})

onMounted(() => {
	fetchTeachers()
})

async function fetchTeachers() {
	console.log('Fetching teachers')

	try {
		teachers.push(...(await PersonService.getTeachers()))
		console.log("Teachers fetched: ", teachers)
	} catch (error) {
		console.error('Error fetching teachers: ', error)
	}
}

const createCurricularUnit = async () => {
  try {
    await CurricularUnitService.createCurricularUnit(newCurricularUnit.value)
    emit('curricular-unit-created')
  } catch (error) {
    console.error("Error creating person: ", error)
  }

  newCurricularUnit.value = {
    code: '',
		name: '',
		semester: 1,
		course: '',
		mainTeacher: {}
  }
}
</script>
