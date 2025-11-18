import { defineStore } from 'pinia'
import jwt_decode from 'jwt-decode'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: null,
    claims: null,
    insignias: null,
  }),

  actions: {
    setToken(token) {
      this.token = token
      this.claims = jwt_decode(token)
    },
    setInsignias(insignias) {
      console.log('insignias', insignias)
      this.insignias = insignias
    },

    logout() {
      this.token = null
      this.claims = null
      localStorage.removeItem('jwt_token')
    },
  },
  persist: true,
})
