import api from './http'

export const usuarioService = {
  buscarTodosUsuarios: () => api.get('/usuario'),
  save: (usuario) => api.post('/usuario', usuario),
  obtenerUsuariosAsignados: (idCapacitacion) =>
    api.get(`/usuario/usuariosPorCapacitacion/${idCapacitacion}`),
  eliminar: (id) => api.delete(`/usuario/${id}`),

  buscarUsuario: (idUsuario) => api.get(`/usuario/usuarioPorId/${idUsuario}`),
}

export default usuarioService
