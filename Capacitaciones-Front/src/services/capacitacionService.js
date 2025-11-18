import api from './http'

export const capacitacionService = {
  buscarTodosCapacitaciones: () => api.get('/capacitacion'),
  save: (capacitacion) => api.post('/capacitacion', capacitacion),
  eliminar: (idCapacitacion) => api.delete(`/capacitacion/${idCapacitacion}`),
  buscarMisCapacitaciones: (idUsuario) => api.get(`/capacitacion/buscarByUsuario/${idUsuario}`),
  buscarCapacitacion: (idCapacitacion) => api.get(`/capacitacion/buscarById/${idCapacitacion}`),
}

export default capacitacionService
