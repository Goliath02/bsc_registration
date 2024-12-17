import {createMemoryHistory, createRouter} from 'vue-router'

import MainPage from "@/components/Pages/MainPage.vue";
import DatenschutzPage from "@/components/Pages/DatenschutzPage.vue";
import DefaultRegistration from "@/components/Pages/DefaultRegistration.vue";
import FinanzialRegistration from "@/components/Pages/FinanzialRegistration.vue";
import ConfirmationPage from "@/components/Pages/ConfirmationPage.vue";
import RequestSuccessPage from "@/components/Pages/RequestSuccessPage.vue";

const routes = [

    {
        path: '/', component: MainPage,
        children: [
            {path: '', component: DefaultRegistration, name: "DefaultRegistration"},
            {path: '/kontodaten', component: FinanzialRegistration, name: "FinanzialRegistration"},
            {path: '/zusammenfassung', component: ConfirmationPage, name: "ConfirmationPage"}
        ]
    },
    {path: '/datenschutz', component: DatenschutzPage},
    {path: '/erfolg', component: RequestSuccessPage, name: "Erfolg"},
]

const router = createRouter({
    history: createMemoryHistory(),
    routes,
})

export default router