<template>
	<v-app-bar color="secondary" :height="36" class="px-2">
		<v-container fluid class="d-flex align-center justify-space-between">
      <div class="d-flex align-center">
        <v-btn
          href="https://dei.tecnico.ulisboa.pt/"
          selected-class="no-active"
          class="dei-title"
          size="small"
        >
          DEI
        </v-btn>
        <v-spacer />
				<v-toolbar-items class="align-center">
					<DarkModeSwitch />
				</v-toolbar-items>
			</div>
		  <v-spacer />
			<div class="d-flex align-center justify-center" style="flex: 1">
				<ChangeRoleDropdown />
			</div>
		  <v-spacer />
      <v-toolbar-items class="ms-2">
        <v-btn size="small" variant="text">
          Terminar sessão
          <v-icon size="small" class="ms-1" icon="mdi-logout"></v-icon>
        </v-btn>
      </v-toolbar-items>
		</v-container>
	</v-app-bar>
</template>

<script setup lang="ts">
import DarkModeSwitch from './DarkModeSwitch.vue'
import ChangeRoleDropdown from './ChangeRoleDropdown.vue'
</script>

<style scoped>
.dei-title:hover {
  background-color: transparent !important;
}
</style>
