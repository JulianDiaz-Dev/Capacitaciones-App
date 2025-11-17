import api from './http'

export const asignacionService = {
  asignarCapacitaciones: (asignacion) => api.post('/usuarioCapacitacion', asignacion),
  eliminar: (idUsuario, idCapacitacion) =>
    api.delete(`/usuarioCapacitacion/${idUsuario}/${idCapacitacion}`),
}

export default asignacionService
