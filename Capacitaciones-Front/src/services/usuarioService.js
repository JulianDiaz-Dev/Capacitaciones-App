import api from './http'

export const usuarioService = {
  buscarTodosUsuarios: () => api.get('/usuario'),
  save: (usuario) => api.post('/usuario', usuario),
  eliminar: (id) => api.delete(`/usuario/${id}`),
}

export default usuarioService
