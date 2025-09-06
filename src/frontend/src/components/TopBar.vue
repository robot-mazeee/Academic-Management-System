<template>
  <UtilBar />
  <NavBar :navbarItems="navbarItems" />
</template>

<script setup lang="ts">
import UtilBar from '@/components/UtilBar.vue'
import NavBar from '@/components/NavBar.vue'
import { computed } from 'vue'
import { useRoleStore } from '../stores/role'

const roleStore = useRoleStore()

const navbarItems = computed(() => {
  const items = [
    { name: 'Pessoal', path: '/people', icon: 'mdi-account-group' },
    { name: 'Cursos', path: '/courses', icon: 'mdi-book' },
    { name: 'Unidades Curiculares', path: '/curricular-units', icon: 'mdi-school' },
    { name: 'Revisões de Prova', path: '/revisions', icon: 'mdi-chart-bar' },
    { name: 'Calendário', path: '/calendar', icon: 'mdi-calendar' },
    { name: 'Estatísticas', path: '/statistics', icon: 'mdi-chart-bar' },
  ]

  if (roleStore.isStudent || roleStore.isAdministrator) {
    items.push({ name: 'Avaliações', path: '/evaluations', icon: 'mdi-file-document' })
  }

  return items
})
</script>
