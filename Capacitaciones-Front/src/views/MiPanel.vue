<template>
  <div class="mis-capacitaciones-container">
    <PvToast ref="toast" />

    <PvCard title="Mis Capacitaciones Asignadas">
      <template #content>
        <PvDataTable :value="capacitaciones" dataKey="idCapacitacion">
          <PvColumn field="titulo" header="Título" />
          <PvColumn field="descripcion" header="Descripción" />
          <PvColumn field="progreso" header="Progreso (%)" />
          <PvColumn header="Acciones" style="width: 10rem">
            <template #body="slotProps">
              <PvButton
                label="Ver Detalle"
                icon="pi pi-info-circle"
                class="p-button-sm"
                @click="verDetalle(slotProps.data)"
              />
            </template>
          </PvColumn>
        </PvDataTable>
      </template>
    </PvCard>

    <PvDialog
      header="Detalle de Capacitación"
      v-model:visible="displayDetalleModal"
      :modal="true"
      :style="{ width: '50%' }"
    >
      <div v-if="detalleCapacitacion">
        <p><strong>Título:</strong> {{ detalleCapacitacion.titulo }}</p>
        <p><strong>Descripción:</strong> {{ detalleCapacitacion.descripcion }}</p>
        <p><strong>Progreso:</strong> {{ detalleCapacitacion.progreso }}%</p>
      </div>
    </PvDialog>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
import { useAuthStore } from '../stores/authStore'
// import { asignacionService } from '../services/asignacionService'
import { capacitacionService } from '../services/capacitacionService'

export default defineComponent({
  name: 'MisCapacitacionesView',

  data() {
    return {
      capacitaciones: [],
      displayDetalleModal: false,
      detalleCapacitacion: null,
    }
  },

  mounted() {
    this.cargarCapacitaciones()
  },

  methods: {
    cargarCapacitaciones() {
      const authStore = useAuthStore()
      const idUsuario = authStore.claims?.idUsuario

      if (!idUsuario) {
        this.$refs.toast.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Usuario no identificado',
          life: 3000,
        })
        return
      }

      capacitacionService
        .buscarMisCapacitaciones(idUsuario)
        .then((res) => {
          this.capacitaciones = res.data
        })
        .catch((err) => {
          console.error(err)
          this.$refs.toast.add({
            severity: 'error',
            summary: 'Error',
            detail: 'No se pudieron cargar las capacitaciones',
            life: 3000,
          })
        })
    },

    verDetalle(capacitacion) {
      this.detalleCapacitacion = capacitacion
      this.displayDetalleModal = true
    },
  },
})
</script>

<style scoped>
.mis-capacitaciones-container {
  padding: 2rem;
}
</style>
