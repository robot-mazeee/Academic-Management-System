<template>
  <UtilBar />
  <NavBar :navbarItems="navbarItems" />
</template>

<script setup lang="ts">
import UtilBar from '@/components/UtilBar.vue'
import NavBar from '@/components/NavBar.vue'

const navbarItems = [
  { name: 'Pessoal', path: '/people', icon: 'mdi-account-group' },
  { name: 'Cursos', path: '/courses', icon: 'mdi-book' },
  { name: 'Unidades Curiculares', path: '/curricular-units', icon: 'mdi-school' },
  { name: 'Estatísticas', path: '/statistics', icon: 'mdi-chart-bar' }
]
</script>
