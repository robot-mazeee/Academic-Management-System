<template>
	<v-container>
		<v-row justify="center">
			<v-col cols="12">
        <v-tabs v-model="activeTab" background-color="primary" dark>
          <v-tab value="tests">Testes</v-tab>
          <v-tab value="projects">Projetos</v-tab>
        </v-tabs>

        <v-window v-model="activeTab" class="mt-4">
          <v-window-item value="tests">
            <div>
              <CurricularUnitTestsList />
            </div>
          </v-window-item>
          <v-window-item value="projects">
            <div>
              <CurricularUnitProjectsList />
            </div>
          </v-window-item>
        </v-window>
			</v-col>
		</v-row>
	</v-container>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import CurricularUnitTestsList from './CurricularUnitTestsList.vue'
import CurricularUnitProjectsList from './CurricularUnitProjectsList.vue'

const activeTab = ref('tests')
</script>