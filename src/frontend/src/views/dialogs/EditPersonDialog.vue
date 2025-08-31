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
            v-model="editablePerson.name"
            label="Name"
            required
            :placeholder="props.personToEdit.name"
          />
          <v-text-field
            v-model="editablePerson.istId"
            label="IST ID"
            required
            :placeholder="props.personToEdit.istId"
          />
          <v-text-field
            v-model="editablePerson.email"
            label="Email"
            required
            :placeholder="props.personToEdit.email"
          />
          <v-select v-if="props.canAlterType"
            v-model="editablePerson.type"
            :items="['Administrador', 'Professor Regente', 'Professor Assistente', 'Aluno']"
            label="Role"
            required
            :placeholder="props.personToEdit.type"
          />
        </v-card-text>

        <v-divider />

        <v-card-actions>
          <v-spacer />
          <v-btn text="Close" variant="plain" @click="dialog = false" />
          <v-btn
              text="Save"
              color="primary"
              variant="tonal"
              @click=" () => { if (isValidPerson()) editPerson() }"
            ></v-btn>
        </v-card-actions>
      </v-card>
		</v-dialog>
	</div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import PersonDto from '../../models/PersonDto'
import RemoteService from '../../services/RemoteService'
import { typeMappings } from '../../mappings/peopleMappings'

const dialog = ref(false)
const emit = defineEmits(['close-dialog'])

const props = defineProps<{
  personToEdit: PersonDto,
	canAlterType: Boolean
}>()

const editablePerson = reactive({ ...props.personToEdit })

function isValidPerson(): boolean {
	if (!editablePerson.name) {
		alert('Nome inválido.')
		return false;
	}

	if (!editablePerson.istId) {
		alert('Ist ID inválido.')
		return false;
	}

	if (!/^[0-9]+$/.test(editablePerson.istId)){
		alert('IST ID tem de ser apenas números.')
		return false;
	}

	if (!editablePerson.email) {
		alert('Email inválido.')
		return false;
	}

	const emailRegex = /^[^\s@]+@(gmail\.com|tecnico\.ulisboa\.pt)$/
	if (!emailRegex.test(editablePerson.email)) {
		alert('Email inválido; os domínios aceites são @tecnico.ulisboa.pt e @gmail.com.')
		return false;
	}

	if (!editablePerson.type) {
		alert('Por favor, selecione uma categoria.')
		return false;
	}

	return true;
}

const editPerson = async () => {
	const person = { ...editablePerson }
	const isValidTypeValue = Object.values(typeMappings).includes(person.type)
	if (!isValidTypeValue) {
		person.type = typeMappings[person.type as keyof typeof typeMappings]
	}

	try {
		console.log("Editing person: ", person)
		await RemoteService.editPerson(person)
		console.log('Person edited!')
	} catch (error) {
		console.error("Error deleting person: ", error)
	}

	emit('close-dialog')
	dialog.value = false
}
</script>