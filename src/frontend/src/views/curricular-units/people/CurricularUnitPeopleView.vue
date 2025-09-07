<template>
	<v-container>
		<v-row justify="center">
			<v-col cols="12">
        <v-tabs v-model="activeTab" background-color="primary" dark>
          <v-tab value="teachers">Professores</v-tab>
          <v-tab value="students">Alunos e Notas</v-tab>
          <v-tab value="people">Pessoal</v-tab>
        </v-tabs>

        <v-window v-model="activeTab" class="mt-4">
          <v-window-item value="students">
            <div>
              <CurricularUnitStudentsList />
            </div>
          </v-window-item>
          <v-window-item value="teachers">
            <div>
              <CurricularUnitTeachersList />
            </div>
          </v-window-item>
          <v-window-item value="people">
            <div>
              <CurricularUnitPeopleList :refresh-key="refreshKey" />
            </div>
          </v-window-item>
        </v-window>
			</v-col>
		</v-row>
	</v-container>
</template>

<script setup lang="ts">
import CurricularUnitTeachersList from './CurricularUnitTeachersList.vue';
import CurricularUnitStudentsList from './CurricularUnitStudentsList.vue';
import CurricularUnitPeopleList from './CurricularUnitPeopleList.vue';
import { ref, watch } from 'vue'

const activeTab = ref('teachers')
const refreshKey = ref(0)

watch(activeTab, (newTab) => {
  if (newTab === 'people') {
    refreshKey.value++
  }
})
</script>