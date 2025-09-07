<template>
  <h1>DEI-AMS Home Page</h1>
  <v-container class="pa-4" fluid>
    <v-row dense>
      <v-col cols="12" md="6" lg="4" v-for="card in cards" :key="card.name">
        <v-card class="pa-4" outlined>
          <v-card-title class="text-h6">
            <v-icon left>{{ card.icon }}</v-icon>
            {{ card.title }}
          </v-card-title>
          <v-card-actions>
            <v-btn :color="card.color" @click="goTo(card.path)">Acessar</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter, Router } from 'vue-router'
import { useRoleStore } from '../stores/role'

interface Card {
  name: string
  title: string
  path: string
  color: string
  icon: string
}

const router: Router = useRouter()
const roleStore = useRoleStore()

const dashboardRoutes = router.getRoutes().filter(
  (r) => r.path !== '/' && !r.path.includes(':') && r.name !== undefined
)

const cards = computed<Card[]>(() => {
  return dashboardRoutes
    .filter((r) => {
      if (r.name === 'evaluations' && !roleStore.isStudent && !roleStore.isAdministrator) {
        return false
      }
      return true
  })
  .map((r) => ({
    name: r.name as string,
    title: (r.name as string).replace(/-/g, ' ').replace(/\b\w/g, (c) => c.toUpperCase()),
    path: r.path,
    color: 'primary',
    icon: 'mdi-view-dashboard'
  }))
})

const goTo = (path: string) => router.push(path)
</script>
