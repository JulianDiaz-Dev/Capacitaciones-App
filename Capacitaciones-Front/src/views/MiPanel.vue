<template>
  <div class="mis-capacitaciones-container">
    <PvToast ref="toast" />

    <PvCard title="Mis Capacitaciones Asignadas">
      <template #content>
        <PvButton
          label="Filtros"
          icon="pi pi-filter"
          class="p-button-sm"
          @click="filtrosVisible = true"
        />
        <PvDataTable :value="capacitaciones" :filters="filters" dataKey="idCapacitacion">
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

        <PvSidebar v-model:visible="filtrosVisible" position="right">
          <h3>Filtros Secciones</h3>
          <div style="margin-bottom: 0.75rem">
            <PvInputText v-model="filters.descripcion.value" placeholder="Descripcion" />
          </div>
          <div style="margin-bottom: 0.75rem; display: flex; align-items: center; gap: 0.5rem">
            <label for="checkTerminado">Terminado</label>
            <PvCheckbox
              v-model="progresoTerminado"
              binary
              label="Terminado"
              @change="filtrarProgreso"
            />
          </div>
          <PvButton label="Limpiar filtros" icon="pi pi-filter-slash" @click="limpiarFiltros" />
        </PvSidebar>
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
      filtrosVisible: null,
      filters: {
        descripcion: { value: '', matchMode: 'contains' },
        progreso: { value: 99, matchMode: 'lt' },
      },
      progresoTerminado: false,
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

    filtrarProgreso() {
      if (this.progresoTerminado) {
        this.filters.progreso.matchMode = 'equals'
        this.filters.progreso.value = 100
      } else {
        this.filters.progreso.matchMode = 'lt'
        this.filters.progreso.value = 99
      }
    },

    limpiarFiltros() {
      this.filters.descripcion.value = ''
      this.progresoTerminado = false
      this.filters.progreso.matchMode = 'lt'
      this.filters.progreso.value = 99
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
