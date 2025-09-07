<template>
  <h2 class="text-left ml-1">Lista de Pessoas da UC</h2>

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
  </v-data-table>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'
import CurricularUnitService from '../../../services/CurricularUnitService'
import { reactive, ref } from 'vue'
import { onMounted, watch } from 'vue'
import PersonDto from '../../../models/person/PersonDto'
import { getColorByType, translateType } from '../../../mappings/peopleMappings'

let search = ref('')
let loading = ref(true)

const people: PersonDto[] = reactive([])
const route = useRoute()
const curricularUnitId = parseInt(route.params.id as string, 10)

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
  }
]

const props = defineProps<{ refreshKey: number }>()

onMounted(() => {
  getCurricularUnitPeople()
})

watch(() => props.refreshKey, () => {
  getCurricularUnitPeople()
})

async function getCurricularUnitPeople() { 
	people.splice(0, people.length)
	try {
		people.push(...(await CurricularUnitService.getCurricularUnitPeople(curricularUnitId)))
	} catch (error) {
		console.error("Error getting people: ", error)
	}

	loading.value = false
  console.log('people: ', people)
}

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
