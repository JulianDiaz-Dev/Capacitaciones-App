<template>
  <div class="layout">
    <aside class="sidebar">
      <PvMenu :model="menuItems" class="h-full p-2" />
    </aside>

    <div class="main-container">
      <header class="header">
        <div class="header-info">
          <p class="header-text">Bienvenido {{ nombre }} {{ apellido }}</p>
          <p class="header-text">Insignias: {{ insignias }}</p>
        </div>
        <img src="@/assets/logo.png" alt="logo" class="header-logo" />
      </header>

      <main class="content">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import usuarioService from '@/services/usuarioService'

const router = useRouter()
const authStore = useAuthStore()

const roles = authStore.claims?.role || []
const nombre = authStore.claims?.nombre || ''
const apellido = authStore.claims?.apellido || ''
const idUsuario = authStore.claims?.idUsuario || 0
const insignias = computed(() => authStore.insignias)
usuarioService
  .buscarUsuario(idUsuario)
  .then((res) => {
    const numeroInsignias = res.data.insignias
    console.log(numeroInsignias)
    authStore.setInsignias(numeroInsignias)
  })
  .catch((err) => console.error('Error cargando usuario:', err))
const esAdmin = roles.includes('ADMIN')

const menuItems = ref(
  esAdmin
    ? [
        { label: 'Mis Capacitaciones', icon: 'pi pi-home', command: () => router.push('/MiPanel') },
        { label: 'Usuarios', icon: 'pi pi-users', command: () => router.push('/Usuarios') },
        {
          label: 'Configurar Capacitaciones',
          icon: 'pi pi-chart-bar',
          command: () => router.push('/CrearCapacitacion'),
        },
        {
          label: 'Asignacion Capacitacion',
          icon: 'pi pi-bookmark',
          command: () => router.push('/AsignacionCapacitacion'),
        },
        {
          label: 'Cerrar sesión',
          icon: 'pi pi-sign-out',
          command: () => {
            localStorage.removeItem('jwt_token')
            window.location.href = '/'
          },
        },
      ]
    : [
        { label: 'Inicio', icon: 'pi pi-home', command: () => router.push('/MiPanel') },
        {
          label: 'Cerrar sesión',
          icon: 'pi pi-sign-out',
          command: () => {
            localStorage.removeItem('jwt_token')
            window.location.href = '/'
          },
        },
      ],
)
</script>

<style scoped>
.layout {
  display: flex;
  height: 100vh;
}
.header-info {
  display: flex;
  flex-direction: column;
  margin-right: 20px;
}
.header-text {
  margin: 0;
  font-size: 16px;
  font-weight: 500;
}
.sidebar {
  width: 250px;
  background: #f9f9f9;
  border-right: 1px solid #ddd;
}
.main-container {
  flex: 1;
  display: flex;
  flex-direction: column;
}
.header {
  height: 80px;
  background: white;
  border-bottom: 1px solid #ddd;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding: 0 1rem;
}
.header-logo {
  height: 60px;
  width: auto;
}
.content {
  flex: 1;
  padding: 2rem;
  background: #f5f6fa;
  overflow: auto;
}
</style>
