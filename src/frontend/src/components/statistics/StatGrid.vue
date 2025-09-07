<template>
  <v-row>
    <v-col
      v-for="(count, label) in stats"
      :key="label"
      cols="12"
      sm="6"
      md="4"
      lg="3"
    >
      <v-card class="stat-card" :color="getColor(label)" elevation="2" rounded="xl">
        <v-card-text class="text-center">
          <h3 class="text-h6">{{ label }}</h3>
          <p v-if="count !== null" class="text-h4 font-weight-bold">{{ count }}</p>
          <p v-else class="text-h6 text-grey">No data</p>
          <p class="text-caption">{{ caption }}</p>
        </v-card-text>
      </v-card>
    </v-col>
  </v-row>
</template>

<script setup>
import { defineProps } from 'vue'

const props = defineProps({
  stats: { type: Object, required: true },
  caption: { type: String, default: 'students' },
  statusColors: { type: Object, default: () => ({}) } // optional color mapping
})

const getColor = (label) => {
  return props.statusColors[label] || 'primary lighten-5'
}
</script>

<style scoped>
.stat-card {
  transition: transform 0.2s ease-in-out;
}
.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}
</style>
