<template>
  <div class="asignacion-container">
    <PvToast ref="toast" />

    <PvCard title="Asignar Capacitaciones">
      <template #content>
        <div style="margin-bottom: 1rem">
          <PvDropdown
            v-model="selectedUsuario"
            :options="usuarios"
            optionLabel="nombre"
            optionValue="idUsuario"
            placeholder="Seleccione un usuario"
            style="width: 100%"
          />
        </div>

        <PvDataTable
          :value="capacitaciones"
          selectionMode="multiple"
          v-model:selection="selectedCapacitaciones"
          dataKey="idCapacitacion"
        >
          <PvColumn selectionMode="multiple" style="width: 3rem"></PvColumn>
          <PvColumn field="titulo" header="Título" />
          <PvColumn field="descripcion" header="Descripción" />
          <PvColumn header="Usuarios Asignados" style="width: 10rem">
            <template #body="slotProps">
              <PvButton
                label="Ver usuarios"
                icon="pi pi-users"
                class="p-button-sm"
                @click="verUsuariosAsignados(slotProps.data)"
              />
            </template>
          </PvColumn>
        </PvDataTable>

        <PvButton
          label="Asignar Capacitaciones"
          icon="pi pi-user-plus"
          @click="asignarCapacitaciones"
          class="p-mt-3"
        />

        <PvDialog
          header="Usuarios Asignados"
          v-model:visible="displayUsuariosModal"
          :modal="true"
          :style="{ width: '50%' }"
        >
          <PvDataTable :value="usuariosAsignados" dataKey="idUsuario">
            <PvColumn field="nombre" header="Nombre" />
            <PvColumn field="email" header="Email" />
            <PvColumn header="Eliminar" style="width: 10rem">
              <template #body="slotProps">
                <PvButton
                  label="Eliminar Asignacion"
                  icon="pi pi-trash"
                  class="p-button-sm p-button-danger"
                  @click="eliminarAsignacion(slotProps.data.idUsuario)"
                />
              </template>
            </PvColumn>
          </PvDataTable>
        </PvDialog>
      </template>
    </PvCard>
  </div>
</template>

<script>
import { usuarioService } from '../services/usuarioService'
import { capacitacionService } from '../services/capacitacionService'
import { asignacionService } from '../services/asignacionService'

export default {
  name: 'AsignacionCapacitacionesView',

  data() {
    return {
      usuarios: [],
      selectedUsuario: null,
      capacitaciones: [],
      selectedCapacitaciones: [],
      displayUsuariosModal: false,
      usuariosAsignados: [],
      GlobalCapacitacion: null,
    }
  },

  mounted() {
    this.getUsuarios()
    this.getCapacitaciones()
  },

  methods: {
    getUsuarios() {
      usuarioService
        .buscarTodosUsuarios()
        .then((res) => (this.usuarios = res.data))
        .catch((err) => console.error('Error cargando usuarios:', err))
    },

    getCapacitaciones() {
      capacitacionService
        .buscarTodosCapacitaciones()
        .then((res) => (this.capacitaciones = res.data))
        .catch((err) => console.error('Error cargando capacitaciones:', err))
    },
    eliminarAsignacion(idUsuario) {
      asignacionService
        .eliminar(idUsuario, this.GlobalCapacitacion.idCapacitacion)
        .then(() => {
          this.$refs.toast.add({
            severity: 'success',
            summary: 'Eliminado',
            detail: 'Se eliminó la asignación',
            life: 3000,
          })
          this.verUsuariosAsignados(this.GlobalCapacitacion)
        })
        .catch((err) => {
          console.error(err)
          this.$refs.toast.add({
            severity: 'error',
            summary: 'Error',
            detail: 'No se pudo eliminar la asignación',
            life: 3000,
          })
        })
    },

    asignarCapacitaciones() {
      if (!this.selectedUsuario) {
        alert('Seleccione un usuario')
        return
      }
      if (this.selectedCapacitaciones.length === 0) {
        alert('Seleccione al menos una capacitación')
        return
      }

      const payload = {
        idUsuario: this.selectedUsuario,
        idCapacitaciones: this.selectedCapacitaciones.map((c) => c.idCapacitacion),
      }

      asignacionService
        .asignarCapacitaciones(payload)
        .then(() => {
          this.$refs.toast.add({
            severity: 'success',
            summary: 'Asignación completada',
            life: 3000,
          })
          this.selectedCapacitaciones = []
        })
        .catch((err) => {
          console.error(err)
          this.$refs.toast.add({
            severity: 'error',
            summary: 'Error',
            detail: 'No se pudo asignar',
            life: 3000,
          })
        })
    },

    verUsuariosAsignados(capacitacion) {
      this.GlobalCapacitacion = capacitacion
      usuarioService
        .obtenerUsuariosAsignados(capacitacion.idCapacitacion)
        .then((res) => {
          this.usuariosAsignados = res.data
          this.displayUsuariosModal = true
        })
        .catch((err) => {
          console.error(err)
          this.$refs.toast.add({
            severity: 'error',
            summary: 'Error',
            detail: 'No se pudieron cargar los usuarios asignados',
            life: 3000,
          })
        })
    },
  },
}
</script>
