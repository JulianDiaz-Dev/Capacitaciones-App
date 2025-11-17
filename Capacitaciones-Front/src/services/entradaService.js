import api from './http'

export const entradaService = {
  buscarPorSeccion: (idSeccion) => api.get(`/entrada/entradaPorSeccion/${idSeccion}`),
  save: (entrada) => api.post('/entrada', entrada),
  eliminar: (id) => api.delete(`/entrada/${id}`),
}

export default entradaService
