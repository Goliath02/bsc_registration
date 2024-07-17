import {createMemoryHistory, createRouter} from 'vue-router'

import MainPage from "@/components/Pages/MainPage.vue";
import DatenschutzPage from "@/components/Pages/DatenschutzPage.vue";
import DefaultRegistration from "@/components/Pages/DefaultRegistration.vue";
import FinanzialRegistration from "@/components/Pages/FinanzialRegistration.vue";

const routes = [

	{
		path: '/', component: MainPage,
		children: [
			{path: '', component: DefaultRegistration, name: "DefaultRegistration"},
			{path: '/kontodaten', component: FinanzialRegistration, name: "FinanzialRegistration"}
		]
	},
	{path: '/datenschutz', component: DatenschutzPage},
]

const router = createRouter({
	history: createMemoryHistory(),
	routes,
})

export default router