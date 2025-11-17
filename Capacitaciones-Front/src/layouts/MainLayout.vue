<template>
  <div class="layout">
    <!-- Sidebar -->
    <aside class="sidebar">
      <PvMenu :model="menuItems" class="h-full p-2" />
    </aside>

    <!-- Contenedor principal: Header + Contenido -->
    <div class="main-container">
      <!-- Header -->
      <header class="header">
        <img src="@/assets/logo.png" alt="logo" class="header-logo" />
      </header>

      <!-- Contenido principal -->
      <main class="content">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()

const menuItems = ref([
  { label: 'Inicio', icon: 'pi pi-home', command: () => router.push('/MiPanel') },
  { label: 'Usuarios', icon: 'pi pi-users', command: () => router.push('/Usuarios') },
  {
    label: 'Capacitaciones',
    icon: 'pi pi-chart-bar',
    command: () => router.push('/CrearCapacitacion'),
  },
  {
    label: 'Cerrar sesión',
    icon: 'pi pi-sign-out',
    command: () => {
      localStorage.removeItem('jwt_token')
      window.location.href = '/'
    },
  },
])
</script>

<style scoped>
.layout {
  display: flex;
  height: 100vh;
}

/* Sidebar izquierda */
.sidebar {
  width: 250px;
  background: #f9f9f9;
  border-right: 1px solid #ddd;
}

/* Contenedor principal: header + contenido */
.main-container {
  flex: 1;
  display: flex;
  flex-direction: column;
}

/* Header arriba */
.header {
  height: 80px;
  background: white;
  border-bottom: 1px solid #ddd;
  display: flex;
  align-items: center;
  justify-content: flex-end; /* logo a la derecha */
  padding: 0 1rem;
  border: 1px solid #ddd;
}

/* Logo */
.header-logo {
  height: 60px;
  width: auto;
}

/* Contenido principal */
.content {
  flex: 1;
  padding: 2rem;
  background: #f5f6fa;
  overflow: auto; /* evita scroll extraño si el contenido crece */
}
</style>
