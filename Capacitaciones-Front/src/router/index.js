import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '../layouts/MainLayout.vue'
import LoginView from '../views/LoginView.vue'
import MiPanel from '../views/MiPanel.vue'
import UsuariosView from '../views/UsuariosView.vue'
import CapacitacionCreateView from '../views/CapacitacionCreateView.vue'
import AsignacionCapacitacionView from '../views/AsignacionCapacitacionView.vue'

const routes = [
  { path: '/', component: LoginView },
  {
    path: '/',
    component: MainLayout,
    children: [
      { path: 'MiPanel', name: 'MiPanel', component: MiPanel },
      { path: 'Usuarios', name: 'UsuariosView', component: UsuariosView },
      {
        path: 'CrearCapacitacion',
        name: 'CapacitacionCreateView',
        component: CapacitacionCreateView,
      },
      {
        path: 'ConfiguracionCapacitacion/:idCapacitacion',
        name: 'CapacitacionConfigView',
        component: () => import('@/views/CapacitacionConfigView.vue'),
        props: true,
      },
      {
        path: 'AsignacionCapacitacion',
        name: 'AsignacionCapacitacionView',
        component: AsignacionCapacitacionView,
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
