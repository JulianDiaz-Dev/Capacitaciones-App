<template>
  <div class="users-container">
    <PvToast />

    <PvCard title="Secciones y Entradas">
      <template #content>
        <div class="table-header">
          <h3>Secciones</h3>
          <PvButton
            label="Filtros"
            icon="pi pi-filter"
            class="p-button-sm"
            @click="filtrosSeccionesVisible = true"
          />
        </div>
        <br />

        <PvMenubar :model="itemsSecciones" />

        <PvDataTable
          :value="secciones"
          :filters="filtersSecciones"
          :paginator="true"
          :rows="5"
          :rowsPerPageOptions="[5, 10]"
          v-model:selection="selectedSeccion"
          selectionMode="single"
          dataKey="idSeccion"
        >
          <PvColumn field="idSeccion" header="ID" />
          <PvColumn field="titulo" header="Título" />
          <PvColumn field="orden" header="Orden" />
        </PvDataTable>
        <PvSidebar v-model:visible="filtrosSeccionesVisible" position="right">
          <h3>Filtros Secciones</h3>
          <div style="margin-bottom: 0.75rem">
            <PvInputText v-model="filtersSecciones.idSeccion.value" placeholder="ID" />
          </div>
          <div style="margin-bottom: 0.75rem">
            <PvInputText v-model="filtersSecciones.titulo.value" placeholder="Título" />
          </div>
          <div style="margin-bottom: 0.75rem">
            <PvInputText v-model="filtersSecciones.orden.value" placeholder="Orden" />
          </div>
          <PvButton
            label="Limpiar filtros"
            icon="pi pi-filter-slash"
            @click="limpiarFiltrosSecciones"
          />
        </PvSidebar>

        <br /><br />
        <div class="table-header">
          <h3>Entradas</h3>
          <PvButton
            label="Filtros"
            icon="pi pi-filter"
            class="p-button-sm"
            @click="filtrosEntradasVisible = true"
          />
        </div>
        <br />

        <PvMenubar :model="itemsEntradas" />

        <PvDataTable
          :value="entradas"
          :filters="filtersEntradas"
          :paginator="true"
          :rows="5"
          :rowsPerPageOptions="[5, 10]"
          v-model:selection="selectedEntrada"
          selectionMode="single"
          dataKey="idEntrada"
        >
          <PvColumn field="idEntrada" header="ID" />
          <PvColumn field="tipoEntrada.nombre" header="Tipo de Entrada" />
          <PvColumn field="titulo" header="Título" />
          <PvColumn field="contenido" header="Contenido" />
          <PvColumn field="archivoUrl" header="Archivo URL" />
          <PvColumn field="orden" header="Orden" />
        </PvDataTable>
        <PvSidebar v-model:visible="filtrosEntradasVisible" position="right">
          <h3>Filtros Entradas</h3>
          <div style="margin-bottom: 0.75rem">
            <PvInputText v-model="filtersEntradas.idEntrada.value" placeholder="ID" />
          </div>
          <div style="margin-bottom: 0.75rem">
            <PvInputText v-model="filtersEntradas.descripcion.value" placeholder="Descripción" />
          </div>
          <div style="margin-bottom: 0.75rem">
            <PvCalendar
              v-model="filtersEntradas.fechaRegistro.value"
              placeholder="Fecha Registro"
              dateFormat="yy-mm-dd"
              showIcon
            />
          </div>
          <PvButton
            label="Limpiar filtros"
            icon="pi pi-filter-slash"
            @click="limpiarFiltrosEntradas"
          />
        </PvSidebar>
        <PvDialog header="Crear Sección" v-model:visible="displayModalSecciones" :modal="true">
          <span class="p-float-label">
            <PvInputText id="nombre" type="text" v-model="seccion.titulo" style="width: 100%" />
            <label>Título</label>
          </span>

          <br />

          <span class="p-float-label">
            <PvInputText id="email" type="text" v-model="seccion.orden" style="width: 100%" />
            <label>Orden</label>
          </span>

          <template #footer>
            <PvButton label="Guardar" icon="pi pi-check" @click="saveSeccion" />
            <PvButton
              label="Cancelar"
              icon="pi pi-times"
              @click="closeModal"
              class="p-button-secondary"
            />
          </template>
        </PvDialog>
        <PvDialog header="Crear Entrada" v-model:visible="displayModalEntrada" :modal="true">
          <span class="p-float-label">
            <PvInputText id="nombre" type="text" v-model="entrada.titulo" style="width: 100%" />
            <label>Título</label>
          </span>
          <br />
          <span class="p-float-label">
            <PvInputText id="email" type="text" v-model="entrada.contenido" style="width: 100%" />
            <label>Contenido</label>
          </span>
          <br />
          <span class="p-float-label">
            <PvInputText id="email" type="text" v-model="entrada.archivoUrl" style="width: 100%" />
            <label>Archivo/Url</label>
          </span>
          <br />
          <span class="p-float-label">
            <PvInputText id="email" type="text" v-model="entrada.orden" style="width: 100%" />
            <label>Orden</label>
          </span>
          <br />
          <span class="p-float-label">
            <PvDropdown
              id="tipoEntrada"
              v-model="entrada.tipoEntrada"
              :options="tiposEntrada"
              optionLabel="nombre"
              style="width: 100%"
              placeholder="Seleccione"
            />
            <label>Tipo Entrada</label>
          </span>

          <template #footer>
            <PvButton label="Guardar" icon="pi pi-check" @click="saveEntrada" />
            <PvButton
              label="Cancelar"
              icon="pi pi-times"
              @click="closeModal"
              class="p-button-secondary"
            />
          </template>
        </PvDialog>
      </template>
    </PvCard>
  </div>
