<template>
  <v-row align="center">
    <v-col>
      <h2 class="text-left ml-1">Lista de Pessoas</h2>
    </v-col>
    <v-col cols="auto" v-if="roleStore.isAdministrator">
      <CreatePersonDialog @person-created="getPeople" />
    </v-col>
  </v-row>

  <v-text-field
    v-model="search"
    label="Search"
    prepend-inner-icon="mdi-magnify"
    variant="outlined"
    hide-details
    single-line
  ></v-text-field>

  <v-data-table
    :headers="headers"
    :items="people"
    :search="search"
    :loading="loading"
    :custom-filter="fuzzySearch"
    item-key="id"
    class="text-left"
    no-data-text="Sem pessoas a apresentar."
  >
    <template v-slot:[`item.type`]="{ item }">
      <v-chip :color="getColorByType(item.type)" text-color="white">
				{{ translateType(item.type) }}
			</v-chip>
    </template>
    <template v-slot:[`item.actions`]="{ item }" v-if="roleStore.isAdministrator">
      <div class="d-flex align-center justify-center ga-2">
				<EditPersonDialog :person-to-edit="item" :can-alter-type="true" @close-dialog="getPeople" />
				<DeleteButton :fn="deletePerson" :item="item" />
			</div>
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
import PersonDto from '../../models/person/PersonDto'
import PersonService from '../../services/PersonService'
import CreatePersonDialog from '../dialogs/person/CreatePersonDialog.vue'
import { reactive, ref } from 'vue'
import { onMounted } from 'vue'
import { getColorByType, translateType } from '../../mappings/peopleMappings'
import EditPersonDialog from '../dialogs/person/EditPersonDialog.vue'
import { useRoleStore } from '../../stores/role'
import DeleteButton from '../../components/buttons/DeleteButton.vue'

let search = ref('')
let loading = ref(true)

const people: PersonDto[] = reactive([])

const roleStore = useRoleStore()

const headers = [
  { title: 'ID', key: 'id', value: 'id', sortable: true, filterable: false },
  {
    title: 'Nome',
    key: 'name',
    value: 'name',
    sortable: true,
    filterable: true
  },
  {
    title: 'IST ID',
    key: 'istId',
    value: 'istId',
    sortable: true,
    filterable: true
  },
  {
    title: 'Email',
    key: 'email',
    value: 'email',
    sortable: true,
    filterable: true
  },
  {
    title: 'Tipo',
    key: 'type',
    value: 'type',
    sortable: true,
    filterable: true
  },
  {
    title: 'Ações',
    key: 'actions',
    value: 'actions',
    sortable: false,
    filterable: false,
    align: 'center'
  }
]

onMounted(() => {
	getPeople()
})

async function getPeople() { 
	people.splice(0, people.length)
	try {
		people.push(...(await PersonService.getPeople()))
	} catch (error) {
		console.error("Error getting people: ", error)
	}

	loading.value = false
  console.log(people)
}

const deletePerson = async (person: PersonDto) => {
	console.log("Deleting person:", person)
	try {
		await PersonService.deletePerson(person)
		await getPeople()
	} catch (error) {
		console.error("Error deleting person: ", error)
	}
}

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
