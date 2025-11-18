<template>
  <div class="login-container">
    <PvCard style="width: 30rem; overflow: hidden">
      <template #title>Login</template>

      <template #content>
        <form @submit.prevent="handleLogin" class="form-body">
          <div class="form-group">
            <label for="email">Email</label>
            <PvInputText id="email" v-model="email" class="input-field" />
          </div>

          <div class="form-group">
            <label for="password">Contraseña</label>
            <PvPassword id="password" v-model="password" toggleMask class="input-field" />
          </div>

          <PvButton label="Login" type="submit" class="p-mt-2 w-full" />
          <PvMessage v-if="errorMessage" severity="error" class="p-mt-2">
            {{ errorMessage }}
          </PvMessage>
        </form>
        <PvDialog header="Actualizar Contraseña" v-model:visible="displayModal" :modal="true">
          <span class="p-float-label">
            <PvInputText
              id="nombre"
              type="text"
              v-model="usuario.nombreCompleto"
              style="width: 100%"
              :readonly="true"
            />
            <label>Nombre</label>
          </span>
          <br />
          <span class="p-float-label">
            <PvInputText
              id="email"
              type="text"
              v-model="usuario.email"
              style="width: 100%"
              :readonly="true"
            />
            <label>Email</label>
          </span>
          <br />
          <span class="p-float-label">
            <PvInputText
              id="password"
              type="text"
              v-model="usuario.password"
              style="width: 100%"
              placeholder="Ingrese contraseña"
              required
            />
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

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f2f5;
}

.form-body {
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 0.3rem;
  font-weight: 600;
}

.input-field {
  width: 100%;
}

.w-full {
  width: 100%;
}
</style>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '../services/loginService'
import { useAuthStore } from '../stores/authStore'
import { usuarioService } from '../services/usuarioService'

export default {
  name: 'LoginView',
  setup() {
    const router = useRouter()
    const email = ref('')
    const password = ref('')
    const errorMessage = ref('')
    const auth = useAuthStore()
    const displayModal = ref(false)
    const usuario = ref({
      idUsuario: null,
      nombre: null,
      apellido: null,
      nombreCompleto: '',
      email: '',
      password: '',
      ContraseniaTemporal: false,
    })

    const handleLogin = async () => {
      errorMessage.value = ''
      try {
        const response = await login(email.value, password.value)
        const token = response.data.token
        console.log(token)
        localStorage.setItem('jwt_token', token)
        auth.setToken(token)
        if (auth.claims) {
          usuario.value.idUsuario = auth.claims.idUsuario
          usuario.value.nombre = auth.claims.nombre
          usuario.value.apellido = auth.claims.apellido
          usuario.value.nombreCompleto = auth.claims.nombre + ' ' + auth.claims.apellido
          usuario.value.email = auth.claims.email
          usuario.value.ContraseniaTemporal = auth.claims.ContraseniaTemporal
        }
        if (usuario.value.ContraseniaTemporal) {
          displayModal.value = true
        } else {
          router.push('/MiPanel')
        }
      } catch (error) {
        if (error.response && error.response.status === 401) {
          errorMessage.value = 'Usuario o contraseña incorrectos'
        } else {
          errorMessage.value = 'Error en el servidor'
        }
      }
    }
    const closeModal = () => {
      displayModal.value = false
    }
    const save = () => {
      if (!usuario.value.password || usuario.value.password.trim() === '') {
        alert('La contraseña es obligatoria')
        return
      }
      usuarioService
        .save(usuario.value)
        .then((res) => {
          console.log(res)
          displayModal.value = false
          router.push('/MiPanel')
        })
        .catch((err) => {
          console.error(err)
          alert('No se pudo guardar')
        })
    }

    return { email, password, errorMessage, handleLogin, closeModal, displayModal, usuario, save }
  },
}
</script>
