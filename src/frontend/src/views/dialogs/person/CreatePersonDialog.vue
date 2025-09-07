<template>
  <div class="pa-4 text-center">
    <v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Adicionar Pessoa"
          v-bind="activatorProps"
          color="primary"
        ></v-btn>
      </template>

      <v-card prepend-icon="mdi-account" title="Nova">
        <v-card-text>
          <v-text-field
            label="Nome*"
            v-model="newPerson.name"
            :error-messages="errors.name"
          />

          <v-text-field
            label="IST ID*"
            v-model="newPerson.istId"
            :error-messages="errors.istId"
          />

          <v-text-field
            label="Email*"
            v-model="newPerson.email"
            :error-messages="errors.email"
          />

          <v-select
            :items="['Administrador', 'Professor', 'Aluno']"
            label="Categoria*"
            v-model="newPerson.type"
            :error-messages="errors.type"
          />
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn text="Close" variant="plain" @click="dialog = false"></v-btn>

          <v-btn
            color="primary"
            text="Save"
            variant="tonal"
            @click="savePerson()"
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import PersonDto from '../../../models/person/PersonDto'
import PersonService from '../../../services/PersonService'
import { typeMappings } from '../../../mappings/peopleMappings'

const dialog = ref(false)

const emit = defineEmits(['person-created'])
const errors = ref<Record<string, string>>({})

const newPerson = ref<PersonDto>({
  name: '',
  istId: '',
  type: '',
  email: ''
})

const savePerson = async () => {
  if (!newPerson.value.type) {
    errors.value.type = "Campo tipo é obrigatório"
    return
  }
  newPerson.value.type = typeMappings[newPerson.value.type as keyof typeof typeMappings]

  try {
    await PersonService.createPerson(newPerson.value)
    emit('person-created')
    dialog.value = false
  } catch (error: any) {
    if (error.response && error.response.status === 400) {
      errors.value = error.response.data
    } else {
      console.error("Error creating person: ", error)
    }
  }

  newPerson.value = {
    name: '',
    istId: '',
    type: '',
    email: ''
  }
}
</script>
