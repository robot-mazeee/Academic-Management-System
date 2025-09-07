<template>
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
        :items="history"
        :search="search"
        :loading="loading"
        :custom-filter="fuzzySearch"
        item-key="id"
        class="text-left"
        no-data-text="Sem revisões."
    >
        <template v-slot:[`item.student`]="{ item }">
            {{ item.student.name }} ({{ item.student.istId }})
        </template>
        <template v-slot:[`item.test`]="{ item }">
            {{ item.test.title }}
        </template>
        <template v-slot:[`item.status`]="{ item }">
            <v-chip :color="getColorByStatus(item.status)" text-color="white">
                {{ item.status }}
            </v-chip>
        </template>
        <template v-slot:[`item.date`]="{ item }">
            {{ formatDateTime(item.dateTime) }}
        </template>
    </v-data-table>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { getColorByStatus } from '../../mappings/revisionMappings'
import RevisionService from '../../services/RevisionService'
import { useRoute } from 'vue-router'
import RevisionHistoryDto from '../../models/RevisionHistoryDto'
import formatDateTime from '../../mappings/dateMappings'

let search = ref('')
let loading = ref(true)

const history = ref<RevisionHistoryDto[]>([])

const headers = [
  { title: 'ID', key: 'id', value: 'id', sortable: true, filterable: false },
  {
    title: 'Aluno',
    key: 'student',
    value: 'student',
    sortable: true,
    filterable: true
  },
  {
    title: 'Teste',
    key: 'test',
    value: 'test',
    sortable: true,
    filterable: true
  },
  {
    title: 'Status',
    key: 'status',
    value: 'status',
    sortable: true,
    filterable: true
  },
  {
    title: 'Data',
    key: 'date',
    value: 'date',
    sortable: true,
    filterable: true
  }
]

const route = useRoute()
const revisionId = parseInt(route.params.id as string, 10)

onMounted(async () => {
  await getRevisionHistory()
})

async function getRevisionHistory() {
  try {
    history.value = await RevisionService.getRevisionHistory(revisionId)
    console.log('Fetched revision history: ', history.value)
  } catch (error) {
    console.log('Error getting revision history')
  }
  loading.value = false
}

const fuzzySearch = (value: string, search: string) => {
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>