import api from './http'

export const seccionService = {
  buscarSeccionesPorCapacitacion: (idCapacitacion) =>
    api.get(`/seccion/seccionPorCapacitacion/${idCapacitacion}`),
  save: (seccion) => api.post('/seccion', seccion),
  eliminar: (id) => api.delete(`/seccion/${id}`),
}

export default seccionService
