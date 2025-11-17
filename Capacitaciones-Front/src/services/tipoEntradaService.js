import api from './http'

export const tipoEntradaService = {
  buscarAll: () => api.get('/tipoEntrada'),
}

export default tipoEntradaService