</template>

<script>
import { seccionService } from '../services/seccionService'
import { entradaService } from '../services/entradaService'
import { tipoEntradaService } from '../services/tipoEntradaService'

export default {
  name: 'CapacitacionConfigView',

  data() {
    return {
      secciones: [],
      entradas: [],
      seccion: {
        idSeccion: null,
        titulo: null,
        orden: null,
        idCapacitacion: null,
      },
      entrada: {
        idEntrada: null,
        titulo: null,
        contenido: null,
        orden: null,
        idSeccion: null,
        tipoEntrada: null,
      },
      tiposEntrada: [],

      selectedSeccion: null,
      selectedEntrada: null,
      idCapacitacion: this.$route.params.idCapacitacion || null,

      filtrosSeccionesVisible: false,
      displayModalSecciones: false,

      filtersSecciones: {
        idSeccion: { value: null, matchMode: 'equals' },
        titulo: { value: '', matchMode: 'contains' },
        orden: { value: null, matchMode: 'equals' },
      },

      filtrosEntradasVisible: false,
      filtersEntradas: {
        idEntrada: { value: null, matchMode: 'equals' },
        descripcion: { value: '', matchMode: 'contains' },
        fechaRegistro: { value: null, matchMode: 'equals' },
      },

      itemsSecciones: [
        { label: 'Nuevo', icon: 'pi pi-fw pi-plus', command: () => this.verSaveSeccionModal() },
        {
          label: 'Editar',
          icon: 'pi pi-fw pi-pencil',
          command: () => this.verUpdateSeccionModal(),
        },
        { label: 'Eliminar', icon: 'pi pi-fw pi-trash', command: () => this.deleteSeccion() },
      ],
      displayModalEntrada: false,
      itemsEntradas: [
        { label: 'Nuevo', icon: 'pi pi-fw pi-plus', command: () => this.verSaveEntradaModal() },
        {
          label: 'Editar',
          icon: 'pi pi-fw pi-pencil',
          command: () => this.verUpdateEntradaModal(),
        },
        { label: 'Eliminar', icon: 'pi pi-fw pi-trash', command: () => this.deleteEntrada() },
      ],
    }
  },

  mounted() {
    this.getSecciones()
    this.getTiposEntrada()
  },

  watch: {
    selectedSeccion(newSeccion) {
      if (!newSeccion) {
        this.entradas = []
        return
      }
      this.getEntradasPorSeccion(newSeccion?.idSeccion)
    },
  },

  methods: {
    verSaveSeccionModal() {
      this.seccion = {
        idSeccion: null,
        titulo: null,
        orden: null,
        idCapacitacion: this.idCapacitacion,
      }
      this.displayModalSecciones = true
    },
    verSaveEntradaModal() {
      if (!this.selectedSeccion) {
        alert('Seleccione un Seccion')
        return
      }
      this.entrada = {
        idEntrada: null,
        titulo: null,
        contenido: null,
        orden: null,
        idSeccion: this.selectedSeccion.idSeccion,
        tipoEntrada: null,
      }
      this.displayModalEntrada = true
    },
    verUpdateEntradaModal() {
      if (!this.selectedEntrada) {
        alert('Seleccione una Entrada')
        return
      }
      this.entrada = { ...this.selectedEntrada }
      this.entrada.idSeccion = this.selectedSeccion.idSeccion

      const tipo = this.tiposEntrada.find(
        (t) => t.idTipoEntrada === this.selectedEntrada.tipoEntrada.idTipoEntrada,
      )
      this.entrada.tipoEntrada = tipo || null
      this.displayModalEntrada = true
    },
    verUpdateSeccionModal() {
      if (!this.selectedSeccion) {
        alert('Seleccione un Seccion')
        return
      }
      this.seccion = { ...this.selectedSeccion }
      this.seccion.idCapacitacion = this.idCapacitacion
      this.displayModalSecciones = true
    },
    saveEntrada() {
      console.log(this.entrada)
      entradaService
        .save(this.entrada)
        .then((response) => {
          if (response.status === 201 || response.status === 200) {
            if (this.selectedEntrada) {
              this.selectedEntrada = this.entrada
            }
            this.displayModalEntrada = false
            this.entrada = {
              idEntrada: null,
              titulo: null,
              contenido: null,
              orden: null,
              idSeccion: this.selectedSeccion.idSeccion,
              tipoEntrada: null,
            }

            this.getEntradasPorSeccion(this.selectedSeccion.idSeccion)
          }
        })
        .catch((error) => {
          alert('No se pudo guardar la sección')
          console.log(error)
        })
    },

    saveSeccion() {
      seccionService
        .save(this.seccion)
        .then((response) => {
          if (response.status === 201 || response.status === 200) {
            if (this.selectedSeccion) {
              this.selectedSeccion = this.seccion
            }
            this.displayModalSecciones = false
            this.seccion = {
              idSeccion: null,
              titulo: null,
              orden: null,
              idCapacitacion: this.idCapacitacion,
            }

            this.getSecciones()
          }
        })
        .catch((error) => {
          alert('No se pudo guardar la sección', error)
        })
    },

    getSecciones() {
      seccionService
        .buscarSeccionesPorCapacitacion(this.idCapacitacion)
        .then((res) => (this.secciones = res.data))
        .catch((err) => console.error('Error al cargar secciones:', err))
    },
    getEntradasPorSeccion(idSeccion) {
      if (!idSeccion) {
        this.entradas = []
        return
      }

      entradaService
        .buscarPorSeccion(idSeccion)
        .then((res) => (this.entradas = res.data))
        .catch((err) => console.error('Error al cargar entradas:', err))
    },
    deleteEntrada() {
      if (!this.selectedEntrada) {
        alert('Seleccione una Entrada')
        return
      }

      if (!confirm(`¿Eliminar la entrada "${this.selectedEntrada.titulo}"?`)) return

      const id = this.selectedEntrada.idEntrada

      entradaService
        .eliminar(id)
        .then((response) => {
          if (response.status === 200 || response.status === 204) {
            this.$toast.add({
              severity: 'success',
              summary: 'Eliminación Completada',
              detail: 'Se eliminó el registro correctamente.',
              life: 3000,
            })
            this.getEntradasPorSeccion(this.selectedSeccion.idSeccion)
          }
        })
        .catch((error) => {
          console.error(error)
          alert('No se pudo eliminar la entrada')
        })
    },

    deleteSeccion() {
      if (!this.selectedSeccion) {
        alert('Seleccione una sección')
        return
      }

      if (!confirm(`¿Eliminar la sección "${this.selectedSeccion.titulo}"?`)) return

      const id = this.selectedSeccion.idSeccion

      seccionService
        .eliminar(id)
        .then((response) => {
          if (response.status === 200 || response.status === 204) {
            this.$toast.add({
              severity: 'success',
              summary: 'Eliminación Completada',
              detail: 'Se eliminó el registro correctamente.',
              life: 3000,
            })
            this.getSecciones()
          }
        })
        .catch((error) => {
          console.error(error)
          alert('No se pudo eliminar la sección')
        })
    },
    getTiposEntrada() {
      tipoEntradaService
        .buscarAll()
        .then((res) => (this.tiposEntrada = res.data))
        .catch((err) => console.error('Error cargando tipos de entrada:', err))
    },

    limpiarFiltrosSecciones() {
      this.filtersSecciones.idSeccion.value = null
      this.filtersSecciones.titulo.value = ''
      this.filtersSecciones.orden.value = null
    },

    limpiarFiltrosEntradas() {
      this.filtersEntradas.idEntrada.value = null
      this.filtersEntradas.descripcion.value = ''
      this.filtersEntradas.fechaRegistro.value = null
    },

    closeModal() {
      this.displayModalSecciones = false
      this.displayModalEntrada = false
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
