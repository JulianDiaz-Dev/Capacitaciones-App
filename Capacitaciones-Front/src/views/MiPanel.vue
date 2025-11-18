<template>
  <div class="mis-capacitaciones-container">
    <PvToast ref="toast" />

    <PvCard title="Mis Capacitaciones Asignadas">
      <template #content>
        <PvDataTable :value="capacitaciones" dataKey="idCapacitacion">
          <PvColumn field="titulo" header="Título" />
          <PvColumn field="descripcion" header="Descripción" />

          <PvColumn header="Progreso (%)" style="width: 11rem">
            <template #body="slotProps"> {{ slotProps.data.progreso }}% </template>
          </PvColumn>

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
  </div>
</template>

<script>
import { defineComponent } from 'vue'
import { useAuthStore } from '../stores/authStore'
import { useRouter } from 'vue-router'
import { capacitacionService } from '../services/capacitacionService'

export default defineComponent({
  name: 'MiPanel',

  data() {
    return {
      capacitaciones: [],
    }
  },
  setup() {
    const router = useRouter()
    return { router }
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
      this.router.push({
        name: 'DetalleCapacitacionView',
        params: { idCapacitacion: capacitacion.idCapacitacion, progreso: capacitacion.progreso },
      })
    },
  },
})
</script>

<style scoped>
.mis-capacitaciones-container {
  padding: 2rem;
}
</style>
