<template>
  <div class="users-container">
    <PvToast />
    <PvCard title="Usuarios">
      <template #content>
        <PvMenubar :model="items" />
        <br />
        <PvDataTable
          :value="usuarios"
          tableStyle="min-width: 50rem"
          :paginator="true"
          :rows="5"
          :rowsPerPageOptions="[5, 10]"
          v-model:selection="selectedUser"
          selectionMode="single"
          dataKey="idUsuario"
        >
          <PvColumn field="idUsuario" header="Id" />
          <PvColumn field="email" header="Email" />
          <PvColumn field="nombre" header="Nombre" />
          <PvColumn field="apellido" header="Apellido" />
          <PvColumn header="Activo">
            <template #body="slotProps">
              {{ slotProps.data.activo ? 'Sí' : 'No' }}
            </template>
          </PvColumn>
        </PvDataTable>
        <PvDialog header="Crear Usuario" v-model:visible="displayModal" :modal="true">
          <span class="p-float-label">
            <PvInputText id="nombre" type="text" v-model="usuario.nombre" style="width: 100%" />
            <label>Nombre</label>
          </span>
          <br />

          <span class="p-float-label">
            <PvInputText id="apellido" type="text" v-model="usuario.apellido" style="width: 100%" />
            <label>Apellido</label>
          </span>
          <br />

          <span class="p-float-label">
            <PvInputText id="email" type="text" v-model="usuario.email" style="width: 100%" />
            <label>Email</label>
          </span>
          <br />
          <div class="flex align-items-center gap-2">
            <PvCheckbox v-model="usuario.activo" :binary="true" inputId="activoCheck" />
            <label for="activoCheck">Activo</label>
          </div>
          <br />
          <span class="p-float-label">
            <PvInputText id="password" type="text" v-model="usuario.password" style="width: 100%" />
            <label>Contraseña</label>
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
      </template>
    </PvCard>
  </div>
</template>

<script>
import { usuarioService } from '../services/usuarioService'

export default {
  name: 'UsuariosView',
  data() {
    return {
      usuarios: [],
      selectedUser: null,
      displayModal: false,
      usuario: {
        nombre: null,
        apellido: null,
        email: null,
        password: null,
        activo: true,
        contraseniaTemporal: true,
      },
      items: [
        {
          label: 'Nuevo',
          icon: 'pi pi-fw pi-plus',
          command: () => this.verSaveModal(),
        },
        {
          label: 'Editar',
          icon: 'pi pi-fw pi-pencil',
          command: () => this.verUpdateModal(),
        },
        {
          label: 'Eliminar',
          icon: 'pi pi-fw pi-trash',
          command: () => this.deleteUser(),
        },
      ],
    }
  },

  mounted() {
    this.getAll()
  },

  methods: {
    verSaveModal() {
      this.usuario = {
        nombre: null,
        apellido: null,
        email: null,
        password: null,
        activo: true,
        contraseniaTemporal: true,
      }
      this.displayModal = true
    },

    getAll() {
      usuarioService
        .buscarTodosUsuarios()
        .then((res) => {
          this.usuarios = res.data
        })
        .catch((err) => {
          console.error('Error al cargar usuarios:', err)
        })
    },

    save() {
      if (this.usuario.password && this.usuario.password.trim() !== '') {
        this.usuario.contraseniaTemporal = true
      }

      usuarioService
        .save(this.usuario)
        .then((data) => {
          if (data.status === 201 || data.status === 200) {
            if (this.selectedUser) {
              this.selectedUser = this.usuario
            }
            this.displayModal = false
            this.usuario = { nombre: null, apellido: null, email: null, password: null }
            this.getAll()
          }
        })
        .catch((error) => {
          console.error(error)
          alert('No se pudo eliminar el usuario')
        })
    },
    verUpdateModal() {
      if (!this.selectedUser) {
        alert('Seleccione un usuario')
        return
      }
      this.usuario = { ...this.selectedUser }
      this.displayModal = true
    },

    deleteUser() {
      if (!this.selectedUser) {
        alert('Seleccione un usuario')
        return
      }
      const confirmar = window.confirm(
        `¿Está seguro de que desea eliminar al usuario ${this.selectedUser.nombre}?`,
      )
      if (!confirmar) return
      const id = this.selectedUser.idUsuario

      usuarioService
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
</style>
