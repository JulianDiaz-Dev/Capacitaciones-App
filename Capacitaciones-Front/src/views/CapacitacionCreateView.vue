<template>
  <div class="users-container">
    <PvToast />
    <PvCard title="Capacitaciones">
      <template #content>
        <div class="table-header">
          <h3>Secciones</h3>
          <PvButton
            label="Filtros"
            icon="pi pi-filter"
            class="p-button-sm"
            @click="filtrosVisible = true"
          />
        </div>
        <br />
        <PvMenubar :model="items" />
        <PvDataTable
          :value="capacitaciones"
          :filters="filters"
          :paginator="true"
          :rows="5"
          :rowsPerPageOptions="[5, 10]"
          v-model:selection="selectedCapacitacion"
          selectionMode="single"
          dataKey="idCapacitacion"
        >
          <PvColumn field="idCapacitacion" header="ID" />
          <PvColumn field="titulo" header="Título" />
          <PvColumn field="descripcion" header="Descripción" />
          <PvColumn field="fechaCreacion" header="Fecha Creación" />
          <PvColumn header="Acciones">
            <template #body="slotProps">
              <PvButton
                label="Secciones"
                icon="pi pi-arrow-right"
                class="p-button-sm"
                @click="irSecciones(slotProps.data)"
              />
            </template>
          </PvColumn>
        </PvDataTable>
        <PvDialog header="Crear Capacitacion" v-model:visible="displayModal" :modal="true">
          <span class="p-float-label">
            <PvInputText
              id="nombre"
              type="text"
              v-model="capacitacion.titulo"
              style="width: 100%"
            />
            <label>Titulo</label>
          </span>
          <br />
          <span class="p-float-label">
            <PvInputText
              id="email"
              type="text"
              v-model="capacitacion.descripcion"
              style="width: 100%"
            />
            <label>Descripcion</label>
          </span>
          <template #footer>
            <PvButton label="Guardar" icon="pi pi-check" @click="save" />
            <PvButton
              label="Cancelar"
              icon="pi pi-times"
              @click="closeModal"
              class="p-button-secondary"
            />
          </template>
        </PvDialog>
        <PvSidebar v-model:visible="filtrosVisible" position="right">
          <h3>Filtros</h3>
          <div style="margin-bottom: 0.75rem">
            <PvInputText v-model="filters.idCapacitacion.value" placeholder="ID" />
          </div>
          <div style="margin-bottom: 0.75rem">
            <PvInputText v-model="filters.titulo.value" placeholder="Título" />
          </div>
          <div style="margin-bottom: 0.75rem">
            <PvInputText v-model="filters.descripcion.value" placeholder="Descripción" />
          </div>
          <div style="margin-bottom: 0.75rem">
            <PvCalendar
              v-model="filters.fechaCreacion.value"
              placeholder="Fecha Creación"
              dateFormat="yy-mm-dd"
              showIcon
            />
          </div>
          <PvButton label="Limpiar filtros" icon="pi pi-filter-slash" @click="limpiarFiltros" />
        </PvSidebar>
      </template>
    </PvCard>
  </div>
</template>

<script>
import { capacitacionService } from '../services/capacitacionService'

export default {
  name: 'CapacitacionCreateView',
  data() {
    return {
      capacitaciones: [],
      selectedCapacitacion: null,
      displayModal: false,
      filtrosVisible: false,
      filters: {
        idCapacitacion: { value: null, matchMode: 'equals' },
        titulo: { value: '', matchMode: 'contains' },
        descripcion: { value: '', matchMode: 'contains' },
        fechaCreacion: { value: null, matchMode: 'contains' },
      },
      items: [
        { label: 'Nuevo', icon: 'pi pi-fw pi-plus', command: () => this.verSaveModal() },
        { label: 'Editar', icon: 'pi pi-fw pi-pencil', command: () => this.verUpdateModal() },
        { label: 'Eliminar', icon: 'pi pi-fw pi-trash', command: () => this.deleteCapacitacion() },
      ],
      capacitacion: {
        titulo: null,
        descripcion: null,
      },
    }
  },
  mounted() {
    this.getAll()
  },
  methods: {
    verSaveModal() {
      this.capacitacion = {
        nombre: null,
        descripcion: null,
      }
      this.displayModal = true
    },
    irSecciones(capacitacion) {
      this.$router.push({
        name: 'CapacitacionConfigView',
        params: { idCapacitacion: capacitacion.idCapacitacion },
      })
    },
    save() {
      this.capacitacion.fechaCreacion = null
      capacitacionService
        .save(this.capacitacion)
        .then((data) => {
          if (data.status === 201 || data.status === 200) {
            if (this.selectedCapacitacion) {
              this.selectedCapacitacion = this.capacitacion
            }
            this.displayModal = false
            this.capacitacion = { titulo: null, descripcion: null }
            this.getAll()
          }
        })
        .catch((error) => {
          console.error(error)
          alert('No se pudo guardar la capacitacion')
        })
    },
    verUpdateModal() {
      if (!this.selectedCapacitacion) {
        alert('Seleccione un Capacitacion')
        return
      }
      this.capacitacion = { ...this.selectedCapacitacion }
      this.displayModal = true
    },
    getAll() {
      capacitacionService
        .buscarTodosCapacitaciones()
        .then((res) => (this.capacitaciones = res.data))
        .catch((err) => console.error('Error al cargar capacitaciones:', err))
    },
    deleteCapacitacion() {
      if (!this.selectedCapacitacion) {
        alert('Seleccione un usuario')
        return
      }
      const confirmar = window.confirm(
        `¿Está seguro de que desea eliminar al usuario ${this.selectedCapacitacion.titulo}?`,
      )
      if (!confirmar) return
      const id = this.selectedCapacitacion.idCapacitacion

      capacitacionService
        .eliminar(id)
        .then((response) => {
          if (response.status === 200 || response.status === 204) {
            this.displayModal = false
            this.selectedUser = null
            this.usuario = { nombre: null, apellido: null, email: null, password: null }
            this.$toast.add({
              severity: 'success',
              summary: 'Eliminación Completada',
              detail: 'Se eliminó el registro correctamente.',
              life: 3000,
            })
            this.getAll()
          }
        })
        .catch((error) => {
          console.error(error)
          alert('No se pudo eliminar el usuario')
        })
    },
    limpiarFiltros() {
      this.filters.idCapacitacion.value = null
      this.filters.titulo.value = ''
      this.filters.descripcion.value = ''
      this.filters.fechaCreacion.value = null
    },
    closeModal() {
      this.displayModal = false
    },
  },
}
</script>

<style scoped>
.users-container {
  padding: 2rem;
}
.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.5rem;
}
</style>
