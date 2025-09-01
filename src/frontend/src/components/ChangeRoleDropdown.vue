<template>
    <v-menu
		v-model="menuVisible"
		:close-on-content-click="false"
		offset-y
	>
		<template #activator="{ props }">
			<v-btn v-bind="props">
				Role: {{ currentRole }}
				<v-icon color="contrast" icon="mdi-school"></v-icon>
			</v-btn>
		</template>

		<v-list>
			<v-list-item
				v-for="role in options"
				:key="role"
				@click="changeRole(role)"
			>
				<v-list-item-title>{{ role }}</v-list-item-title>
			</v-list-item>
		</v-list>
	</v-menu>
</template>

<script setup lang="ts">
import { useRoleStore } from '../stores/role';
import { ref } from 'vue';
import { watch } from 'vue';

const roleStore = useRoleStore();
const menuVisible = ref(false);
const options = ['student', 'teaching_assistant', 'main_teacher', 'administrator'];

const changeRole = (role: string) => {
	roleStore.currentRole = role;
	console.log(roleStore.currentRole)
	menuVisible.value = false;
}

const currentRole = ref(roleStore.currentRole);

watch(
	() => roleStore.currentRole,
	(newRole) => {
		currentRole.value = newRole;
	}
)
</script>