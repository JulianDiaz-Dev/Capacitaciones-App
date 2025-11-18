import api from './http'

export const asignacionService = {
  asignarCapacitaciones: (asignacion) => api.post('/usuarioCapacitacion', asignacion),
  guardaProgreso: (progreso) => api.post('/usuarioCapacitacion/guardaProgreso', progreso),
  eliminar: (idUsuario, idCapacitacion) =>
    api.delete(`/usuarioCapacitacion/${idUsuario}/${idCapacitacion}`),
}

export default asignacionService
