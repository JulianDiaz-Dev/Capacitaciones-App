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
          <PvColumn field="titulo" header="Título" />
          <PvColumn field="contenido" header="Contenido" />
          <PvColumn field="archivoUrl" header="Archivo" />
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
            <PvInputText v-model="seccion.titulo" style="width: 100%" />
            <label>Título</label>
          </span>
          <br />
          <span class="p-float-label">
            <PvInputText v-model="seccion.orden" type="number" style="width: 100%" />
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
        <PvDialog
          header="Crear / Editar Entrada"
          v-model:visible="displayModalEntrada"
          :modal="true"
        >
          <span class="p-float-label">
            <PvInputText v-model="entrada.titulo" style="width: 100%" />
            <label>Título</label>
          </span>
          <br />
          <span class="p-float-label">
            <PvInputText v-model="entrada.contenido" style="width: 100%" />
            <label>Contenido</label>
          </span>
          <br />

          <span class="p-float-label" v-if="displayModalEntrada">
            <input
              type="file"
              @change="onFileSelected"
              style="width: 100%"
              accept="image/*,video/*,.pdf,.doc,.docx"
            />
          </span>
          <div v-if="uploadProgress > 0" style="margin-top: 0.5rem">
            <PvProgressBar :value="uploadProgress" />
            <p style="margin-top: 0.25rem">Subiendo... {{ uploadProgress }}%</p>
          </div>
          <br />
          <span class="p-float-label">
            <PvInputText v-model.number="entrada.orden" type="number" style="width: 100%" />
            <label>Orden</label>
          </span>
          <br />

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

export default {
  name: 'CapacitacionConfigView',

  data() {
    return {
      secciones: [],
      entradas: [],
      uploadProgress: 0,
      seccion: {
        idSeccion: null,
        titulo: null,
        orden: null,
        idCapacitacion: null,
      },
      entrada: {
        idEntrada: null,
        titulo: '',
        contenido: '',
        orden: null,
        idSeccion: null,
        archivo: null,
        archivoUrl: null,
      },
      selectedSeccion: null,
      selectedEntrada: null,
      idCapacitacion: this.$route.params.idCapacitacion || null,
      filtrosSeccionesVisible: false,
      displayModalSecciones: false,
      filtrosEntradasVisible: false,
      displayModalEntrada: false,
      filtersSecciones: {
        idSeccion: { value: null, matchMode: 'equals' },
        titulo: { value: '', matchMode: 'contains' },
        orden: { value: null, matchMode: 'equals' },
      },
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
  },

  watch: {
    selectedSeccion(newSeccion) {
      if (!newSeccion) {
        this.entradas = []
        return
      }
      this.getEntradasPorSeccion(newSeccion.idSeccion)
    },
  },

  methods: {
    verSaveSeccionModal() {
      this.seccion = {
        idSeccion: null,
        titulo: '',
        orden: null,
        idCapacitacion: this.idCapacitacion,
      }
      this.displayModalSecciones = true
    },

    verSaveEntradaModal() {
      if (!this.selectedSeccion) {
        alert('Seleccione una Sección')
        return
      }
      this.entrada = {
        idEntrada: null,
        titulo: '',
        contenido: '',
        orden: null,
        idSeccion: this.selectedSeccion.idSeccion,
        archivo: null,
        archivoUrl: null,
      }
      this.displayModalEntrada = true
    },

    onFileSelected(event) {
      console.log('Archivo seleccionado:')
      const file = event.target.files[0]

      if (file) this.entrada.archivo = file
    },

    async saveEntrada() {
      try {
        if (this.entrada.archivo) {
          const formData = new FormData()
          formData.append('file', this.entrada.archivo)

          const response = await entradaService.uploadFile(formData, {
            onUploadProgress: (progressEvent) => {
              this.uploadProgress = Math.round((progressEvent.loaded * 100) / progressEvent.total)
            },
          })
          if (response && (response.status === 200 || response.status === 201)) {
            this.entrada.archivoUrl = response.data.url
          } else {
            console.error('Error al subir archivo', response)
            alert('No se pudo subir el archivo')
            return
          }
        }

        const res = await entradaService.save(this.entrada)
        if (res && (res.status === 200 || res.status === 201)) {
          this.displayModalEntrada = false
          this.getEntradasPorSeccion(this.selectedSeccion.idSeccion)
        } else {
          console.error('Error al guardar entrada', res)
          alert('No se pudo guardar la entrada')
        }
        this.uploadProgress = 0
      } catch (error) {
        console.error(error)
        alert('Ocurrió un error al guardar la entrada')
      }
    },

    saveSeccion() {
      seccionService
        .save(this.seccion)
        .then(() => {
          this.displayModalSecciones = false
          this.getSecciones()
        })
        .catch((err) => console.error(err))
    },

    getSecciones() {
      seccionService
        .buscarSeccionesPorCapacitacion(this.idCapacitacion)
        .then((res) => (this.secciones = res.data))
        .catch((err) => console.error(err))
    },

    getEntradasPorSeccion(idSeccion) {
      entradaService
        .buscarPorSeccion(idSeccion)
        .then((res) => (this.entradas = res.data))
        .catch((err) => console.error(err))
    },

    closeModal() {
      this.displayModalEntrada = false
      this.displayModalSecciones = false
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
