import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import PeopleView from '@/views/people/PeopleView.vue'
import StatisticsView from '@/views/statistics/StatisticsView.vue'
import CurricularUnitsView from '@/views/curricular-units/CurricularUnitsView.vue'
import CurricularUnitPeopleView from '@/views/curricular-units/people/CurricularUnitPeopleView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/people',
      name: 'people',
      component: PeopleView
    },
    {
      path: '/curricular-units',
      name: 'curricular-units',
      component: CurricularUnitsView
    },
    {
      path: '/curricular-units/:id/people',
      name: 'curricular-unit-people-management',
      component: CurricularUnitPeopleView
    },
    {
      path: '/statistics',
      name: 'statistics',
      component: StatisticsView
    }
  ]
})

export default router
