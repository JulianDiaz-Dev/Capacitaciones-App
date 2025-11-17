import api from './http'

export const capacitacionService = {
  buscarTodosCapacitaciones: () => api.get('/capacitacion'),
  save: (capacitacion) => api.post('/capacitacion', capacitacion),
  eliminar: (idCapacitacion) => api.delete(`/capacitacion/${idCapacitacion}`),
}

export default capacitacionService
