import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ToastService from 'primevue/toastservice'

import App from './App.vue'
import router from './router'
import piniaPersist from 'pinia-plugin-persistedstate'

import PrimeVue from 'primevue/config'
import 'primevue/resources/themes/lara-light-blue/theme.css'
import 'primevue/resources/primevue.min.css'
import 'primeicons/primeicons.css'
import 'primeflex/primeflex.css'

import Button from 'primevue/button'
import InputText from 'primevue/inputtext'
import Password from 'primevue/password'
import Card from 'primevue/card'
import Message from 'primevue/message'
import Toast from 'primevue/toast'
import Menu from 'primevue/menu'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import ColumnGroup from 'primevue/columngroup'
import Row from 'primevue/row'
import Paginator from 'primevue/paginator'
import Panel from 'primevue/panel'
import Menubar from 'primevue/menubar'
import Dialog from 'primevue/dialog'
import Checkbox from 'primevue/checkbox'
import ConfirmationService from 'primevue/confirmationservice'
import Sidebar from 'primevue/sidebar'
import InputNumber from 'primevue/inputnumber'
import Calendar from 'primevue/calendar'
import Dropdown from 'primevue/dropdown'

const app = createApp(App)
const pinia = createPinia()
pinia.use(piniaPersist)

app.use(pinia)
app.use(router)
app.use(PrimeVue)
app.use(ToastService)

app.component('PvButton', Button)
app.component('PvInputText', InputText)
app.component('PvPassword', Password)
app.component('PvCard', Card)
app.component('PvMessage', Message)
app.component('PvToast', Toast)
app.component('PvMenu', Menu)
app.component('PvDataTable', DataTable)
app.component('PvColumn', Column)
app.component('PvColumnGroup', ColumnGroup)
app.component('PvRow', Row)
app.component('PvPaginator', Paginator)
app.component('PvPanel', Panel)
app.component('PvMenubar', Menubar)
app.component('PvDialog', Dialog)
app.component('PvCheckbox', Checkbox)
app.component('PvConfirmationService', ConfirmationService)
app.component('PvSidebar', Sidebar)
app.component('PvInputNumber', InputNumber)
app.component('PvCalendar', Calendar)
app.component('PvDropdown', Dropdown)

app.mount('#app')
