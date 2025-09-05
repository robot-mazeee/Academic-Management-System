import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import PeopleView from '@/views/people/PeopleView.vue'
import StatisticsView from '@/views/statistics/StatisticsView.vue'
import CurricularUnitsView from '@/views/curricular-units/CurricularUnitsView.vue'
import CurricularUnitPeopleView from '@/views/curricular-units/people/CurricularUnitPeopleView.vue'
import CurricularUnitEvaluationsView from '@/views/curricular-units/evaluations/CurricularUnitEvaluationsView.vue'
import CurricularUnitTestGradesView from '@/views/curricular-units/evaluations/CurricularUnitTestGradesView.vue'
import CoursesView from '@/views/courses/CoursesView.vue'
import StudentEvaluationsView from '@/views/student/StudentEvaluationsView.vue'
import CurricularUnitProjectSubmissionsView from '@/views/curricular-units/evaluations/CurricularUnitProjectSubmissionsView.vue'

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
      path: '/courses',
      name: 'courses',
      component: CoursesView
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
      path: '/curricular-units/:id/evaluations',
      name: 'curricular-unit-evaluations-management',
      component: CurricularUnitEvaluationsView
    },
    {
      path: '/curricular-units/:curricularUnitId/tests/:testId/grades',
      name: 'curricular-unit-test-grades-management',
      component: CurricularUnitTestGradesView
    },
    {
      path: '/curricular-units/:curricularUnitId/tests/:projectId/submissions',
      name: 'curricular-unit-project-submissions-management',
      component: CurricularUnitProjectSubmissionsView
    },
    {
      path: '/evaluations',
      name: 'evaluations',
      component: StudentEvaluationsView
    },
    {
      path: '/statistics',
      name: 'statistics',
      component: StatisticsView
    }
  ]
})

export default router
