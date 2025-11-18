<template>
  <div class="detalle-capacitacion-container">
    <PvToast ref="toast" />

    <PvCard v-if="capacitacion" :title="capacitacion.titulo">
      <template #content>
        <p><strong>Descripción:</strong> {{ capacitacion.descripcion }}</p>
        <div class="progreso-container">
          <PvProgressBar :value="capacitacion.progreso" :showValue="true" />
        </div>

        <div class="secciones">
          <div
            v-for="seccion in capacitacion.secciones"
            :key="seccion.idSeccion"
            class="seccion"
            :ref="(el) => el && (el._seccionId = seccion.idSeccion)"
          >
            <h3 class="titulo-seccion">{{ seccion.titulo }}</h3>

            <div v-for="entrada in seccion.entradas" :key="entrada.idEntrada" class="entrada">
              <div class="entrada-contenido">
                <div v-if="entrada.archivoUrl" class="archivo-container">
                  <img
                    v-if="isImage(entrada.archivoUrl)"
                    :src="entrada.archivoUrl"
                    alt="Imagen"
                    class="archivo-imagen"
                  />
                  <video v-else-if="isVideo(entrada.archivoUrl)" controls class="archivo-video">
                    <source :src="entrada.archivoUrl" />
                    Tu navegador no soporta video.
                  </video>
                  <iframe
                    v-else-if="isPdf(entrada.archivoUrl)"
                    :src="entrada.archivoUrl"
                    class="archivo-pdf"
                    frameborder="0"
                  ></iframe>
                  <a
                    v-else
                    :href="entrada.archivoUrl"
                    target="_blank"
                    rel="noopener noreferrer"
                    class="archivo-link"
                  >
                    Descargar archivo
                  </a>
                </div>
                <p class="entrada-texto">{{ entrada.contenido }}</p>
              </div>
            </div>
          </div>
        </div>
      </template>
    </PvCard>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
import { useRoute } from 'vue-router'
import { capacitacionService } from '../services/capacitacionService'

export default defineComponent({
  name: 'DetalleCapacitacion',
  data() {
    return {
      capacitacion: null,
      progreso: 0,
    }
  },
  mounted() {
    this.cargarDetalle()
  },
  methods: {
    initObserver() {
      const options = { root: null, threshold: 0.3 }
      const observer = new IntersectionObserver((entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            const idSeccion = entry.target._seccionId
            if (idSeccion !== undefined) {
              this.marcarSeccionVista(idSeccion)
            }
          }
        })
      }, options)
      const elementos = document.querySelectorAll('.seccion')
      elementos.forEach((el) => observer.observe(el))
    },

    marcarSeccionVista(idSeccion) {
      console.log('Sección vista:', idSeccion)
      if (!this.capacitacion.seccionesVistas) this.capacitacion.seccionesVistas = []

      if (!this.capacitacion.seccionesVistas.includes(idSeccion)) {
        this.capacitacion.seccionesVistas.push(idSeccion)
        this.actualizarProgreso()
      }
    },

    actualizarProgreso() {
      const totalSecciones = this.capacitacion.secciones.length
      const vistas = this.capacitacion.seccionesVistas.length
      this.capacitacion.progreso = Math.round((vistas / totalSecciones) * 100)
      console.log('Progreso actualizado:', this.capacitacion.progreso)
    },

    cargarDetalle() {
      const route = useRoute()
      const idCapacitacion = route.params.idCapacitacion
      this.progreso = route.params.progreso || 0

      if (!idCapacitacion) {
        this.$refs.toast.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No se pudo identificar la capacitación',
          life: 3000,
        })
        return
      }

      capacitacionService
        .buscarCapacitacion(idCapacitacion)
        .then((res) => {
          this.capacitacion = res.data
          this.capacitacion.progreso = this.progreso
          this.capacitacion.seccionesVistas = []

          this.$nextTick(() => {
            this.initObserver()
          })
        })
        .catch((err) => {
          console.error(err)
          this.$refs.toast.add({
            severity: 'error',
            summary: 'Error',
            detail: 'No se pudo cargar la capacitación',
            life: 3000,
          })
        })
    },

    isImage(url) {
      return /\.(jpeg|jpg|gif|png|webp)$/i.test(url)
    },

    isVideo(url) {
      return /\.(mp4|webm|ogg)$/i.test(url)
    },

    isPdf(url) {
      return /\.pdf$/i.test(url)
    },
  },
})
</script>

<style scoped>
.detalle-capacitacion-container {
  padding: 2rem;
  max-width: 800px;
  margin: auto;
}

.progreso-container {
  margin-bottom: 1.5rem;
  position: sticky;
  top: 0;
  background: white;
  padding: 1rem 0;
  z-index: 1000;
}

.secciones {
  margin-top: 2rem;
}

.seccion {
  margin-bottom: 2rem;
}

.titulo-seccion {
  font-size: 1.3rem;
  font-weight: bold;
  margin-bottom: 0.5rem;
  border-bottom: 1px solid #ccc;
  padding-bottom: 0.2rem;
}

.entrada {
  margin-bottom: 2rem;
  display: flex;
  justify-content: center;
}

.entrada-contenido {
  text-align: center;
  max-width: 100%;
}

.archivo-container {
  margin-bottom: 0.5rem;
}

.archivo-imagen {
  max-width: 100%;
  max-height: 200px;
  object-fit: contain;
  margin-bottom: 0.5rem;
}

.archivo-video {
  max-width: 100%;
  max-height: 250px;
  margin-bottom: 0.5rem;
}

.archivo-pdf {
  width: 100%;
  height: 400px;
  margin-bottom: 0.5rem;
}

.archivo-link {
  display: block;
  margin-bottom: 0.5rem;
  color: blue;
  text-decoration: underline;
}

.entrada-texto {
  margin-top: 0.5rem;
}
</style>
