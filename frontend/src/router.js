import {createRouter, createWebHistory} from 'vue-router'

import MainPage from "@/components/Pages/MainPage.vue";
import DatenschutzPage from "@/components/Pages/DatenschutzPage.vue";
import DefaultRegistration from "@/components/Pages/DefaultRegistration.vue";
import FinanzialRegistration from "@/components/Pages/FinanzialRegistration.vue";
import ConfirmationPage from "@/components/Pages/ConfirmationPage.vue";
import RequestSuccessPage from "@/components/Pages/RequestSuccessPage.vue";
import NotSwimmerMainPage from "@/components/Pages/NotSwimmerMainPage.vue";
import NswRegistration from "@/components/Pages/NswRegistration.vue";
import NswFinanzialRegistration from "@/components/Pages/NswFinanzialRegistration.vue";
import NswConfirmationPage from "@/components/Pages/NswConfirmationPage.vue";

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
    {
        path: '/nichtschwimmer', component: NotSwimmerMainPage, name: "NotSwimmerMainPage",
        children: [
            {path: '', component: NswRegistration, name: "NswDefaultRegistration"},
            {path: '/nswKontodaten', component: NswFinanzialRegistration, name: "NswFinanzialRegistration"},
            {path: '/nwsZusammenfassung', component: NswConfirmationPage, name: "NswConfirmationPage"}
        ]
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router