<template>
  <div class="login-container">
    <PvCard style="width: 30rem; overflow: hidden">
      <template #title>Login</template>

      <template #content>
        <form @submit.prevent="handleLogin" class="form-body">
          <div class="form-group">
            <label for="username">Usuario</label>
            <PvInputText id="username" v-model="username" class="input-field" />
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

export default {
  name: 'LoginView',
  setup() {
    const router = useRouter()
    const username = ref('')
    const password = ref('')
    const errorMessage = ref('')

    const handleLogin = async () => {
      errorMessage.value = ''
      try {
        const response = await login(username.value, password.value)

        localStorage.setItem('jwt_token', response.data.token)
        router.push('/MiPanel')
      } catch (error) {
        if (error.response && error.response.status === 401) {
          errorMessage.value = 'Usuario o contraseña incorrectos'
        } else {
          errorMessage.value = 'Error en el servidor'
        }
      }
    }

    return { username, password, errorMessage, handleLogin }
  },
}
</script>
