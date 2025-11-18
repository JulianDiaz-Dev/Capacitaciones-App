import { defineStore } from 'pinia'
import jwt_decode from 'jwt-decode'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: null,
    claims: null,
  }),

  actions: {
    setToken(token) {
      this.token = token
      this.claims = jwt_decode(token)
    },

    logout() {
      this.token = null
      this.claims = null
      localStorage.removeItem('jwt_token')
    },
  },
  persist: true,
})
