<template>
  <v-row align="center">
    <v-col>
      <h2 class="text-left ml-1">Lista de Cursos</h2>
    </v-col>
    <v-col cols="auto" v-if="roleStore.isAdministrator">
      <CreateCourseDialog @course-created="getCourses" />
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
    :items="courses"
    :search="search"
    :loading="loading"
    :custom-filter="fuzzySearch"
    item-key="id"
    class="text-left"
    no-data-text="Sem cursos a apresentar."
  >
    <template v-slot:[`item.actions`]="{ item }" v-if="roleStore.isAdministrator">
      <div class="d-flex align-center justify-center ga-2">
				<EditCourseDialog :course-to-edit="item" @course-edited="getCourses" />
				<v-icon @click="deleteCourse(item)" color="red" class="cursor-pointer">
					mdi-delete
				</v-icon>
			</div>
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { onMounted } from 'vue'
import { useRoleStore } from '../../stores/role'
import CourseDto from '../../models/CourseDto'
import CourseService from '../../services/CourseService'
import CreateCourseDialog from '../dialogs/course/CreateCourseDialog.vue'
import EditCourseDialog from '../dialogs/course/EditCourseDialog.vue'

let search = ref('')
let loading = ref(true)

const courses: CourseDto[] = reactive([])

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
    title: 'Código',
    key: 'code',
    value: 'code',
    sortable: true,
    filterable: true
  },
  {
    title: 'Duração (anos)',
    key: 'duration',
    value: 'duration',
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
	getCourses()
})

async function getCourses() { 
	courses.splice(0, courses.length)
	try {
		courses.push(...(await CourseService.getCourses()))
	} catch (error) {
		console.error("Error getting courses: ", error)
	}

	loading.value = false
  console.log(courses)
}

const deleteCourse = async (course: CourseDto) => {
	console.log("Deleting course:", course)
	try {
		await CourseService.deleteCourse(course.id)
		await getCourses()
	} catch (error) {
		console.error("Error deleting course: ", error)
	}
}

const fuzzySearch = (value: string, search: string) => {
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
