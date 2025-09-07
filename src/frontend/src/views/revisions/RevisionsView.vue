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
		:items="revisions"
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
		<template v-slot:[`item.actions`]="{ item }">
			<div class="d-flex align-center justify-center ga-2">
				<MainTeacherActions 
					v-if="roleStore.isMainTeacher" 
					:revision="item" 
					@revision-updated="getRevisions"
				/>
        <TeachingAssistantActions 
					v-if="roleStore.isTeachingAssistant" 
					:revision="item" 
					@revision-updated="getRevisions"
				/>
			</div>
		</template>
		<template v-slot:[`item.history`]="{ item }">
			<v-btn 
        v-if="item"
        class="mb-3" 
        color="contrast" 
        @click="displayRevisionHistoryView(item.id)"
      >
        Histórico
      </v-btn>
		</template>
	</v-data-table>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoleStore } from '../../stores/role'
import { getColorByStatus } from '../../mappings/revisionMappings'
import RevisionService from '../../services/RevisionService'
import RevisionDto from '../../models/RevisionDto'
import MainTeacherActions from './MainTeacherActions.vue'
import TeachingAssistantActions from './TeachingAssistantActions.vue'
import { useRouter } from 'vue-router'

let search = ref('')
let loading = ref(true)

const revisions = ref<RevisionDto[]>([])

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
		title: 'Justificação',
		key: 'reason',
		value: 'reason',
		sortable: true,
		filterable: true
	},
	{
		title: 'Actions',
		key: 'actions',
		value: 'actions',
		sortable: false,
		filterable: false,
    	align: 'center'
	},
	{
		title: 'Histórico',
		key: 'history',
		value: 'history',
		sortable: false,
		filterable: false,
    	align: 'center'
	}
]

const roleStore = useRoleStore()
const router = useRouter()

onMounted(async () => {
  await getRevisions()
})

async function getRevisions() {
  try {
    revisions.value = await RevisionService.getRevisions()
    console.log('Fetched revisions: ', revisions.value)
  } catch (error) {
    console.log('Error getting revisions')
  }
  loading.value = false
}

function displayRevisionHistoryView(id: number) {
  router.push({ name: 'revision-history-view', params: { id } })
}

const fuzzySearch = (value: string, search: string) => {
	let searchRegex = new RegExp(search.split('').join('.*'), 'i')
	return searchRegex.test(value)
}
</script>