import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '@/views/HomeView.vue';
import { useAuthStore } from '@/stores/auth.store';
import { Role } from '@/models/auth/role.enum';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/LoginView.vue')
    },
    {
      path: '/patients',
      name: 'waitingRoom',
      component: () => import('@/views/WaitingRoomView.vue'),
      meta: {
        roles: [Role.STUDENT]
      }
    },
    {
      path: '/diagnostic/:virtualPatientId/:diagnosticId',
      name: 'diagnostic',
      component: () => import('@/views/DiagnosticView.vue')
    },
    {
      path: '/diagnostic/:diagnosticId/resultat',
      name: 'diagnosticResult',
      component: () => import('@/views/DiagnosticResultView.vue')
    },
    {
      path: '/resultats',
      name: 'studentResults',
      component: () => import('@/views/StudentResultsView.vue')
    },
    {
      path: '/questions',
      name: 'questions',
      component: () => import('@/views/administration/QuestionsView.vue'),
      meta: {
        roles: [Role.ADMIN]
      }
    },
    {
      path: '/teacher/patients/new',
      name: 'newPatient',
      component: () => import('@/views/teacher/NewPatientView.vue'),
      meta: {
        roles: [Role.TEACHER]
      }
    },
    {
      path: '/teacher/patients/edit/:id',
      name: 'editPatient',
      component: () => import('@/views/teacher/EditPatientView.vue'),
      meta: {
        roles: [Role.TEACHER]
      }
    },
    {
      path: '/teacher/patients',
      name: 'teacherPatients',
      component: () => import('@/views/teacher/PatientsView.vue'),
      meta: {
        roles: [Role.TEACHER]
      }
    }
  ]
});

/**
 * This function is called before each route change
 * @param to The route we are going to
 */
router.beforeEach(async (to, from) => {
  const authStore = useAuthStore();

  // If the store is not initialized (on page mount)
  if (!authStore.isInitialized && from.path === '/') {
    try {
      await authStore.initialize();
    } catch (err) {
      return { name: 'login' };
    }
  }

  if (!authStore.isCurrentUserAuthenticated && to.name !== 'login') {
    // If the user is not authenticated and is not going to the login page, redirect to the login page
    return { name: 'login' };
  } else if (authStore.isCurrentUserAuthenticated && to.name === 'login') {
    // If the user is authenticated and is going to the login page, redirect to the home page
    return { name: 'home' };
  } else if (authStore.isCurrentUserAuthenticated) {
    // If the user is authenticated, check that he has the required role
    const routeRequiredRoles = to.meta.roles as Role[] | undefined;
    const currentUserRole = authStore.getUserRole;
    if (currentUserRole && routeRequiredRoles && !routeRequiredRoles.includes(currentUserRole)) {
      return { name: 'home' };
    }
    return true;
  }
});

export default router;
